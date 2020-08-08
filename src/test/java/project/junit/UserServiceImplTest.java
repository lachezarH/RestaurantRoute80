package project.junit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.model.entity.RoleEntity;
import project.model.entity.User;
import project.model.service.UserServiceModel;
import project.repository.RoleRepository;
import project.repository.UserRepository;
import project.service.Impl.UserDetailsServiceImpl;
import project.service.Impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//jUnit 4
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    UserRepository mockUserRepository;

    @Mock
    RoleRepository mockRoleRepository;


   private ModelMapper modelMapper;


   private BCryptPasswordEncoder encoder;

    @Mock
    UserDetailsServiceImpl mockUserDetailsService;



    @Autowired
    UserServiceImpl mockUserServiceImpl;


    @BeforeEach
    void setUp() {
            this.modelMapper = new ModelMapper();
            this.encoder = new BCryptPasswordEncoder();
           mockUserServiceImpl = new UserServiceImpl(mockUserRepository,modelMapper,encoder,mockRoleRepository,mockUserDetailsService);

    }


  @Test
  public void testFindByUsername(){
        User user = createUser();

      Mockito.lenient().when(mockUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

      UserServiceModel actualUser = mockUserServiceImpl.findByUsername(user.getUsername());
      actualUser.setRole(user.getRoles().get(0).getRole());

      Assertions.assertEquals(user.getId(), actualUser.getId());
      Assertions.assertEquals(user.getUsername(), actualUser.getUsername());
      Assertions.assertEquals(user.getEmail(), actualUser.getEmail());
      Assertions.assertEquals(user.getRoles().get(0).getRole(), actualUser.getRole());
      Assertions.assertEquals(user.getPassword(), actualUser.getPassword());
  }

    @Test
    public void testFindByEmail(){
        User user = createUser();

        Mockito.lenient().when(mockUserRepository.findOneByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User actualUser = mockUserServiceImpl.findByEmail(user.getEmail());


        Assertions.assertEquals(user.getId(), actualUser.getId());
        Assertions.assertEquals(user.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(user.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(user.getRoles(), actualUser.getRoles());
        Assertions.assertEquals(user.getPassword(), actualUser.getPassword());
    }

    @Test
    public void testExistMethodByUsername(){
        User user = createUser();

        Mockito.lenient().when(mockUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertTrue(this.mockUserServiceImpl.exists(user.getUsername()));
    }
    @Test
    public void testExistMethodByEmail(){
        User user = createUser();

        Mockito.lenient().when(mockUserRepository.findOneByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Assertions.assertTrue(this.mockUserServiceImpl.existsByEmail(user.getEmail()));
    }


    @Test
    public void testCreateUser(){
        User user = createUser();


       this.mockUserServiceImpl.createUser(this.modelMapper.map(user,UserServiceModel.class));

        verify(mockUserRepository, times(1)).save(any());

    }


    private User createUser(){
        User user = new User();
        user.setId("1");
        user.setUsername("someEmail");
        user.setPassword("pass");
        user.setRoles(List.of(new RoleEntity().setRole("USER")));
        user.setEmail("loko@loko.bg");

        return user;
    }

}
