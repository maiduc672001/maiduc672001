package vn.myclass.core.data.daoimpl;

import org.apache.log4j.Logger;
import org.hibernate.*;
import vn.myclass.core.common.constant.CoreConstant;
import vn.myclass.core.common.utils.HibernateUtils;
import vn.myclass.core.data.dao.GenericDao;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractDao<ID extends Serializable,T> implements GenericDao<ID,T> {
    private final Logger log=Logger.getLogger(this.getClass());
    private Class<T> persistenceClass;
    public AbstractDao(){
        this.persistenceClass=(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    public String getPersistenceName(){
        return persistenceClass.getSimpleName();
    }
    public List<T> findAll() {
        List<T> list=new ArrayList<T>();
        Session session=HibernateUtils.getSessionFactory().openSession();
        Transaction transaction=null;
        try {
            transaction=session.beginTransaction();
            StringBuilder hql = new StringBuilder("from ");
            hql.append(this.getPersistenceName());
            Query query = session.createQuery(hql.toString());
            list=query.list();
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }finally {
            session.close();
        }
        return list;
    }

    public T upDate(T entity) {
    T result=null;
    Session session=HibernateUtils.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        try{
            Object o=session.merge(entity);
            result=(T) o;
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }finally {
            session.close();
        }
        return result;
    }

    public void save(T entity) {
        Session session=HibernateUtils.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }finally {
            session.close();
        }
    }

    public T findById(ID id) {
        T result=null;
        Session session=HibernateUtils.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        try {
            result=(T) session.get(persistenceClass,id);
            if(result==null){
                throw new ObjectNotFoundException("NOT FOUND"+id, null);
            }
        }catch (HibernateException e){
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }finally {
            session.close();
        }
        return result;
    }

    public Object[] findByProperty(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit) {
        Session session=HibernateUtils.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        List<T> list=new ArrayList<T>();
        Object total=0;
        String[] params=new String[property.size()];
        Object[] values=new Object[property.size()];
        int i=0;
        for (Map.Entry item:property.entrySet()) {
            params[i]= (String) item.getKey();
            values[i]=item.getValue();
            i++;
        }
        try {

            StringBuilder sql=new StringBuilder("from ");
            sql.append(this.getPersistenceName()).append(" WHERE 1=1 ");
            if(property.size()>0){
                for (int j = 0; j <params.length ; j++) {
                    sql.append(" and ").append("LOWER("+params[j]+") LIKE :"+params[j]);
                }
            }
            if(sortDirection!=null&&sortExpression!=null){
                sql.append(" order by ").append(sortExpression);
                sql.append(" "+(sortDirection.equals(CoreConstant.SORT_ASC)?"asc":"desc"));
            }
            Query query1=session.createQuery(sql.toString());
            if(property.size()>0){
                for (int j = 0; j <params.length ; j++) {
                    query1.setParameter(params[j],"%"+values[j]+"%");
                }
            }
            if(offset!=null&&offset>=0){
                query1.setFirstResult(offset);
            }
            if(limmit!=null&&limmit>0){
                query1.setMaxResults(limmit);
            }
            list=query1.list();
            StringBuilder sql2=new StringBuilder("SELECT COUNT(*) FROM ");
            sql2.append(getPersistenceName()).append(" WHERE 1=1 ");
            if(property.size()>0){
                for (int j = 0; j <params.length ; j++) {
                    sql2.append(" and ").append("LOWER("+params[j]+") LIKE :"+params[j]);
                }
            }
            Query query2=session.createQuery(sql2.toString());
            if(property.size()>0){
                for (int j = 0; j <params.length ; j++) {
                    query2.setParameter(params[j],"%"+values[j]+"%");
                }
            }
            total=query2.list().get(0);
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }
        finally {
            session.close();
        }
        return new Object[]{total,list};
    }

    public Integer delete(List<ID> ids) {
        Session session=HibernateUtils.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        Integer count=0;
        try {
            for (ID id:ids) {
                T t= (T) session.get(this.persistenceClass,id);
                session.delete(t);
                count++;
            }
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }finally {
            session.close();
        }
        return count;
    }

    @Override
    public T findEqualUnique(String property, Object value) {
        Session session=HibernateUtils.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        T result=null;
        try{
            String sql="FROM "+this.getPersistenceName()+" model WHERE model."+property+" = :value";
            Query query=session.createQuery(sql.toString());
            query.setParameter("value",value);
            result= (T) query.uniqueResult();
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }finally {
            session.close();
        }
        return result;
    }


}
