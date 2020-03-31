package vn.myclass.core.dao;

import vn.myclass.core.data.dao.GenericDao;
import vn.myclass.core.persistence.etity.ExerciseQuestionEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

public interface ExerciseQuestionDao extends GenericDao<Integer, ExerciseQuestionEntity> {
   //Object[] findByProperty2(Map<String,Object>property, String sortExpression, String sortDirection, Integer offset, Integer limit,String whereClause);
}
