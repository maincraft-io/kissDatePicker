package io.maincraft.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.maincraft.kissdatepicker.kissDatePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final kissDatePicker datePicker = new kissDatePicker(this);
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.setOnResultListner(new kissDatePicker.onResultListner() {
                    @Override
                    public void onResult(String Year, String Month, String Day) {
                        ((TextView) findViewById(R.id.input)).setText(Day + "." + Month + "." + Year);
                    }
                });
                datePicker.show();
            }
        });


    }
}
