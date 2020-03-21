package vn.myclass.core.service.util;

import vn.myclass.core.daoimpl.ListenGuideLineimpl;
import vn.myclass.core.daoimpl.RoleDaoimpl;
import vn.myclass.core.daoimpl.UserDaoImpl;

public class SingletonDaoImpl {
    private static UserDaoImpl userDaoImpl=null;
    private static RoleDaoimpl roleDaoimpl=null;
    private static ListenGuideLineimpl listenGuideLineimpl=null;
    public static UserDaoImpl getUserDaoInstance(){
        if(userDaoImpl==null){
            userDaoImpl=new UserDaoImpl();
        }
        return userDaoImpl;
    }
    public static RoleDaoimpl getRoleDaoInstance(){
        if(roleDaoimpl==null){
            roleDaoimpl=new RoleDaoimpl();
        }
        return roleDaoimpl;
    }
    public static ListenGuideLineimpl getListenGuideLineDaoInstance(){
        if(listenGuideLineimpl==null){
            listenGuideLineimpl=new ListenGuideLineimpl();
        }
        return listenGuideLineimpl;
    }

}
