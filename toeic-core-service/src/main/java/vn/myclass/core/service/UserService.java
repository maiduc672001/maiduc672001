package vn.myclass.core.service;

import vn.myclass.core.dto.CheckLogin;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.dto.UserImportDTO;
import vn.myclass.core.persistence.etity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    CheckLogin checkLogin(String name,String password);
    UserDTO findById(Integer userId);
    Object[] findByproperty(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit);
    void saveUser(UserDTO dto);
    UserDTO updateUser(UserDTO dto);
    void validateImportUser(List<UserImportDTO> userImportDTOS);
    void saveUserImport(List<UserImportDTO> userImportDTOS);
}
