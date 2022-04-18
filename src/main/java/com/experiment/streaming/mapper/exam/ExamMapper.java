package com.experiment.streaming.mapper.exam;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.exam.ExamAnswerEntity;
import com.experiment.streaming.entity.exam.ExamEntity;
import com.experiment.streaming.entity.exam.ExamQuestionEntity;
import com.experiment.streaming.model.exam.Answer;
import com.experiment.streaming.model.exam.Exam;
import com.experiment.streaming.model.exam.ExamInsertion;
import com.experiment.streaming.model.exam.QuestionInsertion;
import com.experiment.streaming.utils.PathUtils;
import com.experiment.streaming.utils.uploader.ImageUploader;
import com.experiment.streaming.utils.uploader.Uploader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class ExamMapper {

    public static ExamEntity toEntity(Exam exam){
        if(exam == null)
            return null;
        ExamEntity examEntity = new ExamEntity();
        examEntity.setId(exam.getId());
        examEntity.setName(exam.getName());
        examEntity.setTitle(exam.getTitle());
        examEntity.setDescription(exam.getDescription());
        examEntity.setStartDate(exam.getStartDate());
        examEntity.setFinishDate(exam.getFinishDate());
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(exam.getCompanyId());
        examEntity.setCompany(companyEntity);
        examEntity.setDiscontinueDate(exam.getDiscontinueDate());
        return examEntity;
    }

    public static Exam toModel(ExamEntity examEntity){
        if (examEntity == null)
            return null;
        Exam exam = new Exam();
        exam.setId(examEntity.getId());
        exam.setName(examEntity.getName());
        exam.setTitle(examEntity.getTitle());
        exam.setDescription(examEntity.getDescription());
        exam.setStartDate(examEntity.getStartDate());
        exam.setFinishDate(examEntity.getFinishDate());
        exam.setCompanyId(examEntity.getCompany().getId());
        exam.setDiscontinueDate(examEntity.getDiscontinueDate());
        return exam;
    }

    public static ExamEntity toEntity(ExamInsertion examInsertion, List<MultipartFile> files){
        if(examInsertion == null)
            return null;
        ListIterator<MultipartFile> filesIterator = null;
        if(files != null)
            filesIterator = files.listIterator();
        ExamEntity examEntity = new ExamEntity();
        examEntity.setId(examInsertion.getId());
        examEntity.setName(examInsertion.getName());
        examEntity.setTitle(examInsertion.getTitle());
        examEntity.setDescription(examInsertion.getDescription());
        examEntity.setStartDate(examInsertion.getStartDate());
        examEntity.setFinishDate(examInsertion.getFinishDate());
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(examInsertion.getCompanyId());
        examEntity.setCompany(companyEntity);
        if(examInsertion.getContentId() != null && examInsertion.getContentId() != -1L) {
            ContentEntity contentEntity = new ContentEntity();
            contentEntity.setId(examInsertion.getContentId());
            examEntity.setContent(contentEntity);
        }
        examEntity.setDiscontinueDate(examInsertion.getDiscontinueDate());

        for(QuestionInsertion questionInsertion:examInsertion.getQuestions().stream().sorted(Comparator.comparing(QuestionInsertion::getOrder)).collect(Collectors.toList())){
            ExamQuestionEntity examQuestionEntity = new ExamQuestionEntity();
            examQuestionEntity.setId(questionInsertion.getId());
            examQuestionEntity.setQuestion(questionInsertion.getQuestion());
            examQuestionEntity.setTitle(questionInsertion.getTitle());
            examQuestionEntity.setIsMultipleChoice(questionInsertion.getIsMultipleChoice());
            examQuestionEntity.setDiscontinueDate(questionInsertion.getDiscontinueDate());
            examQuestionEntity.setOrder(questionInsertion.getOrder());
            if(questionInsertion.isHasImage()){
                if(filesIterator.hasNext()){
                    try {
                        ImageUploader imageUploader = Uploader.getImageUploader(filesIterator.next());
                        imageUploader.upload();
                        examQuestionEntity.setImageName(imageUploader.getContentName());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            if(!questionInsertion.getIsMultipleChoice()){
                ExamAnswerEntity examAnswerEntity = new ExamAnswerEntity();
                if(questionInsertion.getAnswers().size()>0)
                    examAnswerEntity.setId(questionInsertion.getAnswers().get(0).getId());
                examAnswerEntity.setOrder(1);
                examAnswerEntity.setQuestion(examQuestionEntity);
                examQuestionEntity.getAnswers().add(examAnswerEntity);
            }else {
                for (Answer answer : questionInsertion.getAnswers().stream().sorted(Comparator.comparing(Answer::getOrder)).collect(Collectors.toList())) {
                    ExamAnswerEntity examAnswerEntity = new ExamAnswerEntity();
                    examAnswerEntity.setId(answer.getId());
                    examAnswerEntity.setAnswer(answer.getAnswer());
                    examAnswerEntity.setIsRightAnswer(answer.getIsRightAnswer());
                    examAnswerEntity.setDiscontinueDate(answer.getDiscontinueDate());
                    examAnswerEntity.setOrder(answer.getOrder());
                    examAnswerEntity.setQuestion(examQuestionEntity);
                    examQuestionEntity.getAnswers().add(examAnswerEntity);
                }
            }
            examQuestionEntity.setExam(examEntity);
            examEntity.getQuestions().add(examQuestionEntity);
        }
        return examEntity;
    }

    public static ExamInsertion toInsertionModel(ExamEntity examEntity){
        if(examEntity == null)
            return null;
        ExamInsertion examInsertion = new ExamInsertion();
        examInsertion.setId(examEntity.getId());
        examInsertion.setName(examEntity.getName());
        examInsertion.setTitle(examEntity.getTitle());
        examInsertion.setDescription(examEntity.getDescription());
        examInsertion.setStartDate(examEntity.getStartDate());
        examInsertion.setFinishDate(examEntity.getFinishDate());
        examInsertion.setCompanyId(examEntity.getCompany().getId());
        if(examEntity.getContent() != null)
            examInsertion.setContentId(examEntity.getContent().getId());
        examInsertion.setDiscontinueDate(examEntity.getDiscontinueDate());

        for(ExamQuestionEntity questionEntity:examEntity.getQuestions().stream().sorted(Comparator.comparing(ExamQuestionEntity::getOrder)).collect(Collectors.toList())){
            QuestionInsertion questionInsertion = new QuestionInsertion();
            questionInsertion.setId(questionEntity.getId());
            questionInsertion.setQuestion(questionEntity.getQuestion());
            questionInsertion.setTitle(questionEntity.getTitle());
            questionInsertion.setIsMultipleChoice(questionEntity.getIsMultipleChoice());
            questionInsertion.setDiscontinueDate(questionEntity.getDiscontinueDate());
            questionInsertion.setOrder(questionEntity.getOrder());
            questionInsertion.setHasImage(questionEntity.getImageName() != null);
            questionInsertion.setImagePath(PathUtils.UPLOAD_IMAGE_SHORT_DIR + questionEntity.getImageName());
            for(ExamAnswerEntity answerEntity:questionEntity.getAnswers().stream().sorted(Comparator.comparing(ExamAnswerEntity::getOrder)).collect(Collectors.toList())){
                Answer answer = new Answer();
                answer.setId(answerEntity.getId());
                answer.setAnswer(answerEntity.getAnswer());
                answer.setIsRightAnswer(answerEntity.getIsRightAnswer());
                answer.setDiscontinueDate(answerEntity.getDiscontinueDate());
                answer.setOrder(answerEntity.getOrder());
                questionInsertion.getAnswers().add(answer);
            }
            examInsertion.getQuestions().add(questionInsertion);
        }
        return examInsertion;
    }

    public static List<Exam> toModelList(List<ExamEntity> examEntityList){
        return examEntityList.stream().map(examEntity -> toModel(examEntity)).collect(Collectors.toList());
    }

    public static List<ExamEntity> toEntityList(List<Exam> examList){
        return examList.stream().map(exam -> toEntity(exam)).collect(Collectors.toList());
    }
}
