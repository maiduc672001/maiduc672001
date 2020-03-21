package vn.myclass.core.service.impl;

import vn.myclass.core.dao.UserDao;
import vn.myclass.core.daoimpl.UserDaoImpl;
import vn.myclass.core.dto.CheckLogin;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.persistence.etity.UserEntity;
import vn.myclass.core.service.UserService;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.utils.UserBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    @Override
    public CheckLogin checkLogin(String name, String password) {
        CheckLogin checkLogin=new CheckLogin();
        if(name!=null&&password!=null) {
            Object[] objects = SingletonDaoImpl.getUserDaoInstance().checkLogin(name, password);
            checkLogin.setUserExist((Boolean) objects[0]) ;
            checkLogin.setRoleName((String) objects[1]);
        }

        return checkLogin;
    }

    public UserDTO findById(Integer userId) {
        UserEntity entity=SingletonDaoImpl.getUserDaoInstance().findById(userId);
        UserDTO dto=UserBeanUtil.entityToDTO(entity);

        return dto;
    }

    public Object[] findByproperty(Map<String, Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit) {
        Object[] objects=SingletonDaoImpl.getUserDaoInstance().findByProperty(property,sortDirection,sortExpression,offset,limmit);
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
        SingletonDaoImpl.getUserDaoInstance().save(entity);
    }

    public UserDTO updateUser(UserDTO dto) {
        UserEntity entity=UserBeanUtil.dTOTOEntity(dto);
        entity=SingletonDaoImpl.getUserDaoInstance().upDate(entity);
        dto=UserBeanUtil.entityToDTO(entity);
        return dto;
    }
}
