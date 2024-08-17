package org.example.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEntity {
    public String date;
    public boolean business_day;

    @Override
    public String toString() {
        return "BusinessDay{" +
                "date='" + date + '\'' +
                ", business_day=" + business_day +
                '}';
    }
}
