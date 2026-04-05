package br.com.alura.filme.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private double  avaliacao;
    private LocalDate dataLacamento;

    public Episodio(Integer numeroTemporadas, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporadas;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numero();

        try {
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }

        try {
            this.dataLacamento = LocalDate.parse(dadosEpisodio.dataLacamento());
        } catch (Exception ex) {
            this.dataLacamento = null;
        }
    }
}
