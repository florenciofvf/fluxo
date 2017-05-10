package br.com.florencio.fluxo.util;

import java.util.ResourceBundle;

public class Strings {
	private static final ResourceBundle bundle = ResourceBundle.getBundle("strings");

	public static String get(String chave) {
		return bundle.getString(chave);
	}
}