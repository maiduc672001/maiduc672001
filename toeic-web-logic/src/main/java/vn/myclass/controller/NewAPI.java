package vn.myclass.controller;

import org.codehaus.jackson.map.ObjectMapper;
import vn.myclass.core.daoimpl.NewModelDaoImpl;
import vn.myclass.core.dto.NewModel;
import vn.myclass.core.persistence.etity.NewModelEntity;
import vn.myclass.core.service.NewModelService;
import vn.myclass.core.service.impl.NewModelServiceImpl;
import vn.myclass.core.web.utils.HttpUtil;
import vn.myclass.core.web.utils.NewModelBeanUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(urlPatterns = {"/ajax-admin"})
public class NewAPI extends HttpServlet {
    NewModelServiceImpl newModelService=new NewModelServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
req.setCharacterEncoding("UTF-8");
resp.setContentType("application/json");
        ObjectMapper objectMapper=new ObjectMapper();
        NewModel newModel=HttpUtil.of(req.getReader()).toModel(NewModel.class);
        newModelService.saveNewModel(newModel);
        objectMapper.writeValue(resp.getOutputStream(),newModel);
        System.out.println(newModel);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
req.setCharacterEncoding("UTF-8");
resp.setContentType("application/json");
ObjectMapper objectMapper=new ObjectMapper();
NewModel newModel=HttpUtil.of(req.getReader()).toModel(NewModel.class);
newModel=newModelService.upDateModel(newModel);
objectMapper.writeValue(resp.getOutputStream(),newModel);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper mapper=new ObjectMapper();
        NewModel newModel=HttpUtil.of(req.getReader()).toModel(NewModel.class);
        Integer count=newModelService.deleteModel(newModel.getIds());
        mapper.writeValue(resp.getOutputStream(),"{}");
        System.out.println(count);
    }
}
