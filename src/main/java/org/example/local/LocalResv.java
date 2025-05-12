package org.example.local;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.*;

@Entity
@Getter
@Setter
class LocalResv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //국내는 타임존, DST가 없으므로 LocalDateTime으로 현재 시간 저장해도 무방함. 추후 확장성을 고려해서 iana, tzdb, utc 저장해도 됨.
    private LocalDateTime inputTime;

    public void setReservationTime(LocalDateTime inputTime) {
        this.inputTime = inputTime;
    }

}