package com.example.domain.seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

interface SellerRepository extends JpaRepository<Sellers, UUID>, JpaSpecificationExecutor<Sellers> {
}
