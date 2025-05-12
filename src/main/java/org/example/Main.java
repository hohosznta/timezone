package org.example;

import org.example.global.GlobalResvService;
import org.example.local.LocalResvService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);

        System.out.println("=================국제 서비스 예약========================");
        String globalResvTime = "2025-06-01T10:00:00";
        String timezoneArea= "Asia/Seoul";

        GlobalResvService globalResvService = context.getBean(GlobalResvService.class);
        globalResvService.createReservation(globalResvTime, timezoneArea);   // 국제 시간 저장
        globalResvService.updateUtcTimesForTimezoneDriftIfNeeded();    //UTC 시간 업데이트

        System.out.println("=================국내 서비스 예약========================");
        String localResvTime = "2025-06-01T10:00:00";

        LocalResvService localResvService = context.getBean(LocalResvService.class);
        localResvService.createReservation(localResvTime);    // 국내 시간 저장


    }
}