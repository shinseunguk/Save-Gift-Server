package com.savegift.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Friend {
    private String userId;
    private String name;
    private String friend;
    private String status;
}
