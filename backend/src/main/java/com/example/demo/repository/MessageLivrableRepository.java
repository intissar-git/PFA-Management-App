package com.example.demo.repository;

import com.example.demo.model.MessageLivrable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageLivrableRepository extends JpaRepository<MessageLivrable, Long> {
    List<MessageLivrable> findByLivrableIdOrderByDateEnvoiAsc(Integer livrableId);
}