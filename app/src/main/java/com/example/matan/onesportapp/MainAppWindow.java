package com.example.matan.onesportapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.matan.onesportapp.Cache.UserCache;
import com.example.matan.onesportapp.Fragments.DialogFragment.AddEventDialog;
import com.example.matan.onesportapp.Fragments.MainWindowFragments.BlankFragment;
import com.example.matan.onesportapp.Fragments.MainWindowFragments.BlankFragment2;
import com.example.matan.onesportapp.Fragments.MainWindowFragments.ItemFragment;
import com.example.matan.onesportapp.Util.DummyContent;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class MainAppWindow extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener, AddEventDialog.OnDialogFragmentClickListener {

    private TextView mTextMessage;
    private Toolbar toolbar;
    private BottomNavigationView navigation;
    private Fragment activeFragment;
    private SearchView searchView;
    private FloatingActionButton addEventBtm;

    Object obj = new Integer(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_window);

        // setup the nevigation bar
        this.mTextMessage = findViewById(R.id.message);
        this.navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // set up the add event buttom
        this.addEventBtm = findViewById(R.id.AddEventButton);
        this.addEventBtm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AddEventDialog addEventDialog = new AddEventDialog();
                addEventDialog.show(getSupportFragmentManager(),"title");
            }
        });

        // init ui element
        this.initToolBar();

        // init the default fragment
        this.activeFragment = new ItemFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameMainApp, activeFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.logout_toolbar:
                this.logout();

            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        // logout from facebook if the user logged in using facebook.
        if(AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }

        // clear the cache;
        UserCache.clear();

        Intent intent = new Intent(getApplicationContext(), LoginWindow.class);
        startActivity(intent);
        finish();
        // TODO: logout from the server
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    activeFragment = new ItemFragment();
                    break;
                case R.id.navigation_dashboard:
                    activeFragment = new BlankFragment();
                    break;
                case R.id.navigation_notifications:
                    activeFragment = new BlankFragment2();
                    break;
                default:
                    return false;
            }

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameMainApp, activeFragment)
                    .commit();
            return true;
        }
    };

    public void onListFragmentInteraction(DummyContent.DummyItem item){
        // TODO: implement
    }

    private void initToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public void onOkClicked(AddEventDialog dialog) {

    }
}
