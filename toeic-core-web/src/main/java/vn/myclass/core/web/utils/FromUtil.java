package vn.myclass.core.web.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public class FromUtil {
        public static <T> T populate(Class<T> tclass, HttpServletRequest request){
            T object=null;
            try {
                object=(T) tclass.newInstance();
                BeanUtils.populate(object,request.getParameterMap());
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }catch (InvocationTargetException e){
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return object;
        }
}
