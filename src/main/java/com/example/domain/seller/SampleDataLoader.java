package com.example.domain.seller;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {
    private final SellerRepository sellerRepository;
    private final ProducerRepo producerRepo;
    private final MarketPlaceRepo marketPlaceRepo;
    private final SellerInfoRepo sellerInfoRepo;
    private final Faker faker;

    SampleDataLoader(SellerRepository sellerRepository, ProducerRepo producerRepo, MarketPlaceRepo marketPlaceRepo,
                     SellerInfoRepo sellerInfoRepo, Faker faker) {
        this.sellerRepository = sellerRepository;
        this.producerRepo = producerRepo;
        this.marketPlaceRepo = marketPlaceRepo;
        this.sellerInfoRepo = sellerInfoRepo;
        this.faker = faker;
    }

    @Override
    public void run(String... args) {

        var producers = IntStream.rangeClosed(1, 100)
                .mapToObj(producer -> producerRepo.save(new Producers(faker.company().name(), Instant.now())))
                .toList();

        var marketplaces = IntStream.rangeClosed(1, 100).mapToObj(marketPlace -> {
            var marketplace = new Marketplace(faker.company().name());
            return marketPlaceRepo.save(marketplace);
        }).toList();

        var sellerInfos = marketplaces.stream().map(marketplace -> {
            var sellerInfo = new SellerInfo(
                    marketplace,
                    faker.company().name(),
                    faker.company().url(),
                    faker.country().name(),
                    faker.company().name().toUpperCase()
            );
            return sellerInfoRepo.save(sellerInfo);
        }).toList();

        var sellers = IntStream.rangeClosed(1, 1000).mapToObj(
                count -> sellerRepository.save(new Sellers(
                        producers.get(new Random().nextInt(producers.size())),
                        sellerInfos.get(new Random().nextInt(sellerInfos.size())),
                        SellerState.values()[new Random().nextInt(SellerState.values().length)]
                ))
        ).toList();
    }
}
