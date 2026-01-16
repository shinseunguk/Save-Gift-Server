package com.savegift.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String userId;
    private String userPassword;
    private String name;
    private String phoneNumber;
    private int imgCount;
    private int pushYn;
    private int emailYn;
    private int smsYn;
    private String createDate;
    private String socialLogin;
}
