package com.nolnol.useminserver.domain.item;

import lombok.Getter;

@Getter
public enum Category {
    FOOD("식품"),
    CLOTHES("의류"),
    NECESSITIES("생활 용품"),
    SOUVENIR("기념품"),
    ELECTRONICS("전자 기기"),
    ETC("기타");

    private final String value;

    Category(String value) {
        this.value = value;
    }
}
