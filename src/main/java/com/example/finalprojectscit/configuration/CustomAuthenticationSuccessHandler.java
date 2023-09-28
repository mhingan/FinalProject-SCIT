/**
 * @author Mihaita Hingan
 */
package com.example.finalprojectscit.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> "USER".equals(authority.getAuthority()))) {
            return "/dashboard";
        } else {
            return "/admin/panel";
        }
    }

}

