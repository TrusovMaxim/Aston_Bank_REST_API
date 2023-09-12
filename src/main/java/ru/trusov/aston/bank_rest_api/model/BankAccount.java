package ru.trusov.aston.bank_rest_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Bank_Account")
@Data
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "bank_account_number")
    private Long bankAccountNumber;
    @Column(name = "all_money")
    private Double allMoney;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public BankAccount(Long bankAccountNumber, Double allMoney, Client client) {
        this.bankAccountNumber = bankAccountNumber;
        this.allMoney = allMoney;
        this.client = client;
    }
}