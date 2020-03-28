package vn.myclass.command;

import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.dto.ListenGuideLineDTO;
import vn.myclass.core.web.command.AbstractCommand;

public class ExerciseCommand extends AbstractCommand<ExerciseDTO> {
    public ExerciseCommand(){
        this.pojo=new ExerciseDTO();
    }
}
