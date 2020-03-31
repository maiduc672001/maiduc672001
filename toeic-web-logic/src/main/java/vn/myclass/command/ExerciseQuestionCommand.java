package vn.myclass.command;

import vn.myclass.core.dto.ExerciseQuestionDTO;
import vn.myclass.core.web.command.AbstractCommand;

public class ExerciseQuestionCommand extends AbstractCommand<ExerciseQuestionDTO> {
    public String getAnswerUser() {
        return answerUser;
    }
private boolean checkAnswer;
private Integer exerciseId;

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public boolean isCheckAnswer() {
        return checkAnswer;
    }

    public void setCheckAnswer(boolean checkAnswer) {
        this.checkAnswer = checkAnswer;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    private String answerUser;
    public ExerciseQuestionCommand(){ this.pojo=new ExerciseQuestionDTO();
    }

}
