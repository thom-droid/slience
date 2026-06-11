package org.unexpected.slience.user.domain;

public class UserEntityMapper {

    private UserEntityMapper() {}

    public static User toUserDomain(UserEntity e) {
        User user = new User();
        user.setId(e.getId());
        user.setUsername(e.getUsername());
        user.setEmail(e.getEmail());
        return user;
    }

    public static UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }
}
