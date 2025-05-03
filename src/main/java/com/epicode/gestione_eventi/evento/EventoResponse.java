package com.epicode.gestione_eventi.evento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponse {
    private Long id;
    private String nome;
    private String descrizione;
    private LocalDate data;
    private String luogo;
    private int postiTotali;
    private int postiDisponibili;
    private String organizerUsername;
}
