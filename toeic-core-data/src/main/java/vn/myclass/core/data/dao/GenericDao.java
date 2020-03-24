package vn.myclass.core.data.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<ID extends Serializable,T> {
    List<T> findAll();
    T upDate(T entity);
    void save(T entity);
    T findById(ID id);
    Object[] findByProperty(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit);
    Integer delete(List<ID> ids);
    T findEqualUnique(String property,Object value);
}
