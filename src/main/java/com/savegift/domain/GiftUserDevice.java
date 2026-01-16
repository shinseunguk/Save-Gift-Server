package com.savegift.domain;

import java.sql.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GiftUserDevice {
    private int seq;
    private String userId;
    private String imgUrl;
    private String brand;
    private String barcodeNumber;
    private Date expirationPeriod;
    private Date registrationDate;
    private int useYn;
    private String deviceId;
    private String registrant;
    private String productName;
    private String imgLocalUrl;
    private String presentId;
    private String presentMessage;
    private int presentCheck;
    private String deviceModel;
    private String pushToken;
    private int pushYn;
    private int push30;
    private int push7;
    private int push1;
}
