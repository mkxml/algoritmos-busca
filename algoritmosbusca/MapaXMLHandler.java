package algoritmosbusca;

import java.util.ArrayList;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MapaXMLHandler extends DefaultHandler {

  private Stack<String> pilhaElementos = new Stack<String>();
  private Stack<MapaPonto> pilhaPontos = new Stack<MapaPonto>();
  private ArrayList<MapaPonto> listaPontos = new ArrayList<MapaPonto>();

  private String elementoAtual() {
    return pilhaElementos.peek();
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    pilhaElementos.push(qName);
    // Nova ponto encontrado
    if (qName.equals("cidade")) {
      MapaPonto ponto = new MapaPonto();
      ponto.vizinhos = new ArrayList<>();
      this.pilhaPontos.push(ponto);
    }
    if (qName.equals("vizinha")) {
      MapaPonto pontoAtual = pilhaPontos.peek();
      pontoAtual.vizinhos.add(0, new ArrayList<>());
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    pilhaElementos.pop();
    // Terminou ponto
    if (qName.equals("cidade")) {
      MapaPonto ponto = this.pilhaPontos.pop();
      listaPontos.add(ponto);
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    String valor = new String(ch, start, length).trim();
    if (valor.length() == 0 || pilhaPontos.empty()) {
      return;
    }
    // Lida com os valores baseado no tipo
    MapaPonto ponto = pilhaPontos.peek();
    String atual = elementoAtual();
    switch (atual) {
      case "nome":
        if (ponto.vizinhos.size() == 0) {
          ponto.nome = valor;
        } else {
          ponto.vizinhos.get(0).add(valor);
        }
        break;
      case "x":
        int valorX = Integer.parseInt(valor);
        ponto.x = valorX;
        break;
      case "y":
        int valorY = Integer.parseInt(valor);
        ponto.y = valorY;
        break;
      case "custo":
        ponto.vizinhos.get(0).add(valor);
        break;
      default:
        return;
    }
  }

  public ArrayList<MapaPonto> getListaPontos() {
    return listaPontos;
  }

}
