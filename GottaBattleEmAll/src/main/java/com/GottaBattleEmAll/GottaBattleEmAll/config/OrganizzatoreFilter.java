package com.GottaBattleEmAll.GottaBattleEmAll.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class OrganizzatoreFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(request.getSession().getAttribute("organizzatore") == null){
                //setting attribute error
                request.setAttribute("error", "Effettua il login");

                response.sendRedirect("/login");
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
        return !path.startsWith("/Organizzatore/");
    }
}
