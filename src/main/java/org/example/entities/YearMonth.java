package org.example.entities;

import lombok.Getter;

@Getter
public class YearMonth {
    private String year;
    private String month;
    private String day;

    public YearMonth(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }


    @Override
    public String toString() {
        return "Year: " + year + ", Month: " + month + ", Day: " + day;
    }
}
