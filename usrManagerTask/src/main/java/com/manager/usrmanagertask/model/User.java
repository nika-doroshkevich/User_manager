package com.manager.usrmanagertask.model;

import com.manager.usrmanagertask.enums.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Please, enter username.")
    @Size(min=1, max=255, message = "Username length must be between 1 and 255.")
    private String username;

    @NotNull(message = "Please, enter password.")
    @Size(min=1, max=255, message = "Password length must be between 1 and 255.")
    private String password;

    @NotNull(message = "Please, enter email.")
    @Email(message = "Email must be valid.")
    @Size(min=1, max=255, message = "Email length must be between 1 and 255.")
    private String mail;

    private LocalDate registrationDate;

    private LocalDate lastLoginDate;

    private Boolean blocked;

    private Boolean deleted;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
