package it.numble.numbledanggeun.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import it.numble.numbledanggeun.domain.shared.ResponseFormat;
import it.numble.numbledanggeun.domain.user.model.UserDto;
import it.numble.numbledanggeun.domain.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation("회원가입")
    @PostMapping
    public ResponseFormat<Boolean> signUp(@RequestBody @Valid UserDto.CREATE userCreateDto) {
        return ResponseFormat.ok(userService.signUp(userCreateDto));
    }

    @ApiOperation("회원 정보 조회")
    @ApiImplicitParams({
        @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Access Token", paramType = "header", required = true)
    })
    @GetMapping
    public ResponseFormat<UserDto.READ> readUserInfo() {
        return ResponseFormat.ok(userService.readUserInfo());
    }
}
