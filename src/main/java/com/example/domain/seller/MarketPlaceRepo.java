package com.example.domain.seller;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MarketPlaceRepo extends CrudRepository<Marketplace, UUID> {
}
