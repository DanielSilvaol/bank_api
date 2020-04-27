package edu.saojudas.br.bank.Domain.Config.Security;

import edu.saojudas.br.bank.Domain.Entity.CustomerEntity;
import edu.saojudas.br.bank.Infrastructure.Repository.IUserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TokenFilterAuthentication extends OncePerRequestFilter {
    private TokenService _tokenService;
    private IUserRepository _repository;

    public TokenFilterAuthentication(TokenService _tokenService, IUserRepository repository)
    {
        this._tokenService = _tokenService;
        this._repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        String token = recuperarToken(httpServletRequest);

        boolean valido = _tokenService.isTokenValid(token);
        if (valido)
            autenticarUsuario(token);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void autenticarUsuario(String token)
    {
        Long idUsuario = _tokenService.getIdUsuario(token);
        CustomerEntity usuario = _repository.findById(idUsuario).get();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String recuperarToken(HttpServletRequest httpServletRequest)
    {
        final String token = httpServletRequest.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer "))
        {
            return null;
        }

        return token.substring(7);
    }
}
