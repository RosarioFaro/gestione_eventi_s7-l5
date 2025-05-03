package com.epicode.gestione_eventi.evento;

import com.epicode.gestione_eventi.auth.AppUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class EventoService {
    
    private final EventoRepository eventoRepository;
    
    public EventoResponse creaEvento(EventoRequest request, AppUser organizer) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(request, evento);
        evento.setData(LocalDate.parse(request.getData()));
        evento.setOrganizer(organizer);
        evento.setPostiDisponibili(request.getPostiTotali());
        
        return toResponse(eventoRepository.save(evento));
    }
    
    public List<EventoResponse> getAll() {
        return eventoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public EventoResponse modificaEvento(Long id, EventoRequest request, AppUser organizer) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
        
        if (!evento.getOrganizer().getId().equals(organizer.getId())) {
            throw new AccessDeniedException("Non sei il creatore di questo evento.");
        }
        
        BeanUtils.copyProperties(request, evento, "id", "organizer");
        evento.setData(LocalDate.parse(request.getData()));
        evento.setPostiDisponibili(request.getPostiTotali());
        
        return toResponse(eventoRepository.save(evento));
    }
    
    public void eliminaEvento(Long id, AppUser organizer) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
        
        if (!evento.getOrganizer().getId().equals(organizer.getId())) {
            throw new AccessDeniedException("Non sei il creatore di questo evento.");
        }
        
        eventoRepository.delete(evento);
    }
    
    private EventoResponse toResponse(Evento evento) {
        EventoResponse response = new EventoResponse();
        BeanUtils.copyProperties(evento, response);
        response.setOrganizerUsername(evento.getOrganizer().getUsername());
        return response;
    }
}

