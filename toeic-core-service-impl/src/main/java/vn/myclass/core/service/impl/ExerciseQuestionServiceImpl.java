package vn.myclass.core.service.impl;

import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.dto.ExerciseQuestionDTO;
import vn.myclass.core.persistence.etity.ExerciseEntity;
import vn.myclass.core.persistence.etity.ExerciseQuestionEntity;
import vn.myclass.core.service.ExerciseQuestionService;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.utils.ExerciseBeanUtil;
import vn.myclass.core.web.utils.ExerciseQuestionBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseQuestionServiceImpl implements ExerciseQuestionService {
    @Override
    public Object[] findExerciseQuestionByProperties(Map<String, Object> property, String sortDirection, String sortExpression, Integer offset, Integer limit,Integer exerciseId) {

        List<ExerciseQuestionDTO> result = new ArrayList<ExerciseQuestionDTO>();
        String whereClause = null;
        if (exerciseId != null) {
            whereClause = " AND exerciseEntity.exerciseId = "+exerciseId+"";
        }
        Object[] objects = SingletonDaoImpl.getExerciseQuestionDaoImplInstance().findByProperty(property, sortExpression, sortDirection, offset, limit, whereClause);
        for (ExerciseQuestionEntity item: (List<ExerciseQuestionEntity>)objects[1]) {
            ExerciseQuestionDTO dto = ExerciseQuestionBeanUtil.entity2Dto(item);
            dto.setExercise(ExerciseBeanUtil.entity2Dto(item.getExerciseEntity()));
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }



    }
