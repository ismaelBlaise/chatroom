package com.ms.chatroom.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms.chatroom.models.Utilisateur;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    public Optional<Utilisateur> findByEmail(String email);
}
