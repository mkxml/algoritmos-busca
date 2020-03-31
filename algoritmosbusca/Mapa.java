package algoritmosbusca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.SAXException;

public class Mapa {
  private HashMap<String, Cidade> cidades;
  private HashMap<String, Integer> custos;
  private HashMap<String, Integer> cacheDistanciaEuclidiana;

  public Mapa(String caminhoXML) {
    cidades = new HashMap<String, Cidade>();
    custos = new HashMap<String, Integer>();
    cacheDistanciaEuclidiana = new HashMap<String, Integer>();
    try {
      File mapaXML = new File(caminhoXML);
      carregaMapa(mapaXML);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void carregaMapa(File mapaXML) throws ParserConfigurationException, SAXException, IOException {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setValidating(true);
    SAXParser saxParser = factory.newSAXParser();
    MapaXMLHandler handler = new MapaXMLHandler();
    saxParser.parse(mapaXML, handler);
    ArrayList<MapaPonto> listaPontos = handler.getListaPontos();
    // Converte pontos do mapa carregado em XML em cidades para a classe Mapa
    for (MapaPonto ponto : listaPontos) {
      Cidade cidade = new Cidade(ponto.nome);
      cidade.setPosicao(ponto.x, ponto.y);
      adicionaCidade(ponto.nome, cidade);
    }
    // Adiciona vizinhos no mapa
    for (MapaPonto ponto : listaPontos) {
      Cidade cidadeOrigem = cidades.get(ponto.nome);
      ArrayList<Cidade> vizinhos = new ArrayList<>();
      for (ArrayList<String> vizinho : ponto.vizinhos) {
        String nome = vizinho.get(0);
        int custo = Integer.parseInt(vizinho.get(1));
        Cidade cidadeVizinha = cidades.get(nome);
        vizinhos.add(cidadeVizinha);
        // Adiciona custos dos caminhos
        registraCusto(cidadeOrigem, cidadeVizinha, custo);
      }
      cidadeOrigem.setVizinhos(vizinhos);
    }
  }

  public void adicionaCidade(String nome, Cidade cidade) {
    cidades.put(nome, cidade);
  }

  public Cidade getCidade(String nome) {
    return cidades.get(nome);
  }

  public Cidade[] getCidades() {
    return cidades.values().toArray(new Cidade[cidades.size()]);
  }

  public HashSet<Cidade> getCidadesSet() {
    HashSet<Cidade> cidadesSet = new HashSet<>();
    for (Cidade cidade : getCidades()) {
      cidadesSet.add(cidade);
    }
    return cidadesSet;
  }

  public int getNumeroCaminhos() {
    return custos.size();
  }

  public ArrayList<Cidade> getVizinhos(String nomeCidade) {
    Cidade cidade = cidades.get(nomeCidade);
    return cidade.getVizinhos();
  }

  public ArrayList<Cidade> getVizinhos(Cidade cidadeOrigem) {
    return cidadeOrigem.getVizinhos();
  }

  public int getDistanciaEuclidiana(Cidade origem, Cidade destino) {
    String hashId = getCaminho(origem, destino);
    // Verifica se ja temos a distancia calculada
    if (cacheDistanciaEuclidiana.containsKey(hashId)) {
      return cacheDistanciaEuclidiana.get(hashId);
    }
    // Segue o calculo
    Posicao p1 = origem.getPosicao();
    Posicao p2 = destino.getPosicao();
    return (int) Math.round(Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2)));
  }

  public void registraCusto(Cidade origem, Cidade destino, int custo) {
    String hashId = getCaminho(origem, destino);
    if (!custos.containsKey(hashId)) {
      custos.put(hashId, custo);
    }
  }

  public int getCusto(Cidade origem, Cidade destino) {
    String hashId = getCaminho(origem, destino);
    return (int) custos.get(hashId);
  }

  public HashMap<Cidade, Integer> getCustos() {
    HashMap<Cidade, Integer> saida = new HashMap<>();
    for (Entry<String, Cidade> entry : cidades.entrySet()) {
      saida.put(entry.getValue(), custos.get(entry.getKey()));
    }
    return saida;
  }

  public String getCaminho(Cidade origem, Cidade destino) {
    int idOrigem = origem.getId();
    int idDestino = destino.getId();
    String primeiro = String.valueOf(Math.min(idOrigem, idDestino));
    String segundo = String.valueOf(Math.max(idOrigem, idDestino));
    return primeiro + "<->" + segundo;
  }
}
