package vn.myclass.controller.admin;

import org.apache.log4j.Logger;
import vn.myclass.command.UserCommand;
import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.service.RoleService;
import vn.myclass.core.service.UserService;
import vn.myclass.core.service.impl.RoleServiceImpl;
import vn.myclass.core.service.impl.UserServiceImpl;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FromUtil;
import vn.myclass.core.web.utils.WebCommonUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/admin-user-list.html","/ajax-admin-user-edit.html"})
public class UserController extends HttpServlet {
    private final Logger log=Logger.getLogger(this.getClass());
    UserService service=new UserServiceImpl();
    RoleService roleService=new RoleServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCommand command= FromUtil.populate(UserCommand.class,req);
        ResourceBundle resourceBundle=ResourceBundle.getBundle("ApplicationResources");
        UserDTO pojo=command.getPojo();
        if(command.getUrlType()!=null&&command.getUrlType().equals(WebConstant.URL_LIST)){
            Map<String,Object> mapProperty=new HashMap<String, Object>();
            Object[] objects=service.findByproperty(mapProperty,command.getSortDirection(),command.getSortExpression(),command.getFirstItem(),command.getMaxPageItems());
            command.setListResult((List<UserDTO>) objects[1]);
            command.setTotalItems(Integer.parseInt(objects[0].toString()));
            req.setAttribute(WebConstant.LIST_ITEMS,command);
            if(command.getCrudaction()!=null){
               Map<String,String> mapMessage=buildMapRedirectMessage(resourceBundle);
                WebCommonUtil.addRedirectMessage(req,command.getCrudaction(),mapMessage);
            }
            RequestDispatcher rd=req.getRequestDispatcher("/views/admin/user/list.jsp");
            rd.forward(req,resp);
        }else if(command.getUrlType()!=null&&command.getUrlType().equals(WebConstant.URL_EDIT)){
            if(command.getCrudaction()!=null&&command.getCrudaction().equals(WebConstant.INSERT_UPDATE)){
req.setAttribute(WebConstant.MESSAGE_RESPONSE,"insert success");
            }
            else if(pojo!=null&&pojo.getUserId()!=null){
                command.setPojo(service.findById(pojo.getUserId()));
            }
            command.setRoles(roleService.findAll());
            req.setAttribute(WebConstant.FORM_ITEM,command);
            RequestDispatcher rd=req.getRequestDispatcher("/views/admin/user/edit.jsp");
            rd.forward(req,resp);
        }
    }

    private Map<String, String> buildMapRedirectMessage(ResourceBundle resourceBundle) {
        Map<String,String> mapMessage=new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT,resourceBundle.getString("label.user.message.add.success"));
        mapMessage.put(WebConstant.REDIRECT_UPDATE,resourceBundle.getString("label.user.message.update.success"));
        mapMessage.put(WebConstant.REDIRECT_DELETE,resourceBundle.getString("label.user.message.delete.success"));
        mapMessage.put(WebConstant.REDIRECT_ERROR,resourceBundle.getString("label.user.message.error"));
        return mapMessage;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserCommand command= FromUtil.populate(UserCommand.class,req);
            UserDTO pojo=command.getPojo();
            if(command.getUrlType().equals(WebConstant.URL_EDIT)) {
                if (command.getCrudaction()!=null&&command.getCrudaction().equals(WebConstant.INSERT_UPDATE)) {
                    RoleDTO dto=new RoleDTO();
                    dto.setRoleId(command.getRoleId());
                    pojo.setRoleDTO(dto);
                    if(pojo!=null&&pojo.getUserId()!=null){
                        // updateUser

                        service.updateUser(pojo);
                        req.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_UPDATE);
                    }else {
                        service.saveUser(pojo);
                        req.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_INSERT);
                    }
                }
            }
        }catch (Exception e){
            req.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_ERROR);
            log.error(e.getMessage(),e);
        }
        RequestDispatcher rd=req.getRequestDispatcher("/views/admin/user/edit.jsp");
        rd.forward(req,resp);
    }
}
