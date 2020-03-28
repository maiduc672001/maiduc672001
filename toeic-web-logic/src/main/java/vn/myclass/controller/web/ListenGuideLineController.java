package vn.myclass.controller.web;

import org.apache.commons.lang.StringUtils;
import vn.myclass.command.ListenGuideLineCommand;
import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FromUtil;
import vn.myclass.core.web.utils.RequestUtils;
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
import java.util.Map;

@WebServlet(urlPatterns = {"/danh-sach-huong-dan-nghe.html","/noi-dung-bai-huong-dan-nghe.html"})
public class ListenGuideLineController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListenGuideLineCommand command= FromUtil.populate(ListenGuideLineCommand.class,req);
        if(req.getParameter("listenguidelineid")!=null){
            String listenGuideLineId=req.getParameter("listenguidelineid");
            ListenGuideLineDTO dto=SingletonServiceImpl.getListenGuideLineServiceInstance().findByListenGuideLineId("listenGuideLineId",Integer.parseInt(listenGuideLineId));
            command.setPojo(dto);
            req.setAttribute(WebConstant.FORM_ITEM,command);
            RequestDispatcher rd =req.getRequestDispatcher("views/web/listenguideline/detail.jsp");
            rd.forward(req,resp);
        }else {
            executeSearchListenGuideLine(req,command);
            req.setAttribute(WebConstant.LIST_ITEMS,command);
            RequestDispatcher rd =req.getRequestDispatcher("views/web/listenguideline/list.jsp");
            rd.forward(req,resp);
        }

    }

    private void executeSearchListenGuideLine(HttpServletRequest req, ListenGuideLineCommand command) {
        Map<String,Object> properties=buildMapProperties(command);
        command.setMaxPageItems(3);
        RequestUtils.initSearchBeanManual(command);
        Object[] objects= SingletonServiceImpl.getListenGuideLineServiceInstance().findListenGuideLineByProperties(properties,command.getSortDirection(),command.getSortExpression(),command.getFirstItem(),command.getMaxPageItems());
        command.setListResult((List<ListenGuideLineDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
        command.setTotalPage((int) Math.ceil((double) command.getTotalItems()/command.getMaxPageItems()));

    }

    private Map<String, Object> buildMapProperties(ListenGuideLineCommand command) {
        Map<String,Object> properties=new HashMap<String, Object>();
        if(StringUtils.isNotBlank(command.getPojo().getTitle())){
            properties.put("title",command.getPojo().getTitle());
        }
        return properties;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
