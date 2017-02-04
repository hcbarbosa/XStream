package br.com.hcb.entidades;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import br.com.hcb.entidades.Compra;
import br.com.hcb.entidades.CompraDiferenteConverter;
import br.com.hcb.entidades.Livro;
import br.com.hcb.entidades.Musica;
import br.com.hcb.entidades.Produto;

import com.thoughtworks.xstream.XStream;

public class CompraTest {

    @Test
    public void deveGerarCadaUmDosProdutosDeUmaCompra() {

        String xmlEsperado = "<compra>\n" + "  <id>15</id>\n" + "  <produtos>\n" + "    <produto codigo=\"1587\">\n"
                + "      <nome>geladeira</nome>\n" + "      <preco>1000.1</preco>\n"
                + "      <descrição>com duas portas</descrição>\n" + "    </produto>\n"
                + "    <produto codigo=\"1588\">\n" + "      <nome>fogao</nome>\n" + "      <preco>500.0</preco>\n"
                + "      <descrição>com 7 bocas</descrição>\n" + "    </produto>\n" + "  </produtos>\n" + "</compra>";

        XStream xStream = xStreamCompraProduto();

        Compra compra = compraComFerroGeladeira();

        String xmlGerado = xStream.toXML(compra);

        assertEquals(xmlEsperado, xmlGerado);
    }

    @Test
    public void deveGerarUmaCompraComCadaUmDosProdutosXML() {

        String xml = "<compra>\n" + "  <id>15</id>\n" + "  <produtos>\n" + "    <produto codigo=\"1587\">\n"
                + "      <nome>geladeira</nome>\n" + "      <preco>1000.1</preco>\n"
                + "      <descrição>com duas portas</descrição>\n" + "    </produto>\n"
                + "    <produto codigo=\"1588\">\n" + "      <nome>fogao</nome>\n" + "      <preco>500.0</preco>\n"
                + "      <descrição>com 7 bocas</descrição>\n" + "    </produto>\n" + "  </produtos>\n" + "</compra>";

        XStream xStream = xStreamCompraProduto();

        Compra compraEsperada = compraComFerroGeladeira();

        Compra compraDeserializada = (Compra) xStream.fromXML(xml);

        assertEquals(compraEsperada, compraDeserializada);
    }

    @Test
    public void deveSerializarDuasGeladeirasIguais() {

        String xmlEsperado = "<compra>\n" + "  <id>15</id>\n" + "  <produtos>\n" + "    <produto codigo=\"1587\">\n"
                + "      <nome>geladeira</nome>\n" + "      <preco>1000.1</preco>\n"
                + "      <descrição>com duas portas</descrição>\n" + "    </produto>\n"
                + "    <produto codigo=\"1587\">\n" + "      <nome>geladeira</nome>\n"
                + "      <preco>1000.1</preco>\n" + "      <descrição>com duas portas</descrição>\n"
                + "    </produto>\n" + "  </produtos>\n" + "</compra>";

        XStream xStream = xStreamCompraProduto();

        Compra compra = compraDuasGeladeirasIguais();

        xStream.setMode(XStream.NO_REFERENCES);

        String xmlGerado = xStream.toXML(compra);

        assertEquals(xmlEsperado, xmlGerado);

    }

    @Test
    public void deveUtilizarUmConversorTotalmenteCustomizado() {

        String xmlEsperado = "<compra estilo=\"novo\">\n" + "  <id>15</id>\n"
                + "  <fornecedor>guilherme.silveira@caelum.com.br</fornecedor>\n" + "  <endereco>\n"
                + "    <linha1>Rua Vergueiro 3185</linha1>\n" + "    <linha2>8 andar - Sao Paulo - SP</linha2>\n"
                + "  </endereco>\n" + "  <produtos>\n" + "    <produto codigo=\"1587\">\n"
                + "      <nome>geladeira</nome>\n" + "      <preco>1000.1</preco>\n"
                + "      <descrição>com duas portas</descrição>\n" + "    </produto>\n"
                + "    <produto codigo=\"1587\">\n" + "      <nome>geladeira</nome>\n"
                + "      <preco>1000.1</preco>\n" + "      <descrição>com duas portas</descrição>\n"
                + "    </produto>\n" + "  </produtos>\n" + "</compra>";

        Compra compra = compraDuasGeladeirasIguais();

        XStream xstream = xStreamCompraProduto();
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.registerConverter(new CompraDiferenteConverter());
        String xmlGerado = xstream.toXML(compra);

        assertEquals(xmlEsperado, xmlGerado);

        Compra deserializada = (Compra) xstream.fromXML(xmlGerado);

        assertEquals(compra, deserializada);
    }

