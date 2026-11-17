package org.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


@Entity
@Table(name = "uzenet")
public class Uzenet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A név megadása kötelező!")
    @Size(max = 100, message = "A név legfeljebb 100 karakter lehet!")
    @Column(name = "nev", length = 100, nullable = false)
    private String nev;

    @NotBlank(message = "Az e-mail cím megadása kötelező!")
    @Email(message = "Érvénytelen e-mail cím!")
    @Size(max = 100, message = "Az e-mail cím legfeljebb 100 karakter lehet!")
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @NotBlank(message = "Az üzenet megadása kötelező!")
    @Size(min = 10, max = 1000, message = "Az üzenet 10 és 1000 karakter közötti legyen!")
    @Column(name = "uzenet", length = 1000, nullable = false)
    private String uzenet;

    @Column(name = "kuldve", nullable = false)
    private LocalDateTime kuldve;

    public Uzenet() {}

    @PrePersist
    void beforeSave() {
        if (kuldve == null) {
            kuldve = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUzenet() { return uzenet; }
    public void setUzenet(String uzenet) { this.uzenet = uzenet; }
    public LocalDateTime getKuldve() { return kuldve; }
    public void setKuldve(LocalDateTime kuldve) { this.kuldve = kuldve; }
}
