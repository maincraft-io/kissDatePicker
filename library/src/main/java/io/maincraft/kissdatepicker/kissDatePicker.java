package io.maincraft.kissdatepicker;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class kissDatePicker {

    private FragmentActivity context;
    private kissDatePicker.MyViewPagerAdapter myViewPagerAdapter;
    public View pagerLayout;
    public TextView YearInput;
    public TextView MonthInput;
    public TextView DayInput;


    private int selectedYear = 0;
    private int selectedMonth = -1;
    private int selectedDay = 0;

    public kissDatePicker() {

    }

    public void setYear(int Year) {
        selectedYear = Year;
    }

    public void setMonth(int Month) {
        selectedMonth = (Month-1);
    }

    public void setDay(int Day) {
        selectedDay = Day;
    }

    private onResultListner listener_onResult;
    private onLongClickListner listener_onLongClick;

    public interface onResultListner {
        public void onResult(String Year, String Month, String Day);
    }

    public interface onLongClickListner {
        public boolean onLongClick(int position);
    }

    // Assign the listener implementing events interface that will receive the events
    public void setOnResultListner(onResultListner listener) {
        this.listener_onResult = listener;
    }
    public void setOnLongClickListner(onLongClickListner listener) { this.listener_onLongClick = listener;}


    public kissDatePicker(FragmentActivity _context) {
        context = _context;
    }


    public static final kissDatePicker newInstance(FragmentActivity context) {
        kissDatePicker f = new kissDatePicker(context);
        return f;
    }

    public String getShortMonthName(int monthId) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        cal.set(Calendar.MONTH, (monthId));
        String monthName = month_date.format(cal.getTime());
        return monthName;
    }


    public void show() {

        myViewPagerAdapter = new kissDatePicker.MyViewPagerAdapter();
        pagerLayout = context.getLayoutInflater().inflate(R.layout.kissdatepicker, null, false);

        YearInput = ((TextView) pagerLayout.findViewById(R.id.kissDatePicker_Year));
        MonthInput = ((TextView) pagerLayout.findViewById(R.id.kissDatePicker_Month));
        DayInput = ((TextView) pagerLayout.findViewById(R.id.kissDatePicker_Day));

        YearInput.setText((selectedYear !=0) ? String.valueOf(selectedYear) : "");
        MonthInput.setText((selectedMonth >= 0) ? getShortMonthName(selectedMonth) : "");
        DayInput.setText((selectedDay !=0) ? String.valueOf(selectedDay) : "");


        YearInput.clearFocus();
        MonthInput.clearFocus();
        DayInput.clearFocus();

        final ViewPager viewPager = (ViewPager) pagerLayout.findViewById(R.id.kissDatePicker);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    default:
                        YearInput.requestFocus();
                        MonthInput.clearFocus();
                        DayInput.clearFocus();
                        break;
                    case 1:
                        YearInput.clearFocus();
                        MonthInput.requestFocus();
                        DayInput.clearFocus();
                        break;
                    case 2:
                        YearInput.clearFocus();
                        MonthInput.clearFocus();
                        DayInput.requestFocus();
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        YearInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 4) {
                    selectedYear = Integer.valueOf(editable.toString());
                    viewPager.setCurrentItem(1);
                }
            }
        });

        YearInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.requestFocus();
                viewPager.setCurrentItem(0);
                return true;
            }
        });

        MonthInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.requestFocus();
                viewPager.setCurrentItem(1);

                return true;
            }
        });

        MonthInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 3) {
                    viewPager.setCurrentItem(2);
                }
            }
        });


        DayInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.requestFocus();
                viewPager.setCurrentItem(2);
                return true;
            }
        });


        new MaterialDialog.Builder(context)
                .customView(pagerLayout, false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        String yearValue = (String.valueOf(selectedYear));
                        yearValue = (yearValue.length() == 1) ? ("0000") : yearValue;

                        String monthValue = (String.valueOf(selectedMonth+1));
                        monthValue = (monthValue.length() == 1 && selectedMonth+1 !=0 ) ? ("0" + monthValue) : monthValue;

                        String daysValue = (String.valueOf(selectedDay));
                        daysValue = (daysValue.length() == 1) ? ("0" + daysValue) : daysValue;


                        listener_onResult.onResult(yearValue, monthValue, daysValue);

                    }
                })
                .positiveText("Complete")
                .show();
    }

    private String TAG_DIGIT = "kissDatePicker_digit_";
    private String TAG_MONTH = "kissDatePicker_month_";

    private int getDaysInMonth(int Year, int Month) {
        GregorianCalendar calendar = new GregorianCalendar(Year, Month, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return daysInMonth;
    }

    private void hideInvalidDays(final View view) {

        int dayDaysInMonth = getDaysInMonth(selectedYear, selectedMonth);

        for (int i = 0; i <= 9; i++) {
            RelativeLayout digitInput = ((RelativeLayout) view.findViewWithTag(TAG_DIGIT + String.valueOf(i)));
            if ((selectedDay * 10 + i) <= dayDaysInMonth) {
                TextView digitText = ((TextView) digitInput.findViewWithTag("value"));
                digitText.setEnabled(true);

                digitInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View objectView) {
                        String text = ((TextView) objectView.findViewWithTag("value")).getText().toString();
                        DayInput.append(text);
                        selectedDay = Integer.valueOf(DayInput.getText().toString());
                        hideInvalidDays(view);
                    }
                });
            } else {
                TextView digitText = ((TextView) digitInput.findViewWithTag("value"));
                digitText.setEnabled(false);
                digitInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }


        }

    }


    public View daysView;
    public View monthView;
    public View yearsView;

    public class MyViewPagerAdapter extends PagerAdapter {

        public MyViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            RelativeLayout digitInputRemove;

            if (position == 0) {
                yearsView = context.getLayoutInflater().inflate(R.layout.kissdatepicker_digits, container, false);
                for (int i = 0; i <= 9; i++) {
                    RelativeLayout digitInput = ((RelativeLayout) yearsView.findViewWithTag(TAG_DIGIT + String.valueOf(i)));
                    digitInput.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View objectView) {
                            String text = ((TextView) objectView.findViewWithTag("value")).getText().toString();

                            if(YearInput.getText().toString().length() < 4) {
                                YearInput.append(text);
                                DayInput.setText("");
                                selectedDay = 0;
                            }
                        }
                    });

                }

                digitInputRemove = ((RelativeLayout) yearsView.findViewWithTag(TAG_DIGIT + "remove"));
                digitInputRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = YearInput.getText().toString();
                        if (text.length() > 0) {
                            YearInput.setText(text.substring(0, text.length() - 1));
                        }
                    }
                });

                container.addView(yearsView);
                return yearsView;
            }
            if (position == 1) {

                monthView = context.getLayoutInflater().inflate(R.layout.kissdatepicker_months, container, false);

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMM");

                for (int i = 1; i <= 12; i++) {

                    final int currentMonthId = i;
                    cal.set(Calendar.MONTH, (i - 1));
                    String monthName = month_date.format(cal.getTime());
                    TextView month = ((TextView) monthView.findViewWithTag(TAG_MONTH + String.valueOf(i)));
                    month.setText(monthName);
                    month.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View objectView) {
                            String text = ((TextView) objectView).getText().toString();
                            MonthInput.setText(text);
                            DayInput.setText("");
                            selectedDay = 0;
                            selectedMonth = currentMonthId - 1;
                            hideInvalidDays(daysView);
                        }
                    });

                }

                container.addView(monthView);
                return monthView;

            }
            else if (position == 2) {
                daysView = context.getLayoutInflater().inflate(R.layout.kissdatepicker_digits, container, false);
                hideInvalidDays(daysView);

                digitInputRemove = ((RelativeLayout) daysView.findViewWithTag(TAG_DIGIT + "remove"));
                digitInputRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View viewObject) {
                        String text = DayInput.getText().toString();
                        if (text.length() > 0) {
                            DayInput.setText(text.substring(0, text.length() - 1));
                            selectedDay = (!DayInput.getText().toString().isEmpty()) ?
                                    Integer.valueOf(DayInput.getText().toString()) : 0;
                            hideInvalidDays(daysView);
                        }
                    }
                });

                container.addView(daysView);
                return daysView;

            }

            return new Object();
        }


        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

    }
}