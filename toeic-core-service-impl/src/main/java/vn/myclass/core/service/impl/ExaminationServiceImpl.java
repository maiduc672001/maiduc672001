package vn.myclass.core.service.impl;

import vn.myclass.core.dto.ExaminationDTO;
import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.persistence.etity.ExaminationEntity;
import vn.myclass.core.persistence.etity.ExerciseEntity;
import vn.myclass.core.service.ExaminationService;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.utils.ExaminationBeanUtil;
import vn.myclass.core.web.utils.ExerciseBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExaminationServiceImpl implements ExaminationService {
    @Override
    public Object[] findExaminationByProperty(Map<String, Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit) {
        Object[] objects= SingletonDaoImpl.getExaminationDaoImplInstance().findByProperty(property,sortDirection,sortExpression,offset,limmit);
        List<ExaminationDTO> results=new ArrayList<ExaminationDTO>();
        for (ExaminationEntity lineEntity:(List<ExaminationEntity>)objects[1]) {
            ExaminationDTO dto= ExaminationBeanUtil.entity2Dto(lineEntity);
            results.add(dto);
        }
        objects[1]=results;
        return objects;
    }
}
