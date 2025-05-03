package com.epicode.gestione_eventi.prenotazione;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrenotazioneRequest {
    @NotNull
    private Long eventoId;
}