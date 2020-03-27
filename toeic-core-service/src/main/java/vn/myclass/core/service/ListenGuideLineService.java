package vn.myclass.core.service;

import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.core.dto.ListenGuideLineDTO;

import java.util.List;
import java.util.Map;

public interface ListenGuideLineService {
    Object[] findListenGuideLineByProperties(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit);
    ListenGuideLineDTO findByListenGuideLineId(String property,Integer Id);
   void saveListenGuideline(ListenGuideLineDTO dto) throws ConstraintViolationException;
    public ListenGuideLineDTO updateListenGuideline(ListenGuideLineDTO dto);
    Integer delete(List<Integer> ids);
}
