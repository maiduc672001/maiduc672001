package vn.myclass.core.web.utils;

import vn.myclass.core.dto.NewModel;
import vn.myclass.core.persistence.etity.NewModelEntity;

public class NewModelBeanUtil {
    public static NewModelEntity dtoToEntity(NewModel newModel){
        NewModelEntity newModelEntity=new NewModelEntity();
        newModelEntity.setCategoryId(newModel.getCategoryId());
        newModelEntity.setContent(newModel.getContent());
        newModelEntity.setTitle(newModel.getTitle());
        newModelEntity.setId(newModel.getId());
        newModelEntity.setThumbnail(newModel.getThumbnail());
        return newModelEntity;
    }
    public static NewModel entityToDTO(NewModelEntity newModelEntity){
        NewModel newModel=new NewModel();
        newModel.setCategoryId(newModelEntity.getCategoryId());
        newModel.setContent(newModelEntity.getContent());
        newModel.setTitle(newModelEntity.getTitle());
        newModel.setId(newModelEntity.getId());
        newModel.setThumbnail(newModel.getThumbnail());
        return newModel;
    }
}
