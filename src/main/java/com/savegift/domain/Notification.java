package com.savegift.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notification {
    private String userId;
    private String deviceModel;
    private String deviceId;
    private String pushToken;
    private int pushYn;
    private int push30;
    private int push7;
    private int push1;
}
