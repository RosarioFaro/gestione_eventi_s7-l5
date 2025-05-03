package com.epicode.gestione_eventi.evento;

import com.epicode.gestione_eventi.auth.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "eventi")

public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    private String descrizione;
    private LocalDate data;
    private String luogo;
    private int postiTotali;
    private int postiDisponibili;
    
    @ManyToOne
    private AppUser organizer;
}