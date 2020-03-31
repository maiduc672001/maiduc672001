package vn.myclass.core.service.impl;

import vn.myclass.core.dto.ExaminationQuestionDTO;
import vn.myclass.core.persistence.etity.ExaminationQuestionEntity;
import vn.myclass.core.service.ExaminationQuestionService;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.utils.ExaminationBeanUtil;
import vn.myclass.core.web.utils.ExaminationQuestionBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExaminationQuestionServiceImpl implements ExaminationQuestionService {
    @Override
    public Object[] findExaminationQuestionByProperty(Map<String, Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit, Integer examinationId) {
        List<ExaminationQuestionDTO> result = new ArrayList<ExaminationQuestionDTO>();
        String whereClause = null;
        if (examinationId != null) {
            whereClause = "AND examination.examinationId=" + examinationId;
        }
            Object[] objects = SingletonDaoImpl.getExaminationQuestionDaoImplInstance().findByProperty(property, sortExpression, sortDirection, offset, limmit, whereClause);
            int count = 1;
            for (ExaminationQuestionEntity item : (List<ExaminationQuestionEntity>) objects[1]) {
                ExaminationQuestionDTO dto = ExaminationQuestionBeanUtil.entity2Dto(item);
                if (item.getParagraph() == null) {
                    dto.setNumber(count);
                    count++;
                }
                dto.setExamination(ExaminationBeanUtil.entity2Dto(item.getExamination()));
                result.add(dto);
            }
            objects[1] = result;
            return objects;
        }

}
