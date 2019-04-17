package com.pugwoo.webextstarterdemo.web;

import com.pugwoo.webextstarterdemo.bean.WebJsonBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 输出Date类型时，按0时区处理，输出格式yyyy-MM-dd HH:mm:ss
 * @date 2018-06-22
 */
@RestController
public class TestDateToStringController {

    /**
     * http://127.0.0.1:8080/testDataToString?time=20180911
     * @param time
     * @return
     */
    @RequestMapping("/testDateToString")
    public WebJsonBean testDateToString(Date time){
        Map<String, Object> result = new HashMap<>();
        if (time == null) {
            result.put("date", "time参数未提供或者格式错误，请确保time参数正确提供，如 ?time=20180808");
        } else {
            result.put("date", time);
        }
        result.put("now", new Date());
        result.put("localDate", LocalDate.now());
        result.put("localDateTime", LocalDateTime.now());
        result.put("localTime", LocalTime.now());
        return new WebJsonBean(result);
    }

}
