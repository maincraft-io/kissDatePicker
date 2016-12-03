package io.maincraft.kissdatepicker;

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
    private int selectedMonth = 0;
    private int selectedDay = 0;

    public kissDatePicker() {

    }


    public kissDatePicker(FragmentActivity _context) {
        context = _context;
    }


    public static final kissDatePicker newInstance(FragmentActivity context) {
        kissDatePicker f = new kissDatePicker(context);
        return f;
    }

    public void show() {

        myViewPagerAdapter = new kissDatePicker.MyViewPagerAdapter();
        pagerLayout = context.getLayoutInflater().inflate(R.layout.kissdatepicker, null, false);

        YearInput = ((TextView) pagerLayout.findViewById(R.id.kissDatePicker_Year));
        MonthInput = ((TextView) pagerLayout.findViewById(R.id.kissDatePicker_Month));
        DayInput = ((TextView) pagerLayout.findViewById(R.id.kissDatePicker_Day));

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
                .positiveText("Next")
                .show();
    }


    public class MyViewPagerAdapter extends PagerAdapter {

        public MyViewPagerAdapter() {

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


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            final View view;
            RelativeLayout digitInputRemove;


            switch (position) {
                default:
                    view = context.getLayoutInflater().inflate(R.layout.kissdatepicker_digits, container, false);
                   //((TextView) view.findViewById(R.id.kissDatePicker_title)).setText("ГОД");

                    for (int i = 0; i <= 9; i++) {
                        RelativeLayout digitInput = ((RelativeLayout) view.findViewWithTag(TAG_DIGIT + String.valueOf(i)));
                        digitInput.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View objectView) {
                                String text = ((TextView) objectView.findViewWithTag("value")).getText().toString();
                                YearInput.append(text);
                                DayInput.setText("");
                            }
                        });

                    }

                    digitInputRemove = ((RelativeLayout) view.findViewWithTag(TAG_DIGIT + "remove"));
                    digitInputRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = YearInput.getText().toString();
                            if (text.length() > 0) {
                                YearInput.setText(text.substring(0, text.length() - 1));
                            }
                        }
                    });

                    break;

                case 1:


                    view = context.getLayoutInflater().inflate(R.layout.kissdatepicker_months, container, false);
                    //((TextView) view.findViewById(R.id.kissDatePicker_title)).setText("МЕСЯЦ");

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat month_date = new SimpleDateFormat("MMM");

                    for (int i = 1; i <= 12; i++) {

                        final int currentMonthId = i;

                        cal.set(Calendar.MONTH, (i - 1));
                        String monthName = month_date.format(cal.getTime());
                        TextView month = ((TextView) view.findViewWithTag(TAG_MONTH + String.valueOf(i)));
                        month.setText(monthName);
                        month.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text = ((TextView) view).getText().toString();
                                MonthInput.setText(text);
                                selectedMonth = currentMonthId - 1;
                            }
                        });

                    }


                    break;
                case 2:
                    view = context.getLayoutInflater().inflate(R.layout.kissdatepicker_digits, container, false);
                    //((TextView) view.findViewById(R.id.kissDatePicker_title)).setText("ДЕНЬ");


                    for (int i = 0; i <= 9; i++) {
                        RelativeLayout digitInput = ((RelativeLayout) view.findViewWithTag(TAG_DIGIT + String.valueOf(i)));
                        digitInput.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View viewObject) {
                                String text = ((TextView) viewObject.findViewWithTag("value")).getText().toString();
                                DayInput.append(text);
                                selectedDay = Integer.valueOf(DayInput.getText().toString());
                                hideInvalidDays(view);
                            }
                        });

                    }

                    digitInputRemove = ((RelativeLayout) view.findViewWithTag(TAG_DIGIT + "remove"));
                    digitInputRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View viewObject) {
                            String text = DayInput.getText().toString();
                            if (text.length() > 0) {
                                DayInput.setText(text.substring(0, text.length() - 1));
                                selectedDay = (!DayInput.getText().toString().isEmpty()) ?
                                        Integer.valueOf(DayInput.getText().toString()) : 0;
                                hideInvalidDays(view);
                            }
                        }
                    });

                    break;
            }

            container.addView(view);

            return view;
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