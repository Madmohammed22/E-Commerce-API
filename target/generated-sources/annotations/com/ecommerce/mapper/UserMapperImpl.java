package com.ecommerce.mapper;

import com.ecommerce.dto.UserDto;
import com.ecommerce.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-30T22:39:43+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.email( user.getEmail() );
        userDto.fullName( user.getFullName() );
        userDto.address( user.getAddress() );
        userDto.phone( user.getPhone() );

        userDto.role( user.getRole().name() );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDto.getId() );
        user.email( userDto.getEmail() );
        user.fullName( userDto.getFullName() );
        user.address( userDto.getAddress() );
        user.phone( userDto.getPhone() );

        return user.build();
    }

    @Override
    public void updateUserFromDto(UserDto dto, User user) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            user.setId( dto.getId() );
        }
        if ( dto.getEmail() != null ) {
            user.setEmail( dto.getEmail() );
        }
        if ( dto.getFullName() != null ) {
            user.setFullName( dto.getFullName() );
        }
        if ( dto.getAddress() != null ) {
            user.setAddress( dto.getAddress() );
        }
        if ( dto.getPhone() != null ) {
            user.setPhone( dto.getPhone() );
        }
    }
}
