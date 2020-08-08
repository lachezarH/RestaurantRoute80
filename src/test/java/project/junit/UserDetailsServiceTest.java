package project.junit;



import static org.mockito.Mockito.when;


import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.model.entity.RoleEntity;
import project.model.entity.User;
import project.repository.UserRepository;
import project.service.Impl.UserDetailsServiceImpl;



@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    private UserDetailsServiceImpl serviceToTest;

    private String TEST_USER_NAME_EXISTS = "pesho@example.com";
    private String TEST_USER_NAME_NOT_EXISTS = "anna@example.com";

    @Mock
    private User testUserEntity;

    @Mock
    private RoleEntity testRoleEntity;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        testRoleEntity = new RoleEntity();
        testRoleEntity.setRole("USER");


        testUserEntity = new User();
        testUserEntity.setUsername("pesho@example.com");
        testUserEntity.setEmail("pesho@example.com");
        testUserEntity.setPassword("abcdef");
        testUserEntity.setRoles(List.of(testRoleEntity));

        serviceToTest = new UserDetailsServiceImpl(mockUserRepository);

    }

    @Test
    public void testUserNotFound() {

        Mockito.lenient().when(mockUserRepository.findOneByEmail(TEST_USER_NAME_NOT_EXISTS)).
                thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            serviceToTest.loadUserByUsername(TEST_USER_NAME_NOT_EXISTS);
        });
    }

    @Test
    public void testUserFound() {

        Mockito.lenient().when(mockUserRepository.findByUsername(testUserEntity.getUsername())).thenReturn(Optional.of(testUserEntity));

        UserDetails actualDetails = serviceToTest.loadUserByUsername(testUserEntity.getUsername());

        Assertions.assertEquals(testUserEntity.getEmail(), actualDetails.getUsername());
    }



}