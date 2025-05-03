package com.epicode.gestione_eventi.evento;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {
    @NotBlank
    private String nome;
    
    private String descrizione;
    
    @NotBlank
    private String luogo;
    
    @NotNull
    private LocalDate data;
    
    @Min(1)
    private int postiTotali;
}
