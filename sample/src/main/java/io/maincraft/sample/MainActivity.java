package io.maincraft.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import io.maincraft.kissdatepicker.kissDatePicker;
import io.maincraft.kissdatepicker.kissDatePickerPage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final kissDatePicker datePicker = new kissDatePicker(this);


        datePicker.addPage(new kissDatePickerPage() {
            {
                setLabel("День");
                setHint("00");
                setLenght(2);
                setType(DIGIT_TYPE.DAY);
                setValue("");
            }
        });
        datePicker.addPage(new kissDatePickerPage() {
            {
                setLabel("Месяц");
                setHint("00");
                setLenght(2);
                setType(DIGIT_TYPE.MONTH);
                setValue("");
            }
        });

        datePicker.addPage(new kissDatePickerPage() {
            {
                setLabel("Год");
                setHint("0000");
                setLenght(4);
                setType(DIGIT_TYPE.YEAR);
                setValue("");
            }
        });







        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.setOnResultListner(new kissDatePicker.onResultListner() {
                    @Override
                    public void onResult(List<kissDatePickerPage> pagesList)
                    {
                        ((TextView) findViewById(R.id.input)).setText(
                                pagesList.get(0).getFormatedValue() + "."
                                        + pagesList.get(1).getFormatedValue()
                                        + "." + pagesList.get(2).getFormatedValue()
                                        //+ " | Вес питомца: " + pagesList.get(3).getFormatedValue() + "кг"
                        );
                    }
                });
                datePicker.show();
            }
        });


    }
}
