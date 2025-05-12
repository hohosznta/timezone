package org.example.global;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class GlobalResvService {

    private final GlobalResvRepository globalResvRepository;

    @Transactional
    public GlobalResv createReservation(String reservationTimeStr, String timezoneArea) {
        LocalDateTime localDateTime = LocalDateTime.parse(reservationTimeStr);

        GlobalResv reservation = new GlobalResv();
        reservation.setReservationTime(localDateTime, timezoneArea);

        globalResvRepository.save(reservation);

        return reservation;
    }

    public void updateUtcTimesForTimezoneDriftIfNeeded() {
        globalResvRepository.findAll().forEach(resv -> {
            resv.recalculateUtcTime();
            globalResvRepository.save(resv);
        });
    }

}
