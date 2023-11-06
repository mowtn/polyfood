package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_registration_code")
public class CodeRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CodeRegistrationId;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "codeRegistrations")
    User user;

    public CodeRegistration() {
    }

    public CodeRegistration(String code, LocalDateTime createAt, LocalDateTime expiresAt, User user) {
        this.code = code;
        this.createAt = createAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public int getCodeRegistrationId() {
        return CodeRegistrationId;
    }

    public void setCodeRegistrationId(int codeRegistrationId) {
        CodeRegistrationId = codeRegistrationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getConfirmAt() {
        return confirmAt;
    }

    public void setConfirmAt(LocalDateTime confirmAt) {
        this.confirmAt = confirmAt;
    }
}
