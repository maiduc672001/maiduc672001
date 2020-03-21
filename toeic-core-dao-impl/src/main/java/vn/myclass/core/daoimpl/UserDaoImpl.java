package vn.myclass.core.daoimpl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.myclass.core.common.utils.HibernateUtils;
import vn.myclass.core.dao.UserDao;
import vn.myclass.core.data.daoimpl.AbstractDao;
import vn.myclass.core.persistence.etity.UserEntity;

public class UserDaoImpl extends AbstractDao<Integer, UserEntity> implements UserDao {
    @Override
    public Object[] checkLogin(String name, String password) {
        Session session=HibernateUtils.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        boolean isUserExist=false;
         UserEntity entity=null;
         String roleName=null;
        try {
            StringBuilder sql=new StringBuilder("FROM UserEntity eu WHERE eu.name= :name AND eu.password= :password");
            Query query=session.createQuery(sql.toString());
            query.setParameter("name",name);
            query.setParameter("password",password);
            if(query.list().size()>0){
                isUserExist=true;
                entity= (UserEntity) query.uniqueResult();
                roleName=entity.getName();
            }
        }catch (HibernateException e){

            transaction.rollback();
        }finally {
            session.close();
        }
        return new Object[]{isUserExist,roleName};
    }
}
