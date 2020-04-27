package edu.saojudas.br.bank.Domain.Config.Security;

import edu.saojudas.br.bank.Domain.Entity.CustomerEntity;
import edu.saojudas.br.bank.Infrastructure.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    private final IUserRepository _repository;

    @Autowired
    public AuthenticationService(IUserRepository _repository) {this._repository = _repository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<CustomerEntity> usuario = _repository.findByLogin(username);
        if (usuario.isPresent()){
            return usuario.get();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }
}
