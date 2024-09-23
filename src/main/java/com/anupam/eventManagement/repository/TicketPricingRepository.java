package com.anupam.eventManagement.repository;

import com.anupam.eventManagement.entity.TicketPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPricingRepository extends JpaRepository<TicketPricing, Long> {
}
