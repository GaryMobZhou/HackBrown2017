package com.example.garyzhou.snaphistory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {
    TextView saylesHall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        saylesHall = (TextView)findViewById(R.id.saylesHall);
        saylesHall.setMovementMethod(new ScrollingMovementMethod());
        //float [] message = intent.getFloatArrayExtra(MainActivity.LOCATION_INFO);

    }

    public void returnToMain(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
