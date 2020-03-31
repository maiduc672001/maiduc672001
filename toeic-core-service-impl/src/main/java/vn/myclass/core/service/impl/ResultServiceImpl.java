package vn.myclass.core.service.impl;
import vn.myclass.core.dto.ExaminationQuestionDTO;
import vn.myclass.core.dto.ResultDTO;
import vn.myclass.core.persistence.etity.ExaminationEntity;
import vn.myclass.core.persistence.etity.ResultEntity;
import vn.myclass.core.persistence.etity.UserEntity;
import vn.myclass.core.service.ResultService;
import vn.myclass.core.service.util.SingletonDaoImpl;
import vn.myclass.core.web.utils.ResultBeanUtil;

import java.sql.Timestamp;
import java.util.List;

public class ResultServiceImpl implements ResultService {
    @Override
    public ResultDTO saveResult(String userName, Integer examinationId, List<ExaminationQuestionDTO> examinationQuestions) {
        ResultDTO resultDTO=new ResultDTO();
        if(userName!=null&&examinationId!=null&&examinationQuestions!=null){
            UserEntity user= SingletonDaoImpl.getUserDaoInstance().findEqualUnique("name",userName);
            ExaminationEntity examination=SingletonDaoImpl.getExaminationDaoImplInstance().findById(examinationId);
            ResultEntity resultEntity=new ResultEntity();
            calculateListenAndReadScore(resultEntity,examinationQuestions);
            initDataToResultEntity(resultEntity,user,examination);
            SingletonDaoImpl.getResultDaoImplInstance().save(resultEntity);
            resultDTO= ResultBeanUtil.entity2Dto(resultEntity);
        }
        return resultDTO;
    }

    private void initDataToResultEntity(ResultEntity resultEntity, UserEntity user, ExaminationEntity examination) {
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        resultEntity.setCreatedDate(timestamp);
        resultEntity.setExamination(examination);
        resultEntity.setUser(user);
    }

    private void calculateListenAndReadScore(ResultEntity resultEntity, List<ExaminationQuestionDTO> examinationQuestions) {
        int listenScore=0;
        int readingScore=0;
        for (ExaminationQuestionDTO item: examinationQuestions) {
            if(item.getAnswerUser()!=null){
                if(item.getAnswerUser().equals(item.getCorrectAnswer())){
                    if(item.getNumber()<=4){
                        listenScore++;
                    }else {
                        readingScore++;
                    }
                }
            }
        }
        resultEntity.setListenScore(listenScore);
        resultEntity.setReadingScore(readingScore);
    }
}
