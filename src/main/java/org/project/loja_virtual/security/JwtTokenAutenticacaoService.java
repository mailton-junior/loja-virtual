package org.project.loja_virtual.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.project.loja_virtual.ApplicationContextLoad;
import org.project.loja_virtual.model.Users;
import org.project.loja_virtual.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Serviço para lidar com autenticação JWT.
 * Esta classe fornece métodos para gerar, validar,
 * e recuperar tokens JWT para autenticação de usuário.
 */
@Service
@Component
public class JwtTokenAutenticacaoService {


    /* Período de validade do token definido para 11 dias (em milissegundos) */
    private static final long EXPIRATION_TIME = 959990000;

    /* Chave secreta usada para assinar o JWT */
    private static final String SECRET = "ss/-*-*sds565dsd-s/d-s*dsds";

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    /**
     * Gera um token JWT para o nome de usuário fornecido e o adiciona à resposta.
     *
     * @param response o HttpServletResponse ao qual o token será adicionado
     * @param username o nome de usuário para o qual o token é gerado
     * @throws Exception se ocorrer um erro ao gerar o token
     */
    public void addAuthentication(HttpServletResponse response, String username) throws Exception {
        String JWT = Jwts.builder()
                .setSubject(username) // Sets the subject as the username
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Sets expiration time
                .signWith(SignatureAlgorithm.HS512, SECRET) // Signs the token
                .compact(); // Compacts the token

        String token = TOKEN_PREFIX + " " + JWT; // Prepares the token for the response

        response.addHeader(HEADER_STRING, token); // Adds the token to the response header
        liberacaoCors(response); // Handles CORS

        // Optionally write the token to the response body for testing (e.g., in Postman)
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }


    /**
     * Recupera e valida o token JWT da solicitação.
     *
     * @param request o HttpServletRequest contendo o token
     * @param response o HttpServletResponse para definir o status e escrever mensagens
     * @return um objeto Authentication se o token for válido; null caso contrário
     * @throws IOException se ocorrer um erro ao escrever a resposta
     */
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

            try {
                // Faz a validação do token do usuário na requisição e obtém o USER
                String user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(tokenLimpo)
                        .getBody()
                        .getSubject(); // ADMIN ou Alex

                if (user != null) {
                    Users usuario = ApplicationContextLoad
                            .getApplicationContext()
                            .getBean(UserRepository.class)
                            .findUserByLogin(user);

                    if (usuario != null) {
                        return new UsernamePasswordAuthenticationToken(
                                usuario.getLogin(),
                                usuario.getPassword(),
                                usuario.getAuthorities());
                    }
                }
            } catch (Exception e) {
                // Token inválido ou expirado
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
            }
        }

        liberacaoCors(response);
        return null;
    }


    /**
     * Recupera o usuário associado ao token da solicitação.
     * Este método é semelhante a getAuthentication, mas não gera exceções.
     *
     * @param request o HttpServletRequest contendo o token
     * @param response o HttpServletResponse para manipular CORS
     * @return um objeto Authentication se o token for válido; null caso contrário
     */
    public Authentication getAuthetication(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader(HEADER_STRING);

        if (token != null) {

            String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

            /*Faz a validacao do token do usuário na requisicao e obtem o USER*/
            String user = Jwts.parser().
                    setSigningKey(SECRET)
                    .parseClaimsJws(tokenLimpo)
                    .getBody().getSubject(); /*ADMIN ou Alex*/

            if (user != null) {

                Users usuario = ApplicationContextLoad.
                        getApplicationContext().
                        getBean(UserRepository.class).findUserByLogin(user);

                if (usuario != null) {
                    return new UsernamePasswordAuthenticationToken(
                            usuario.getLogin(),
                            usuario.getPassword(),
                            usuario.getAuthorities());
                }

            }

        }

        liberacaoCors(response);
        return null;
    }


    /**
     * Configura as configurações do CORS para evitar erros do navegador.
     *
     * @param response o HttpServletResponse ao qual os cabeçalhos CORS são adicionados
     */
    private void liberacaoCors(HttpServletResponse response) {

        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }


        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }


        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }

    }

}

