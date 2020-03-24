package vn.myclass.controller.admin;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import vn.myclass.command.ListenGuideLineCommand;
import vn.myclass.core.common.utils.UploadUtils;
import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.service.ListenGuideLineService;
import vn.myclass.core.service.impl.ListenGuideLineServiceImpl;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FromUtil;
import vn.myclass.core.web.utils.ListenGuideLineBeanUtil;
import vn.myclass.core.web.utils.RequestUtils;
import vn.myclass.core.web.utils.SingletonServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

//@WebServlet("/admin-guideline-listen-list.html")
@WebServlet(urlPatterns = {"/admin-guideline-listen-list.html","/admin-guideline-listen-edit.html"})
public class ListenGuideLineController extends HttpServlet  {
    private final Logger log=Logger.getLogger(this.getClass());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListenGuideLineCommand lineCommand= FromUtil.populate(ListenGuideLineCommand.class,req);
        req.setAttribute(WebConstant.LIST_ITEMS,lineCommand);
lineCommand.setMaxPageItems(2);
HttpSession session=req.getSession();
        RequestUtils.initSearchBean(req,lineCommand);

        /*Object[] objects= SingletonServiceImpl.getListenGuideLineServiceInstance().findListenGuideLineByProperties(,lineCommand.getSortDirection(),lineCommand.getSortExpression(),lineCommand.getFirstItem(),lineCommand.getMaxPageItems());
        lineCommand.setListResult((List<ListenGuideLineDTO>) objects[1]);
        lineCommand.setTotalItems(Integer.parseInt(objects[0].toString()));*/
        ResourceBundle bundle=ResourceBundle.getBundle("ApplicationResources");
      /* session.setAttribute(WebConstant.ALERT,session.getAttribute(WebConstant.ALERT));
       session.setAttribute(WebConstant.MESSAGE_RESPONSE,session.getAttribute(WebConstant.MESSAGE_RESPONSE));*/
       if(lineCommand.getUrlType()!=null&&lineCommand.getUrlType().equals(WebConstant.URL_LIST)){
           executeSearchListenGuideLine(req,lineCommand);
           req.setAttribute(WebConstant.LIST_ITEMS,lineCommand);
           RequestDispatcher rd=req.getRequestDispatcher("/views/admin/listenguideline/list.jsp");
           rd.forward(req,resp);
       }else if(lineCommand.getUrlType()!=null&&lineCommand.getUrlType().equals(WebConstant.URL_EDIT)){
           RequestDispatcher rd=req.getRequestDispatcher("/views/admin/listenguideline/edit.jsp");
           rd.forward(req,resp);
       }
session.removeAttribute(WebConstant.ALERT);
       session.removeAttribute(WebConstant.MESSAGE_RESPONSE);
    }

    private void executeSearchListenGuideLine(HttpServletRequest req, ListenGuideLineCommand lineCommand) {
        Map<String,Object> properties=buildMapProperties(lineCommand);
        RequestUtils.initSearchBean(req,lineCommand);

        Object[] objects= SingletonServiceImpl.getListenGuideLineServiceInstance().findListenGuideLineByProperties(properties,lineCommand.getSortDirection(),lineCommand.getSortExpression(),lineCommand.getFirstItem(),lineCommand.getMaxPageItems());
        lineCommand.setListResult((List<ListenGuideLineDTO>) objects[1]);
        lineCommand.setTotalItems(Integer.parseInt(objects[0].toString()));
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
        ListenGuideLineCommand lineCommand= new ListenGuideLineCommand();
        UploadUtils uploadUtils=new UploadUtils();
        ResourceBundle bundle=ResourceBundle.getBundle("ApplicationResources");
        HttpSession session=req.getSession();
       // Set<String> valueTitle=buidSetValueListenGuideline();
        Map<String,String> mapValue=new HashMap<String, String>();
        /*
        try {
            Object[] objects= uploadUtils.writeOrUpdateFile(req,valueTitle,WebConstant.LISTENGUIDELINE);
            mapValue= (Map<String, String>) objects[3];
            lineCommand=returnValueListenGuideLineCommand(lineCommand,valueTitle,mapValue);

            session.setAttribute(WebConstant.ALERT,WebConstant.TYPE_SUCCESS);
            session.setAttribute(WebConstant.MESSAGE_RESPONSE,bundle.getString("label.guideline.listen.add.success"));
        } catch (FileUploadException e) {
            log.error(e.getMessage(),e);
            session.setAttribute(WebConstant.ALERT,WebConstant.TYPE_ERROR);
            session.setAttribute(WebConstant.MESSAGE_RESPONSE,bundle.getString("label.error"));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            session.setAttribute(WebConstant.ALERT,WebConstant.TYPE_ERROR);
            session.setAttribute(WebConstant.MESSAGE_RESPONSE,bundle.getString("label.error"));
        }*/
        resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list");
    }

   /* private ListenGuideLineCommand returnValueListenGuideLineCommand(ListenGuideLineCommand lineCommand, Set<String> valueTitle, Map<String, String> mapValue) {
        for(String item:valueTitle){
            if(mapValue.containsKey(item)){
                if(item.equals("pojo.title")){
                    lineCommand.getPojo().setTitle(mapValue.get(item));
                }else {
                    lineCommand.getPojo().setContent(mapValue.get(item));
                }

            }
        }
        return lineCommand;
    }

    private Set<String> buidSetValueListenGuideline() {
        Set<String> valueTitle=new HashSet<String>();
        valueTitle.add("pojo.title");
        valueTitle.add("pojo.content");
        return valueTitle;
    }*/
}
