package edu.saojudas.br.bank.Domain.Entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity(name = "profile")
public class ProfileEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    @Column(name = "ID")
    private Long Id;

    @Column(name = "name")
    private String name;

    @Override
    public String getAuthority()
    {
        return name;
    }
}
