package br.com.florencio.fluxo.view;

public abstract class Acao {
	final String descricao;
	final boolean especial;

	public Acao(String descricao) {
		this(descricao, false);
	}

	public Acao(String descricao, boolean especial) {
		this.descricao = descricao;
		this.especial = especial;
	}

	public abstract void executar();

	@Override
	public String toString() {
		return descricao;
	}
}