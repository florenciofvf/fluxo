package br.com.florencio.fluxo.util;

import java.awt.Color;
import java.io.File;
import java.io.PrintWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import br.com.florencio.fluxo.Instancia;
import br.com.florencio.fluxo.InstanciaRaiz;

public class ArquivoUtil {
	public static final String SUFIXO = ".fvf";

	private static String citar(String s) {
		return "\"" + s + "\"";
	}

	private static void gravarPrologo(PrintWriter pw) {
		pw.println("<?xml version=" + citar("1.0") + " encoding=" + citar(Constantes.CODIFICACAO) + "?>");
		pw.println();
	}

	public static void salvarArquivo(InstanciaRaiz raiz, File file) throws Exception {
		PrintWriter pw = new PrintWriter(file, Constantes.CODIFICACAO);
		gravarPrologo(pw);
		inicioTag("", raiz, pw, false);
		if (!raiz.getRaizEsquerda().estaVazio()) {
			raiz.getRaizEsquerda().imprimir("\t", pw, true);
		}
		if (!raiz.getRaizDireita().estaVazio()) {
			raiz.getRaizDireita().imprimir("\t", pw, true);
		}
		finalTag("", raiz, pw);
		pw.close();
	}

	public static String semSufixo(String s) {
		if (s == null) {
			return "";
		}

		if (s.endsWith(ArquivoUtil.SUFIXO)) {
			int pos = s.lastIndexOf(ArquivoUtil.SUFIXO);
			s = s.substring(0, pos);
		}

		return s;
	}

	public static InstanciaRaiz lerArquivo(File file) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		Manipulador m = new Manipulador();
		parser.parse(file, m);
		return m.raiz;
	}

	public static void inicioTag(String tab, Instancia i, PrintWriter pw, boolean salvarLado) {
		pw.print(tab + "<instancia nome=" + citar(Util.escaparString(i.getDescricao())));

		if (salvarLado) {
			pw.print(" esquerdo=" + citar("" + i.isEsquerdo()));
		}

		if (i.getCor() != null) {
			pw.print(" cor=" + citar("" + i.getCor().getRGB()));
		}

		if (i.isMinimizado()) {
			pw.print(" minimizado=" + citar("true"));
		}

		if (i.getAlturaComplementar() != 0) {
			pw.print(" alturaComplementar=" + citar("" + i.getAlturaComplementar()));
		}

		if (i.isDesenharComentario()) {
			pw.print(" desenharComentario=" + citar("true"));
		}

		if (i.getComentario().length() > 0) {
			pw.print(" comentario=" + citar(Util.escaparString(i.getComentario())));
		}

		if (i.estaVazio()) {
			pw.println("/>");
		} else {
			pw.println(">");
		}
	}

	public static void finalTag(String tab, Instancia i, PrintWriter pw) {
		if (!i.estaVazio()) {
			pw.println(tab + "</instancia>");
		}
	}

	private static class Manipulador extends DefaultHandler {
		InstanciaRaiz raiz;
		Instancia sel;

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			Instancia instancia = new Instancia(attributes.getValue("nome"));

			String minimizado = attributes.getValue("minimizado");
			instancia.setMinimizado(Boolean.parseBoolean(minimizado));

			String esquerdo = attributes.getValue("esquerdo");
			instancia.setEsquerdo(Boolean.parseBoolean(esquerdo));

			String desenharComentario = attributes.getValue("desenharComentario");
			instancia.setDesenharComentario(Boolean.parseBoolean(desenharComentario));

			String cor = attributes.getValue("cor");
			if (!Util.estaVazio(cor)) {
				instancia.setCor(new Color(Integer.parseInt(cor)));
			}

			String alturaComplementar = attributes.getValue("alturaComplementar");
			if (!Util.estaVazio(alturaComplementar)) {
				instancia.setAlturaComplementar(Integer.parseInt(alturaComplementar));
			}

			String comentario = attributes.getValue("comentario");
			instancia.setComentario(comentario);

			if (raiz == null) {
				raiz = new InstanciaRaiz(instancia.getDescricao());
				raiz.setDesenharComentario(instancia.isDesenharComentario());
				raiz.setComentario(instancia.getComentario());
				raiz.setCor(instancia.getCor());

				sel = raiz;
				return;
			}

			sel.adicionar(instancia);
			sel = instancia;
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			sel = sel.getPai();
		}
	}
}