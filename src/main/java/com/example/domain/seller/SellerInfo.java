package com.example.domain.seller;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "seller_infos")
class SellerInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "marketplace_id", nullable = false)
    private Marketplace marketplace;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private String country;
    @Column
    private String externalId;

    @OneToMany(mappedBy = "sellerInfoId")
    private Set<Sellers> sellers;

    SellerInfo() {
    }

    SellerInfo(Marketplace marketplace, String name, String url, String country, String externalId) {
        this.marketplace = marketplace;
        this.name = name;
        this.url = url;
        this.country = country;
        this.externalId = externalId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Set<Sellers> getSellers() {
        return sellers;
    }

    public void setSellers(Set<Sellers> sellers) {
        this.sellers = sellers;
    }
}
