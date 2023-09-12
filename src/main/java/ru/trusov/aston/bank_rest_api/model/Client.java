package ru.trusov.aston.bank_rest_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "Client")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "bankAccount"})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    @NotEmpty(message = "Имя не может быть пустым")
    private String username;
    @Column(name = "pincode")
    private Integer pincode;
    @OneToOne(mappedBy = "client", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private BankAccount bankAccount;

    public Client(String username, int pincode) {
        this.username = username;
        this.pincode = pincode;
    }
}