package vn.myclass.core.service;

import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.persistence.etity.UserEntity;

import java.util.Map;

public interface UserService {
    UserDTO isUserExist(UserDTO dto);
    UserDTO findRoleByUser(UserDTO dto);
    UserDTO findById(Integer userId);
    Object[] findByproperty(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit);
    void saveUser(UserDTO dto);
    UserDTO updateUser(UserDTO dto);
}
