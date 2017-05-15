package br.com.florencio.fluxo;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import br.com.florencio.fluxo.util.Constantes;

public class InstanciaRaiz extends Instancia {
	private boolean clicadoNoIconeEsquerdo;
	private boolean clicadoNoIconeDireito;
	public boolean processado;
	Instancia raizEsquerda;
	Instancia raizDireita;

	public InstanciaRaiz(String descricao) {
		super(descricao);
		aparencia = new InstanciaAparenciaRaiz();
		adicionar(new Instancia(descricao, true));
		adicionar(new Instancia(descricao));
	}

	@Override
	public Instancia clonar() {
		throw new IllegalStateException();
	}

	@Override
	public void adicionar(Instancia i, int indice) {
		if (i.esquerdo) {
			raizEsquerda = i;
		} else {
			raizDireita = i;
		}

		i.descricao = descricao;
		i.braco = true;
		i.pai = this;
		i.cor = cor;
	}

	public void adicionarInstancia(Instancia i) {
		if (i.esquerdo) {
			raizEsquerda.adicionar(i);
		} else {
			raizDireita.adicionar(i);
		}
	}

	@Override
	public boolean excluir(Instancia i) {
		throw new IllegalStateException();
	}

	@Override
	public void limpar() {
		adicionar(new Instancia("esquerdo", true));
		adicionar(new Instancia("direito"));
	}

	@Override
	public List<Instancia> getFilhos() {
		throw new IllegalStateException();
	}

	@Override
	public Instancia getFilho(int index) {
		throw new IllegalStateException();
	}

	@Override
	public int getIndice(Instancia i) {
		throw new IllegalStateException();
	}

	@Override
	public Instancia getFilho(String descricao) {
		Instancia resp = raizEsquerda.getFilho(descricao);

		if (resp == null) {
			resp = raizDireita.getFilho(descricao);
		}

		return resp;
	}

	public int getTamanho() {
		throw new IllegalStateException();
	}

	public boolean estaVazio() {
		return raizEsquerda.estaVazio() && raizDireita.estaVazio();
	}

	public List<Linha> getLinhas() {
		throw new IllegalStateException();
	}

	public void processar(FontMetrics metrics) {
		Constantes.ALTURA_FONTE = (byte) metrics.getHeight();
		inicializar();
		definirDimensaoAltura();
		definirDimensaoLargura(metrics);
		setLocalizacao(0, 0);
		definirLocalizacaoX();
		definirLocalizacaoY(0);
		definirLocalizacaoAfastamentoHorizontal();
		definirLocalizacaoAparenciaVertical(Constantes.APARENCIA_MEIO);
		AtomicInteger valor = inverterLadoEsquerdo();
		deslocarTodaHierarquia(valor);
		voltarRaizELadoDireito();
		alinharLadosComRaizHorizontalmente();
		afastarHierarquiaDaBordaEsquerda();
		criarLinhas();
		processado = true;
	}

	private void voltarRaizELadoDireito() {
		int deslocEsquerdo = dimensao.getLargura() * 2 + Constantes.LARGURA_AFASTAMENTO;
		somarLocalizacaoX(deslocEsquerdo * -1);
		deslocEsquerdo += dimensao.getLargura() + Constantes.LARGURA_AFASTAMENTO;
		raizDireita.somarLocalizacaoX(deslocEsquerdo * -1);
	}

	private void afastarHierarquiaDaBordaEsquerda() {
		raizEsquerda.somarLocalizacaoX(Constantes.LARGURA_AFASTAMENTO);
		somarLocalizacaoX(Constantes.LARGURA_AFASTAMENTO);
		raizDireita.somarLocalizacaoX(Constantes.LARGURA_AFASTAMENTO);
	}

	private void alinharLadosComRaizHorizontalmente() {
		if (raizEsquerda.dimensao.getAltura() > raizDireita.dimensao.getAltura()) {
			int difY = localizacaoAparencia.getY() - raizDireita.localizacaoAparencia.getY();
			raizDireita.somarLocalizacaoY(difY);
		} else if (raizEsquerda.dimensao.getAltura() < raizDireita.dimensao.getAltura()) {
			int difY = localizacaoAparencia.getY() - raizEsquerda.localizacaoAparencia.getY();
			raizEsquerda.somarLocalizacaoY(difY);
		}
	}

	private void deslocarTodaHierarquia(AtomicInteger valor) {
		raizEsquerda.somarLocalizacaoX(valor.get());
		somarLocalizacaoX(valor.get());
		raizDireita.somarLocalizacaoX(valor.get());
	}

	private AtomicInteger inverterLadoEsquerdo() {
		AtomicInteger valor = new AtomicInteger(0);
		raizEsquerda.calcularLarguraTotal(valor);
		raizEsquerda.inverterLocalizacaoX();
		return valor;
	}

	@Override
	void inicializar() {
		super.inicializar();
		raizEsquerda.inicializar();
		raizDireita.inicializar();
	}

	@Override
	void definirDimensaoAltura() {
		raizEsquerda.definirDimensaoAltura();
		raizDireita.definirDimensaoAltura();

		dimensao.setLarguraAltura(0, Math.max(raizEsquerda.dimensao.getAltura(), raizDireita.dimensao.getAltura()));
	}

