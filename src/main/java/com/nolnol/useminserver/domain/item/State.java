package com.nolnol.useminserver.domain.item;

import lombok.Getter;

@Getter
public enum State {
    AVAILABLE("나눔 가능"),
    RESERVED("예약중"),
    COMPLETE("완료");

    private final String value;

    State(String value){
        this.value = value;
    }
}
