package vn.myclass.core.test;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import vn.myclass.core.dao.ListenGuideLineDao;
import vn.myclass.core.dao.UserDao;
import vn.myclass.core.daoimpl.ListenGuideLineimpl;
import vn.myclass.core.daoimpl.UserDaoImpl;

import java.util.HashMap;
import java.util.Map;

public class ListenGuideLineTest {
    ListenGuideLineDao listenGuideLineDao;
    @BeforeTest
    public void initData(){
        listenGuideLineDao=new ListenGuideLineimpl();
    }
    @Test
    public void testFindByProperty(){
        //ListenGuideLineDao listenGuideLineDao=new ListenGuideLineimpl();
       /* Object[] results=listenGuideLineDao.findByProperty(null,null,null,null,0,2);*/

    }
    @Test
    public void checkFindByProperty(){
        Map<String,Object> property=new HashMap<String, Object>();
        property.put("title","bai hd 8");
        property.put("content","Noi dung bai hd 8");
        Object[] objects=listenGuideLineDao.findByProperty(property,null,null,null,null);
    }
}
