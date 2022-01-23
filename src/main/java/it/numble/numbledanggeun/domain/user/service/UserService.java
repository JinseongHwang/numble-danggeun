package it.numble.numbledanggeun.domain.user.service;

import it.numble.numbledanggeun.domain.user.model.UserDto;

public interface UserService {

    boolean signUp(final UserDto.CREATE userCreateDto);

    UserDto.READ readUserInfo();
}
