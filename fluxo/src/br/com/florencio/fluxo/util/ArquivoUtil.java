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

		pw.print(" corSelecionado=" + citar("" + Constantes.COR_SELECIONADO.getRGB()));
		pw.print(" corLimite=" + citar("" + Constantes.COR_LIMITE.getRGB()));
		pw.print(" desenharFonte=" + citar("" + Constantes.DESENHAR_FONTE));
		pw.print(" larguraPadrao=" + citar("" + Constantes.LARGURA_PADRAO));
		pw.print(" corFundo=" + citar("" + Constantes.COR_FUNDO.getRGB()));
		pw.print(" corBorda=" + citar("" + Constantes.COR_BORDA.getRGB()));
		pw.print(" alturaPadrao=" + citar("" + Constantes.ALTURA_PADRAO));
		pw.print(" alinhamento=" + citar("" + Constantes.ALINHAMENTO));

		if (i.getCor() != null) {
			pw.print(" cor=" + citar("" + i.getCor().getRGB()));
		}

		if (i.isMinimizado()) {
			pw.print(" minimizado=" + citar("true"));
		}

		if (i.getAlturaComplementar() != 0) {
			pw.print(" alturaComplementar=" + citar("" + i.getAlturaComplementar()));
		}

		if (i.isDesenharDestacado()) {
			pw.print(" desenharDestacado=" + citar("true"));
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

		if (i.isDesenharDestacado()) {
			pw.print(" desenharDestacado=" + citar("true"));
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

			instancia.setDesenharComentario(Boolean.parseBoolean(attributes.getValue("desenharComentario")));
			instancia.setDesenharObservacao(Boolean.parseBoolean(attributes.getValue("desenharObservacao")));
			instancia.setDesenharDestacado(Boolean.parseBoolean(attributes.getValue("desenharDestacado")));
			instancia.setMinimizado(Boolean.parseBoolean(attributes.getValue("minimizado")));
			instancia.setEsquerdo(Boolean.parseBoolean(attributes.getValue("esquerdo")));
			instancia.setComentario(attributes.getValue("comentario"));
			instancia.setObservacao(attributes.getValue("observacao"));

			if (!Util.estaVazio(attributes.getValue("larguraRetanguloTotal"))) {
				instancia.setLarguraRetanguloTotal(Integer.parseInt(attributes.getValue("larguraRetanguloTotal")));
			}

			String desenharAparencia = attributes.getValue("desenharAparencia");
			if (desenharAparencia == null || desenharAparencia.length() == 0) {
				desenharAparencia = "true";
			}
			instancia.setDesenharAparencia(Boolean.parseBoolean(desenharAparencia));

			if (!Util.estaVazio(attributes.getValue("cor"))) {
				instancia.setCor(new Color(Integer.parseInt(attributes.getValue("cor"))));
			}

			if (!Util.estaVazio(attributes.getValue("alturaComplementar"))) {
				instancia.setAlturaComplementar(Integer.parseInt(attributes.getValue("alturaComplementar")));
			}

			if (!Util.estaVazio(attributes.getValue("margemSuperior"))) {
				instancia.setMargemSuperior(Integer.parseInt(attributes.getValue("margemSuperior")));
			}

			if (!Util.estaVazio(attributes.getValue("margemInferior"))) {
				instancia.setMargemInferior(Integer.parseInt(attributes.getValue("margemInferior")));
			}

			if (raiz == null) {
				Constantes.DESENHAR_FONTE = Boolean.parseBoolean(attributes.getValue("desenharFonte"));
				Constantes.COR_SELECIONADO = Constantes.COR_SELECIONADO_PADRAO;
				Constantes.ALTURA_PADRAO = Constantes.ALTURA_PADRAO_RETANGULO;
				Constantes.COR_LIMITE = Constantes.COR_RETAN_PADRAO;
				Constantes.COR_FUNDO = Constantes.COR_FUNDO_PADRAO;
				Constantes.COR_BORDA = Constantes.COR_BORDA_PADRAO;
				Constantes.ALINHAMENTO = Constantes.APARENCIA_MEIO;
				Constantes.LARGURA_PADRAO = 0;

				if (!Util.estaVazio(attributes.getValue("corSelecionado"))) {
					Constantes.COR_SELECIONADO = new Color(Integer.parseInt(attributes.getValue("corSelecionado")));
				}

				if (!Util.estaVazio(attributes.getValue("corLimite"))) {
					Constantes.COR_LIMITE = new Color(Integer.parseInt(attributes.getValue("corLimite")));
				}

				if (!Util.estaVazio(attributes.getValue("corFundo"))) {
					Constantes.COR_FUNDO = new Color(Integer.parseInt(attributes.getValue("corFundo")));
				}

				if (!Util.estaVazio(attributes.getValue("corBorda"))) {
					Constantes.COR_BORDA = new Color(Integer.parseInt(attributes.getValue("corBorda")));
				}

				if (!Util.estaVazio(attributes.getValue("larguraPadrao"))) {
					Constantes.LARGURA_PADRAO = Integer.parseInt(attributes.getValue("larguraPadrao"));
				}

				if (!Util.estaVazio(attributes.getValue("alturaPadrao"))) {
					Constantes.ALTURA_PADRAO = Integer.parseInt(attributes.getValue("alturaPadrao"));
				}

				if (!Util.estaVazio(attributes.getValue("alinhamento"))) {
					Constantes.ALINHAMENTO = Byte.parseByte(attributes.getValue("alinhamento"));
				}

				Constantes.USAR_LARGURA_PADRAO = Constantes.LARGURA_PADRAO > 0;
				if (!Constantes.USAR_LARGURA_PADRAO) {
					Constantes.LARGURA_PADRAO = 0;
				}

				raiz = new InstanciaRaiz(instancia.getDescricao());
				raiz.setAlturaComplementar(instancia.getAlturaComplementar());
				raiz.setDesenharComentario(instancia.isDesenharComentario());
				raiz.setDesenharObservacao(instancia.isDesenharObservacao());
				raiz.setDesenharDestacado(instancia.isDesenharDestacado());
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