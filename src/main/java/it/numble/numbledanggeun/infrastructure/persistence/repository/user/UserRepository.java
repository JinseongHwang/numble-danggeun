package it.numble.numbledanggeun.infrastructure.persistence.repository.user;

import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {

}
