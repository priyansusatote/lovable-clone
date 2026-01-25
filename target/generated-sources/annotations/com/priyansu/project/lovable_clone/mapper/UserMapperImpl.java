package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.auth.SignupRequest;
import com.priyansu.project.lovable_clone.dto.auth.UserProfileResponse;
import com.priyansu.project.lovable_clone.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-25T18:37:07+0530",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(SignupRequest signupRequest) {
        if ( signupRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( signupRequest.name() );
        user.username( signupRequest.username() );
        user.password( signupRequest.password() );

        return user.build();
    }

    @Override
    public UserProfileResponse toUserProfileResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String username = null;
        String name = null;

        id = user.getId();
        username = user.getUsername();
        name = user.getName();

        UserProfileResponse userProfileResponse = new UserProfileResponse( id, username, name );

        return userProfileResponse;
    }
}
