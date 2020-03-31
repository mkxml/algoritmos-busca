package algoritmosbusca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.Map.Entry;

public class Buscador {
  private Mapa mapa;

  public Buscador(Mapa mapa) {
    setMapa(mapa);
  }

  public void setMapa(Mapa mapa) {
    this.mapa = mapa;
  }

  public Mapa getMapa() {
    return this.mapa;
  }

  private ArrayList<Cidade> caminhoAsList(HashMap<Cidade, Cidade> caminho, Cidade origem, Cidade destino) {
    ArrayList<Cidade> saida = new ArrayList<>();
    if (caminho.size() > 0) {
      // Percorre o caminho a partir do destino
      Cidade atual = destino;
      saida.add(0, atual);
      while (atual != origem) {
        if (atual == null)
          return new ArrayList<Cidade>();
        Cidade pai = caminho.get(atual);
        atual = pai;
        saida.add(0, pai);
      }
    }
    return saida;
  }

  private Cidade getCidadeMaisProxima(HashMap<Cidade, Integer> nodos, Collection<Cidade> filtro) {
    Cidade saida = null;
    int min = Integer.MAX_VALUE;
    for (Entry<Cidade, Integer> entry : nodos.entrySet()) {
      Cidade cidade = entry.getKey();
      if (filtro.contains(cidade)) {
        int valor = entry.getValue();
        if (valor < min) {
          min = valor;
          saida = cidade;
        }
      }
    }
    return saida;
  }

  public ArrayList<Cidade> dijkstra(Cidade origem, Cidade destino) {
    HashMap<Cidade, Cidade> caminho = new HashMap<>();
    HashSet<Cidade> aProcessar = mapa.getCidadesSet();
    Cidade[] cidades = mapa.getCidades();
    HashMap<Cidade, Integer> nodos = new HashMap<>();
    // Adicionando nodos ao hashmap
    for (Cidade cidade : cidades) {
      nodos.put(cidade, Integer.MAX_VALUE);
    }
    // Setando distancia da origem para 0
    nodos.put(origem, 0);
    // Inicia busca
    while (!aProcessar.isEmpty()) {
      // Seleciona o nodo atual = nodo com menor valor de distancia
      Cidade atual = getCidadeMaisProxima(nodos, aProcessar);
      // Calcula vizinhos
      for (Cidade vizinha : atual.getVizinhos()) {
        if (aProcessar.contains(vizinha)) {
          int distanciaAtual = nodos.get(atual);
          int distanciaVizinho = mapa.getDistanciaEuclidiana(atual, vizinha);
          int alternativa = distanciaAtual + distanciaVizinho;
          if (nodos.get(vizinha) > alternativa) {
            nodos.put(vizinha, alternativa);
            // Gera relacao filho -> pai, para poder fazer o retorno do caminho depois
            caminho.put(vizinha, atual);
          }
        }
      }
      // Nodo atual processado
      aProcessar.remove(atual);
      if (atual == destino) {
        // Chegamos ao fim
        break;
      }
    }
    return caminhoAsList(caminho, origem, destino);
  }

  private ArrayList<Cidade> buscaSemInformacao(Cidade origem, Cidade destino, boolean profundidade) {
    Stack<Cidade> abertos = new Stack<>();
    Stack<Cidade> fechados = new Stack<>();
    HashMap<Cidade, Cidade> caminho = new HashMap<>();
    if (origem == destino) {
      caminho.put(origem, destino);
      return caminhoAsList(caminho, origem, destino);
    }
    // Coloca origem em abertos
    abertos.add(origem);
    while (!abertos.isEmpty()) {
      Cidade atual = abertos.pop();
      if (atual == destino) {
        // Condicao de SUCESSO
        break;
      }
      ArrayList<Cidade> vizinhos = atual.getVizinhos();
      fechados.add(atual);
      for (Cidade vizinha : vizinhos) {
        if (!abertos.contains(vizinha) && !fechados.contains(vizinha)) {
          if (profundidade) {
            abertos.add(0, vizinha);
          } else {
            abertos.add(vizinha);
          }
          caminho.put(vizinha, atual);
        }
      }
    }
    return caminhoAsList(caminho, origem, destino);
  }

