package com.usermanagment.mapper;

import com.usermanagment.dto.UserDto;
import com.usermanagment.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    User toEntity(UserDto userDto);

    @InheritInverseConfiguration
    UserDto toDto(User user);
}
