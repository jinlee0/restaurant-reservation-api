package com.restaurantreservation.api.service.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.UUID;

public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public String generate(SharedSessionContractImplementor session, Object object) {
        return UUID.randomUUID().toString();
    }
}
