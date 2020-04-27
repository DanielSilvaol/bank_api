package edu.saojudas.br.bank.Domain.Config.Security;

import edu.saojudas.br.bank.Domain.Entity.CustomerEntity;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;

@Service
public class TokenService  {
    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authenticate)
    {
        CustomerEntity usuario = (CustomerEntity) authenticate.getPrincipal();

        return Jwts.builder().setIssuer("API do Forum da Alura").setSubject(usuario.getId().toString()).setIssuedAt(new Date())//Data de hoje
                .setExpiration(new Date(new Date().getTime() + Long.parseLong(expiration)))//Data de hoje mais experation em milissegundos
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    boolean isTokenValid(String token)
    {

        try
        {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }


    }

    Long getIdUsuario(String token)
    {
        return Long.parseLong(Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject());


    }

}
