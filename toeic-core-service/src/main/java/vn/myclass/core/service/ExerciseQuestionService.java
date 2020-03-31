package vn.myclass.core.service;

import java.util.Map;

public interface ExerciseQuestionService {
    Object[] findExerciseQuestionByProperties(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit,Integer exerciseId);

}
