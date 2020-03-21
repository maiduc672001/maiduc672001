package vn.myclass.core.web.utils;

import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.persistence.etity.ListenGuideLineEntity;
import vn.myclass.core.persistence.etity.RoleEntity;

public class ListenGuideLineBeanUtil {
    public static ListenGuideLineDTO entityToDTO(ListenGuideLineEntity entity) {
        ListenGuideLineDTO dto=new ListenGuideLineDTO();
        dto.setListenGuideLineId(entity.getListenGuideLineId());
        dto.setContent(entity.getContent());
        dto.setTitle(entity.getTitle());
        dto.setImage(entity.getImage());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public static ListenGuideLineEntity dTOTOEntity(ListenGuideLineDTO dto){
        ListenGuideLineEntity entity=new ListenGuideLineEntity();
        entity.setContent(dto.getContent());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setImage(dto.getImage());
        entity.setListenGuideLineId(dto.getListenGuideLineId());
        entity.setTitle(dto.getTitle());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
