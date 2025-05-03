package com.epicode.gestione_eventi.prenotazione;

import com.epicode.gestione_eventi.auth.AppUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {
    
    private final PrenotazioneService prenotazioneService;
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneResponse prenota(@Valid @RequestBody PrenotazioneRequest request,
                                        @AuthenticationPrincipal AppUser user) {
        return prenotazioneService.prenotaEvento(request, user);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<PrenotazioneResponse> miePrenotazioni(@AuthenticationPrincipal AppUser user) {
        return prenotazioneService.getPrenotazioniUtente(user);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancella(@PathVariable Long id,
                         @AuthenticationPrincipal AppUser user) {
        prenotazioneService.cancellaPrenotazione(id, user);
    }
}