	@Override
	void definirDimensaoLargura(FontMetrics metrics) {
		super.definirDimensaoLargura(metrics);
		raizEsquerda.definirDimensaoLargura(metrics);
		raizDireita.definirDimensaoLargura(metrics);

		int largura = dimensao.getLargura();

		if (!estaVazio()) {
			largura -= Constantes.LARGURA_MIN_MAX;
		}

		if (!raizEsquerda.estaVazio()) {
			largura += Constantes.LARGURA_MIN_MAX;
		}

		if (!raizDireita.estaVazio()) {
			largura += Constantes.LARGURA_MIN_MAX;
		}

		dimensao.setLarguraAltura(largura, dimensao.getAltura());
		dimensaoAparencia.setLarguraAltura(largura, Constantes.APARENCIA_ALTURA_PADRAO);
		raizEsquerda.dimensao.setLarguraAltura(largura, raizEsquerda.dimensao.getAltura());
		raizEsquerda.dimensaoAparencia.setLarguraAltura(largura, Constantes.APARENCIA_ALTURA_PADRAO);
		raizDireita.dimensao.setLarguraAltura(largura, raizDireita.dimensao.getAltura());
		raizDireita.dimensaoAparencia.setLarguraAltura(largura, Constantes.APARENCIA_ALTURA_PADRAO);
	}

	@Override
	void definirLocalizacaoX() {
		raizEsquerda.definirLocalizacaoX();
		raizDireita.definirLocalizacaoX();
	}

	@Override
	void definirLocalizacaoY(int acumulo) {
		raizEsquerda.definirLocalizacaoY(0);
		raizDireita.definirLocalizacaoY(0);
	}

	@Override
	void definirLocalizacaoAfastamentoHorizontal() {
		raizEsquerda.definirLocalizacaoAfastamentoHorizontal();
		raizDireita.definirLocalizacaoAfastamentoHorizontal();
	}

	@Override
	public void calcularLarguraTotal(AtomicInteger integer) {
		AtomicInteger esquerda = new AtomicInteger(0);
		AtomicInteger direita = new AtomicInteger(0);

		// raizEsquerda.calcularLarguraTotal(esquerda);
		raizDireita.calcularLarguraTotal(direita);

		integer.set(esquerda.get() + direita.get() + Constantes.LARGURA_AFASTAMENTO);
	}

	@Override
	public void calcularAlturaTotal(AtomicInteger integer) {
		integer.set(dimensao.getAltura());
	}

	@Override
	void inverterLocalizacaoX() {
		throw new IllegalStateException();
	}

	@Override
	void definirLocalizacaoAparenciaVertical(byte local) {
		super.definirLocalizacaoAparenciaVertical(local);
		raizEsquerda.definirLocalizacaoAparenciaVertical(local);
		raizDireita.definirLocalizacaoAparenciaVertical(local);
	}

	@Override
	void criarLinhas() {
		raizEsquerda.criarLinhas();
		raizDireita.criarLinhas();
	}

	@Override
	public Instancia procurar(int x, int y) {
		Instancia i = super.procurar(x, y);

		if (i != null) {
			return i;
		}

		i = raizEsquerda.procurar(x, y);

		if (i != null) {
			return i;
		}

		i = raizDireita.procurar(x, y);

		return i;
	}

	public Instancia getClicadoNoIcone() {
		if (clicadoNoIconeEsquerdo) {
			return raizEsquerda;
		}

		if (clicadoNoIconeDireito) {
			return raizDireita;
		}

		return null;
	}

	@Override
	public boolean clicadoNoIcone(int x, int y) {
		clicadoNoIconeEsquerdo = raizEsquerda.clicadoNoIcone(x, y);
		clicadoNoIconeDireito = raizDireita.clicadoNoIcone(x, y);
		return clicadoNoIconeEsquerdo ^ clicadoNoIconeDireito;
	}

	@Override
	public boolean clicadoAreaIcone(int x, int y) {
		return raizEsquerda.clicadoAreaIcone(x, y) ^ raizDireita.clicadoAreaIcone(x, y);
	}

	@Override
	public void desenhar(Graphics2D g2) {
		aparencia.desenhar(this, g2);
		raizEsquerda.desenhar(g2);
		raizDireita.desenhar(g2);
	}

	@Override
	public void setDescricao(String descricao) {
		super.setDescricao(descricao);

		if (raizEsquerda != null) {
			raizEsquerda.setDescricao(descricao);
		}

		if (raizDireita != null) {
			raizDireita.setDescricao(descricao);
		}
	}

	@Override
	public void setCor(Color cor) {
		super.setCor(cor);
		raizEsquerda.setCor(cor);
		raizDireita.setCor(cor);
	}

	@Override
	public void setCorHierarquia(Color cor) {
		super.setCorHierarquia(cor);
		raizEsquerda.setCorHierarquia(cor);
		raizDireita.setCorHierarquia(cor);
	}

	@Override
	public void minMaxTodos(boolean b) {
		super.minMaxTodos(b);
		raizEsquerda.minMaxTodos(b);
		raizDireita.minMaxTodos(b);
	}

	@Override
	public void inverterMinMax() {
		super.inverterMinMax();
		if (clicadoNoIconeEsquerdo) {
			raizEsquerda.inverterMinMax();
		}

		if (clicadoNoIconeDireito) {
			raizDireita.inverterMinMax();
		}
	}

	@Override
	public void setComentario(String comentario) {
		super.setComentario(comentario);
		raizEsquerda.setComentario(comentario);
		raizDireita.setComentario(comentario);
	}

	public void setDesenharComentario(boolean desenharComentario) {
		this.desenharComentario = desenharComentario;
		raizEsquerda.setDesenharComentario(desenharComentario);
		raizDireita.setDesenharComentario(desenharComentario);
	}

	@Override
	public Instancia getPonta() {
		throw new IllegalStateException();
	}

	public Instancia getRaizEsquerda() {
		return raizEsquerda;
	}

	public Instancia getRaizDireita() {
		return raizDireita;
	}
}