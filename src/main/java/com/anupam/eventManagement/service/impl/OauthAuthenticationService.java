package com.anupam.eventManagement.service.impl;

import com.anupam.eventManagement.entity.User;
import com.anupam.eventManagement.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OauthAuthenticationService implements AuthenticationSuccessHandler {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(OauthAuthenticationService.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        logger.info("Authentication was successful");

        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        User user1 = userRepository.findByEmail(email).orElse(null);
        User user2 =user1;
        if (user1 == null) {
            user1 = User.builder()
                    .email(email)
                    .fullName(name)
                    .authProvider("GOOGLE")
                    .password(null)
                    .build();
          user2 =  userRepository.save(user1);
            logger.info("New OAuth user saved: {}", user1);
        } else {
            logger.info("Existing user found: {}", user1);
        }

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user1);

//        // Set JWT in cookie with proper settings
//        Cookie jwtCookie = new Cookie("authToken", jwtToken);
//        jwtCookie.setHttpOnly(true);
//        jwtCookie.setSecure(false);   // Set to true when in production and using HTTPS
//        jwtCookie.setPath("/");
//        jwtCookie.setMaxAge(24 * 60 * 60);
//
//        // Add the cookie
//        response.addCookie(jwtCookie);

        // Redirect to frontend (adjust URL if needed)
        response.sendRedirect("http://localhost:5173/oauthCallback?token="+jwtToken+"&"+"userName="+user2.getFullName()+"&"+"userId="+user2.getId());
    }
}
