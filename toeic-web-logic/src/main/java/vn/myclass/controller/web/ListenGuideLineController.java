package vn.myclass.controller.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/danh-sach-huong-dan-nghe.html","/noi-dung-bai-huong-dan-nghe.html"})
public class ListenGuideLineController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("id")!=null){
            RequestDispatcher rd =req.getRequestDispatcher("views/web/listenguideline/detail.jsp");
            rd.forward(req,resp);
        }else {
            RequestDispatcher rd =req.getRequestDispatcher("views/web/listenguideline/list.jsp");
            rd.forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
