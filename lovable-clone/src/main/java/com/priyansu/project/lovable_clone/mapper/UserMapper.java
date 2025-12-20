package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.auth.SignupRequest;
import com.priyansu.project.lovable_clone.dto.auth.UserProfileResponse;
import com.priyansu.project.lovable_clone.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(SignupRequest signupRequest);  ///convert Signup req to User

    UserProfileResponse toUserProfileResponse(User user);
}
