package it.numble.numbledanggeun.interfaces.controller;

import io.swagger.annotations.ApiOperation;
import it.numble.numbledanggeun.domain.auth.model.AuthDto;
import it.numble.numbledanggeun.domain.auth.service.AuthService;
import it.numble.numbledanggeun.domain.shared.ResponseFormat;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @ApiOperation("로그인")
    @PostMapping("/signin")
    public ResponseFormat<AuthDto.TOKEN> signIn(@RequestBody @Valid AuthDto.SIGN_IN authSignInDto) {
        return ResponseFormat.ok(authService.signIn(authSignInDto));
    }
}
