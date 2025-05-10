package com.alkemy.java2.mapper;

import com.alkemy.java2.authsecurity.dto.RegisterRequest;
import com.alkemy.java2.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-10T19:20:50-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(RegisterRequest registerRequest) {
        if ( registerRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( registerRequest.getUsername() );
        user.password( registerRequest.getPassword() );

        return user.build();
    }
}
