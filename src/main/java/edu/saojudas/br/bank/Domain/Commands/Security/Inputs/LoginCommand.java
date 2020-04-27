package edu.saojudas.br.bank.Domain.Commands.Security.Inputs;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginCommand {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;

    public UsernamePasswordAuthenticationToken userData()
    {
        return new UsernamePasswordAuthenticationToken(login,password);
    }
}
