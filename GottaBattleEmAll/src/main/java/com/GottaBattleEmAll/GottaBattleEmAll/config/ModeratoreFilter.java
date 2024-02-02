package com.GottaBattleEmAll.GottaBattleEmAll.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.io.IOException;

@Component
public class ModeratoreFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(request.getSession().getAttribute("moderatore") == null){
                //setting attribute error
                request.setAttribute("error", "Effettua il login");

                response.sendRedirect("/Moderatore/loginAdmin");
            }
            else{
                filterChain.doFilter(request, response);
            }

        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Non hai i permessi per accedere a questa pagina");
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return !path.startsWith("/Moderatore/") || path.equals("/Moderatore/loginAdmin") || path.equals("/Moderatore/");
    }
}
