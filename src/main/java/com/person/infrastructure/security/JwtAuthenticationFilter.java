package com.person.infrastructure.security;

import com.person.infrastructure.object.JwtUser;
import com.person.model.Users;
import com.person.service.UserService;
import com.person.utils.ConvertUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private ServletValidator servletValidator;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("NV-TOKEN");
        JwtUser jwtUser = null;
        String urlPath = request.getRequestURI();
        log.info("[" + request.getMethod() + "] " + urlPath);

        if (needPushToKpi(urlPath)) {
            log.info("KPI tracking for: " + urlPath);
        }

        if (!urlPath.startsWith("/sys/v1")) {
            RequestWrapper reqWrapper = new RequestWrapper(request);
            RequestObject req = ConvertUtils.requestToObject(reqWrapper.getBody(), RequestObject.class);
            if (req != null) {
                servletValidator.validate(req);
            }
            filterChain.doFilter(reqWrapper, response);
            return; // Stop further processing
        }

        if (authHeader != null) {
            try {
                jwtUser = jwtUtils.extractJwtUserFromToken(authHeader);
            } catch (ExpiredJwtException e) {
                log.error("JWT Token has expired", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                return;
            } catch (Exception e) {
                log.error("JWT Token is invalid", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
                return;
            }
        }

        if (jwtUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Users userDetails = userService.getUser(jwtUser.getId());
            if (verifyUser(userDetails, jwtUser) && jwtUtils.validateToken(authHeader)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
                return;
            }
        }

        if (!request.getMethod().equals(HttpMethod.GET.name()) && !isNotJsonBody(urlPath)) {
            RequestWrapper reqWrapper = new RequestWrapper(request);
            RequestObject req = ConvertUtils.requestToObject(reqWrapper.getBody(), RequestObject.class);
            if (req != null) {
                servletValidator.validate(req);
            }
            filterChain.doFilter(reqWrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isNotJsonBody(String urlPath) {
        String apiRoute = urlPath.replace("/api/v1/", "");
        return apiRoute.startsWith("user/updateImageProfile")
                || apiRoute.startsWith("userMetadata/uploadImage");
    }

    private boolean verifyUser(Users user, JwtUser jwtUser) {
        return user != null;
    }

    private boolean needPushToKpi(String urlPath) {
        return urlPath.startsWith("/api/v1/chat/login")
                || urlPath.startsWith("/api/v1/chat/register");
    }
}
