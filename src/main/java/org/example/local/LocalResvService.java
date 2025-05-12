package org.example.local;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class LocalResvService {

    private final LocalResvRepository localResvRepository;

    @Transactional
    public LocalResv createReservation(String reservationTimeStr) {
        LocalDateTime localResvTime = LocalDateTime.parse(reservationTimeStr);

        LocalResv localResv = new LocalResv();
        localResv.setReservationTime(localResvTime);
        localResvRepository.save(localResv);

        return localResv;
    }

}
