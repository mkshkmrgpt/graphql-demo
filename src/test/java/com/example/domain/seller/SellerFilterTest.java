package com.example.domain.seller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SellerFilterTest {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProducerRepo producerRepo;

    @Autowired
    SellerInfoRepo sellerInfo;

    @Autowired
    MarketPlaceRepo marketPlaceRepo;

    @Test
    void should_filter_sellers_by_name() {
        createSellers("seller");

        assertThat(sellerRepository.findAll(
                new SellerFilter("seller", List.of(), List.of()).sellersSpecification())
        ).hasSize(1);

        assertThat(sellerRepository.findAll(
                new SellerFilter("seller1", List.of(), List.of()).sellersSpecification())
        ).hasSize(0);
    }

    @Test
    void should_filter_by_producer_id() {
        createSellers("seller");
        var producerId = producerRepo.findAll().iterator().next().getId();

        assertThat(sellerRepository.findAll(new SellerFilter(
                null, List.of(producerId), List.of()).sellersSpecification()
        )).hasSize(1);

        assertThat(sellerRepository.findAll(new SellerFilter(
                null, List.of(UUID.randomUUID()), List.of()).sellersSpecification()
        )).hasSize(0);
    }

    @Test
    void should_filter_by_marketplace_id() {
        createSellers("seller");
        var marketplaceId = marketPlaceRepo.findAll().iterator().next().getId();
        assertThat(sellerRepository.findAll(new SellerFilter(
                null, List.of(), List.of(marketplaceId)).sellersSpecification()
        )).hasSize(1);

        assertThat(sellerRepository.findAll(new SellerFilter(
                null, List.of(), List.of(UUID.randomUUID().toString())).sellersSpecification()
        )).hasSize(0);
    }

    @Test
    void should_filter_by_name_producerId_marketplaceId(){
        createSellers("seller");
        var marketplaceId = marketPlaceRepo.findAll().iterator().next().getId();
        var producerId = producerRepo.findAll().iterator().next().getId();
        assertThat(sellerRepository.findAll(new SellerFilter(
                "seller", List.of(producerId), List.of(marketplaceId)).sellersSpecification()
        )).hasSize(1);
    }


    private void createSellers(String sellerName) {
        var producer = new Producers("producer", Instant.now());
        Marketplace marketplace = new Marketplace("marketplace");
        var seller = new SellerInfo(marketplace, sellerName, "url", "IND", "externalId");
        producerRepo.save(producer);
        marketPlaceRepo.save(marketplace);
        sellerInfo.save(seller);
        Sellers sellers = new Sellers(
                producer,
                seller,
                SellerState.REGULAR
        );
        sellerRepository.save(sellers);
    }
}
