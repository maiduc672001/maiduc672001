package vn.myclass.controller.admin;

import org.apache.log4j.Logger;
import vn.myclass.command.UserCommand;
import vn.myclass.core.dto.CheckLogin;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.service.UserService;
import vn.myclass.core.service.impl.UserServiceImpl;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FromUtil;
import vn.myclass.core.web.utils.SingletonServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet("/login.html")
public class LoginController extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
ResourceBundle bundle =ResourceBundle.getBundle("ApplicationResources");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.error("jsp servlet");
        RequestDispatcher rd = req.getRequestDispatcher("views/web/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCommand command = FromUtil.populate(UserCommand.class, req);
        UserDTO pojo = command.getPojo();
        if(pojo!=null) {
            CheckLogin checkLogin = SingletonServiceImpl.getUserServiceInstance().checkLogin(pojo.getName(), pojo.getPassword());
            if (checkLogin.isUserExist()) {
                if (checkLogin.getRoleName().equals(WebConstant.ROLE_ADMIN)) {
                    resp.sendRedirect("/admin-home.html");
                } else if(checkLogin.getRoleName().equals(WebConstant.ROLE_USER)){
                    resp.sendRedirect("/home.html");
                }
            }else {
                req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_ERROR);
                req.setAttribute(WebConstant.MESSAGE_RESPONSE, bundle.getString("label.name.password.wrong"));
                RequestDispatcher rd = req.getRequestDispatcher("views/web/login.jsp");
                rd.forward(req, resp);
            }
        }
        /*try {
            if (SingletonServiceImpl.getUserServiceInstance().isUserExist(pojo) != null) {
                if (SingletonServiceImpl.getUserServiceInstance().findRoleByUser(pojo) != null && SingletonServiceImpl.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO() != null) {
                    if (SingletonServiceImpl.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_ADMIN)) {
                        resp.sendRedirect("/admin-home.html");
                    }
                    else if (SingletonServiceImpl.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_USER)) {
                        resp.sendRedirect("/home.html");
                    }
                }
            }
        } catch (NullPointerException e) {
            req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_ERROR);
            req.setAttribute(WebConstant.MESSAGE_RESPONSE, "Tên hoặc mật khẩu sai");
            RequestDispatcher rd = req.getRequestDispatcher("views/web/login.jsp");
            rd.forward(req, resp);
        }
*/

    }
}
