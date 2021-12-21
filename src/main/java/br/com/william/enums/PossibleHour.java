package br.com.william.enums;

import br.com.william.domain.hour.Hour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PossibleHour {

    FIRST_HOUR,
    SECOND_HOUR,
    THIRD_HOUR,
    FOURTH_HOUR;

    public static List<Hour> timeGenerator() {
        List<Hour> hours = new ArrayList<>();
        Arrays.stream(PossibleHour.values())
                .map( x -> new Hour(false, x))
                .forEach( hour -> hours.add(hour));
        return hours;
    }
}
