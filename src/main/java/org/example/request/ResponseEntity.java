package org.example.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

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
