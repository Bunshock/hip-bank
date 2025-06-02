package com.bunshock.accounts.repository;

import com.bunshock.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByCustomerId(Long customerId);

}
