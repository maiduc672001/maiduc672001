package vn.myclass.core.service;

import java.util.Map;

public interface ListenGuideLineService {
    Object[] findListenGuideLineByProperties(Map<String,Object> property, String sortDirection, String sortExpression, Integer offset, Integer limmit);
}
