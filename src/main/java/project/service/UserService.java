package project.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.model.entity.User;
import project.model.service.UserServiceModel;

public interface UserService {

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);



    User findByEmail(String email);

  /*  User createUser();*/

   // void initRoles();

    void login(String username);

    boolean exists(String username);
    boolean existsByEmail(String email);

    void registerAndLoginUser(UserServiceModel userServiceModel);
    User createUser(UserServiceModel userServiceModel) ;

    User getOrCreateUser(String username);

    User createUserFromFacebook(String username);

}
