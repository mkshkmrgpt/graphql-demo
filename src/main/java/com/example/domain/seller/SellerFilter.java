package com.example.domain.seller;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public record SellerFilter(String searchByName, List<UUID> producerIds, List<String> marketplaceIds) {
    Specification<Sellers> sellersSpecification() {
        return getNameSpecification()
                .and(getProducerSpecification())
                .and(getMarketplaceSpecification());
    }

    private Specification<Sellers> getMarketplaceSpecification() {
        return (root, query, criteriaBuilder) -> {
            if (marketplaceIds != null && !marketplaceIds.isEmpty()) {
                return root.get("sellerInfoId").get("marketplace").get("id").in(marketplaceIds);
            } else {
                return criteriaBuilder.and();
            }
        };
    }

    private Specification<Sellers> getProducerSpecification() {
        return (root, query, criteriaBuilder) -> {
            if (producerIds != null && !producerIds.isEmpty()) {
                return root.get("producerId").get("id").in(producerIds);
            } else {
                return criteriaBuilder.and();
            }
        };
    }

    private Specification<Sellers> getNameSpecification() {
        return (root, query, criteriaBuilder) -> {
            if (searchByName != null && !searchByName.isBlank()) {
                return criteriaBuilder.equal(
                        root.get("sellerInfoId").get("name"),
                        searchByName
                );
            } else {
                return criteriaBuilder.and();
            }
        };
    }
}
