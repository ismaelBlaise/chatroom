package com.ms.chatroom.controllers;

import com.ms.chatroom.request.UtilisateurRequest;
import com.ms.chatroom.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String mdp) {
        try {
            String token = utilisateurService.login(email, mdp);
            return ResponseEntity.ok(token); 
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @PostMapping("/register")
    public ResponseEntity<String> signup(@RequestBody UtilisateurRequest utilisateurRequest) {
        try {
            String token = utilisateurService.signup(utilisateurRequest);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
