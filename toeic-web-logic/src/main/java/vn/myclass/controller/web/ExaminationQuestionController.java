package vn.myclass.controller.web;

import vn.myclass.command.ExaminationQuestionCommand;
import vn.myclass.core.common.utils.SessionUtil;
import vn.myclass.core.dto.ExaminationQuestionDTO;
import vn.myclass.core.dto.ResultDTO;
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
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = {"/bai-thi-thuc-hanh.html","/bai-thi-dap-an.html"})
public class ExaminationQuestionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExaminationQuestionCommand command= FromUtil.populate(ExaminationQuestionCommand.class,req);
        getExaminationQuestion(command);
        req.setAttribute(WebConstant.LIST_ITEMS,command);
        RequestDispatcher rd=req.getRequestDispatcher("views/web/examination/detail.jsp");
        rd.forward(req,resp);
    }

    private void getExaminationQuestion(ExaminationQuestionCommand command) {
        Object[] objects= SingletonServiceImpl.getExaminationQuestionServiceImplInstance().findExaminationQuestionByProperty(new HashMap<String, Object>(),command.getSortDirection(),command.getSortExpression(),null,null,command.getExaminationId());
        command.setListResult((List<ExaminationQuestionDTO>) objects[1]);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExaminationQuestionCommand command=new ExaminationQuestionCommand();
        Integer examinationId=Integer.parseInt(req.getParameter("examinationId"));
        command.setExaminationId(examinationId);
        getExaminationQuestion(command);
        String userName=(String) SessionUtil.getInstance().getValue(req,WebConstant.LOGIN_NAME);
        for (ExaminationQuestionDTO item:command.getListResult()) {
            if(req.getParameter("answerUser["+item.getExaminationQuestionId()+"]")!=null){
                item.setAnswerUser(req.getParameter("answerUser["+item.getExaminationQuestionId()+"]"));
            }
        }
        ResultDTO resultDTO=SingletonServiceImpl.getResultServiceImplInstance().saveResult(userName,command.getExaminationId(),command.getListResult());
        command.setReadingScore(resultDTO.getReadingScore());
        command.setListenScore(resultDTO.getListenScore());
        RequestDispatcher rd=req.getRequestDispatcher("views/web/examination/result.jsp");
        rd.forward(req,resp);
    }
}
