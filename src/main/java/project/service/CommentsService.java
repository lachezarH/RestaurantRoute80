package project.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import project.model.binding.CommentsBindingModel;

import java.util.List;

public interface CommentsService {

    void updateOrCreateComments(CommentsBindingModel commentsBindingModel);


    void deleteComments(String  commentsId);

    List<CommentsBindingModel> findAll();
}
