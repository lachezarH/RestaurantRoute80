package project.service.Impl;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.model.entity.RoleEntity;
import project.model.entity.User;
import project.model.service.UserServiceModel;
import project.repository.RoleRepository;
import project.repository.UserRepository;
import project.service.UserService;

import java.util.*;

@Primary
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserDetailsService userDetailsService;



    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
    }




    //find UserServiceModel by username
    @Override
    public UserServiceModel findByUsername(String username) {

        return this.userRepository.findByUsername(username)
                .map(user ->
                        this.modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    //find User by email
    @Override
    public User findByEmail(String email) {
        return this.userRepository.findOneByEmail(email)
                .map(user ->
                        this.modelMapper.map(user, User.class))
                .orElse(null);
    }


    //spring security login method
    @Override
    public void login(String username) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication authentication = new
                UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    //return boolean for user exists in userRepository or no BY username
    @Override
    public boolean exists(String username) {

            Optional<User> userEntityOpt =
                    userRepository.findByUsername(username);

            return userEntityOpt.isPresent();
        }

    //return boolean for user exists in userRepository or no BY email
    @Override
    public boolean existsByEmail(String email) {
        Optional<User> userEntityOpt =
                userRepository.findOneByEmail(email);

        return userEntityOpt.isPresent();
    }

    //spring security method for register and login in app
    @Override
    public void registerAndLoginUser(UserServiceModel userServiceModel) {
        User userEntity = createUser(userServiceModel);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getEmail());

        Authentication authentication = new
                UsernamePasswordAuthenticationToken(
                userDetails,
                userEntity.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    //create save and return user
    @Override
    public User createUser(UserServiceModel userServiceModel) {
        LOGGER.info("Creating a new user with email [PROTECTED].");

        User userEntity = this.modelMapper.map(userServiceModel, User.class);

        if (userEntity.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }

        if (this.userRepository.count() == 0){
            RoleEntity adminRole = new RoleEntity().setRole("ROLE_ADMIN");
            RoleEntity userRole = new RoleEntity().setRole("ROLE_USER");
            userEntity.setRoles(List.of(userRole,adminRole));
        }else {
            RoleEntity userRole = new RoleEntity().setRole("ROLE_USER");
            userEntity.setRoles(List.of(userRole));
        }
        return userRepository.save(userEntity);
    }

    //this method is for facebook login who doesn't work i should to fix it
    @Override
    public User getOrCreateUser(String  username) {
        Optional<User> userOpt =
                userRepository.findByUsername(username);

        return userOpt.
                orElseGet(() -> createUserFromFacebook(username));
    }

    //this method is for facebook login who doesn't work i should to fix it
    @Override
    public User createUserFromFacebook(String username) {
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername(username);
        userServiceModel.setEmail("facebookUser@" +username + ".com");

        User user = this.modelMapper.map(userServiceModel, User.class);


            RoleEntity userRole = new RoleEntity().setRole("ROLE_USER");
            user.setRoles(List.of(userRole));

        return userRepository.save(user);
    }


}

