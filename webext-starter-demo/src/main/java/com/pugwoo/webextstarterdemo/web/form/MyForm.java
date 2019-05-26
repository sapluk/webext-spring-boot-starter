package com.pugwoo.webextstarterdemo.web.form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MyForm {

    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date myDate;

    public Date getMyDate() {
        return myDate;
    }

    public void setMyDate(Date myDate) {
        this.myDate = myDate;
    }
}
