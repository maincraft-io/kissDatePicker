package io.maincraft.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.maincraft.kissdatepicker.kissDatePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new kissDatePicker(this).show();
    }
}
