package vn.myclass.core.service.impl;

import vn.myclass.core.dao.RoleDao;
import vn.myclass.core.daoimpl.RoleDaoimpl;
import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.persistence.etity.RoleEntity;
import vn.myclass.core.service.RoleService;
import vn.myclass.core.web.utils.RoleBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    RoleDao dao=new RoleDaoimpl();
    public List<RoleDTO> findAll(){
        List<RoleEntity> entities=dao.findAll();
        List<RoleDTO> roleDTOS=new ArrayList<RoleDTO>();
        for(RoleEntity entity:entities){
            RoleDTO dto= RoleBeanUtil.entityToDTO(entity);
            roleDTOS.add(dto);
        }
        return roleDTOS;
    }
}
