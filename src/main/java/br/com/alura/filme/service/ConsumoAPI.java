package br.com.alura.filme.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    //método
    public String obterDados(String endereco) {
        //HttpCliente como fosse o cliente
        HttpClient client = HttpClient.newHttpClient();
        //HttpResquest que crio uma URI pra dizer pra qual endereço vou fazer requição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            //Vou tentar receber essa requisição então meu cliente vai enviar e vou tentar
            //Receber essa resposta
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //retorno do meu método é devolver o corpo da reposta que é
        // response.body que guarda na variavel json que return json

        String json = response.body();
        return json;
    }
}
