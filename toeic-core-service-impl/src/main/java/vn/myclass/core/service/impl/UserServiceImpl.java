package vn.myclass.core.service.impl;

import org.apache.commons.lang.StringUtils;
import vn.myclass.core.dao.UserDao;
import vn.myclass.core.daoimpl.UserDaoImpl;
import vn.myclass.core.dto.CheckLogin;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.dto.UserImportDTO;
import vn.myclass.core.persistence.etity.RoleEntity;
import vn.myclass.core.persistence.etity.UserEntity;
import vn.myclass.core.service.UserService;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.utils.UserBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public void validateImportUser(List<UserImportDTO> userImportDTOS) {
List<String> names=new ArrayList<String>();
List<String> roles=new ArrayList<String>();
        for (UserImportDTO item:userImportDTOS) {
            if(item.isValid()){
                names.add(item.getUserName());
                if(!roles.contains(item.getRoleName())){
                    roles.add(item.getRoleName());
            }
            }
        }
        Map<String,UserEntity> userEntityMap=new HashMap<String, UserEntity>();
        Map<String, RoleEntity> roleEntityMap=new HashMap<String, RoleEntity>();
        if(names.size()>0){
            List<UserEntity> userEntities=SingletonDaoImpl.getUserDaoInstance().findByUser(names);
            for (UserEntity item:userEntities) {
                userEntityMap.put(item.getName().toUpperCase(),item);
            }
        }
        if(roles.size()>0){
            List<RoleEntity> roleEntities=SingletonDaoImpl.getRoleDaoInstance().findByRoles(roles);
                for (RoleEntity item:roleEntities) {
                    roleEntityMap.put(item.getName().toUpperCase(),item);
                }
            }
        for (UserImportDTO item:userImportDTOS) {
            String message=item.getError();
            if(item.isValid()){
                UserEntity userEntity=userEntityMap.get(item.getUserName().toUpperCase());
                if(userEntity!=null){
                    message+="<br/>";
                    message+="Tên đăng nhập đã tồn tại";
                }
                RoleEntity roleEntity=roleEntityMap.get(item.getRoleName().toUpperCase());
                if(roleEntity==null){
                    message+="<br/>";
                    message+="Vai trò không tồn tại";
                }
                if(StringUtils.isNotBlank(message)){
                    item.setError(message.substring(5));
                    item.setValid(false);
                }
            }
        }
    }

    @Override
    public void saveUserImport(List<UserImportDTO> userImportDTOS) {
        for (UserImportDTO item:userImportDTOS) {
            if(item.isValid()){
                UserEntity userEntity=new UserEntity();
                userEntity.setName(item.getUserName());
                userEntity.setFullName(item.getFullName());
                userEntity.setPassword(item.getPassword());
                Timestamp timestamp=new Timestamp(System.currentTimeMillis());
                userEntity.setCreatedDate(timestamp);
                RoleEntity roleEntity=SingletonDaoImpl.getRoleDaoInstance().findEqualUnique("name",item.getRoleName().toUpperCase());
                userEntity.setRoleEntity(roleEntity);
                SingletonDaoImpl.getUserDaoInstance().save(userEntity);
            }
        }
    }
}
