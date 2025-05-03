package com.epicode.gestione_eventi.prenotazione;

import com.epicode.gestione_eventi.auth.AppUser;
import com.epicode.gestione_eventi.evento.Evento;
import com.epicode.gestione_eventi.evento.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {
    
    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;
    
    public PrenotazioneResponse prenotaEvento(PrenotazioneRequest request, AppUser utente) {
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
        
        if (evento.getPostiDisponibili() <= 0) {
            throw new IllegalStateException("Non ci sono posti disponibili.");
        }
        
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setEvento(evento);
        prenotazione.setDataPrenotazione(LocalDateTime.now());
        
        evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
        eventoRepository.save(evento);
        
        return toResponse(prenotazioneRepository.save(prenotazione));
    }
    
    public List<PrenotazioneResponse> getPrenotazioniUtente(AppUser utente) {
        return prenotazioneRepository.findAll().stream()
                .filter(p -> p.getUtente().getId().equals(utente.getId()))
                .map(this::toResponse)
                .toList();
    }
    
    public void cancellaPrenotazione(Long id, AppUser utente) {
        Prenotazione pren = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));
        
        if (!pren.getUtente().getId().equals(utente.getId())) {
            throw new SecurityException("Non puoi cancellare questa prenotazione.");
        }
        
        Evento evento = pren.getEvento();
        evento.setPostiDisponibili(evento.getPostiDisponibili() + 1);
        eventoRepository.save(evento);
        
        prenotazioneRepository.delete(pren);
    }
    
    private PrenotazioneResponse toResponse(Prenotazione p) {
        PrenotazioneResponse res = new PrenotazioneResponse();
        res.setId(p.getId());
        res.setEventoId(p.getEvento().getId());
        res.setEventoNome(p.getEvento().getNome());
        res.setDataEvento(p.getEvento().getData().toString());
        res.setLuogo(p.getEvento().getLuogo());
        res.setDataPrenotazione(p.getDataPrenotazione().toString());
        return res;
    }
}
