package com.example.domain.seller;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface ProducerRepo extends CrudRepository<Producers, UUID> {
}
