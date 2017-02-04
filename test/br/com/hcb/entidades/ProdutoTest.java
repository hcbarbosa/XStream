package br.com.hcb.entidades;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.hcb.entidades.PrecoConverter;
import br.com.hcb.entidades.Produto;

import com.thoughtworks.xstream.XStream;

public class ProdutoTest {

    /*
     * Na web há vários sistemas que se comunicam através de xml biblioteca que
     * transforma objeto -> xml / xml -> objeto / xml -> json /
     */

    @Test
    public void deveGerarXmlComNomePrecoDescricaoCorretos() {
        String xmlEsperado = "<produto codigo=\"1587\">\n" + "  <nome>Livro de java com xml</nome>"
                + "\n  <preco>R$ 1.000,00</preco>" + "\n  <descrição>um livro que fala sobre java e xml</descrição>\n"
                + "</produto>";

        Produto produto = new Produto("Livro de java com xml", 1000, "um livro que fala sobre java e xml", 1587);

        XStream xstream = new XStream(); // problema com
        xstream.alias("produto", Produto.class); // br.com.alura.entidades.Produto
        xstream.aliasField("descrição", Produto.class, "descricao"); // renomea
        xstream.useAttributeFor(Produto.class, "codigo"); // especifica atributo

        xstream.registerLocalConverter(Produto.class, "preco", new PrecoConverter());

        String xmlGerado = xstream.toXML(produto); // cria xml
        assertEquals(xmlEsperado, xmlGerado);
    }
}
