package com.cognizant.microservices.authservice.security;

import com.cognizant.microservices.authservice.exceptions.TokenInvalidException;
import com.cognizant.microservices.authservice.service.LoginUserDetailsServiceImpl;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@NoArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private LoginUserDetailsServiceImpl loginUserDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String auth = request.getHeader("Authorization");
        String x = request.getRequestURI();
//        if(!x.equals("/auth/login")) {
//            try {
//                boolean b = checkIsTokenValidOne(auth);
//                if (b) {
//                    String userName = tokenHandler.extractUsername(auth.substring(7));
//                    UserDetails details = loginUserDetailsService.loadUserByUsername(userName);
//                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
//                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(upat);
//                }
//            } catch (Exception e) {
//                log.error("************** authentication failed for the token ***********");
//            }
//          }
        filterChain.doFilter(request, response);
    }

    boolean checkIsTokenValidOne(String auth) throws TokenInvalidException {
            boolean b = tokenHandler.validateToken(auth.substring(7));
        //eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZWRkeSIsInN1YiI6InRlZGR5IiwiaWF0IjoxNjYwMzA1NTI4LCJleHAiOjE2NjAzMDU1ODh9.p7wTsIT5PsnodgruWB0ZrY86bCriCz1SrOu8LnZbOow
            return b;
    }
}
