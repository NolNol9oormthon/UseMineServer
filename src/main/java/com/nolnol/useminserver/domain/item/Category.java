package com.nolnol.useminserver.domain.item;

import lombok.Getter;

@Getter
public enum Category {
    BEAUTY("미용"),
    ELECTRONICS("예약중"),
    EMERGENCY_MEDICINE("비상약"),
    KIDS("아이 관련"),
    LEISURE("레져 관련");

    private final String value;

    Category(String value) {
        this.value = value;
    }
}
