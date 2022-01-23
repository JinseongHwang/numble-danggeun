package it.numble.numbledanggeun.infrastructure.error.model;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    SERVER_ERROR("CO_001", "서버가 응답하지 않습니다.", 500),
    INVALID_INPUT_VALUE("CO_002", "유효하지 않은 입력입니다.", 400),
    INVALID_TYPE_VALUE("CO_003", "형식에 맞지 않는 입력입니다.", 400),
    METHOD_NOT_ALLOWED("CO_004", "사용할 수 없는 API 입니다.", 405),

    // Auth
    BAD_EMAIL("AU_001", "존재하지 않는 회원입니다.", 400),
    BAD_PASSWORD("AU_002", "비밀번호를 다시 확인해주세요.", 400),
    DUPLICATED_ID("AU_003", "중복된 아이디를 사용할 수 없습니다.", 409),
    DUPLICATED_EMAIL("AU_004", "중복된 이메일을 사용할 수 없습니다.", 409),
    DUPLICATED_NICKNAME("AU_005", "중복된 닉네임을 사용할 수 없습니다.", 409),
    DUPLICATED_USER("AU_006", "이미 존재하는 회원입니다.", 400),
    UNAUTHENTICATED_USER("AU_007", "사용자 인증 과정에서 문제가 발생했습니다.", 401),
    UNAUTHORIZED_USER("AU_008", "해당 기능 사용 권한이 없습니다.", 403),
    EXPIRED_TOKEN("AU_009", "만료된 토큰입니다.", 401),
    INVALID_TOKEN("AU_010", "유효하지 않은 토큰입니다.", 400);

    private final String code;
    private final String message;
    private final Integer statusCode;

    ErrorCode(String code, String message, Integer statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", code, message);
    }
}
