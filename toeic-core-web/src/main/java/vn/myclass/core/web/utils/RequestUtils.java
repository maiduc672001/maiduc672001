package vn.myclass.core.web.utils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import vn.myclass.core.web.command.AbstractCommand;
import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static void initSearchBean(HttpServletRequest request, AbstractCommand command){
        String sortExpression=request.getParameter( new ParamEncoder(command.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_SORT));
        String sortDirection=request.getParameter( new ParamEncoder(command.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
        String pageSr=request.getParameter( new ParamEncoder(command.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        Integer page=1;
        if(StringUtils.isNotBlank(pageSr)){
            try{
                page=Integer.valueOf(pageSr);
            }catch (Exception e){

            }

        }
        command.setPage(page);
        command.setSortDirection(sortDirection);
        command.setSortExpression(sortExpression);
        command.setFirstItem((command.getPage()-1)*command.getMaxPageItems());
    }
public static void initSearchBeanManual(AbstractCommand command){
    if(command!=null){
        Integer page=1;
        if(command.getPage()!=0){
            page=command.getPage();
        }
        command.setPage(page);
        command.setFirstItem((command.getPage()-1)*command.getMaxPageItems());
    }
}
}
