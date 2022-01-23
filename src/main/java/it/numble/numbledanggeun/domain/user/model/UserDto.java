package it.numble.numbledanggeun.domain.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static final class CREATE {

        @ApiModelProperty(example = "jinseong.dev@gmail.com")
        private String email;

        @ApiModelProperty(example = "password")
        private String password;

        @ApiModelProperty(example = "Jinseong Hwang")
        private String username;

        @ApiModelProperty(example = "010-1234-1234")
        private String phoneNumber;

        @ApiModelProperty(example = "jeeensong")
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static final class READ {

        private String email;

        private String nickname;
    }
}
