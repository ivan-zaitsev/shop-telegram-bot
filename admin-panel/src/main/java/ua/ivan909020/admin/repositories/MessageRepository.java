package ua.ivan909020.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.ivan909020.admin.models.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
