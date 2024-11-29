package com.ms.chatroom.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chatroom.models.Utilisateur;
import com.ms.chatroom.repositories.UtilisateurRepository;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Long login(String email,String mdp) {
        Optional<Utilisateur> utilisateur=utilisateurRepository.findByEmail(email);

        if(utilisateur.isPresent()){
            return utilisateur.get().getId();
        }else{
            throw new RuntimeException("L'utilisateur n'existe pas");
        }
    }
}
