package br.com.florencio.fluxo.util;

public class Dimensao {
	final int largura;
	final int altura;

	public Dimensao(int largura, int altura) {
		this.largura = largura;
		this.altura = altura;
	}

	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
	}

	@Override
	public String toString() {
		return "largura=" + largura + " altura=" + altura;
	}
}