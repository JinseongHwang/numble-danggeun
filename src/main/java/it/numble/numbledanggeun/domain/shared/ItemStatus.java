package it.numble.numbledanggeun.domain.shared;

import lombok.Getter;

@Getter
public enum ItemStatus {

    ON_SALE("판매중"),
    RESERVED("예약중"),
    SOLD_OUT("판매완료");

    private final String korStatus;

    ItemStatus(String korStatus) {
        this.korStatus = korStatus;
    }
}
