package com.savegift.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDevice {
    private String userId;
    private String deviceId;
    private String deviceModel;
    private String pushToken;
    private int pushYn;
    private int imgCount;
    private int push30;
    private int push7;
    private int push1;
}
