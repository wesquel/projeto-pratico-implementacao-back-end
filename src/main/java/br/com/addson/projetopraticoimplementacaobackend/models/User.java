package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usu_seq")
    @SequenceGenerator(name = "usu_seq", sequenceName = "usu_seq", allocationSize = 1)
    @Column(name = "usu_id", nullable = false)
    private Integer id;

    @Column(name = "usu_username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "usu_password", nullable = false)
    private String password;

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
