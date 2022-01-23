package it.numble.numbledanggeun.domain.auth.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static final class TOKEN {

        @ApiModelProperty(example = "AccessToken")
        private String accessToken;

        @ApiModelProperty(example = "RefreshToken")
        private String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static final class SIGN_IN {

        @ApiModelProperty(example = "jinseong.dev@gmail.com")
        private String email;

        @ApiModelProperty(example = "password")
        private String password;
    }


}