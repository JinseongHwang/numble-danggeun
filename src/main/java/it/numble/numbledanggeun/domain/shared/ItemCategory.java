package it.numble.numbledanggeun.domain.shared;

import lombok.Getter;

@Getter
public enum ItemCategory {

    DIGITAL_DEVICE("디지털기기"),
    HOME_ELECTRONIC("생활가전"),
    FURNITURE_AND_INTERIOR("가구/인테리어"),
    CHILD("유아동"),
    LIVING_FOODSTUFFS("생활/가공식품"),
    CHILD_BOOK("유아도서"),
    SPORTS_LEISURE("스포츠/레저"),
    WOMAN_GOODS("여성잡화"),
    WOMAN_CLOTHES("여성의류"),
    MAN_FASHION_GOODS("남성패션/잡화"),
    GAME_HOBBY("게임/취미"),
    BEAUTY("뷰티/미용"),
    PET_GOODS("반려동물용품"),
    BOOK_TICKET_RECORD("도서/티켓/음반"),
    PLANT("식물"),
    USED_ETC("기타 중고물품"),
    USED_CAR("중고차");

    private final String korCategory;

    ItemCategory(String korCategory) {
        this.korCategory = korCategory;
    }
}
