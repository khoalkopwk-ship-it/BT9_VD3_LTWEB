package vn.iotstar.service;

import org.springframework.data.domain.Page;
import vn.iotstar.dto.UserDTO;

public interface UserService {

    Page<UserDTO> findAll(
            String keyword,
            int page,
            int size
    );

    UserDTO findById(Long id);

    UserDTO create(UserDTO dto);

    UserDTO update(
            Long id,
            UserDTO dto
    );

    void delete(Long id);

    long countUsers();

    long countProducts(Long userId);
}