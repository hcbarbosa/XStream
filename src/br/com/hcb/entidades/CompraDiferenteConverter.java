package br.com.hcb.entidades;

import java.util.List;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CompraDiferenteConverter implements Converter {

    @Override
    public boolean canConvert(Class type) {
        return type.isAssignableFrom(Compra.class);
    }

    @Override
    public void marshal(Object valor, HierarchicalStreamWriter writer, MarshallingContext context) {
        Compra compra = (Compra) valor;

        writer.addAttribute("estilo", "novo");

        writer.startNode("id");
        context.convertAnother(compra.getId());
        writer.endNode();

        writer.startNode("fornecedor");
        context.convertAnother("guilherme.silveira@caelum.com.br");
        writer.endNode();

        writer.startNode("endereco");
        writer.startNode("linha1");
        context.convertAnother("Rua Vergueiro 3185");
        writer.endNode();
        writer.startNode("linha2");
        context.convertAnother("8 andar - Sao Paulo - SP");
        writer.endNode();
        writer.endNode();

        List<Produto> produtos = compra.getProdutos();
        writer.startNode("produtos");
        context.convertAnother(produtos);
        writer.endNode();

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String estilo = reader.getAttribute("estilo");

        reader.moveDown();// abre id
        String nomeId = reader.getNodeName();
        String valorId = reader.getValue();
        reader.moveUp();

        reader.moveDown();// abre fornecedor
        String fornecedor = reader.getValue();
        reader.moveUp();

        reader.moveDown();// abre endereco
        reader.moveDown(); // abre linha1
        String endereco = reader.getValue();
        reader.moveUp();
        reader.moveDown();// abre linha2
        endereco += "\n" + reader.getValue();
        reader.moveUp();
        reader.moveUp();

        reader.moveDown();// abre produtos
        List<Produto> produtos = (List<Produto>) context.convertAnother("produtos", List.class);

        int id = Integer.parseInt(valorId);
        return new Compra(id, produtos);

    }

}
