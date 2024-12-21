package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.user.UserDto;
import ch.hslu.swda.data.entities.user.User;

public final class UserMappers {

    public static User toEntity(final UserDto userDto) {
        if (userDto == null)
            return null;

        return new User(
            userDto.firstname(),
            userDto.lastname(),
            userDto.email(),
            userDto.role(),
            userDto.branchNumber()
        );
    }

    public static UserDto toDto(final User user) {
        if (user == null)
            return null;

        return new UserDto(
            user.getFirstname(),
            user.getLastname(),
            user.getEmail(),
            user.getRole(),
            user.getBranchNumber()
        );
    }
}