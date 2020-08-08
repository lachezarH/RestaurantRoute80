package project.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.model.binding.CommentsBindingModel;
import project.model.entity.Comments;
import project.repository.CommentsRepository;
import project.service.CommentsService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final ModelMapper modelMapper;

    public CommentsServiceImpl(CommentsRepository commentsRepository, ModelMapper modelMapper) {
        this.commentsRepository = commentsRepository;
        this.modelMapper = modelMapper;
    }




    //create comments and save it
    @Override
    public void updateOrCreateComments(CommentsBindingModel commentsBindingModel) {
        if (commentsBindingModel.getAuthor().isEmpty()){
            commentsBindingModel.setAuthor("Anonymous");
        }

        Comments comments = this.modelMapper.map(commentsBindingModel, Comments.class);

        comments.setCreatedOn(Instant.now());
        comments.setUpdatedOn(Instant.now());

        commentsRepository.saveAndFlush(comments);
    }

    //delete comment by ID
    @Override
    public void deleteComments(String  commentsId) {
        this.commentsRepository.deleteById(commentsId);
    }

    //findAll comments return List<CommentsBindingModel> models
    @Override
    public List<CommentsBindingModel> findAll() {
        return this.commentsRepository.findAll()
                .stream()
                .map(comments -> {
                    CommentsBindingModel model = this.modelMapper.map(comments, CommentsBindingModel.class);

                    return model;
                })
                .collect(Collectors.toList());

    }
}
