package br.com.florencio.fluxo.util;

public class Util {

	private Util() {
	}

	public static boolean estaVazio(String string) {
		return string == null || string.trim().length() == 0;
	}

	public static String escaparString(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (char c : s.toCharArray()) {
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\n':
			case '\r':
				sb.append("&#xa;");
				break;
			case '\t':
				sb.append("&#x9;");
				break;
			case 0xa0:
				sb.append("&#xa0;");
				break;
			default:
				sb.append(c);
			}
		}

		return sb.toString();
	}
}