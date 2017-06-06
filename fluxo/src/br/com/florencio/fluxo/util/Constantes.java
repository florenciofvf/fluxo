package br.com.florencio.fluxo.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

public class Constantes {

	private Constantes() {
	}

	public static final float STROKE_SELECIONADO_F = 2.0f;
	public static final float STROKE_PADRAO_F = 1.0f;
	public static Stroke STROKE_SELECIONADO = new BasicStroke(STROKE_SELECIONADO_F);
	public static Stroke STROKE_PADRAO = new BasicStroke(STROKE_PADRAO_F);
	public static final Color COR_BORDA_PADRAO = Color.LIGHT_GRAY;
	public static final Color COR_SELECIONADO_PADRAO = Color.BLUE;
	public static final byte TAMANHO_SINAL_ICONE_COMENTARIO = 4;
	public static final Color COR_FUNDO_PADRAO = Color.WHITE;
	public static final Color COR_RETAN_PADRAO = Color.BLACK;
	public static final String CODIFICACAO = "iso-8859-1";
	public static final byte ALTURA_PADRAO_RETANGULO = 32;
	public static final byte APARENCIA_ALTURA_PADRAO = 20;
	public static final byte LARGURA_AFASTAMENTO = 40;
	public static boolean DESENHAR_LIMITE = false;
	public static final byte LARGURA_MIN_MAX = 10;
	public static final byte APARENCIA_ABAIXO = 0;
	public static final byte APARENCIA_ACIMA = 1;
	public static boolean DESENHAR_FONTE = true;
	public static final byte APARENCIA_MEIO = 2;
	public static int ALTURA_PADRAO = ALTURA_PADRAO_RETANGULO;
	public static final byte METADE_APARENCIA_ALTURA = APARENCIA_ALTURA_PADRAO / 2;
	public static final byte MARGEM_MIN_MAX = (APARENCIA_ALTURA_PADRAO - LARGURA_MIN_MAX) / 2;
	public static final byte METADE_MIN_MAX = LARGURA_MIN_MAX / 2;
	public static Color COR_SELECIONADO = COR_SELECIONADO_PADRAO;
	public static boolean USAR_LARGURA_PADRAO = false;
	public static Color COR_FUNDO = COR_FUNDO_PADRAO;
	public static Color COR_BORDA = COR_BORDA_PADRAO;
	public static Color COR_LIMITE = COR_RETAN_PADRAO;
	public static byte ALINHAMENTO = APARENCIA_MEIO;
	public static int LARGURA_PADRAO = 0;
	public static byte ALTURA_FONTE;
}