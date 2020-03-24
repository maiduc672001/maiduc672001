package vn.myclass.core.daoimpl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.myclass.core.common.utils.HibernateUtils;
import vn.myclass.core.dao.RoleDao;
import vn.myclass.core.data.daoimpl.AbstractDao;
import vn.myclass.core.persistence.etity.RoleEntity;
import vn.myclass.core.persistence.etity.UserEntity;

import java.util.List;

public class RoleDaoimpl extends AbstractDao<Integer,RoleEntity> implements RoleDao {
    @Override
    public List<RoleEntity> findByRoles(List<String> roles) {
        Session session= HibernateUtils.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        List<RoleEntity> roleEntities=null;
        try{
            StringBuilder sql=new StringBuilder("FROM RoleEntity re WHERE re.name IN (:roles)");
            Query query=session.createQuery(sql.toString());
            query.setParameterList("roles",roles);
            roleEntities=query.list();
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return roleEntities;
    }
}
