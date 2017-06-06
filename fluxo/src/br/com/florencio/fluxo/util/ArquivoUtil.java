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
		inicioTagPrincipal("", raiz, pw);
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

	public static void inicioTagPrincipal(String tab, Instancia i, PrintWriter pw) {
		pw.print(tab + "<instancia nome=" + citar(Util.escaparString(i.getDescricao())));

		pw.print(" alinhamento=" + citar("" + Constantes.ALINHAMENTO));
		pw.print(" desenharFonte=" + citar("" + Constantes.DESENHAR_FONTE));
		pw.print(" alturaPadrao=" + citar("" + Constantes.RETANGULO_ALTURA_PADRAO));
		pw.print(" larguraPadrao=" + citar("" + Constantes.LARGURA_PADRAO));

		if (i.getCor() != null) {
			pw.print(" cor=" + citar("" + i.getCor().getRGB()));
		}

		if (i.isMinimizado()) {
			pw.print(" minimizado=" + citar("true"));
		}

		if (i.getAlturaComplementar() != 0) {
			pw.print(" alturaComplementar=" + citar("" + i.getAlturaComplementar()));
		}

		if (i.isDesenharRetanguloTotal()) {
			pw.print(" desenharRetanguloTotal=" + citar("true"));
			pw.print(" larguraRetanguloTotal=" + citar("" + i.getLarguraRetanguloTotal()));
		}

		if (i.isDesenharComentario()) {
			pw.print(" desenharComentario=" + citar("true"));
		}

		if (!i.isDesenharAparencia()) {
			pw.print(" desenharAparencia=" + citar("false"));
		}

		if (i.getComentario().length() > 0) {
			pw.print(" comentario=" + citar(Util.escaparString(i.getComentario())));
		}

		if (i.isDesenharObservacao()) {
			pw.print(" desenharObservacao=" + citar("true"));
		}

		if (i.getObservacao().length() > 0) {
			pw.print(" observacao=" + citar(Util.escaparString(i.getObservacao())));
		}

		if (i.getMargemSuperior() > 0) {
			pw.print(" margemSuperior=" + citar("" + i.getMargemSuperior()));
		}

		if (i.getMargemInferior() > 0) {
			pw.print(" margemInferior=" + citar("" + i.getMargemInferior()));
		}

		if (i.estaVazio()) {
			pw.println("/>");
		} else {
			pw.println(">");
		}
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

		if (i.isDesenharRetanguloTotal()) {
			pw.print(" desenharRetanguloTotal=" + citar("true"));
			pw.print(" larguraRetanguloTotal=" + citar("" + i.getLarguraRetanguloTotal()));
		}

		if (i.isDesenharComentario()) {
			pw.print(" desenharComentario=" + citar("true"));
		}

		if (!i.isDesenharAparencia()) {
			pw.print(" desenharAparencia=" + citar("false"));
		}

		if (i.getComentario().length() > 0) {
			pw.print(" comentario=" + citar(Util.escaparString(i.getComentario())));
		}

		if (i.isDesenharObservacao()) {
			pw.print(" desenharObservacao=" + citar("true"));
		}

		if (i.getObservacao().length() > 0) {
			pw.print(" observacao=" + citar(Util.escaparString(i.getObservacao())));
		}

		if (i.getMargemSuperior() > 0) {
			pw.print(" margemSuperior=" + citar("" + i.getMargemSuperior()));
		}

		if (i.getMargemInferior() > 0) {
			pw.print(" margemInferior=" + citar("" + i.getMargemInferior()));
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

			String desenharRetanguloTotal = attributes.getValue("desenharRetanguloTotal");
			instancia.setDesenharRetanguloTotal(Boolean.parseBoolean(desenharRetanguloTotal));
			String larguraRetanguloTotal = attributes.getValue("larguraRetanguloTotal");
			if (!Util.estaVazio(larguraRetanguloTotal)) {
				instancia.setLarguraRetanguloTotal(Integer.parseInt(larguraRetanguloTotal));
			}

			String desenharComentario = attributes.getValue("desenharComentario");
			instancia.setDesenharComentario(Boolean.parseBoolean(desenharComentario));

			String desenharAparencia = attributes.getValue("desenharAparencia");
			if (desenharAparencia == null || desenharAparencia.length() == 0) {
				desenharAparencia = "true";
			}
			instancia.setDesenharAparencia(Boolean.parseBoolean(desenharAparencia));

			String desenharObservacao = attributes.getValue("desenharObservacao");
			instancia.setDesenharObservacao(Boolean.parseBoolean(desenharObservacao));

			String cor = attributes.getValue("cor");
			if (!Util.estaVazio(cor)) {
				instancia.setCor(new Color(Integer.parseInt(cor)));
			}

			String alturaComplementar = attributes.getValue("alturaComplementar");
			if (!Util.estaVazio(alturaComplementar)) {
				instancia.setAlturaComplementar(Integer.parseInt(alturaComplementar));
			}

			String margemSuperior = attributes.getValue("margemSuperior");
			if (!Util.estaVazio(margemSuperior)) {
				instancia.setMargemSuperior(Integer.parseInt(margemSuperior));
			}

			String margemInferior = attributes.getValue("margemInferior");
			if (!Util.estaVazio(margemInferior)) {
				instancia.setMargemInferior(Integer.parseInt(margemInferior));
			}

			String comentario = attributes.getValue("comentario");
			instancia.setComentario(comentario);

			String observacao = attributes.getValue("observacao");
			instancia.setObservacao(observacao);

			if (raiz == null) {
				Constantes.DESENHAR_FONTE = Boolean.parseBoolean(attributes.getValue("desenharFonte"));

				String alturaPadrao = attributes.getValue("alturaPadrao");

				if (!Util.estaVazio(alturaPadrao)) {
					Constantes.RETANGULO_ALTURA_PADRAO = Integer.parseInt(alturaPadrao);
				} else {
					Constantes.RETANGULO_ALTURA_PADRAO = Constantes.ALTURA_PADRAO_RETANGULO;
				}

				Constantes.LARGURA_PADRAO = 0;
				String larguraPadrao = attributes.getValue("larguraPadrao");
				if (!Util.estaVazio(larguraPadrao)) {
					Constantes.LARGURA_PADRAO = Integer.parseInt(larguraPadrao);
				}

				Constantes.ALINHAMENTO = Constantes.APARENCIA_MEIO;
				String alinhamento = attributes.getValue("alinhamento");
				if (!Util.estaVazio(alinhamento)) {
					Constantes.ALINHAMENTO = Byte.parseByte(alinhamento);
				}

				Constantes.USAR_LARGURA_PADRAO = Constantes.LARGURA_PADRAO > 0;
				if (!Constantes.USAR_LARGURA_PADRAO) {
					Constantes.LARGURA_PADRAO = 0;
				}

				raiz = new InstanciaRaiz(instancia.getDescricao());
				raiz.setDesenharRetanguloTotal(instancia.isDesenharRetanguloTotal());
				raiz.setAlturaComplementar(instancia.getAlturaComplementar());
				raiz.setDesenharComentario(instancia.isDesenharComentario());
				raiz.setDesenharObservacao(instancia.isDesenharObservacao());
				raiz.setDesenharAparencia(instancia.isDesenharAparencia());
				raiz.setMargemSuperior(instancia.getMargemSuperior());
				raiz.setMargemInferior(instancia.getMargemInferior());
				raiz.setComentario(instancia.getComentario());
				raiz.setObservacao(instancia.getObservacao());
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