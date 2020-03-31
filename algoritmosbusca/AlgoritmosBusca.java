package algoritmosbusca;

import java.util.ArrayList;

public class AlgoritmosBusca {

  public static String geraResposta(ArrayList<Cidade> cidades) {
    if (cidades.size() == 0) {
      return "Sem caminho disponível";
    }
    String caminho = cidades.get(0).getNome();
    for (int i = 1, l = cidades.size(); i < l; i++) {
      caminho += " -> " + cidades.get(i).getNome();
    }
    return caminho;
  }

  public static void main(String[] args) {
    System.out.println("Projeto da disciplina de Inteligência Artifical da Universidade Feevale");
    System.out.println();
    System.out.println();
    System.out.println("Carregando o mapa do arquivo mapa.xml...");
    System.out.println();

    Mapa mapa = new Mapa("mapa.xml");
    Buscador buscador = new Buscador(mapa);

    System.out.println("Mapa carregado!");
    System.out.println();
    System.out.println();

    System.out.println("Insira a cidade de origem seguindo o mapa fornecido:");
    String nomeOrigem = System.console().readLine();

    System.out.println("Insira a cidade de destino:");
    String nomeDestino = System.console().readLine();

    Cidade cidadeOrigem = mapa.getCidade(nomeOrigem);

    if (cidadeOrigem == null) {
      System.out.println();
      System.out.println("Cidade origem inexistente");
      return;
    }

    Cidade cidadeDestino = mapa.getCidade(nomeDestino);

    if (cidadeDestino == null) {
      System.out.println();
      System.out.println("Cidade destino inexistente");
      return;
    }

    ArrayList<Cidade> caminhoResposta = buscador.dijkstra(cidadeOrigem, cidadeDestino);
    System.out.println("Algoritmo de Dijkstra - Caminho resposta:");
    System.out.println(geraResposta(caminhoResposta));

    System.out.println();
    System.out.println();

    System.out.println("Algoritmo de busca por Largura sem Informação - Caminho resposta:");
    caminhoResposta = buscador.larguraSemInformacao(cidadeOrigem, cidadeDestino);
    System.out.println(geraResposta(caminhoResposta));

    System.out.println();
    System.out.println();

    System.out.println("Algoritmo de busca por Profundidade sem Informação - Caminho resposta:");
    caminhoResposta = buscador.profundidadeSemInformacao(cidadeOrigem, cidadeDestino);
    System.out.println(geraResposta(caminhoResposta));

    System.out.println();
    System.out.println();

    System.out.println("Algoritmo de busca com informação - Caminho resposta:");
    caminhoResposta = buscador.buscaComInformacao(cidadeOrigem, cidadeDestino);
    System.out.println(geraResposta(caminhoResposta));

    System.out.println();
    System.out.println();

    System.out.println("Algoritmo A* - Caminho resposta:");
    caminhoResposta = buscador.aEstrela(cidadeOrigem, cidadeDestino);
    System.out.println(geraResposta(caminhoResposta));
  }
}
