package com.example.matan.onesportapp.Fragments.DialogFragment;

import android.app.Activity;
import android.support.v4.app.DialogFragment;

public class BaseDialogFragment<T> extends DialogFragment {

    private T mActivityInstance;

    @Override
    public void onAttach(Activity activity){
        this.mActivityInstance = (T)activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mActivityInstance = null;
    }
}
