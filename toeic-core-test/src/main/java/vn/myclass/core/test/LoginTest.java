package vn.myclass.core.test;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import vn.myclass.core.dao.UserDao;
import vn.myclass.core.daoimpl.UserDaoImpl;
import vn.myclass.core.persistence.etity.UserEntity;

public class LoginTest {
    private final Logger logger=Logger.getLogger(this.getClass());
    @Test
    public void checkUserExist(){

        UserDao userDao=new UserDaoImpl();
        String name="admin";
        String password="123456";
        UserEntity entity=userDao.findUserByUsernameAnhPassword(name,password);
        if(entity!=null){
            logger.error("Loggin success");
        }else {
            logger.error("Loggin fail");
        }
    }
    @Test
    public void checkFindRoleByUser(){

        UserDao userDao=new UserDaoImpl();
        String name="admin";
        String password="123456";
        UserEntity entity=userDao.findUserByUsernameAnhPassword(name,password);
        if(entity!=null){
            logger.error("Loggin success");
        }else {
            logger.error("Loggin fail");
        }
    }
}
