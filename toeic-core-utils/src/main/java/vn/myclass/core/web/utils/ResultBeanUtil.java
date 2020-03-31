package vn.myclass.core.web.utils;

import vn.myclass.core.dto.ResultDTO;
import vn.myclass.core.persistence.etity.ResultEntity;

public class ResultBeanUtil {
    public static ResultDTO entity2Dto(ResultEntity entity) {
        ResultDTO dto = new ResultDTO();
        dto.setResultId(entity.getResultId());
        dto.setListenScore(entity.getListenScore());
        dto.setReadingScore(entity.getReadingScore());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public static ResultEntity dto2Entity(ResultDTO dto) {
        ResultEntity entity = new ResultEntity();
        entity.setResultId(dto.getResultId());
        entity.setReadingScore(dto.getReadingScore());
        entity.setListenScore(dto.getListenScore());
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
    }
}
