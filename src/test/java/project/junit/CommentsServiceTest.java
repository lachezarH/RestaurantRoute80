package project.junit;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import project.model.binding.CommentsBindingModel;

import project.model.entity.Comments;
import project.repository.CommentsRepository;
import project.service.CommentsService;
import project.service.Impl.CommentsServiceImpl;


import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceTest {


    private CommentsServiceImpl testService;

    @Mock
    CommentsRepository mockCommentsRepository;


    private ModelMapper modelMapper;


    @BeforeEach
    public void setUp() {
        this.modelMapper = new ModelMapper();
        testService = new CommentsServiceImpl(mockCommentsRepository,modelMapper);

    }

    @AfterEach
    public void afterEach() {
        this.mockCommentsRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        //Arrange

        Comments comments = createComments();

       when(mockCommentsRepository.findAll()).thenReturn(List.of(comments));

        //act
        List<CommentsBindingModel> commentsBindingModels = this.testService.findAll();

        //assert
        Assertions.assertEquals(1, commentsBindingModels.size());

        CommentsBindingModel model = commentsBindingModels.get(0);

        Assertions.assertEquals(comments.getAuthor(), model.getAuthor());
        Assertions.assertEquals(comments.getDescription(), model.getDescription());
    }

    @Test
    public void testDeleteComment(){
        Comments comments = createComments();

        testService.deleteComments(comments.getId());

        verify(mockCommentsRepository, times(1)).deleteById(comments.getId());
    }

    @Test
    public void testUpdateOrCreateComments(){
        CommentsBindingModel model = this.modelMapper.map(createComments(), CommentsBindingModel.class);
        testService.updateOrCreateComments(model);


        verify(mockCommentsRepository, times(1)).saveAndFlush(any());

    }

    private Comments createComments(){
        Comments comments = new Comments();
        comments.setId("1");
        comments.setAuthor("Lucho");
        comments.setDescription("Hello world!");
        comments.setUpdatedOn(Instant.now());
        comments.setCreatedOn(Instant.now());
        return comments;
    }
}
