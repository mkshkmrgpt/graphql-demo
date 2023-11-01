package com.example.domain.seller;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "sellers")
class Sellers implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "producer_id", nullable = false)
    private Producers producerId;

    @ManyToOne
    @JoinColumn(name = "seller_info_id", nullable = false)
    private SellerInfo sellerInfoId;

    @Column
    private SellerState state;

    Sellers() {
    }

    Sellers(Producers producerId, SellerInfo sellerInfoId, SellerState state) {
        this.producerId = producerId;
        this.sellerInfoId = sellerInfoId;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Producers getProducerId() {
        return producerId;
    }

    public void setProducerId(Producers producerId) {
        this.producerId = producerId;
    }

    public SellerInfo getSellerInfoId() {
        return sellerInfoId;
    }

    public void setSellerInfoId(SellerInfo sellerInfoId) {
        this.sellerInfoId = sellerInfoId;
    }

    public SellerState getState() {
        return state;
    }

    public void setState(SellerState state) {
        this.state = state;
    }
}
