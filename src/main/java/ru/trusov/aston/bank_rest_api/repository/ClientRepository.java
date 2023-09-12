package ru.trusov.aston.bank_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.trusov.aston.bank_rest_api.model.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "FROM Client c LEFT JOIN FETCH c.bankAccount WHERE c.username = :username AND c.pincode = :pincode")
    Optional<Client> getClientByUsernameAndPincode(@Param("username") String username, @Param("pincode") Integer pincode);
}