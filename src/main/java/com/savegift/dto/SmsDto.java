package com.savegift.dto;

import java.sql.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsDto {
    private String phoneNumber;
    private String certNumber;
    private int count;
    private Date certDate;
    private String deviceId;
}
