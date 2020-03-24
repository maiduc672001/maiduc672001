package vn.myclass.core.test;

import org.testng.annotations.Test;
import vn.myclass.core.dao.RoleDao;
import vn.myclass.core.daoimpl.RoleDaoimpl;
import vn.myclass.core.persistence.etity.RoleEntity;

public class TestImport {
    @Test
    public void testImport(){
        RoleDao roleDao=new RoleDaoimpl();
        RoleEntity entity=roleDao.findEqualUnique("name","ADMIN");
        System.out.println(entity.getName());
    }
}
