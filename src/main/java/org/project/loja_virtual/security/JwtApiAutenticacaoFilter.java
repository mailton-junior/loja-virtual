package org.project.loja_virtual.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Filtro de autenticação JWT que intercepta todas as requisições
 * para autenticar o usuário antes de permitir o acesso aos recursos protegidos.
 */
public class JwtApiAutenticacaoFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtApiAutenticacaoFilter.class);

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

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            // Estabelece a autenticação do usuário
            Authentication authentication = new JwtTokenAutenticacaoService()
                    .getAuthentication((HttpServletRequest) request, httpResponse);

            // Define o contexto de segurança com a autenticação obtida
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Continua a cadeia de filtros
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            // Loga o erro detalhadamente para depuração
            logger.error("Erro de autenticação: {}", e.getMessage(), e);

            // Define o status HTTP e mensagem de erro apropriada
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");

            String errorMessage = "{ \"error\": \"Falha na autenticação\", \"message\": \"" + e.getMessage() + "\" }";
            httpResponse.getWriter().write(errorMessage);
        } finally {
            // Limpa o contexto de segurança para evitar vazamentos em requisições subsequentes
            SecurityContextHolder.clearContext();
        }
    }
}
