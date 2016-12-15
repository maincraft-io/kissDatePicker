package io.maincraft.kissdatepicker;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class kissDatePicker {

    private FragmentActivity context;
    private kissDatePicker.MyViewPagerAdapter myViewPagerAdapter;
    public View pagerLayout;

    public kissDatePicker() {

    }

    private onResultListner listener_onResult;
    private onLongClickListner listener_onLongClick;

    public interface onResultListner {
        public void onResult(List<kissDatePickerPage> pagesList);
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

    MaterialEditText pagerInput;
    RelativeLayout pagerInputRemove;
    ViewPager viewPager;
    MaterialDialog box;

    private String TAG_DIGIT = "kissDatePicker_digit_";


    public void show() {
        myViewPagerAdapter = new kissDatePicker.MyViewPagerAdapter();
        pagerLayout = context.getLayoutInflater().inflate(R.layout.kissdatepicker, null, false);

        pagerInput = ((MaterialEditText) pagerLayout.findViewById(R.id.kissDatePickerInput));
        pagerInputRemove = (RelativeLayout) pagerLayout.findViewWithTag(TAG_DIGIT + "remove");


        final TextView pagerTitle = ((TextView) pagerLayout.findViewById(R.id.kissDatePickerTitle));

        pagerInput.setHint(pageList.get(0).getHint());
        //pagerInput.setMaxCharacters(pageList.get(0).getLenght());
        pagerInput.setRawInputType(Configuration.KEYBOARD_QWERTY);
        pagerInput.setText(pageList.get(0).getValue());
        pagerInput.setSelection(pagerInput.getText().length());
        setBackspaceListner(0);

        pagerTitle.setText(pageList.get(0).getLabel());


        viewPager = (ViewPager) pagerLayout.findViewById(R.id.kissDatePicker);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position)
            {
                pagerInput.setText(pageList.get(position).getValue());
                pagerInput.setHint(pageList.get(position).getHint());
                pagerInput.setSelection(pagerInput.getText().length());
                //pagerInput.setMaxCharacters(pageList.get(position).getLenght());
                pagerTitle.setText(pageList.get(position).getLabel());
                setBackspaceListner(position);
                hideInvalidValues(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        box = new MaterialDialog.Builder(context)
                .customView(pagerLayout, false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        listener_onResult.onResult(pageList);

                    }
                })
                .show();
    }


    private void setBackspaceListner(final int position) {

        pagerInputRemove.setOnClickListener(null);
        pagerInputRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = pagerInput.getText().toString();
                if (text.length() > 0) {
                    pagerInput.setText(text.substring(0, text.length() - 1));
                    pagerInput.setSelection(pagerInput.getText().toString().length());
                    pageList.get(position).setValue(pagerInput.getText().toString());
                    hideInvalidValues(position);
                }
            }
        });
    }


    String currentDay = "";
    String currentMonth = "";
    String currentYear = "";

    private void getSelectedOptions() {
        for (kissDatePickerPage page : pageList) {
            switch (page.getType()) {
                case DAY:
                    currentDay = page.getValue();
                    currentDay = (currentDay.isEmpty()) ? "0" : currentDay;
                    break;
                case MONTH:
                    currentMonth = page.getValue();
                    currentMonth = (currentMonth.isEmpty()) ? "0" : currentMonth;
                    break;
                case YEAR:
                    currentYear = page.getValue();
                    break;
            }
        }
    }


    private void enableDigit(final Boolean isEnabled, final TextView digitText, final Integer position)
    {
        digitText.setEnabled(isEnabled);
        ((RelativeLayout) digitText.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(isEnabled) {
                    if(pagerInput.getText().toString().length() + 1 <= pageList.get(position).getLenght())
                    {
                        pagerInput.append(digitText.getText().toString());
                        pageList.get(position).setValue(pagerInput.getText().toString());
                        hideInvalidValues(position);
                    }

                }
            }
        });
    }

    private void hideInvalidValues(int position)
    {
            getSelectedOptions();

            kissDatePickerPage page = pageList.get(position);

                    switch (page.getType()) {
                        case DAY:

                                for (int i = 0; i <= 9; i++) {
                                    RelativeLayout digitInput = ((RelativeLayout) page.pageView.findViewWithTag(TAG_DIGIT + String.valueOf(i)));
                                    TextView digitText = ((TextView) digitInput.findViewWithTag("value"));

                                    //Получим кол-во дней в выбранном месяце и годе:
                                    GregorianCalendar calendar = new GregorianCalendar(
                                            currentYear.length() == 4 ? (Integer.valueOf(currentYear)) : 2000,
                                            currentMonth.length() > 0 ? (Integer.valueOf(currentMonth) - 1) : 0,
                                            1);
                                    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                                    if ( (i !=0 || !currentDay.equals("0")) &&  Integer.valueOf(currentDay) * 10 + i <= daysInMonth)
                                    {
                                        enableDigit(true, digitText, position);
                                    }
                                    else {
                                        enableDigit(false, digitText, position);
                                    }
                                }

                            break;
                        case MONTH:

                            //Массив кол-ва дней для каждого из месяцев:
                            List<Integer> daysInMonth = new ArrayList<>();

                            daysInMonth.add(0);
                            //Получаем массив кол-ва дней для выбранного или невыбранного года:
                            for (int month_i = 0; month_i < 12; month_i++) {
                                //Получим кол-во дней в выбранном месяце и годе:
                                GregorianCalendar calendar = new GregorianCalendar(
                                        currentYear.length() == 4 ? (Integer.valueOf(currentYear)) : 2000,
                                        month_i,
                                        1);
                                daysInMonth.add(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            }

                            for (int i = 0; i <= 9; i++) {

                                RelativeLayout digitInput = ((RelativeLayout) page.pageView.findViewWithTag(TAG_DIGIT + String.valueOf(i)));
                                TextView digitText = ((TextView) digitInput.findViewWithTag("value"));

                                if (daysInMonth.size() > (Integer.valueOf(currentMonth) * 10) + i
                                        && Integer.valueOf(currentDay) <= daysInMonth.get((Integer.valueOf(currentMonth) * 10 + i))) {
                                    enableDigit(true, digitText, position);
                                } else {
                                    enableDigit(false, digitText, position);
                                }

                            }

                            break;

                        case YEAR:


                            int sinceFactor = (currentYear.length() == 1)
                                    ? 1000
                                    : (currentYear.length() == 2)
                                    ? 100
                                    : (currentYear.length() == 3)
                                    ? 10
                                    : (currentYear.length() == 4) ? 1 : 0;


                            //Массив годов
                            List<Integer> yearsList = new ArrayList<>();

                            //Получаем массив кол-ва дней для выбранного или невыбранного года:
                            List<String> foundYears = new ArrayList<>();

                            if(currentYear.length() > 0 && currentYear.length() < 4) {
                                for (int year_i = (Integer.valueOf(currentYear) * sinceFactor); year_i <= Calendar.getInstance().get(Calendar.YEAR); year_i++) {
                                    String loopYear = String.valueOf(year_i);
                                    if (loopYear.substring(0, currentYear.length()).equals(currentYear)) {

                                        String yearFactor = String.valueOf(loopYear.charAt(currentYear.length()));

                                        //Получим кол-во дней в выбранном месяце и годе:
                                        GregorianCalendar calendar = new GregorianCalendar(
                                                year_i,
                                                currentMonth.length() > 0 ? (Integer.valueOf(currentMonth) - 1) : 0,
                                                1);

                                        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                                        if (!foundYears.contains(yearFactor)) {
                                            if (daysInYear >= Integer.valueOf(currentDay)) {
                                                foundYears.add(yearFactor);
                                            }
                                        }

                                    }
                                }
                            }
                            //Отключаем все года начинающиеся не с 1000, 2000годов
                            else if(currentYear.length() == 0) {
                                    foundYears.add("1");
                                    foundYears.add("2");
                            }



                            for (int i = 0; i <= 9; i++)
                            {

                                RelativeLayout digitInput = ((RelativeLayout) page.pageView.findViewWithTag(TAG_DIGIT + String.valueOf(i)));
                                TextView digitText = ((TextView) digitInput.findViewWithTag("value"));

                                if (foundYears.contains(String.valueOf(i)))
                                {
                                    enableDigit(true, digitText, position);
                                }
                                else
                                {
                                    enableDigit(false, digitText, position);
                                }

                            }

                            break;

                        default:
                            for (int i = 0; i <= 9; i++)
                            {

                                RelativeLayout digitInput = ((RelativeLayout) page.pageView.findViewWithTag(TAG_DIGIT + String.valueOf(i)));
                                TextView digitText = ((TextView) digitInput.findViewWithTag("value"));

                                if(pagerInput.getText().toString().length() < pageList.get(position).getLenght() ) {
                                    enableDigit(true, digitText, position);
                                }
                                else {
                                    enableDigit(false, digitText, position);
                                }

                            }
                            break;

                    }

    }



    private List<kissDatePickerPage> pageList = new ArrayList<>();

    public void addPage(kissDatePickerPage page) {
        pageList.add(page);
    }



    public class MyViewPagerAdapter extends PagerAdapter {

        public MyViewPagerAdapter() {

        }



        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            RelativeLayout digitInputRemove;
            View digital_input = context.getLayoutInflater().inflate(R.layout.kissdatepicker_digits, container, false);
            digital_input.findViewWithTag(TAG_DIGIT + "apply").setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(position+1 == pageList.size())
                    {
                        box.dismiss();
                        listener_onResult.onResult(pageList);
                    }
                    else
                    {
                        viewPager.setCurrentItem(position+1);
                    }

                }
            });
            digital_input.findViewWithTag(TAG_DIGIT + "back").setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(position-1 >= 0)
                    {
                        viewPager.setCurrentItem(position-1);
                    }
                    else if (position-1 < 0)
                    {
                        viewPager.setCurrentItem(pageList.size());
                    }

                }
            });
            pageList.get(position).pageView = digital_input;
            hideInvalidValues(position);
            container.addView(digital_input);


            return digital_input;
        }


        @Override
        public int getCount() {
            return pageList.size();
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