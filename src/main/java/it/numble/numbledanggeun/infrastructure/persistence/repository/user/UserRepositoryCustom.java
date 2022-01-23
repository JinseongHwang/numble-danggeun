package it.numble.numbledanggeun.infrastructure.persistence.repository.user;

import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<UserEntity> fetchOneByEmail(String emailCond);

    boolean existsByEmail(String emailCond);
}
