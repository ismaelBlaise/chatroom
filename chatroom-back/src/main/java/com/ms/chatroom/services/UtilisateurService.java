package com.ms.chatroom.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.chatroom.models.Utilisateur;
import com.ms.chatroom.repositories.UtilisateurRepository;
import com.ms.chatroom.request.UtilisateurRequest;
import com.ms.chatroom.security.JwtUtil;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtUtil jwtUtil;  

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; 

    
    public String login(String email, String mdp) {
        
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

       
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();

            
            if (passwordEncoder.matches(mdp, utilisateur.getMdp())) { 
                return jwtUtil.generateToken(utilisateur.getPseudo());
            } else { 
                throw new RuntimeException("Mot de passe incorrect");
            }
        } else { 
            throw new RuntimeException("L'utilisateur n'existe pas");
        }
    }

      
    public String signup(UtilisateurRequest utilisateurRequest){
         
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(utilisateurRequest.getEmail());
        
        if (utilisateurOpt.isPresent()) {
            throw new RuntimeException("l'adresse email est deja utilise");
        }
        if(utilisateurRepository.findByPseudo(utilisateurRequest.getPseudo()).isPresent()){
            throw new RuntimeException("le pseudo est deja utilise");
        }
        if(utilisateurRepository.findByContact(utilisateurRequest.getContact()).isPresent()){
            throw new RuntimeException("le contact existe deja");
        }

         
        Utilisateur nouvelUtilisateur = new Utilisateur();
        nouvelUtilisateur.setPseudo(utilisateurRequest.getPseudo());
        nouvelUtilisateur.setNom(utilisateurRequest.getNom());
        nouvelUtilisateur.setPrenom(utilisateurRequest.getPrenom());
        nouvelUtilisateur.setDateNaissance(utilisateurRequest.getDateNaissance());
        nouvelUtilisateur.setSexe(utilisateurRequest.getSexe());
        nouvelUtilisateur.setContact(utilisateurRequest.getContact());
        nouvelUtilisateur.setEmail(utilisateurRequest.getEmail());
        
        
        String motDePasseHache = passwordEncoder.encode(utilisateurRequest.getMdp());
        nouvelUtilisateur.setMdp(motDePasseHache);

         
        utilisateurRepository.save(nouvelUtilisateur);

         
        return jwtUtil.generateToken(nouvelUtilisateur.getPseudo());
    }
}
