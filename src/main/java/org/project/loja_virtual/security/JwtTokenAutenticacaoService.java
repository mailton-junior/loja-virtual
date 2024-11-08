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

/*Criar a autenticação e retonar também a autenticação JWT*/
@Service
@Component
public class JwtTokenAutenticacaoService {


    /*Token de validade de 11 dias*/
    private static final long EXPIRATION_TIME = 959990000;

    /*Chave de senha para juntar com o JWT*/
    private static final String SECRET = "ss/-*-*sds565dsd-s/d-s*dsds";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    // Generates and sends the JWT token to the client
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


    /*Gera o token e da a responsta para o cliente o com JWT*/
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


    /*Retorna o usuário validado com token ou caso nao seja valido retona null*/
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


    /*Fazendo liberação contra erro de COrs no navegador*/
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

