package project.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.service.OAuth2UserAuthSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder passwordEncoder;

  private final OAuth2UserAuthSuccessHandler oAuth2UserAuthSuccessHandler;

  public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder,OAuth2UserAuthSuccessHandler oAuth2UserAuthSuccessHandler) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.oAuth2UserAuthSuccessHandler = oAuth2UserAuthSuccessHandler;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // for the ant pattern matcher syntax, please check:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html

    http
        .authorizeRequests()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .antMatchers("/login**","/login-error**","/registration**").permitAll()
        //.antMatchers("/users/login**", "/login-error**","/").permitAll()
            .antMatchers("/**")
        .authenticated().
        and()
          .formLogin()
          .loginPage("/login")
            .usernameParameter("username")
            .passwordParameter("password")
          .loginProcessingUrl("/login/authenticate")
          .failureForwardUrl("/login-error")
          .successForwardUrl("/home")
        .and()
          .logout()
          .logoutUrl("/logout")
          .logoutSuccessUrl("/home")
          .invalidateHttpSession(true)
          .deleteCookies("JSESSIONID");
       /* .and().
          oauth2Login().
          loginPage("/login").
          successHandler(oAuth2UserAuthSuccessHandler);*/
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth)
      throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(
        new BCryptPasswordEncoder());
  }

  /*@Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
  }*/
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);


/*    auth.inMemoryAuthentication()
            .passwordEncoder(passwordEncoder)
            .withUser("user").password(passwordEncoder.encode("123")).roles("USER_ROLE")
            .and()
            .withUser("admin").password(passwordEncoder.encode("1234")).roles("USER_ROLE", "ADMIN_ROLE");*/
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
            .ignoring()
            .antMatchers("/resources/**");
  }
}
