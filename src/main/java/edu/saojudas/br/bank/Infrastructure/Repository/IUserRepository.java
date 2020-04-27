package edu.saojudas.br.bank.Infrastructure.Repository;

import edu.saojudas.br.bank.Domain.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<CustomerEntity,Long> {
    Optional<CustomerEntity> findByLogin(String login);
}
