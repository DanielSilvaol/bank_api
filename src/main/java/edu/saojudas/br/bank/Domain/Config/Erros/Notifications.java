package edu.saojudas.br.bank.Domain.Config.Erros;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notifications {
    private String Property;
    private String Message;

    public Notifications(String property, String message) {
        Property = property;
        Message = message;
    }
}
