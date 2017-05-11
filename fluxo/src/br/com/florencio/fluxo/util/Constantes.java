package br.com.florencio.fluxo.util;

public class Constantes {

	private Constantes() {
	}

	public static final byte TAMANHO_SINAL_ICONE_COMENTARIO = 5;
	public static boolean DESENHAR_RETANGULO_PADRAO = false;
	public static final byte RETANGULO_ALTURA_PADRAO = 32;
	public static final byte APARENCIA_ALTURA_PADRAO = 20;
	public static final byte METADE_APARENCIA_ALTURA = APARENCIA_ALTURA_PADRAO / 2;
	public static final byte LARGURA_MIN_MAX = 10;
	public static final byte MARGEM_MIN_MAX = (APARENCIA_ALTURA_PADRAO - LARGURA_MIN_MAX) / 2;
	public static final byte METADE_MIN_MAX = LARGURA_MIN_MAX / 2;
	public static final byte LARGURA_AFASTAMENTO = 40;
	public static final byte APARENCIA_ABAIXO = 0;
	public static final byte APARENCIA_ACIMA = 1;
	public static final byte APARENCIA_MEIO = 2;
	public static byte ALTURA_FONTE;
}