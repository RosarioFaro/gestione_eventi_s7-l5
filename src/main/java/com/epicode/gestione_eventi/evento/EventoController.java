package com.epicode.gestione_eventi.evento;

import com.epicode.gestione_eventi.auth.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {
    
    private final EventoService eventoService;
    
    @GetMapping
    public List<EventoResponse> getAll() {
        return eventoService.getAll();
    }
    
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventoResponse crea(@Valid @RequestBody EventoRequest request,
                               @AuthenticationPrincipal AppUser user) {
        return eventoService.creaEvento(request, user);
    }
    
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @PutMapping("/{id}")
    public ResponseEntity<String> modifica(@PathVariable Long id,
                                           @Valid @RequestBody EventoRequest request,
                                           @AuthenticationPrincipal AppUser user) {
        eventoService.modificaEvento(id, request, user);
        return ResponseEntity.ok("Evento aggiornato con successo");
    }
    
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void elimina(@PathVariable Long id,
                        @AuthenticationPrincipal AppUser user) {
        eventoService.eliminaEvento(id, user);
    }
}
