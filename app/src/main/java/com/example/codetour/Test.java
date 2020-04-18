package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Test extends AppCompatActivity {
    private TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test = (TextView)findViewById(R.id.testText);
        Intent intent = getIntent();
        Tour tour=(Tour)intent.getSerializableExtra("class");
        //String name = intent.getExtras().getString("name");
        String s = tour.name;
        test.setText(s);
    }
}
