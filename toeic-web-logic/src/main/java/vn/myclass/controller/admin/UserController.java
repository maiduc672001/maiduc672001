package vn.myclass.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import vn.myclass.command.UserCommand;
import vn.myclass.core.common.utils.ExcelPoiUtil;
import vn.myclass.core.common.utils.SessionUtil;
import vn.myclass.core.common.utils.UploadUtils;
import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.dto.UserImportDTO;
import vn.myclass.core.service.RoleService;
import vn.myclass.core.service.UserService;
import vn.myclass.core.service.impl.RoleServiceImpl;
import vn.myclass.core.service.impl.UserServiceImpl;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FromUtil;
import vn.myclass.core.web.utils.RequestUtils;
import vn.myclass.core.web.utils.SingletonServiceImpl;
import vn.myclass.core.web.utils.WebCommonUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

@WebServlet(urlPatterns = {"/admin-user-list.html", "/ajax-admin-user-edit.html", "/admin-user-import.html", "/admin-user-import-validate.html"})
public class UserController extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());
    private final String SHOW_IMPORT_USER = "show_import_user";
    private final String READ_EXCEL = "read_excel";
    private final String VALIDATE_IMPORT = "validate_import";
    private final String LIST_USER_IMPORT = "list_user_import";
    private final String IMPORT_DATA="import_data";
    ResourceBundle resourceBundle = ResourceBundle.getBundle("ResourcesBundle");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCommand command = FromUtil.populate(UserCommand.class, req);
        UserDTO pojo = command.getPojo();
        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            Map<String, Object> mapProperty = new HashMap<String, Object>();
            RequestUtils.initSearchBean(req,command);
            Object[] objects = SingletonServiceImpl.getUserServiceInstance().findByproperty(mapProperty, command.getSortDirection(), command.getSortExpression(), command.getFirstItem(), command.getMaxPageItems());
            command.setListResult((List<UserDTO>) objects[1]);
            command.setTotalItems(Integer.parseInt(objects[0].toString()));
            req.setAttribute(WebConstant.LIST_ITEMS, command);
            if (command.getCrudaction() != null) {
                Map<String, String> mapMessage = buildMapRedirectMessage(resourceBundle);
                WebCommonUtil.addRedirectMessage(req, command.getCrudaction(), mapMessage);
            }
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/user/list.jsp");
            rd.forward(req, resp);
        } else if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.INSERT_UPDATE)) {
                req.setAttribute(WebConstant.MESSAGE_RESPONSE, "insert success");
            } else if (pojo != null && pojo.getUserId() != null) {
                command.setPojo(SingletonServiceImpl.getUserServiceInstance().findById(pojo.getUserId()));
            }
            command.setRoles(SingletonServiceImpl.getRoleServiceInstance().findAll());
            req.setAttribute(WebConstant.FORM_ITEM, command);
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/user/edit.jsp");
            rd.forward(req, resp);
        } else if (command.getUrlType() != null && command.getUrlType().equals(SHOW_IMPORT_USER)) {
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/user/importuser.jsp");
            rd.forward(req, resp);
            ;
        } else if (command.getUrlType() != null && command.getUrlType().equals(VALIDATE_IMPORT)) {
            List<UserImportDTO> userImportDTOS = (List<UserImportDTO>) SessionUtil.getInstance().getValue(req, LIST_USER_IMPORT);
            /*command.setMaxPageItems(3);
            RequestUtils.initSearchBean(req,command);
            command.setTotalItems(userImportDTOS.size());
            int fromIndex=command.getFirstItem();
            if(fromIndex>command.getTotalItems()){
                fromIndex=0;
                command.setFirstItem(0);
            }
            int toIndex=fromIndex+command.getMaxPageItems();
            if(toIndex>command.getTotalItems()){
                toIndex=command.getTotalItems();
            }
            command.setUserImportDTOS(userImportDTOS.subList(fromIndex,toIndex));*/
            command.setUserImportDTOS(returnListUserImport(command,userImportDTOS,req));
            req.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/user/importuser.jsp");
            rd.forward(req, resp);
        }
    }

    private List<UserImportDTO> returnListUserImport(UserCommand command,List<UserImportDTO> userImportDTOS,HttpServletRequest req) {
        command.setMaxPageItems(3);
        RequestUtils.initSearchBean(req,command);
        command.setTotalItems(userImportDTOS.size());
        int fromIndex=command.getFirstItem();
        if(fromIndex>command.getTotalItems()){
            fromIndex=0;
            command.setFirstItem(0);
        }
        int toIndex=fromIndex+command.getMaxPageItems();
        if(toIndex>command.getTotalItems()){
            toIndex=command.getTotalItems();
        }
        return userImportDTOS.subList(fromIndex,toIndex);
    }

    private Map<String, String> buildMapRedirectMessage(ResourceBundle resourceBundle) {
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT, resourceBundle.getString("label.user.message.add.success"));
        mapMessage.put(WebConstant.REDIRECT_UPDATE, resourceBundle.getString("label.user.message.update.success"));
        mapMessage.put(WebConstant.REDIRECT_DELETE, resourceBundle.getString("label.user.message.delete.success"));
        mapMessage.put(WebConstant.REDIRECT_ERROR, resourceBundle.getString("label.user.message.error"));
        return mapMessage;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        try {
            UploadUtils uploadUtils = new UploadUtils();
            Set<String> title = new HashSet<String>();
            title.add("urlType");
            Object[] objects = uploadUtils.writeOrUpdateFile(req, title, "excel");
            UserCommand command = FromUtil.populate(UserCommand.class, req);
            UserDTO pojo = command.getPojo();
            if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
                if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.INSERT_UPDATE)) {
                    RoleDTO dto = new RoleDTO();
                    dto.setRoleId(command.getRoleId());
                    pojo.setRoleDTO(dto);
                    if (pojo != null && pojo.getUserId() != null) {
                        // updateUser

                        SingletonServiceImpl.getUserServiceInstance().updateUser(pojo);
                        req.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_UPDATE);
                    } else {
                        SingletonServiceImpl.getUserServiceInstance().saveUser(pojo);
                        req.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_INSERT);
                    }
                }
                RequestDispatcher rd = req.getRequestDispatcher("/views/admin/user/edit.jsp");
                rd.forward(req, resp);
            }
            if (objects != null) {
                String urlType = null;
                Map<String, String> mapTitle = (Map<String, String>) objects[3];
                for (Map.Entry<String, String> item : mapTitle.entrySet()) {
                    if (item.getKey().equals("urlType")) {
                        urlType = item.getValue();
                    }
                }
                if (urlType != null && urlType.equals(READ_EXCEL)) {
                    String fileLocation = (String) objects[1];
                    String fileName = objects[2].toString();
                    List<UserImportDTO> userImportDTOS = returnValueFromExcel(fileName, fileLocation);
                    validateData(userImportDTOS);
                    SessionUtil.getInstance().putValue(req, LIST_USER_IMPORT, userImportDTOS);
                    resp.sendRedirect("/admin-user-import-validate.html?urlType=validate_import");
                }
            }
                if(command.getUrlType()!=null&&command.getUrlType().equals(IMPORT_DATA)){
                List<UserImportDTO> userImportDTOS= (List<UserImportDTO>) SessionUtil.getInstance().getValue(req,LIST_USER_IMPORT);
                SingletonServiceImpl.getUserServiceInstance().saveUserImport(userImportDTOS);
                SessionUtil.getInstance().remove(req,LIST_USER_IMPORT);
                resp.sendRedirect("/admin-user-list.html?urlType=url_list");
            }
        } catch (Exception e) {
            req.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_ERROR);
            log.error(e.getMessage(), e);
        }
    }

    private void validateData(List<UserImportDTO> userImportDTOS) {
        Set<String> stringSet = new HashSet<String>();
        for (UserImportDTO item : userImportDTOS) {
            validateRequireField(item);
            validateDuplicate(item, stringSet);

        }
        SingletonServiceImpl.getUserServiceInstance().validateImportUser(userImportDTOS);
    }

    private void validateDuplicate(UserImportDTO item, Set<String> stringSet) {
        String message = "";
            if (!stringSet.contains(item.getUserName())) {
                stringSet.add(item.getUserName());
            } else {
                if (item.isValid()) {
                    message += "</br>";
                    message += resourceBundle.getString("label.username.duplicate");
                }
            }
            if (StringUtils.isNotBlank(message)) {
                item.setValid(false);
            }
            item.setError(message);
    }

    private void validateRequireField(UserImportDTO item) {
        String message = "";
        if (StringUtils.isBlank(item.getUserName())) {
            message += "</br>";
            message += resourceBundle.getString("label.username.notempty");
        }
        if (StringUtils.isBlank(item.getPassword())) {
            message += "</br>";
            message += resourceBundle.getString("label.password.notempty");
        }
        if (StringUtils.isBlank(item.getRoleName())) {
            message += "</br>";
            message += resourceBundle.getString("label.rolename.notempty");
        }
        if (StringUtils.isNotBlank(message)) {
            item.setValid(false);
        }
        item.setError(message);
    }

    private List<UserImportDTO> returnValueFromExcel(String fileName, String fileLocation) throws IOException {
        Workbook workbook = ExcelPoiUtil.getWorkBook(fileName, fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        List<UserImportDTO> userImportDTOS = new ArrayList<UserImportDTO>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            UserImportDTO dto = readDataFromExcel(row);
            userImportDTOS.add(dto);
        }
        return userImportDTOS;
    }

    private UserImportDTO readDataFromExcel(Row row) {
        UserImportDTO dto = new UserImportDTO();
        dto.setUserName(ExcelPoiUtil.getCellValue(row.getCell(0)));
        dto.setPassword(ExcelPoiUtil.getCellValue(row.getCell(1)));
        dto.setFullName(ExcelPoiUtil.getCellValue(row.getCell(2)));
        dto.setRoleName(ExcelPoiUtil.getCellValue(row.getCell(3)));
        return dto;
    }
}
