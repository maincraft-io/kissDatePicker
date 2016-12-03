# kissDatePicker
![Preview](/sample.png)


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
	        compile 'com.github.maincraft-io:kissDatePicker:v0.01-beta'
	}
```

### How to use:
```java
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
```

### How to change locale:
You just need to override strings:
```xml
    <string name="kissDatePicker_Day">Day</string>
    <string name="kissDatePicker_Month">Month</string>
    <string name="kissDatePicker_Year">Year</string>
```

### How to change style:
You just need to override styles:
```xml
    <style name="kissDatePicker" parent="AppTheme">
    </style>
    <style name="kissDatePicker.ViewPager" parent="kissDatePicker">
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">250dp</item>>
    </style>
    <style name="kissDatePicker.Title" parent="kissDatePicker">
        <item name="android:textSize">30dp</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="kissDatePicker.TextView" parent="kissDatePicker">
        <item name="android:textSize">25dp</item>
        <item name="android:layout_margin">10dp</item>
        <item name="android:textAllCaps">true</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="kissDatePicker.TextViewDigit" parent="kissDatePicker.TextView">
        <item name="android:textSize">30sp</item>
        <item name="android:layout_gravity">center</item>
        <item name="android:textStyle">bold</item>
    </style>
    <style name="kissDatePicker.TextViewDigitInactive" parent="kissDatePicker.TextViewDigit">
        <item name="android:color">#d7d7d7</item>
    </style>
```
    
