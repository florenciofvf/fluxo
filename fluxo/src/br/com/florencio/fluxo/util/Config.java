package br.com.florencio.fluxo.util;

import java.util.ResourceBundle;

public class Config {
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");

	public static String get(String chave) {
		return bundle.getString(chave);
	}
}