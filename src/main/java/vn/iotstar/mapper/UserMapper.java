package vn.iotstar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.User;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "roleName", source = "role.name")
    UserDTO toDTO(User entity);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO dto);
}