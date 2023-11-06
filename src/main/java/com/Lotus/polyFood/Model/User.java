package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @NotBlank
    @Size(max = 100)
    private String username;
    private String avatar;
    @NotBlank
    @Size(min = 6,max = 120)

    private String password;

    @Size(max = 11)
    private String phone;
    @NotBlank
    @Email
    private String email;
    private String address;
    private int status;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Decentralization> decentralizations= new HashSet<>();
    private String resetPasswordToken;
    private Date resetPasswordTokenExpiry;
    private Date createAt;
    private Date updateAt;
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = "user")
    Set<ProductReview> productReviews;
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = "user")
    Set<Carts> carts;
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = "user")
    Set<Order> orders;
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = "user")
    Set<CodeRegistration> codeRegistrations;
    @OneToOne(mappedBy = "user")
    @JsonIgnoreProperties(value = "user")
    private RefreshToken refreshToken;



    public User() {
    }

    public User(String username, String password, String email, Date createAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createAt = createAt;
    }

    public User(String userName, String avatar, String password,
                String phone, String email, String address,
                int status, Set<Decentralization> decentralizations, Date createAt) {
        this.username = userName;
        this.avatar = avatar;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
        this.decentralizations = decentralizations;
        this.createAt = createAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Decentralization> getDecentralizations() {
        return decentralizations;
    }

    public void setDecentralizations(Set<Decentralization> decentralizations) {
        this.decentralizations = decentralizations;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Date getResetPasswordTokenExpiry() {
        return resetPasswordTokenExpiry;
    }

    public void setResetPasswordTokenExpiry(Date resetPasswordTokenExpiry) {
        this.resetPasswordTokenExpiry = resetPasswordTokenExpiry;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
