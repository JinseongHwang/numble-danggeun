package it.numble.numbledanggeun.domain.auth.service;

import it.numble.numbledanggeun.domain.auth.model.AuthDto;

public interface AuthService {

    AuthDto.TOKEN signIn(final AuthDto.SIGN_IN authSignInDto);
}