  public ArrayList<Cidade> buscaComInformacao(Cidade origem, Cidade destino) {
    HashSet<Cidade> abertos = new HashSet<>();
    HashSet<Cidade> fechados = new HashSet<>();
    HashMap<Cidade, Integer> nodos = new HashMap<>();
    HashMap<Cidade, Cidade> caminho = new HashMap<>();
    if (origem == destino) {
      caminho.put(origem, destino);
      return caminhoAsList(caminho, origem, destino);
    }
    // Seta distancia da origem para o destino
    nodos.put(origem, mapa.getDistanciaEuclidiana(origem, destino));
    // Coloca origem em abertos
    abertos.add(origem);
    while (!abertos.isEmpty()) {
      Cidade atual = getCidadeMaisProxima(nodos, abertos);
      abertos.remove(atual);
      if (atual == destino) {
        // Condicao de SUCESSO
        break;
      }
      ArrayList<Cidade> vizinhos = atual.getVizinhos();
      for (Cidade vizinha : vizinhos) {
        if (!abertos.contains(vizinha) && !fechados.contains(vizinha)) {
          nodos.put(vizinha, mapa.getDistanciaEuclidiana(vizinha, destino));
          abertos.add(vizinha);
        }
        if (abertos.contains(vizinha)) {
          // Inclui sempre, para evitar caminhos incompletos (loop), encontra um caminho
          caminho.put(vizinha, atual);
        }
        if (fechados.contains(vizinha)) {
          if (nodos.get(vizinha) < nodos.get(atual)) {
            fechados.remove(vizinha);
            abertos.add(vizinha);
          }
        }
      }
      fechados.add(atual);
    }
    return caminhoAsList(caminho, origem, destino);
  }

  public ArrayList<Cidade> aEstrela(Cidade origem, Cidade destino) {
    Cidade[] cidades = mapa.getCidades();
    HashSet<Cidade> abertos = new HashSet<>();
    HashSet<Cidade> fechados = new HashSet<>();
    HashMap<Cidade, Integer> nodos = new HashMap<>();
    HashMap<Cidade, Integer> custos = new HashMap<>();
    HashMap<Cidade, Cidade> caminho = new HashMap<>();
    if (origem == destino) {
      caminho.put(origem, destino);
      return caminhoAsList(caminho, origem, destino);
    }
    // Setando default MAX_VALUE para hashmaps de distancias e scores
    for (Cidade cidade : cidades) {
      nodos.put(cidade, Integer.MAX_VALUE);
      custos.put(cidade, Integer.MAX_VALUE);
    }
    // Seta distancia da origem para o destino
    nodos.put(origem, mapa.getDistanciaEuclidiana(origem, destino));
    // Seta o custo inicial da origem
    custos.put(origem, 0);
    // Coloca origem em abertos
    abertos.add(origem);
    while (!abertos.isEmpty()) {
      Cidade atual = getCidadeMaisProxima(nodos, abertos);
      abertos.remove(atual);
      fechados.add(atual);
      if (atual == destino) {
        // Achamos o caminho
        break;
      }
      for (Cidade vizinha : atual.getVizinhos()) {
        int alternativa = custos.get(atual) + mapa.getCusto(atual, vizinha);
        if (alternativa < custos.get(vizinha)) {
          caminho.put(vizinha, atual);
          custos.put(vizinha, alternativa);
          nodos.put(vizinha, alternativa + mapa.getDistanciaEuclidiana(vizinha, destino));
          if (!fechados.contains(vizinha)) {
            abertos.add(vizinha);
          }
        }
      }
    }
    return caminhoAsList(caminho, origem, destino);
  }

  public ArrayList<Cidade> larguraSemInformacao(Cidade origem, Cidade destino) {
    return buscaSemInformacao(origem, destino, false);
  }

  public ArrayList<Cidade> profundidadeSemInformacao(Cidade origem, Cidade destino) {
    return buscaSemInformacao(origem, destino, true);
  }

}
