package vn.myclass.core.web.utils;

import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.persistence.etity.RoleEntity;

public class RoleBeanUtil {
        public static RoleDTO entityToDTO(RoleEntity roleEntity) {
            RoleDTO roleDTO=new RoleDTO();
            roleDTO.setRoleId(roleEntity.getRoleId());
            roleDTO.setName(roleEntity.getName());
            return roleDTO;
        }
        public static RoleEntity dTOTOEntity(RoleDTO roleDTO){
            RoleEntity roleEntity=new RoleEntity();
            roleEntity.setRoleId(roleDTO.getRoleId());
            roleEntity.setName(roleDTO.getName());
            return roleEntity;

        }
}
