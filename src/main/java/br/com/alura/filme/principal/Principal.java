package br.com.alura.filme.principal;

import br.com.alura.filme.model.DadosEpisodio;
import br.com.alura.filme.model.DadosSerie;
import br.com.alura.filme.model.DadosTemporadas;
import br.com.alura.filme.mapper.ConverteDados;
import br.com.alura.filme.model.Episodio;
import br.com.alura.filme.service.ConsumoAPI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoAPI consumo = new ConsumoAPI();

    private ConverteDados conversor = new ConverteDados();

    //constante
    private final String ENDERECO = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b6011574";
    //"https://omdbapi.com/?t=Gilmore+Girls&season=1&episode=2&apikey=b6011574%20");

    public void exibeMenu() {
        System.out.println("Digite o nome da série para buscar");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);


        List<DadosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporadas Dadostemporada = conversor.obterDados(json, DadosTemporadas.class);
            temporadas.add(Dadostemporada);
        }
        temporadas.forEach(System.out::println);

//
//        for (int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodio();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//      }
            temporadas.forEach(t -> t.episodio().forEach(e -> System.out.println(e.titulo())));
            temporadas.forEach(System.out::println);

            List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                    .flatMap(t -> t.episodio().stream())
                    .collect(Collectors.toList());

        System.out.println("\nTop 5 episódios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodio().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("\n em que ano você deseja ver os episodio?");
        var ano = leitura.nextInt();

        leitura.nextLine();

        LocalDate dataBusca =  LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLacamento() != null & e.getDataLacamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                " Data lançamento: " + e.getDataLacamento().format(formatador)
                ));
        }
    }

