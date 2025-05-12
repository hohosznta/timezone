package org.example.global;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.*;
import java.time.zone.ZoneRulesProvider;

@Entity
@Getter
public class GlobalResv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //국제적 "미래" 시간 저장할 때는 타임존, DST에 따라 UTC가 변경되어야 하므로 로컬시간과 함께 타임존, 타임존 버젼 저장 필요, 과거 시간은 변경되지 않으므로 UTC만으로 저장해도 무방. 물론 시간과 지역이 묶여있어야 하는 경우라면 지역도 저장.
    @Column(name = "input_resv_time", columnDefinition = "DATETIME")
    private LocalDateTime inputResvTime;  //로컬시간 + 타임존이지만 디비에서는 그저 로컬시간..

    @Column(name = "utc_resv_time", columnDefinition = "TIMESTAMP")
    private Instant utcResvTime;          //UTC, 쿼리나 필터링 할 때 유용함
    private String tzdb;              //타임존 버젼 저장
    private String timezoneArea;      // 시간대 지역 (예: Asia/Seoul)

    public void setReservationTime(LocalDateTime inputTime, String timezoneArea) {
        Instant utcResvTime = inputTime.atZone(ZoneId.of(timezoneArea)).toInstant();

        this.inputResvTime = inputTime;
        this.utcResvTime = utcResvTime;
        this.timezoneArea = timezoneArea;  // ZonedDateTime에서 타임존 이름 추출
        this.tzdb = ZoneRulesProvider.getVersions(timezoneArea).lastEntry().getKey();

        System.out.println("입력 시간대: " + timezoneArea);
        System.out.println("로컬 시간: " + inputTime);
        System.out.println("UTC 시간: " + utcResvTime);
        System.out.println("UTC->시스템 시간대: " + utcResvTime.atZone(ZoneId.systemDefault()));
    }

    // 예약 시간의 UTC를 재계산하는 메소드, 로컬 시간을 기반으로 UTC를 업뎃함 (타임존 규칙 변경 시 필요)
    public void recalculateUtcTime() {
        ZoneId zoneId = ZoneId.of(this.timezoneArea);

        String currentTzdb = ZoneRulesProvider.getVersions(zoneId.getId()).lastEntry().getKey();

        if (!this.tzdb.equals(currentTzdb)) {
            ZonedDateTime recalculatedTime = ZonedDateTime.ofLocal(
                    this.inputResvTime, zoneId, null
            );

            this.utcResvTime = recalculatedTime.toInstant();
            this.tzdb = currentTzdb;
        }
    }

}