package com.example.ZeroWasteMessTracker.security;

import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private  final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String path = request.getServletPath();

        if (path.contains("swagger") || path.contains("api-docs") || path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        String header= request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String token=header.substring(7);
        String username=jwtService.extractUsername(token);

        User user= userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

            List<SimpleGrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
            UsernamePasswordAuthenticationToken auth=
                    new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);


        filterChain.doFilter(request, response);
    }
}
