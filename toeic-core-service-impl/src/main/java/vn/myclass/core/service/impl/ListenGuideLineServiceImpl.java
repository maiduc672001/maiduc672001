package vn.myclass.core.service.impl;

import vn.myclass.core.dao.ListenGuideLineDao;
import vn.myclass.core.daoimpl.ListenGuideLineimpl;
import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.persistence.etity.ListenGuideLineEntity;
import vn.myclass.core.service.ListenGuideLineService;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.utils.ListenGuideLineBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListenGuideLineServiceImpl implements ListenGuideLineService {
    public Object[] findListenGuideLineByProperties(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit) {
        Object[] objects= SingletonDaoImpl.getListenGuideLineDaoInstance().findByProperty(property,sortDirection,sortExpression,offset,limmit);
        List<ListenGuideLineDTO> results=new ArrayList<ListenGuideLineDTO>();
        for (ListenGuideLineEntity lineEntity:(List<ListenGuideLineEntity>)objects[1]) {
            ListenGuideLineDTO dto= ListenGuideLineBeanUtil.entityToDTO(lineEntity);
            results.add(dto);
        }
        objects[1]=results;
        return objects;
    }
}
