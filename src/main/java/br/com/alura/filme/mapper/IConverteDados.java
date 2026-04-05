package br.com.alura.filme.mapper;

public interface IConverteDados {

    <T> T obterDados (String json, Class <T> classe);

}
