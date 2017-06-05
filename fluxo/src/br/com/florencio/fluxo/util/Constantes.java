package br.com.florencio.fluxo.util;

import java.awt.BasicStroke;
import java.awt.Stroke;

public class Constantes {

	private Constantes() {
	}

	public static final Stroke STROKE = new BasicStroke(2.0f);
	public static final Stroke STROKE2 = new BasicStroke(1.5f);
	public static final String CODIFICACAO = "iso-8859-1";
	public static final byte TAMANHO_SINAL_ICONE_COMENTARIO = 4;
	public static boolean DESENHAR_RETANGULO_PADRAO = false;
	public static boolean DESENHAR_FONTE = true;
	public static final byte ALTURA_PADRAO_RETANGULO = 32;
	public static int RETANGULO_ALTURA_PADRAO = 32;
	public static boolean USAR_LARGURA_PADRAO = false;
	public static int LARGURA_PADRAO = 0;
	public static final byte APARENCIA_ALTURA_PADRAO = 20;
	public static final byte METADE_APARENCIA_ALTURA = APARENCIA_ALTURA_PADRAO / 2;
	public static final byte LARGURA_MIN_MAX = 10;
	public static final byte MARGEM_MIN_MAX = (APARENCIA_ALTURA_PADRAO - LARGURA_MIN_MAX) / 2;
	public static final byte METADE_MIN_MAX = LARGURA_MIN_MAX / 2;
	public static final byte LARGURA_AFASTAMENTO = 40;
	public static final byte APARENCIA_ABAIXO = 0;
	public static final byte APARENCIA_ACIMA = 1;
	public static final byte APARENCIA_MEIO = 2;
	public static byte ALINHAMENTO = APARENCIA_MEIO;
	public static byte ALTURA_FONTE;
}