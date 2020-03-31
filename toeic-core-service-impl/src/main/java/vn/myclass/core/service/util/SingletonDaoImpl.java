package vn.myclass.core.service.util;

import vn.myclass.core.dao.ExerciseQuestionDao;
import vn.myclass.core.daoimpl.*;

public class SingletonDaoImpl {
    private static UserDaoImpl userDaoImpl=null;
    private static RoleDaoimpl roleDaoimpl=null;
    private static ListenGuideLineimpl listenGuideLineimpl=null;
    private static CommentDaoImpl commentDaoImpl=null;
    private static ExaminationDaoImpl examinationDaoImpl=null;
    private static ExaminationQuestionDaoImpl examinationQuestionDaoImpl=null;
    private static ExerciseDaoImpl exerciseDaoImpl=null;
    private static ExerciseQuestionDaoImpl exerciseQuestionDaoImpl=null;
    private static ResultDaoImpl resultDaoImpl=null;
    public static ResultDaoImpl getResultDaoImplInstance(){
        if(resultDaoImpl==null){
            resultDaoImpl=new ResultDaoImpl();
        }
        return resultDaoImpl;
    }
    public static UserDaoImpl getUserDaoInstance(){
        if(userDaoImpl==null){
            userDaoImpl=new UserDaoImpl();
        }
        return userDaoImpl;
    }
    public static RoleDaoimpl getRoleDaoInstance(){
        if(roleDaoimpl==null){
            roleDaoimpl=new RoleDaoimpl();
        }
        return roleDaoimpl;
    }
    public static CommentDaoImpl getCommentDaoImplInstance(){
        if(commentDaoImpl==null){
            commentDaoImpl=new CommentDaoImpl();
        }
        return commentDaoImpl;
    }
    public static ExaminationDaoImpl getExaminationDaoImplInstance(){
        if(examinationDaoImpl==null){
            examinationDaoImpl=new ExaminationDaoImpl();
        }
        return examinationDaoImpl;
    }
    public static ExaminationQuestionDaoImpl getExaminationQuestionDaoImplInstance(){
        if(examinationQuestionDaoImpl==null){
            examinationQuestionDaoImpl=new ExaminationQuestionDaoImpl();
        }
        return examinationQuestionDaoImpl;
    }
    public static ExerciseDaoImpl getExerciseDaoImplInstance(){
        if(exerciseDaoImpl==null){
            exerciseDaoImpl=new ExerciseDaoImpl();
        }
        return exerciseDaoImpl;
    }
    public static ExerciseQuestionDaoImpl getExerciseQuestionDaoImplInstance(){
        if(exerciseQuestionDaoImpl==null){
            exerciseQuestionDaoImpl=new ExerciseQuestionDaoImpl();
        }
        return exerciseQuestionDaoImpl;
    }
    public static ListenGuideLineimpl getListenGuideLineDaoInstance(){
        if(listenGuideLineimpl==null){
            listenGuideLineimpl=new ListenGuideLineimpl();
        }
        return listenGuideLineimpl;
    }
}
