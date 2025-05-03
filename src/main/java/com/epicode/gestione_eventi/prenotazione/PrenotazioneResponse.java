package com.epicode.gestione_eventi.prenotazione;

import lombok.Data;

@Data
public class PrenotazioneResponse {
    private Long id;
    private Long eventoId;
    private String eventoNome;
    private String dataEvento;
    private String luogo;
    private String dataPrenotazione;
}

