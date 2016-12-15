package io.maincraft.kissdatepicker;


import android.view.View;

public class kissDatePickerPage {

    private String defaultLabel;
    private String defaultHint;
    private String defaultValue;
    private Integer defaultLenght;
    public View pageView;

    public void setLabel (String defaultLabel) {
        this.defaultLabel = defaultLabel;
    }

    public void setHint (String defaultHint) {
        this.defaultHint = defaultHint;
    }

    public void setValue (String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setLenght (Integer defaultLenght) {
        this.defaultLenght = defaultLenght;
    }

    public String getLabel () {
        return this.defaultLabel;
    }

    public enum DIGIT_TYPE { NONE, DAY, MONTH, YEAR }
    private DIGIT_TYPE digit_type = DIGIT_TYPE.NONE;

    public void setType(DIGIT_TYPE digitType ) {
        this.digit_type = digitType;
    }

    public DIGIT_TYPE getType() {
        return digit_type;
    }

    public String getHint () {
        return this.defaultHint;
    }

    public String getValue () {
        return this.defaultValue;
    }

    public String getFormatedValue () {
        if(this.defaultValue.length() == 1) {
            return "0" + this.defaultValue;
        }
        else if(this.defaultValue.length() == 0) {
            return "00";
        }
        else
        {
            return this.defaultValue;
        }
    }

    public Integer getLenght () {
        return this.defaultLenght;
    }

}
