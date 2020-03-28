package vn.myclass.controller.admin;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.command.ListenGuideLineCommand;
import vn.myclass.core.common.utils.UploadUtils;
import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.service.ListenGuideLineService;
import vn.myclass.core.service.impl.ListenGuideLineServiceImpl;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
@WebServlet(urlPatterns = {"/admin-guideline-listen-list.html","/admin-guideline-listen-edit.html"})
public class ListenGuideLineController extends HttpServlet  {
    private final Logger log=Logger.getLogger(this.getClass());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListenGuideLineCommand lineCommand= FromUtil.populate(ListenGuideLineCommand.class,req);
lineCommand.setMaxPageItems(2);
HttpSession session=req.getSession();
        RequestUtils.initSearchBean(req,lineCommand);
        ResourceBundle bundle=ResourceBundle.getBundle("ResourcesBundle");
       if(lineCommand.getUrlType()!=null&&lineCommand.getUrlType().equals(WebConstant.URL_LIST)){
           if (lineCommand.getCrudaction() != null && lineCommand.getCrudaction().equals(WebConstant.REDIRECT_DELETE)) {
               List<Integer> ids = new ArrayList<Integer>();
               for (String item: lineCommand.getCheckList()) {
                   ids.add(Integer.parseInt(item));
               }
               Integer result = SingletonServiceImpl.getListenGuideLineServiceInstance().delete(ids);
               if (result != ids.size()) {
                   lineCommand.setCrudaction(WebConstant.REDIRECT_ERROR);
               }
           }
           executeSearchListenGuideLine(req, lineCommand);
           if (lineCommand.getCrudaction() != null) {
               Map<String, String> mapMessage = buidMapRedirectMessage(bundle);
               WebCommonUtil.addRedirectMessage(req, lineCommand.getCrudaction(), mapMessage);
           }
           req.setAttribute(WebConstant.LIST_ITEMS, lineCommand);
           RequestDispatcher rd = req.getRequestDispatcher("/views/admin/listenguideline/list.jsp");
           rd.forward(req, resp);
       }else if(lineCommand.getUrlType()!=null&&lineCommand.getUrlType().equals(WebConstant.URL_EDIT)){
           if(lineCommand.getPojo()!=null&&lineCommand.getPojo().getListenGuideLineId()!=null){
               lineCommand.setPojo(SingletonServiceImpl.getListenGuideLineServiceInstance().findByListenGuideLineId("listenGuideLineId",lineCommand.getPojo().getListenGuideLineId()));
           }
           req.setAttribute(WebConstant.FORM_ITEM,lineCommand);
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
    private Map<String,String> buidMapRedirectMessage(ResourceBundle resourceBundle) {
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT, resourceBundle.getString("label.listenguideline.add.success"));
        mapMessage.put(WebConstant.REDIRECT_UPDATE, resourceBundle.getString("label.listenguideline.update.success"));
        mapMessage.put(WebConstant.REDIRECT_DELETE, resourceBundle.getString("label.listenguideline.delete.success"));
        mapMessage.put(WebConstant.REDIRECT_ERROR, resourceBundle.getString("label.message.error"));
        return mapMessage;
    }
    private Map<String, Object> buildMapProperties(ListenGuideLineCommand command) {
        Map<String,Object> properties=new HashMap<String, Object>();
        if(StringUtils.isNotBlank(command.getPojo().getTitle())){
            properties.put("title",command.getPojo().getTitle());
        }
        return properties;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ListenGuideLineCommand command = new ListenGuideLineCommand();
        UploadUtils uploadUtil = new UploadUtils();
        Set<String> valueTitle = buildSetValueListenGuideline();
        Object[] objects = uploadUtil.writeOrUpdateFile(request, valueTitle, WebConstant.LISTENGUIDELINE);
        boolean checkStatusUploadImage = (Boolean) objects[0];
        if (!checkStatusUploadImage) {
            response.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_error");
        } else {
            ListenGuideLineDTO dto = command.getPojo();
            if (StringUtils.isNotBlank(objects[2].toString())) {
                dto.setImage(objects[2].toString());
            }
            Map<String, String> mapValue = (Map<String, String>) objects[3];
            dto = returnValueOfDTO(dto, mapValue);
            if (dto != null) {
                if (dto.getListenGuideLineId() != null) {
                    ListenGuideLineDTO listenGuidelineDTO = SingletonServiceImpl.getListenGuideLineServiceInstance().findByListenGuideLineId("listenGuideLineId", dto.getListenGuideLineId());
                    if (dto.getImage() == null) {
                        dto.setImage(listenGuidelineDTO.getImage());
                    }
                    dto.setCreatedDate(listenGuidelineDTO.getCreatedDate());
                    ListenGuideLineDTO result = SingletonServiceImpl.getListenGuideLineServiceInstance().updateListenGuideline(dto);
                    if (result != null) {
                        response.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_update");
                    } else {
                        response.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_error");
                    }
                } else {
                    try {
                        SingletonServiceImpl.getListenGuideLineServiceInstance().saveListenGuideline(dto);
                        response.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_insert");
                    } catch (ConstraintViolationException e) {
                        log.error(e.getMessage(), e);
                        response.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&crudaction=redirect_error");
                    }
                }
            }
        }
    }

    private ListenGuideLineDTO returnValueOfDTO(ListenGuideLineDTO dto, Map<String, String> mapValue) {
        for (Map.Entry<String, String> item: mapValue.entrySet()) {
            if (item.getKey().equals("pojo.title")) {
                dto.setTitle(item.getValue());
            } else if (item.getKey().equals("pojo.content")) {
                dto.setContent(item.getValue());
            } else if (item.getKey().equals("pojo.listenGuideLineId")) {
                dto.setListenGuideLineId(Integer.parseInt(item.getValue().toString()));
            }
        }
        return dto;
    }
    private Set<String> buildSetValueListenGuideline() {
        Set<String> returnValue = new HashSet<String>();
        returnValue.add("pojo.title");
        returnValue.add("pojo.content");
        returnValue.add("pojo.listenGuideLineId");
        return returnValue;
    }
}