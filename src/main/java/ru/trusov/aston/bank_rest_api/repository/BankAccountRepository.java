package ru.trusov.aston.bank_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.trusov.aston.bank_rest_api.model.BankAccount;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query(value = "SELECT b FROM BankAccount b LEFT JOIN FETCH b.client")
    List<BankAccount> findAll();

    @Query(value = "FROM BankAccount b LEFT JOIN FETCH b.client WHERE b.bankAccountNumber = :bankAccountNumber")
    Optional<BankAccount> checkWhetherBankAccountExists(@Param("bankAccountNumber") Long bankAccountNumber);
}