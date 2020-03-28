package vn.myclass.core.service.impl;

import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.persistence.etity.ExerciseEntity;
import vn.myclass.core.persistence.etity.ListenGuideLineEntity;
import vn.myclass.core.service.ExerciseService;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.utils.ExerciseBeanUtil;
import vn.myclass.core.web.utils.ListenGuideLineBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseServiceImpl implements ExerciseService {
    @Override
    public Object[] findExerciseByProperties(Map<String, Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit) {
        Object[] objects= SingletonDaoImpl.getExerciseDaoImplInstance().findByProperty(property,sortDirection,sortExpression,offset,limmit);
        List<ExerciseDTO> results=new ArrayList<ExerciseDTO>();
        for (ExerciseEntity lineEntity:(List<ExerciseEntity>)objects[1]) {
            ExerciseDTO dto= ExerciseBeanUtil.entity2Dto(lineEntity);
            results.add(dto);
        }
        objects[1]=results;
        return objects;
    }
}
