package org.project.loja_virtual.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.loja_virtual.model.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    /*Confgurando o gerenciado de autenticacao*/
    public JwtLoginFilter(String url, AuthenticationManager authenticationManager) {

        /*Ibriga a autenticat a url*/
        super(new AntPathRequestMatcher(url));

        /*Gerenciador de autenticao*/
        setAuthenticationManager(authenticationManager);

    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        Users user;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), Users.class);
        } catch (IOException e) {
            // Retornar erro 400 se o JSON não puder ser lido
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null; // Retornar nulo ou lançar uma exceção personalizada
        }

        // Logar tentativas de autenticação
        System.out.println("Tentativa de autenticação para: " + user.getLogin());

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        try {
            new JwtTokenAutenticacaoService().addAuthentication(response, authResult.getName());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao gerar o token: " + e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Falha na autenticação: " + failed.getMessage());
    }

}

