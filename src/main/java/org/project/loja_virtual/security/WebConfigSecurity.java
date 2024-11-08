package org.project.loja_virtual.security;

import org.project.loja_virtual.service.ImplementationUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpSessionListener;

/**
 * Classe de configuração de segurança para o aplicativo da web.
 * Esta classe é responsável por configurar as configurações de segurança, como autenticação,
 * autorização e gerenciamento de sessão.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

    @Autowired
    private ImplementationUserDetailService implementationUserDetailService;

    /**
     * Configura as configurações de segurança HTTP para o aplicativo.
     * Define quais endpoints são publicamente acessíveis e configura filtros para autenticação JWT.
     *
     * @param http o HttpSecurity a ser configurado
     * @throws Exception se ocorrer um erro durante a configuração
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                /* redireciona ou da um retorno para index quando desloga*/
                .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

                /*mapeia o logout do sistema*/
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                /*Filtra as requisicoes para login de JWT*/
                .and().addFilterAfter(new JwtLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    /**
     * Configura as configurações de autenticação usando um serviço de detalhes do usuário e codificador de senha.
     *
     * @param auth o AuthenticationManagerBuilder para configurar
     * @throws Exception se ocorrer um erro durante a configuração
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(implementationUserDetailService).passwordEncoder(new BCryptPasswordEncoder());

    }




    /**
     * Configura a segurança da web para ignorar certas URLs da autenticação.
     *
     * @param web o WebSecurity a ser configurado
     * @throws Exception se ocorrer um erro durante a configuração
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso", "/deleteAcesso")
        //.antMatchers(HttpMethod.POST, "/salvarAcesso", "/deleteAcesso");
        /*Ingnorando URL no momento para nao autenticar*/
    }


}

