package it.numble.numbledanggeun.infrastructure.interceptor;

import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserThreadLocal {

    private static final ThreadLocal<UserEntity> THREAD_LOCAL = new ThreadLocal<>();

    protected static void set(UserEntity userEntity) {
        THREAD_LOCAL.set(userEntity);
    }

    protected static void remove() {
        THREAD_LOCAL.remove();
    }

    public static UserEntity get() {
        return THREAD_LOCAL.get();
    }

    public static boolean isPresent() {
        return THREAD_LOCAL.get() != null;
    }
}