    @Test
    public void deveSerializarColecaoImplicita() {
        String xmlEsperado = "<compra>\n" + "  <id>15</id>\n" + "  <produto codigo=\"1587\">\n"
                + "    <nome>geladeira</nome>\n" + "    <preco>1000.1</preco>\n"
                + "    <descrição>com duas portas</descrição>\n" + "  </produto>\n" + "  <produto codigo=\"1588\">\n"
                + "    <nome>fogao</nome>\n" + "    <preco>500.0</preco>\n"
                + "    <descrição>com 7 bocas</descrição>\n" + "  </produto>\n" + "</compra>";

        XStream xStream = xStreamCompraProduto();
        xStream.addImplicitCollection(Compra.class, "produtos");

        Compra compra = compraComFerroGeladeira();

        String xmlGerado = xStream.toXML(compra);

        assertEquals(xmlEsperado, xmlGerado);
    }

    @Test
    public void deveSerializarLivroEMusica() {
        String xmlEsperado = "<compra>\n" + "  <id>15</id>\n" + "  <produtos class=\"linked-list\">\n"
                + "    <livro codigo=\"1589\">\n" + "      <nome>O Pássaro Raro</nome>\n"
                + "      <preco>100.0</preco>\n" + "      <descrição>dez histórias sobre a existência</descrição>\n"
                + "    </livro>\n" + "    <musica codigo=\"1590\">\n" + "      <nome>Meu Passeio</nome>\n"
                + "      <preco>100.0</preco>\n" + "      <descrição>música livre</descrição>\n" + "    </musica>\n"
                + "    <produto codigo=\"1587\">\n" + "      <nome>Geladeira</nome>\n" + "      <preco>500.0</preco>\n"
                + "      <descrição>com duas portas</descrição>\n" + "    </produto>\n" + "  </produtos>\n"
                + "</compra>";

        XStream xStream = xStreamCompraProduto();

        Compra compra = compraComLivroEMusica();

        String xmlGerado = xStream.toXML(compra);

        assertEquals(xmlEsperado, xmlGerado);
    }

    private Compra compraDuasGeladeirasIguais() {
        Produto geladeira = geladeira();
        List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(geladeira);
        produtos.add(geladeira);
        return new Compra(15, produtos);
    }

    private XStream xStreamCompraProduto() {
        XStream xStream = new XStream();
        xStream.alias("compra", Compra.class);
        xStream.alias("produto", Produto.class);
        xStream.alias("livro", Livro.class);
        xStream.alias("musica", Musica.class);
        xStream.aliasField("descrição", Produto.class, "descricao");
        xStream.useAttributeFor(Produto.class, "codigo");
        return xStream;
    }

    private Compra compraComFerroGeladeira() {
        Produto geladeira = geladeira();
        Produto fogao = fogao();
        List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(geladeira);
        produtos.add(fogao);
        Compra compraEsperada = new Compra(15, produtos);
        return compraEsperada;
    }

    private Compra compraComLivroEMusica() {
        Livro livro = new Livro("O Pássaro Raro", 100.0, "dez histórias sobre a existência", 1589);
        Musica musica = new Musica("Meu Passeio", 100.0, "música livre", 1590);
        Produto produto = new Produto("Geladeira", 500, "com duas portas", 1587);
        List<Produto> produtos = new LinkedList<Produto>();
        produtos.add(livro);
        produtos.add(musica);
        produtos.add(produto);
        return new Compra(15, produtos);
    }

    private Produto fogao() {
        Produto fogao = new Produto("fogao", 500.0, "com 7 bocas", 1588);
        return fogao;
    }

    private Produto geladeira() {
        Produto geladeira = new Produto("geladeira", 1000.1, "com duas portas", 1587);
        return geladeira;
    }
}
