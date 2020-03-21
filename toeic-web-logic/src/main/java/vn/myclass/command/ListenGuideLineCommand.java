package vn.myclass.command;

import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.web.command.AbstractCommand;

public class ListenGuideLineCommand extends AbstractCommand<ListenGuideLineDTO> {
    public ListenGuideLineCommand(){
        this.pojo=new ListenGuideLineDTO();
    }
}
