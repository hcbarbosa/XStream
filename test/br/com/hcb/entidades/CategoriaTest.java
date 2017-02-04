package br.com.hcb.entidades;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.hcb.entidades.Categoria;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.TreeMarshaller.CircularReferenceException;

public class CategoriaTest {

    @Test
    public void deveGeraUmCiclo() {
        Categoria esporte = new Categoria(null, "esporte");
        Categoria futebol = new Categoria(esporte, "futebol");
        Categoria geral = new Categoria(futebol, "geral");
        esporte.setPai(geral); // fechou o ciclo

        String xmlEsperado = "<categoria id=\"1\">\n" + "  <pai id=\"2\">\n" + "    <pai id=\"3\">\n"
                + "      <pai reference=\"1\"/>\n" + "      <nome>futebol</nome>\n" + "    </pai>\n"
                + "    <nome>geral</nome>\n" + "  </pai>\n" + "  <nome>esporte</nome>\n" + "</categoria>";

        XStream xStream = new XStream();
        xStream.alias("categoria", Categoria.class);
        xStream.setMode(XStream.ID_REFERENCES);
        String xmlGerado = xStream.toXML(esporte);

        assertEquals(xmlEsperado, xmlGerado);

    }

    @Test(expected = CircularReferenceException.class)
    public void naoDeveSerializarUmCicloSemReferencias() {

        Categoria esporte = new Categoria(null, "esporte");
        Categoria futebol = new Categoria(esporte, "futebol");
        Categoria geral = new Categoria(futebol, "geral");
        esporte.setPai(geral); // fechou o ciclo

        String xmlEsperado = "<categoria id=\"1\">\n" + "  <pai id=\"2\">\n" + "    <pai id=\"3\">\n"
                + "      <pai reference=\"1\"/>\n" + "      <nome>futebol</nome>\n" + "    </pai>\n"
                + "    <nome>geral</nome>\n" + "  </pai>\n" + "  <nome>esporte</nome>\n" + "</categoria>";

        XStream xStream = new XStream();
        xStream.alias("categoria", Categoria.class);
        xStream.setMode(XStream.NO_REFERENCES);
        String xmlGerado = xStream.toXML(esporte);

        assertEquals(xmlEsperado, xmlGerado);
    }

}
