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

/**
 * Filtro de autenticação para login usando JWT.
 * Este filtro processa as tentativas de autenticação de usuários
 * e gera um token JWT em caso de sucesso.
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * Constrói um filtro de autenticação.
     *
     * @param url                   a URL que o filtro deve proteger
     * @param authenticationManager o gerenciador de autenticação a ser utilizado
     */
    public JwtLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url)); // Configura a URL que requer autenticação
        setAuthenticationManager(authenticationManager); // Define o gerenciador de autenticação
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        Users user;
        try {
            // Tenta ler os dados do usuário a partir do corpo da requisição
            user = new ObjectMapper().readValue(request.getInputStream(), Users.class);
        } catch (IOException e) {
            // Retorna erro 400 se o JSON não puder ser lido
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null; // Retorna nulo ou pode-se lançar uma exceção personalizada
        }

        // Loga as tentativas de autenticação
        System.out.println("Tentativa de autenticação para: " + user.getLogin());

        // Tenta autenticar o usuário usando o gerenciador de autenticação
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        try {
            // Gera o token JWT e adiciona ao cabeçalho da resposta
            new JwtTokenAutenticacaoService().addAuthentication(response, authResult.getName());
        } catch (Exception e) {
            e.printStackTrace();
            // Em caso de erro ao gerar o token, retorna erro 500
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao gerar o token: " + e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // Retorna erro 401 em caso de falha na autenticação
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Falha na autenticação: " + failed.getMessage());
    }
}

