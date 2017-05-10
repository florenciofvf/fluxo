package br.com.florencio.fluxo.util;

public class Localizacao {
	final int x;
	final int y;

	public Localizacao(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "x=" + x + " y=" + y;
	}
}