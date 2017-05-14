package br.com.florencio.fluxo.util;

public class Dimensao {
	int largura;
	int altura;

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

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public void setLarguraAltura(int largura, int altura) {
		this.largura = largura;
		this.altura = altura;
	}

	@Override
	public String toString() {
		return "largura=" + largura + " altura=" + altura;
	}
}