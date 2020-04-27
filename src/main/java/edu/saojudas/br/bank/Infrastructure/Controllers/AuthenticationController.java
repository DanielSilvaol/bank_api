package edu.saojudas.br.bank.Infrastructure.Controllers;

import edu.saojudas.br.bank.Domain.Commands.Security.Inputs.LoginCommand;
import edu.saojudas.br.bank.Domain.Commands.Security.Outputs.TokenTO;
import edu.saojudas.br.bank.Domain.Config.Security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager _manager;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager _manager, TokenService tokenService) {this._manager = _manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid LoginCommand command)
    {
        try
        {
            final Authentication authenticate = _manager.authenticate(command.userData());
            String token = tokenService.gerarToken(authenticate);
            return ResponseEntity.ok(new TokenTO(token,"Bearer"));
        }
        catch (AuthenticationException e)
        {
            return ResponseEntity.badRequest().build();
        }

    }
}
