package edu.saojudas.br.bank.Domain.Commands.Security.Outputs;

import lombok.Getter;

@Getter
public class TokenTO {
    private String token;
    private String type;

    public TokenTO(String token, String type)
    {
        this.token = token;
        this.type = type;
    }
}
