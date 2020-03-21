package vn.myclass.core.service.impl;

import vn.myclass.core.dao.UserDao;
import vn.myclass.core.daoimpl.UserDaoImpl;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.persistence.etity.UserEntity;
import vn.myclass.core.service.UserService;
import vn.myclass.core.web.utils.UserBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    UserDao dao=new UserDaoImpl();
    public UserDTO isUserExist(UserDTO dto) {
        UserDao userDao=new UserDaoImpl();
        UserEntity userEntity= userDao.findUserByUsernameAnhPassword(dto.getName(),dto.getPassword());


        return UserBeanUtil.entityToDTO(userEntity);
    }

    public UserDTO findRoleByUser(UserDTO dto) {
        UserDao userDao=new UserDaoImpl();
        UserEntity userEntity= userDao.findUserByUsernameAnhPassword(dto.getName(),dto.getPassword());

        return UserBeanUtil.entityToDTO(userEntity);
    }

    public UserDTO findById(Integer userId) {
        UserDao userDao=new UserDaoImpl();
        UserEntity entity=userDao.findById(userId);
        UserDTO dto=UserBeanUtil.entityToDTO(entity);

        return dto;
    }

    public Object[] findByproperty(Map<String, Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit) {
        Object[] objects=dao.findByProperty(property,sortDirection,sortExpression,offset,limmit);
        List<UserDTO> userDTOS=new ArrayList<UserDTO>();
        for (UserEntity entity:(List<UserEntity>)objects[1]) {
            UserDTO userDTO=UserBeanUtil.entityToDTO(entity);
            userDTOS.add(userDTO);
        }
        objects[1]=userDTOS;
        return objects;
    }

    public void saveUser(UserDTO dto) {
        Timestamp createdDate=new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(createdDate);
        UserEntity entity=UserBeanUtil.dTOTOEntity(dto);
        dao.save(entity);
    }

    public UserDTO updateUser(UserDTO dto) {
        UserEntity entity=UserBeanUtil.dTOTOEntity(dto);
        entity=dao.upDate(entity);
        dto=UserBeanUtil.entityToDTO(entity);
        return dto;
    }
}
