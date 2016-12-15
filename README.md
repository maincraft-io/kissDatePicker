
# kissDatePicker
![Preview](/kissDatePicker_DemoShow.gif)

### How to install:
**Step 1:** Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
**Step 2:** Add it in your root build.gradle at the end of repositories:
```gradle
	dependencies {
	        compile 'com.github.maincraft-io:kissDatePicker:v1'
	}
```

### How to use:
```java

        final kissDatePicker datePicker = new kissDatePicker(this);

        //YOU CAN SWIICH THEM ALL
        datePicker.addPage(new kissDatePickerPage() {
            {
                setLabel("DAY");
                setHint("00");
                setLenght(2);
                setType(DIGIT_TYPE.DAY);
                setValue("");
            }
        });
        datePicker.addPage(new kissDatePickerPage() {
            {
                setLabel("MONTH");
                setHint("00");
                setLenght(2);
                setType(DIGIT_TYPE.MONTH);
                setValue("");
            }
        });

        datePicker.addPage(new kissDatePickerPage() {
            {
                setLabel("YEAR");
                setHint("0000");
                setLenght(4);
                setType(DIGIT_TYPE.YEAR);
                setValue("");
            }
        });

        datePicker.addPage(new kissDatePickerPage() {
            {
                setLabel("LENGHT");
                setHint("0000");
                setLenght(3);
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
                                        //+ " | LENGHT: " + pagesList.get(3).getFormatedValue() + "кг"
                        );
                    }
                });
                datePicker.show();
            }
        });
```

### How to change style:
You just need to override styles:
```xml
    <style name="kissDatePicker" parent="AppTheme">
    </style>
    <style name="kissDatePicker.MainLayout" parent="kissDatePicker">
        <item name="android:gravity">center|center_horizontal</item>
        <item name="android:layout_gravity">center|center_horizontal</item>
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">match_parent</item>
    </style>
    <style name="kissDatePicker.Input" parent="kissDatePicker">
        <item name="android:textSize">30sp</item>
    </style>
    <style name="kissDatePicker.ViewPager" parent="kissDatePicker">
        <item name="android:gravity">top</item>
        <item name="android:layout_gravity">top</item>
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">270dp</item>>
    </style>
    <style name="kissDatePicker.Title" parent="kissDatePicker">
        <item name="android:textSize">30dp</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="kissDatePicker.Layout" parent="kissDatePicker">
        <item name="android:layout_width">100dp</item>
        <item name="android:layout_height">70dp</item>
    </style>
    <style name="kissDatePicker.TextView" parent="kissDatePicker">
        <item name="android:gravity">center</item>
        <item name="android:layout_gravity">center</item>
        <item name="android:textSize">30sp</item>
        <item name="android:textAllCaps">true</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="kissDatePicker.TextViewDigit" parent="kissDatePicker.TextView">
        <item name="android:textSize">40sp</item>
        <item name="android:layout_gravity">center</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="kissDatePicker.TextViewDigitInactive" parent="kissDatePicker.TextViewDigit">
        <item name="android:color">#d7d7d7</item>
    </style>
```
