package algoritmosbusca;

import java.util.ArrayList;

class Cidade {

  static int globalId = 1;

  static final int geraId() {
    globalId++;
    return globalId;
  }

  private int id;
  private String nome;
  private Posicao posicao;
  private ArrayList<Cidade> vizinhos;

  Cidade(String nome) {
    this.nome = nome;
    this.id = Cidade.geraId();
  }

  public int getId() {
    return this.id;
  }

  public String getNome() {
    return this.nome;
  }

  public Posicao getPosicao() {
    return posicao;
  }

  public void setPosicao(int x, int y) {
    Posicao p = new Posicao(x, y);
    this.posicao = p;
  }

  public ArrayList<Cidade> getVizinhos() {
    return this.vizinhos;
  }

  public void setVizinhos(ArrayList<Cidade> cidades) {
    this.vizinhos = cidades;
  }

}
