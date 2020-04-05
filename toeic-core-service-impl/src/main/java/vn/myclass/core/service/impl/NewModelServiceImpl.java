package vn.myclass.core.service.impl;

import vn.myclass.core.dao.NewModelDao;
import vn.myclass.core.daoimpl.NewModelDaoImpl;
import vn.myclass.core.dto.NewModel;
import vn.myclass.core.persistence.etity.NewModelEntity;
import vn.myclass.core.service.NewModelService;
import vn.myclass.core.web.utils.NewModelBeanUtil;

import javax.inject.Inject;
import java.util.List;

public class NewModelServiceImpl implements NewModelService {

    NewModelDaoImpl newModelDao=new NewModelDaoImpl();
    @Override
    public void saveNewModel(NewModel newModel) {
        NewModelEntity entity= NewModelBeanUtil.dtoToEntity(newModel);
        newModelDao.save(entity);
    }

    @Override
    public NewModel upDateModel(NewModel newModel) {
        NewModelEntity newModelService=NewModelBeanUtil.dtoToEntity(newModel);
        newModelService=newModelDao.upDate(newModelService);
        return NewModelBeanUtil.entityToDTO(newModelService);
    }

    @Override
    public Integer deleteModel(List<Integer> ids) {
        Integer count=newModelDao.delete(ids);
        return count;
    }


}
