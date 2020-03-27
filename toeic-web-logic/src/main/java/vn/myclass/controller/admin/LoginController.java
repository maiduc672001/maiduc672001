package vn.myclass.controller.admin;

import org.apache.log4j.Logger;
import vn.myclass.command.UserCommand;
import vn.myclass.core.common.utils.SessionUtil;
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

@WebServlet(urlPatterns = {"/login.html","/logout.html"})
public class LoginController extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
ResourceBundle bundle =ResourceBundle.getBundle("ResourcesBundle");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.error("jsp servlet");
        String action =req.getParameter("action");
        if(action.equals(WebConstant.LOGIN)){
            RequestDispatcher rd = req.getRequestDispatcher("views/web/login.jsp");
            rd.forward(req, resp);
        }else if(action.equals(WebConstant.LOGOUT)){
            SessionUtil.getInstance().remove(req,WebConstant.LOGIN_NAME);
            resp.sendRedirect("/home.html");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCommand command = FromUtil.populate(UserCommand.class, req);
        UserDTO pojo = command.getPojo();
        if(pojo!=null) {
            SessionUtil.getInstance().putValue(req,WebConstant.LOGIN_NAME,pojo.getName());
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
    }
}
