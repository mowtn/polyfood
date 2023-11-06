package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_decentralization")
public class Decentralization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int decentralizationId;
    @Enumerated(EnumType.STRING)

    @Column(length = 20)
    private EDecentralization name;

    public Decentralization() {
    }

    public Decentralization(EDecentralization name) {
        this.name = name;
    }

    public int getDecentralizationId() {
        return decentralizationId;
    }

    public void setDecentralizationId(int decentralizationId) {
        this.decentralizationId = decentralizationId;
    }

    public EDecentralization getName() {
        return name;
    }

    public void setName(EDecentralization name) {
        this.name = name;
    }
}
