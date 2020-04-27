package edu.saojudas.br.bank.Domain.Config.Security;

import edu.saojudas.br.bank.Infrastructure.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthenticationService _autenticacao;
    private final TokenService _tokenService;
    private final IUserRepository _repository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception
    {
        return super.authenticationManager();
    }

    @Autowired
    public SecurityConfiguration(AuthenticationService _autenticacao, TokenService _tokenService, IUserRepository repository)
    {
        this._autenticacao = _autenticacao;
        this._tokenService = _tokenService;
        this._repository = repository;
    }

    //Configurações de autenticacao
    //Controle de acesso(Login)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(_autenticacao).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configurações de autorização
    //Parte de url quem pode acessar cada URL
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new TokenFilterAuthentication(_tokenService, _repository), UsernamePasswordAuthenticationFilter.class);
    }

    //Configurações de recursos estaticos
    //Requisições para arquivos(js,css,imagens,etc..)
    //Apenas para utilizar com front, no caso um MVC em vez de uma api
    @Override
    public void configure(WebSecurity web) throws Exception
    {
    }
}
