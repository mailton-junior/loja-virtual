package org.project.loja_virtual.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro de autenticação JWT que captura todas as requisições
 * para autenticar o usuário antes de processá-las.
 */
public class JwtApiAutenticacaoFilter extends GenericFilterBean {

    /**
     * Método que processa o filtro de autenticação.
     *
     * @param request  a requisição do cliente
     * @param response a resposta a ser enviada ao cliente
     * @param chain    a cadeia de filtros para continuar o processamento
     * @throws IOException      se ocorrer um erro de I/O
     * @throws ServletException se ocorrer um erro de servlet
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Estabelece a autenticação do usuário
        Authentication authentication = new JwtTokenAutenticacaoService()
                .getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

        // Define o contexto de segurança com a autenticação obtida
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continua a cadeia de filtros
        chain.doFilter(request, response);
    }
}

