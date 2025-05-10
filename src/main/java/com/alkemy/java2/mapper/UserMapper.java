package com.alkemy.java2.mapper;

import com.alkemy.java2.authsecurity.dto.RegisterRequest;
import com.alkemy.java2.dto.UserDTO;
import com.alkemy.java2.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(RegisterRequest registerRequest);
    //UserDTO toDTO(User user);
    //User toEntity(UserDTO dto);
}



