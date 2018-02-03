package com.example.matan.onesportapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.matan.onesportapp.Cache.UserCache;
import com.example.matan.onesportapp.Util.Pair;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterWindow extends AppCompatActivity {

    private ImageView imageProfile;
    private ImageView trainerSwichImg;
    private Pair<ImageButton,Boolean> imageFootball;
    private Pair<ImageButton,Boolean> imageYoga;
    private Pair<ImageButton,Boolean> imageGYM;
    private Pair<ImageButton,Boolean> imageBasketBall;
    private Pair<ImageButton,Boolean> imageRunning;
    private Pair<ImageButton,Boolean> imageCycling;
    private TextView questionOptionVIew;
    private EditText regFirstName;
    private EditText regLastName;
    private EditText regEmail;
    private EditText reqBirthday;
    private Spinner spinnerOtherWorkout;
    private Button continueBtm;
    private SwitchCompat regTrainerSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_window);

        // Init UI element
        this.trainerSwichImg = findViewById(R.id.trainerSwichImg);
        this.questionOptionVIew = findViewById(R.id.questionTextOption);
        this.imageProfile = findViewById(R.id.ProfilePicture);
        this.continueBtm = findViewById(R.id.registerContinueBtm);
        this.continueBtm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveUserDataAndCreateUser();

                // Continue to the main app window
                Intent intent = new Intent(getApplicationContext(), MainAppWindow.class);
                startActivity(intent);
                finish();
            }
        });
        this.regTrainerSwitch = findViewById(R.id.trainerSwich);
        this.regTrainerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean trainer) {
                if(trainer){
                    questionOptionVIew.setText(R.string.what_is_your_field);
                    trainerSwichImg.setImageResource(R.drawable.ic_trainer);
                }
                else {
                    questionOptionVIew.setText(R.string.what_is_your_workout);
                    trainerSwichImg.setImageResource(0);
                }
            }
        });

        // text field
        this.regFirstName = findViewById(R.id.regFirstName);
        this.regLastName = findViewById(R.id.regLastName);
        this.regEmail = findViewById(R.id.regEmail);
        this.reqBirthday = findViewById(R.id.regBirthday);

        // icon sport category
        this.imageFootball = new Pair<ImageButton, Boolean>((ImageButton)findViewById(R.id.footballImg), false);
        this.imageYoga = new Pair<ImageButton, Boolean>((ImageButton)findViewById(R.id.yogaImg), false);
        this.imageGYM = new Pair<ImageButton, Boolean>((ImageButton)findViewById(R.id.gymImg), false);
        this.imageBasketBall = new Pair<ImageButton, Boolean>((ImageButton)findViewById(R.id.basketballImg), false);
        this.imageRunning = new Pair<ImageButton, Boolean>((ImageButton)findViewById(R.id.runningImg), false);
        this.imageCycling = new Pair<ImageButton, Boolean>((ImageButton)findViewById(R.id.cyclingImg), false);

        // other sport type spinner
        this.spinnerOtherWorkout = (Spinner)findViewById(R.id.workoutTypesSpinner);

        // Set the UI element if there is data
        this.loadProfilePicture();
        this.loadUserData();
        this.initSportImg();
        this.initDataPickerDialog();
    }



    @Override
    public void onBackPressed() {
        // logout from facebook if the user logged in using facebook.
        if(AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }
        // clear the cache.
        UserCache.clear();

        Intent intent = new Intent(getApplicationContext(), LoginWindow.class);
        startActivity(intent);
        finish();
    }

    private void loadProfilePicture(){
        if(AccessToken.getCurrentAccessToken() != null) {
            Picasso.with(this).load(UserCache.getUser().getUserProfilePictureUrl()).into(this.imageProfile);
        }
    }

    private void loadUserData(){
        if(UserCache.getUser() == null) {
            return;
        }

        // FirstName
        if(UserCache.getUser().getUserFirstName() != null && !UserCache.getUser().getUserFirstName().isEmpty()){
            this.regFirstName.setText(UserCache.getUser().getUserFirstName());
        }
        // LastName
        if(UserCache.getUser().getUserLastName() != null && !UserCache.getUser().getUserLastName().isEmpty()){
            this.regLastName.setText(UserCache.getUser().getUserLastName());
        }
        // Email
        if(UserCache.getUser().getUserEmail() != null && !UserCache.getUser().getUserEmail().isEmpty()){
            this.regEmail.setText(UserCache.getUser().getUserEmail());
        }
        // Birthday
        if(UserCache.getUser().getUserBirthDay() != null){
            this.reqBirthday.setText(DateFormat.getDateInstance().format(UserCache.getUser().getUserBirthDay()));
        }
    }

    private void initSportImg(){
        this.imageFootball.getFirst().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!imageFootball.getSecond()){
                    v.setBackgroundResource(R.drawable.green_border);
                    imageFootball.setSecond(true);
                }
                else {
                    v.setBackgroundResource(R.color.BasicColor);
                    imageFootball.setSecond(false);
                }
            }
        });

        this.imageBasketBall.getFirst().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!imageBasketBall.getSecond()){
                    v.setBackgroundResource(R.drawable.green_border);
                    imageBasketBall.setSecond(true);
                }
                else {
                    v.setBackgroundResource(R.color.BasicColor);
                    imageBasketBall.setSecond(false);
                }
            }
        });

        this.imageCycling.getFirst().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!imageCycling.getSecond()){
                    v.setBackgroundResource(R.drawable.green_border);
                    imageCycling.setSecond(true);
                }
                else {
                    v.setBackgroundResource(R.color.BasicColor);
                    imageCycling.setSecond(false);
                }
            }
        });

        this.imageRunning.getFirst().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!imageRunning.getSecond()){
                    v.setBackgroundResource(R.drawable.green_border);
                    imageRunning.setSecond(true);
                }
                else {
                    v.setBackgroundResource(R.color.BasicColor);
                    imageRunning.setSecond(false);
                }
            }
        });

        this.imageGYM.getFirst().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!imageGYM.getSecond()){
                    v.setBackgroundResource(R.drawable.green_border);
                    imageGYM.setSecond(true);
                }
                else {
                    v.setBackgroundResource(R.color.BasicColor);
                    imageGYM.setSecond(false);
                }
            }
        });

        this.imageYoga.getFirst().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!imageYoga.getSecond()){
                    v.setBackgroundResource(R.drawable.green_border);
                    imageYoga.setSecond(true);
                }
                else {
                    v.setBackgroundResource(R.color.BasicColor);
                    imageYoga.setSecond(false);
                }
            }
        });

    }

    private void initDataPickerDialog(){
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        this.reqBirthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterWindow.this,date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(Calendar myCalendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        this.reqBirthday.setText(sdf.format(myCalendar.getTime()));
    }


    //TODO: implement user data save and create
    private void saveUserDataAndCreateUser(){

    }
}
