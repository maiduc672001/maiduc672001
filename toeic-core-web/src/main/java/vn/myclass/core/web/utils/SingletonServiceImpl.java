package vn.myclass.core.web.utils;

import vn.myclass.core.daoimpl.ListenGuideLineimpl;
import vn.myclass.core.daoimpl.UserDaoImpl;
import vn.myclass.core.service.UserService;
import vn.myclass.core.service.impl.*;

public class SingletonServiceImpl {
    private static UserServiceImpl userServiceImpl=null;
    private static RoleServiceImpl roleServiceImpl=null;
    private static ListenGuideLineServiceImpl listenGuideLineServiceImpl=null;
    private static CommentServiceImpl commentServiceImpl=null;
private static ExaminationQuestionServiceImpl examinationQuestionServiceImpl=null;
private static ExaminationServiceImpl examinationServiceImpl=null;
private static ExerciseQuestionServiceImpl exerciseQuestionServiceimpl=null;
private static ExerciseServiceImpl exerciseServiceImpl=null;
private static ResultServiceImpl resultServiceImpl=null;

    public static ResultServiceImpl getResultServiceImplInstance(){
        if(resultServiceImpl==null){
            resultServiceImpl=new ResultServiceImpl();
        }
        return resultServiceImpl;
    }
    public static UserServiceImpl getUserServiceInstance(){
        if(userServiceImpl==null){
            userServiceImpl=new UserServiceImpl();
        }
        return userServiceImpl;
    }
    public static RoleServiceImpl getRoleServiceInstance(){
        if(roleServiceImpl==null){
            roleServiceImpl=new RoleServiceImpl();
        }
        return roleServiceImpl;
    }
    public static ListenGuideLineServiceImpl getListenGuideLineServiceInstance(){
        if(listenGuideLineServiceImpl==null){
            listenGuideLineServiceImpl=new ListenGuideLineServiceImpl();
        }
        return listenGuideLineServiceImpl;
    }
    public static CommentServiceImpl getCommentServiceImplInstance(){
        if(commentServiceImpl==null){
            commentServiceImpl=new CommentServiceImpl();
        }
        return commentServiceImpl;
    }
    public static ExaminationQuestionServiceImpl getExaminationQuestionServiceImplInstance(){
        if(examinationQuestionServiceImpl==null){
            examinationQuestionServiceImpl=new ExaminationQuestionServiceImpl();
        }
        return examinationQuestionServiceImpl;
    }
    public static ExaminationServiceImpl getExaminationServiceImplInstance(){
        if(examinationServiceImpl==null){
            examinationServiceImpl=new ExaminationServiceImpl();
        }
        return examinationServiceImpl;
    }
    public static ExerciseQuestionServiceImpl getExerciseQuestionServiceimplInstance(){
        if(exerciseQuestionServiceimpl==null){
            exerciseQuestionServiceimpl=new ExerciseQuestionServiceImpl();
        }
        return exerciseQuestionServiceimpl;
    }
    public static ExerciseServiceImpl getExerciseServiceImplInstance(){
        if(exerciseServiceImpl==null){
            exerciseServiceImpl=new ExerciseServiceImpl();
        }
        return exerciseServiceImpl;
    }
}
