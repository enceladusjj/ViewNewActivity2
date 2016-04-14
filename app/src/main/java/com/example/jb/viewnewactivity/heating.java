package com.example.jb.viewnewactivity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class heating extends Activity {

    public static final String RETVAL_STRNG = "Error";
    public static boolean selected = true;
    public boolean test = false;
    public String strSel = new String("");
    public CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heating);
        Intent i = getIntent();
        test = i.getBooleanExtra ("visibleButton", test);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setChecked(test);

        //Log.d("tag", "ContentView");
        //Log.d("tag", "CheckBox create");
    }

    public void onClick(View v) {

        if (v.getId() == R.id.checkBox) {
            //strVisBut = String.valueOf(test);
            //Log.d("tagheating", "onclick checkbox");
            //Log.d("tagheating", "test:" + strVisBut);
            //cbox.setSelected(true);
            //Log.d("tag1", "2");
            //cbox.setSelected(false);
            //Log.d("tag1", "3");
            //Log.d("tagheating", "strSel " + strSel);

            selected = checkBox.isChecked();
            strSel = String.valueOf(selected);
            Intent resultData = new Intent();
            resultData.putExtra(RETVAL_STRNG, "retValData: " + selected);
            setResult(Activity.RESULT_OK, resultData);
        }
    }
}
