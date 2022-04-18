package com.experiment.streaming.exception;

public class BusinessExceptions {

    public static final BusinessException CONSTANT_NAME_IS_NOT_ACCEPTABLE = new BusinessException("Constant name is not acceptable");
    public static final BusinessException USER_NOT_FOUND = new BusinessException("User not found");
    public static final BusinessException USER_ALREADY_EXISTS = new BusinessException("UserEntity already exists");
    public static final BusinessException ROLE_NOT_FOUND_BY_ID = new BusinessException("Role not found by id");
    public static final BusinessException COMPANY_NOT_FOUND_BY_ID = new BusinessException("Company not found by id");
    public static final BusinessException CONTENT_NOT_FOUND_BY_ID = new BusinessException("Content not found by id");
    public static final BusinessException CONTENT_TYPE_NOT_FOUND_BY_ID = new BusinessException("Content type not found by id");
    public static final BusinessException EXAM_NOT_FOUND_BY_ID = new BusinessException("Exam not found by id");
    public static final BusinessException SURVEY_NOT_FOUND_BY_ID = new BusinessException("Survey not found by id");
    public static final BusinessException EXAM_HAS_BEEN_COMPLETED_BY_USER = new BusinessException("Exam has been completed by user");
    public static final BusinessException ANSWER_NOT_FOUND_IN_ANSWERED_RESULT = new BusinessException("Answer is not found in answered results");
    public static final BusinessException TRUE_ANSWER_NOT_FOUND =  new BusinessException("True answer is not found");
    public static final BusinessException NO_PERMISSION_TO_WORK_WITH_ANOTHER_COMPANY =  new BusinessException("No permission to work with another company");
    public static final BusinessException FORBIDDEN_TO_EXAMINE_ANOTHER_ONE_EXAM = new BusinessException("Forbidden to examine another one exam");
    public static final BusinessException ONLY_ADMIN_CAN_ADD_ADMIN = new BusinessException("Only admin can add admin");
    public static final BusinessException ONLY_ADMIN_CAN_DELETE_ADMIN = new BusinessException("Only admin can delete admin");

}
