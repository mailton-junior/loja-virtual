package org.project.loja_virtual.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
 * JWTTokenAutenticacaoService Criar e validar token
 */
@Service
@Component
public class JWTTokenAutenticacaoService {

    /*Token de validade de 11 dias*/
    private static final long EXPIRATION_TIME = 959990000;

    /*Uma senha unica para compor a autenticacao e ajudar na segurança*/
    private static final String SECRET = "ss/-*-*sds565dsd-s/d-s*dsds";

    /*Prefixo padrão de Token*/
    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    /*Gerando token de autenticacao e adicionando o prefixo*/
    public void addAuthentication(HttpServletResponse response, String username) throws Exception {

        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        String token = TOKEN_PREFIX + " " + JWT;

        response.addHeader(HEADER_STRING, token);

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }
}
