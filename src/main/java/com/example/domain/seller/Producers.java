package com.example.domain.seller;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "producers")
class Producers implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private Instant timestamp;

    @OneToMany(mappedBy = "producerId")
    Set<Sellers> sellers;

    Producers() {
    }
    Producers(String name, Instant timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Sellers> getSellers() {
        return sellers;
    }

    public void setSellers(Set<Sellers> sellers) {
        this.sellers = sellers;
    }
}
