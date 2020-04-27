package edu.saojudas.br.bank.Domain.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "Customer")
public class CustomerEntity implements UserDetails {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password ;

    @Column(name = "email")
    private String email ;

    @Column(name = "blocked")
    private boolean blocked ;

    @Column(name = "inclusion_user")
    private int inclusion_user ;

    @Column(name = "inclusion_date")
    @Temporal(TemporalType.DATE)
    private Date inclusion_date ;

    @Column(name = "d_e_l_e_t_e")
    private String d_e_l_e_t_e;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profile",
            joinColumns = { @JoinColumn(name = "Id") },
            inverseJoinColumns = { @JoinColumn(name = "IdProfile") },
            foreignKey = @ForeignKey(name = "fk_perfis_usuario"))
    private List<ProfileEntity> profiles = new ArrayList<>();

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Id == null) ? 0 : Id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomerEntity other = (CustomerEntity) obj;
        if (Id == null)
        {
            return other.Id == null;
        } else
            return Id.equals(other.Id);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.profiles;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String getUsername()
    {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
