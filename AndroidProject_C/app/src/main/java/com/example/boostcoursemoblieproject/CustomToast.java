package com.example.boostcoursemoblieproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends Toast {

    Context mContext;
    LayoutInflater layoutInflater;
    View view;
    TextView tv;

    public CustomToast(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public void init() {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.layout_custom_toast, null);
        tv = view.findViewById(R.id.custom_toast_tv1);
    }

    public void makeText(String text, int duration) {
        tv.setText(text);

        show(this, view, duration);

    }

    private void show(CustomToast customToast, View view, int duration) {
        customToast.setDuration(duration);
        customToast.setView(view);
        customToast.show();
    }


}
