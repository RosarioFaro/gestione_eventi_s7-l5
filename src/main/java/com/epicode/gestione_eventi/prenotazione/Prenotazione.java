package com.epicode.gestione_eventi.prenotazione;

import com.epicode.gestione_eventi.auth.AppUser;
import com.epicode.gestione_eventi.evento.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Prenotazione")

public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    private AppUser utente;
    
    @ManyToOne
    private Evento evento;
    
    private LocalDateTime dataPrenotazione;
}