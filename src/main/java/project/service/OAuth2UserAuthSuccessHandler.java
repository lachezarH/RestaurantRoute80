/*
package project.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.model.entity.User;

@Component
public class OAuth2UserAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    private OAuth2AuthorizedClientService authorizedClientService;

    public OAuth2UserAuthSuccessHandler(UserService userService,
                                        UserDetailsService userDetailsService, OAuth2AuthorizedClientService authorizedClientService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authorizedClientService = authorizedClientService;

        // this might be configurable
        setDefaultTargetUrl("/home");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken =
                    (OAuth2AuthenticationToken)authentication;
*/
/*
            String username =
                    oAuth2AuthenticationToken.
                            getPrincipal().
                            getAttribute("username");*//*

            OAuth2AuthorizedClient client = authorizedClientService
                    .loadAuthorizedClient(((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(),
                            authentication.getName());
            String username = client.getPrincipalName();

            User user = userService.getOrCreateUser(username);
            //should not be null
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

            authentication = new
                    UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
*/
