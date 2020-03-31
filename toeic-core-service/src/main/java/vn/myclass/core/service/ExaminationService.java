package vn.myclass.core.service;

import java.util.Map;

public interface ExaminationService {
    Object[] findExaminationByProperty(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit);
}
