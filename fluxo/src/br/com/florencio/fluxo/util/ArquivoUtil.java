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

	public static void salvarArquivo(Instancia raiz, File file) throws Exception {
		PrintWriter pw = new PrintWriter(file, "iso-8859-1");
		gravarPrologo(pw);
		// raiz.imprimir(pw);
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

	private static String citar(String s) {
		return "\"" + s + "\"";
	}

	private static void gravarPrologo(PrintWriter pw) {
		pw.println("<?xml version=" + citar("1.0") + " encoding=" + citar("iso-8859-1") + "?>");
		pw.println();
	}

	public static void inicioTag(String tab, Instancia i, PrintWriter pw) {
		// pw.print(tab + "<instancia nome=" + citar(get(i.getDescricao())) + "
		// margemInferior="
		// + citar("" + i.margemInferior));
		//
		// if (i.getCor() != null) {
		// pw.print(" cor=" + citar("" + i.getCor().getRGB()));
		// }
		//
		// if (i.isMinimizado()) {
		// pw.print(" minimizado=" + citar("true"));
		// }
		//
		// if (i.isDesenharComentario()) {
		// pw.print(" desenharComentario=" + citar("true"));
		// }
		//
		// if (i.getComentario().length() > 0) {
		// pw.print(" comentario=" + citar(get(i.getComentario())));
		// }
		//
		// if (i.isVazio()) {
		// pw.println("/>");
		// } else {
		// pw.println(">");
		// }
	}

	public static void finalTag(String tab, Instancia i, PrintWriter pw) {
		// if (!i.isVazio()) {
		// pw.println(tab + "</instancia>");
		// }
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

	public void imprimir(PrintWriter pw) {
		imprimir("", pw);
	}

	public void imprimir(String tab, PrintWriter pw) {
		// tab += pai != null ? "\t" : "";
		//
		// ArquivoUtil.inicioTag(tab, this, pw);
		//
		// for (Instancia i : filhos) {
		// i.imprimir(tab, pw);
		// }
		//
		// ArquivoUtil.finalTag(tab, this, pw);
	}
}