# Algoritmos de busca em grafos

Este projeto foi desenvolvido para a disciplina de Inteligência Artifical da Universidade Feevale.

O código aqui presente foi desenvolvido em `Java` e não usa nenhuma dependência que já não esteja presente na biblioteca padrão da linguagem.

Então, para rodar o código basta ter o Java instalado.

O programa de teste dos algoritmos utiliza somente o console e não conta com interface gráfica, sendo somente em modo texto.

## Objetivo

O objetivo do projeto é demonstrar implementações de algoritmos de busca em grafos já conhecidos, os seguintes algoritmos são implementados na classe `Buscador`:

- [Algoritmo de Dijkstra](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm);
- Algoritmo de busca em largura (amplitude);
- Algoritmo de busca em profundidade;
- [Algoritmo A*](https://en.wikipedia.org/wiki/A*_search_algorithm)

## Grafo de exemplo

O grafo de exemplo é baseado no famoso mapa da Romênia presente no livro de [Russel e Norvig](http://aima.cs.berkeley.edu/).

Para colocar o mapa em forma de grafo de forma dinâmica, é utilizado um arquivo XML que está disponível como `mapa.xml`.

Este arquivo contém informações sobre cada cidade do mapa, como seu nome, sua localização e cidades vizinhas.

Para montar este XML de forma a espelhar o mapa original e obter distâncias fidedignas, foi utilizada a imagem `mapa.png`. Onde cada quadrado vermelho representa uma cidade e suas posições X e Y na imagem representam sua localização em um espaço 2D para cálculo de distâncias.

## Estrutura do código

O código conta com 7 classes:

- `AlgoritmosBusca`: possui o método `main` e provê as respostas do programa ao console em modo texto.
- `Buscador`: possui os diversos algoritmos implementados como métodos.
- `Cidade`: representa uma cidade no `Mapa`, possui informações de suas cidades vizinhas.
- `Mapa`: classe que representa o mapa carregado, permitindo calcular os custos e distâncias.
- `MapaXMLHandler`: carrega o XML do mapa para dentro da classe `Mapa`.
- `MapaPonto`: Ponto do XML que será convertido em `Cidade`.
- `Posicao`: simples classe que segura coordenadas X e Y para uso em `Cidade`.

## Como rodar o programa

Para rodar o programa basta utilizar um ambiente Java, pode compilar via `javac` diretamente ou utilizar uma IDE compatível.

O programa sempre procura o `mapa.xml` na raiz do diretório de contexto atual do usuário. Caso o programa não encontre o XML ele não seguirá com as demonstrações de funcionamento dos algoritmos.

## Licença

[MIT](LICENSE)
