# kissDatePicker

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
