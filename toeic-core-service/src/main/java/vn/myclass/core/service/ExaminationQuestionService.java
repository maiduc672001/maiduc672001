package vn.myclass.core.service;

import java.util.Map;

public interface ExaminationQuestionService {
    Object[] findExaminationQuestionByProperty(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit,Integer examinationId);
}
