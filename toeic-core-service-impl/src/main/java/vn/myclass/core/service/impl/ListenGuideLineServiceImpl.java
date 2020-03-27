package vn.myclass.core.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.core.dao.ListenGuideLineDao;
import vn.myclass.core.daoimpl.ListenGuideLineimpl;
import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.persistence.etity.ListenGuideLineEntity;
import vn.myclass.core.service.ListenGuideLineService;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.utils.ListenGuideLineBeanUtil;

import java.sql.Timestamp;
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

    @Override
    public ListenGuideLineDTO findByListenGuideLineId(String property,Integer listenGuideLineId) {
        ListenGuideLineEntity entity=SingletonDaoImpl.getListenGuideLineDaoInstance().findEqualUnique(property,listenGuideLineId);
        ListenGuideLineDTO dto=ListenGuideLineBeanUtil.entityToDTO(entity);

        return dto;
    }
    public void saveListenGuideline(ListenGuideLineDTO dto) throws ConstraintViolationException {
        Timestamp timestamp=new java.sql.Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(timestamp);
        ListenGuideLineEntity entity = ListenGuideLineBeanUtil.dTOTOEntity(dto);
        SingletonDaoImpl.getListenGuideLineDaoInstance().save(entity);
    }

    public ListenGuideLineDTO updateListenGuideline(ListenGuideLineDTO dto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setModifiedDate(timestamp);
        ListenGuideLineEntity entity = ListenGuideLineBeanUtil.dTOTOEntity(dto);
        entity = SingletonDaoImpl.getListenGuideLineDaoInstance().upDate(entity);
        dto = ListenGuideLineBeanUtil.entityToDTO(entity);
        return dto;
    }

    public Integer delete(List<Integer> ids) {
        Integer result = SingletonDaoImpl.getListenGuideLineDaoInstance().delete(ids);
        return result;
    }
}
