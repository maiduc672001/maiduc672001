package vn.myclass.controller.admin;

import vn.myclass.core.common.utils.UploadUtils;
import vn.myclass.core.web.common.WebConstant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(urlPatterns = {"/admin-exercise-upload"})
public class ExerciseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd=req.getRequestDispatcher("/views/admin/exercise/upload.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UploadUtils uploadUtils=new UploadUtils();
        Set<String> valueTitle=new HashSet<String>();
        Object[] objects=uploadUtils.writeOrUpdateFile(req,valueTitle, WebConstant.EXERCISE);
        RequestDispatcher rd=req.getRequestDispatcher("/views/admin/exercise/upload.jsp");
        rd.forward(req,resp);
    }
}
