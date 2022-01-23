package it.numble.numbledanggeun.infrastructure.persistence.repository.user;

import static it.numble.numbledanggeun.infrastructure.persistence.entity.QUserEntity.userEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserEntity> fetchOneByEmail(String emailCond) {
        final UserEntity result = fetchJoinQuery()
            .where(emailEq(emailCond))
            .fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsByEmail(String emailCond) {
        final Integer result = queryFactory
            .selectOne()
            .from(userEntity)
            .where(emailEq(emailCond))
            .fetchFirst();
        return result != null;
    }

    // UserEntity 의 연관 관계 확장을 고려해서 메서드로 분리했음
    private JPAQuery<UserEntity> fetchJoinQuery() {
        return queryFactory
            .selectFrom(userEntity);
    }

    private BooleanExpression emailEq(String emailCond) {
        return userEntity.email.eq(emailCond);
    }
}
