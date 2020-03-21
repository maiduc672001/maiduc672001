package vn.myclass.core.test;

import org.testng.annotations.Test;
import vn.myclass.core.dao.RoleDao;
import vn.myclass.core.daoimpl.RoleDaoimpl;
import vn.myclass.core.persistence.etity.RoleEntity;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

public class RoleTest {
    @Test
    public void checkFileAll(){
        RoleDao roleDao= new RoleDaoimpl();
        List<RoleEntity> list=roleDao.findAll();
    }
    @Test
    public void checkUpDateRole(){
        RoleDao roleDao=new RoleDaoimpl();
        RoleEntity role=new RoleEntity();
        role.setName("USER_1");
        role.setRoleId(2);
        roleDao.upDate(role);
    }
    @Test
    public  void checkSaveRole(){
        RoleDao roleDao=new RoleDaoimpl();
        RoleEntity roleEntity=new RoleEntity();
        roleEntity.setRoleId(3);
        roleEntity.setName("USER_2");
        roleDao.save(roleEntity);
    }
    @Test
    public  void checkFindById(){
        RoleDao roleDao=new RoleDaoimpl();
        RoleEntity roleEntity=new RoleEntity();
        roleEntity=roleDao.findById(2);

    }
    @Test
    public void checkFindProperty(){
        RoleDao roleDao=new RoleDaoimpl();
        RoleEntity roleEntity=new RoleEntity();
        String property=null;
        String sortDirection=null;
        String sortExpression=null;
        String value=null;
        //Object[] objects=roleDao.findByProperty(null,null,null,null,null,null);
    }
    @Test
    public void checkDelete(){
       RoleDao roleDao=new RoleDaoimpl();
       List<Integer> list=new ArrayList<Integer>();
       list.add(1);
       list.add(2);
       Integer integer=roleDao.delete(list);
    }
}

