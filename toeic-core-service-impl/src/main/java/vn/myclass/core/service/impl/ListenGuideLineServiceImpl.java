package vn.myclass.core.service.impl;

import vn.myclass.core.dao.ListenGuideLineDao;
import vn.myclass.core.daoimpl.ListenGuideLineimpl;
import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.persistence.etity.ListenGuideLineEntity;
import vn.myclass.core.service.ListenGuideLineService;
import vn.myclass.core.web.utils.ListenGuideLineBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class ListenGuideLineServiceImpl implements ListenGuideLineService {
    ListenGuideLineDao listenGuideLineDao=new ListenGuideLineimpl();
   /* public Object[] findListenGuideLineByProperties(String property, String sortDirection, String sortExpression, Object value, Integer offset, Integer limmit) {
        Object[] objects=listenGuideLineDao.findByProperty(property,sortDirection,sortExpression,value,offset,limmit);
        List<ListenGuideLineDTO> results=new ArrayList<ListenGuideLineDTO>();
        for (ListenGuideLineEntity lineEntity:(List<ListenGuideLineEntity>)objects[1]) {
            ListenGuideLineDTO dto= ListenGuideLineBeanUtil.entityToDTO(lineEntity);
            results.add(dto);
        }
        objects[1]=results;
        return objects;
    }*/
}
