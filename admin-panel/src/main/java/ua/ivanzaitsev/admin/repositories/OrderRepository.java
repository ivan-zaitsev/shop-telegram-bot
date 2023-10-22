package ua.ivanzaitsev.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.ivanzaitsev.admin.models.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
