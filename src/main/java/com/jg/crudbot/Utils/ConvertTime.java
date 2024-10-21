package com.jg.crudbot.Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConvertTime {
    public static LocalTime convert(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[h][hh]:mm a");
        return LocalTime.parse(time, formatter);
    }
}
