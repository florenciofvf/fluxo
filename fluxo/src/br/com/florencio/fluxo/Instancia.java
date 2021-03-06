package br.com.florencio.fluxo;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import br.com.florencio.fluxo.util.ArquivoUtil;
import br.com.florencio.fluxo.util.Constantes;
import br.com.florencio.fluxo.util.Dimensao;
import br.com.florencio.fluxo.util.Localizacao;
import br.com.florencio.fluxo.util.Util;

public class Instancia {
	final Localizacao localizacaoAparencia = new Localizacao(0, 0);
	final Dimensao dimensaoAparencia = new Dimensao(0, 0);
	final Localizacao localizacao = new Localizacao(0, 0);
	final Dimensao dimensao = new Dimensao(0, 0);
	boolean desenharAparencia = true;
	InstanciaAparencia aparencia;
	boolean desenharObservacao;
	boolean desenharComentario;
	boolean iconeMinMaxClicado;
	public boolean selecionado;
	boolean desenharDestacado;
	int larguraRetanguloTotal;
	public boolean marcado;
	int alturaComplementar;
	List<Instancia> filhos;
	int margemSuperior;
	int margemInferior;
	List<Linha> linhas;
	boolean minimizado;
	String observacao;
	String comentario;
	String descricao;
	boolean esquerdo;
	boolean braco;
	Instancia pai;
	byte local;
	Color cor;

	public Instancia(String descricao) {
		this(descricao, false);
	}

	public Instancia(String descricao, boolean esquerdo) {
		aparencia = new InstanciaAparencia();
		filhos = new ArrayList<>();
		linhas = new ArrayList<>();
		this.esquerdo = esquerdo;
		setDescricao(descricao);
	}

	public Instancia clonar() {
		Instancia obj = new Instancia(descricao);
		obj.desenharObservacao = desenharObservacao;
		obj.desenharComentario = desenharComentario;
		obj.desenharAparencia = desenharAparencia;
		obj.observacao = observacao;
		obj.comentario = comentario;
		obj.minimizado = minimizado;
		obj.esquerdo = esquerdo;
		obj.cor = cor;

		for (Instancia i : filhos) {
			Instancia o = i.clonar();
			obj.adicionar(o);
		}

		return obj;
	}

	public void adicionar(Instancia i, int indice) {
		if (i.pai != null) {
			i.pai.excluir(i);
		}

		Instancia cmp = this;

		while (cmp != null) {
			if (i == cmp) {
				throw new IllegalStateException();
			}
			cmp = cmp.pai;
		}

		i.pai = this;
		i.esquerdo = esquerdo;

		if (indice == -1) {
			filhos.add(i);
		} else {
			filhos.add(indice, i);
		}
	}

	public void adicionar(Instancia i) {
		adicionar(i, -1);
	}

	public boolean excluir(Instancia i) {
		if (i.pai != this) {
			return false;
		}

		i.pai = null;
		filhos.remove(i);
		return true;
	}

	public void replicarLado() {
		if (!(pai instanceof InstanciaRaiz)) {
			esquerdo = pai.esquerdo;
		}

		for (Instancia i : filhos) {
			i.replicarLado();
		}
	}

	public boolean sairDaHierarquia() {
		if (pai == null) {
			return false;
		}

		List<Instancia> lista = new ArrayList<>();

		for (Instancia i : filhos) {
			Instancia o = i.clonar();
			lista.add(o);
		}

		int indice = pai.getIndice(this);

		for (Instancia i : lista) {
			if (lista.size() == 1) {
				pai.adicionar(i, indice);
			} else {
				pai.adicionar(i);
			}
		}

		pai.excluir(this);
		return true;
	}

	public boolean primeiro() {
		if (pai == null) {
			return false;
		}

		List<Instancia> filhos = pai.getFilhos();
		int pos = filhos.indexOf(this);

		if (pos > 0) {
			filhos.remove(pos);
			filhos.add(0, this);
			return true;
		}

		return false;
	}

	public boolean subir() {
		if (pai == null) {
			return false;
		}

		List<Instancia> filhos = pai.getFilhos();
		int pos = filhos.indexOf(this);

		if (pos > 0) {
			Instancia objAnterior = filhos.get(pos - 1);
			filhos.set(pos, objAnterior);
			filhos.set(pos - 1, this);
			return true;
		}

		return false;
	}

	public boolean descer() {
		if (pai == null) {
			return false;
		}

		List<Instancia> filhos = pai.getFilhos();
		int pos = filhos.indexOf(this);

		if (pos < filhos.size() - 1) {
			Instancia objPosterior = filhos.get(pos + 1);
			filhos.set(pos, objPosterior);
			filhos.set(pos + 1, this);
			return true;
		}

		return false;
	}

	public boolean ultimo() {
		if (pai == null) {
			return false;
		}

		List<Instancia> filhos = pai.getFilhos();
		int pos = filhos.indexOf(this);

		if (pos < filhos.size() - 1) {
			filhos.remove(pos);
			filhos.add(this);
			return true;
		}

		return false;
	}

	public boolean excluirOutros() {
		if (pai == null) {
			return false;
		}

		List<Instancia> filhos = pai.getFilhos();
		List<Instancia> lista = new ArrayList<>();

		for (Instancia obj : filhos) {
			if (obj != this) {
				lista.add(obj);
			}
		}

		for (Instancia obj : lista) {
			pai.excluir(obj);
		}

		return !lista.isEmpty();
	}

	public boolean excluirAcima() {
		if (pai == null) {
			return false;
		}

		List<Instancia> filhos = pai.getFilhos();
		List<Instancia> lista = new ArrayList<>();

		for (Instancia obj : filhos) {
			if (obj != this) {
				lista.add(obj);
			} else {
				break;
			}
		}

		for (Instancia obj : lista) {
			pai.excluir(obj);
		}

		return !lista.isEmpty();
	}

	public boolean excluirAbaixo() {
		if (pai == null) {
			return false;
		}

		List<Instancia> filhos = pai.getFilhos();
		List<Instancia> lista = new ArrayList<>();

		boolean ativado = false;

		for (Instancia obj : filhos) {
			if (obj == this) {
				ativado = true;
			} else if (ativado) {
				lista.add(obj);
			}
		}

		for (Instancia obj : lista) {
			pai.excluir(obj);
		}

		return !lista.isEmpty();
	}

	public void limpar() {
		while (getTamanho() > 0) {
			Instancia i = getFilho(0);
			excluir(i);
		}
	}

	public List<Instancia> getFilhos() {
		return filhos;
	}

	public Instancia getFilho(int index) {
		if (index < 0 || index >= getTamanho()) {
			throw new IllegalStateException();
		}

		return filhos.get(index);
	}

	public int getIndice(Instancia i) {
		return filhos.indexOf(i);
	}

	public Instancia getFilho(String descricao) {
		if (Util.estaVazio(descricao)) {
			return null;
		}

		for (Instancia i : filhos) {
			if (descricao.equals(i.descricao)) {
				return i;
			}
		}

		return null;
	}

	public int getTamanho() {
		return filhos.size();
	}

	public boolean estaVazio() {
		return filhos.isEmpty();
	}

	public Instancia getPai() {
		return pai;
	}

	public List<Linha> getLinhas() {
		return linhas;
	}

	public Dimensao getDimensao() {
		return dimensao;
	}

	public Dimensao getDimensaoAparencia() {
		return dimensaoAparencia;
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public Localizacao getLocalizacaoAparencia() {
		return localizacaoAparencia;
	}

	void inicializar() {
		linhas = new ArrayList<>();

		for (Instancia i : filhos) {
			i.inicializar();
		}
	}

	void definirDimensaoAltura() {
		if (!minimizado) {
			for (Instancia i : filhos) {
				i.definirDimensaoAltura();
			}
		}

		int altura = 0;

		if (!minimizado) {
			for (Instancia i : filhos) {
				altura += i.dimensao.getAltura();
			}
		}

		if (altura == 0) {
			altura = Constantes.ALTURA_PADRAO;
		}

		altura += alturaComplementar;
		altura += margemSuperior;
		altura += margemInferior;

		dimensao.setLarguraAltura(0, altura);
	}

	void definirDimensaoLargura(FontMetrics metrics) {
		int larguraObservacao = desenharObservacao ? metrics.stringWidth(getObservacao()) : 0;
		int larguraComentario = desenharComentario ? metrics.stringWidth(getComentario()) : 0;
		int larguraDescricao = metrics.stringWidth(getDescricao());

		int largura = larguraObservacao;

		if (larguraComentario > largura) {
			largura = larguraComentario;
		}

		if (larguraDescricao > largura) {
			largura = larguraDescricao;
		}

		if (Constantes.USAR_LARGURA_PADRAO) {
			largura = Constantes.LARGURA_PADRAO;
		}

		if (!estaVazio()) {
			largura += Constantes.LARGURA_MIN_MAX;
		}

		dimensao.setLarguraAltura(largura + 4, dimensao.getAltura());
		dimensaoAparencia.setLarguraAltura(largura + 4, Constantes.APARENCIA_ALTURA_PADRAO);

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.definirDimensaoLargura(metrics);
			}
		}
	}

	void definirLocalizacaoX() {
		int x = pai.localizacao.getX() + pai.dimensao.getLargura();
		localizacao.setXY(x, 0);

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.definirLocalizacaoX();
			}
		}
	}

	void definirLocalizacaoY(int acumulo) {
		acumulo += margemSuperior;
		if (!minimizado) {
			for (Instancia i : filhos) {
				i.setLocalizacao(i.localizacao.getX(), acumulo);
				acumulo += i.dimensao.getAltura();
				i.definirLocalizacaoY(i.localizacao.getY());
			}
		}
	}

	void definirLocalizacaoAfastamentoHorizontal() {
		int x = pai.localizacao.getX() + pai.dimensao.getLargura() + Constantes.LARGURA_AFASTAMENTO;
		setLocalizacao(x, localizacao.getY());

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.definirLocalizacaoAfastamentoHorizontal();
			}
		}
	}

	public void calcularLarguraTotal(AtomicInteger integer) {
		if (localizacao.getX() + dimensao.getLargura() > integer.get()) {
			integer.set(localizacao.getX() + dimensao.getLargura());
		}

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.calcularLarguraTotal(integer);
			}
		}
	}

	public void calcularLarguraTotal() {
		AtomicInteger integer = new AtomicInteger(0);

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.calcularLarguraTotalAux(0, integer);
			}
		}

		if (integer.get() > 0) {
			integer.addAndGet(Constantes.LARGURA_AFASTAMENTO);
		}

		larguraRetanguloTotal = dimensao.getLargura() + integer.get();
	}

	private void calcularLarguraTotalAux(int acumulo, AtomicInteger integer) {
		if (acumulo + dimensao.getLargura() > integer.get()) {
			integer.set(acumulo + dimensao.getLargura());
		}

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.calcularLarguraTotalAux(acumulo + dimensao.getLargura() + Constantes.LARGURA_AFASTAMENTO, integer);
			}
		}
	}

	public void calcularAlturaTotal(AtomicInteger integer) {
		if (localizacao.getY() + dimensao.getAltura() > integer.get()) {
			integer.set(localizacao.getY() + dimensao.getAltura());
		}

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.calcularAlturaTotal(integer);
			}
		}
	}

	void inverterLocalizacaoX() {
		setLocalizacao((localizacao.getX() + dimensao.getLargura()) * -1, localizacao.getY());
		setLocalizacaoAparencia((localizacaoAparencia.getX() + dimensaoAparencia.getLargura()) * -1,
				localizacaoAparencia.getY());

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.inverterLocalizacaoX();
			}
		}
	}

	void somarLocalizacaoX(int valor) {
		setLocalizacao(localizacao.getX() + valor, localizacao.getY());
		setLocalizacaoAparencia(localizacaoAparencia.getX() + valor, localizacaoAparencia.getY());

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.somarLocalizacaoX(valor);
			}
		}
	}

	void somarLocalizacaoY(int valor) {
		setLocalizacao(localizacao.getX(), localizacao.getY() + valor);
		setLocalizacaoAparencia(localizacaoAparencia.getX(), localizacaoAparencia.getY() + valor);

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.somarLocalizacaoY(valor);
			}
		}
	}

	void setLocalizacao(int x, int y) {
		localizacao.setXY(x, y);
	}

	void setLocalizacaoAparencia(int x, int y) {
		localizacaoAparencia.setXY(x, y);
	}

	void definirLocalizacaoAparenciaVertical(byte local) {
		this.local = local;

		if (braco) {
			local = pai.local;
		}

		if (Constantes.APARENCIA_ACIMA == local) {
			localizacaoAparencia.setXY(localizacao.getX(), localizacao.getY() + margemSuperior);

		} else if (Constantes.APARENCIA_MEIO == local) {

			int altura = dimensao.getAltura();
			altura -= margemSuperior;
			altura -= margemInferior;
			int y = (altura - dimensaoAparencia.getAltura()) / 2;
			localizacaoAparencia.setXY(localizacao.getX(), localizacao.getY() + y + margemSuperior);

		} else if (Constantes.APARENCIA_ABAIXO == local) {

			int y = (localizacao.getY() + dimensao.getAltura()) - dimensaoAparencia.getAltura();
			y -= margemInferior;
			localizacaoAparencia.setXY(localizacao.getX(), y);

		} else {
			throw new RuntimeException();
		}

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.definirLocalizacaoAparenciaVertical(local);
			}
		}
	}

	void criarLinhas() {
		if (esquerdo) {

			int x1 = localizacaoAparencia.getX();
			int y1 = localizacaoAparencia.getY() + dimensaoAparencia.getAltura() / 2;

			if (!minimizado) {
				for (Instancia i : filhos) {
					int x2 = i.localizacaoAparencia.getX() + i.dimensaoAparencia.getLargura();
					int y2 = i.localizacaoAparencia.getY() + i.dimensaoAparencia.getAltura() / 2;

					linhas.add(new Linha(x1 - 1, y1, x2 + 1, y2, true));
					i.criarLinhas();
				}
			}

		} else {

			int x1 = localizacaoAparencia.getX() + dimensaoAparencia.getLargura();
			int y1 = localizacaoAparencia.getY() + dimensaoAparencia.getAltura() / 2;

			if (!minimizado) {
				for (Instancia i : filhos) {
					int x2 = i.localizacaoAparencia.getX();
					int y2 = i.localizacaoAparencia.getY() + i.dimensaoAparencia.getAltura() / 2;

					linhas.add(new Linha(x1 + 1, y1, x2 - 1, y2));
					i.criarLinhas();
				}
			}

		}
	}

	public Instancia procurar(int x, int y) {
		if (x >= localizacaoAparencia.getX() && x <= localizacaoAparencia.getX() + dimensaoAparencia.getLargura()
				&& y >= localizacaoAparencia.getY()
				&& y <= localizacaoAparencia.getY() + dimensaoAparencia.getAltura()) {
			return this;
		}

		if (!minimizado) {
			for (Instancia i : filhos) {
				Instancia c = i.procurar(x, y);

				if (c != null) {
					return c;
				}
			}
		}

		return null;
	}

	public Instancia getClicadoNoIcone() {
		return this;
	}

	public boolean clicadoNoIcone(int x, int y) {
		if (estaVazio()) {
			return false;
		}

		int X = localizacaoAparencia.getX();
		int Y = localizacaoAparencia.getY();

		if (esquerdo) {
			return (x >= X && x <= X + Constantes.LARGURA_MIN_MAX) && (y >= Y + Constantes.MARGEM_MIN_MAX
					&& y <= Y + Constantes.MARGEM_MIN_MAX + Constantes.LARGURA_MIN_MAX);
		} else {
			int L = dimensaoAparencia.getLargura();
			X += L - Constantes.LARGURA_MIN_MAX;
			Y += Constantes.MARGEM_MIN_MAX;

			return (x >= X && x <= X + Constantes.LARGURA_MIN_MAX) && (y >= Y && y <= Y + Constantes.LARGURA_MIN_MAX);
		}
	}

	public boolean clicadoAreaIcone(int x, int y) {
		if (estaVazio()) {
			return false;
		}

		int X = localizacaoAparencia.getX();
		int Y = localizacaoAparencia.getY();
		int A = dimensaoAparencia.getAltura();

		if (!esquerdo) {
			int L = dimensaoAparencia.getLargura();
			X += L - Constantes.LARGURA_MIN_MAX;
		}

		return (x >= X && x <= X + Constantes.LARGURA_MIN_MAX) && (y >= Y && y <= Y + A);
	}

	public void desenhar(Graphics2D g2, Color corPadrao) {
		aparencia.desenhar(this, g2);

		if (!minimizado) {
			if (desenharAparencia) {
				for (Linha l : linhas) {
					l.desenhar(g2);
				}
			}

			for (Instancia i : filhos) {
				g2.setColor(corPadrao);
				i.desenhar(g2, corPadrao);
			}
		}
	}

	public void desenhar2(Graphics2D g2) {
		aparencia.desenhar(this, g2);
	}

	public void desenharIcone(Graphics2D g2) {
		aparencia.desenharIcone(this, g2);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		if (Util.estaVazio(descricao)) {
			descricao = ":)";
		}

		this.descricao = descricao;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public void setCorHierarquia(Color cor) {
		this.cor = cor;

		for (Instancia i : filhos) {
			i.setCorHierarquia(cor);
		}
	}

	public void minMaxTodos(boolean b) {
		minimizado = b;

		for (Instancia i : filhos) {
			i.minMaxTodos(b);
		}
	}

	public void podeMaximizar(AtomicBoolean atom) {
		if (minimizado) {
			minimizado = false;
			atom.set(true);
			return;
		}

		for (int j = 0; j < filhos.size(); j++) {
			Instancia i = filhos.get(j);
			if (i.minimizado) {
				i.minimizado = false;
				atom.set(true);
				return;
			}

			i.podeMaximizar(atom);
		}
	}

	public void podeMinimizar(AtomicBoolean atom) {
		if (atom.get() || filhos.isEmpty()) {
			return;
		}

		for (int j = filhos.size() - 1; j >= 0; j--) {
			Instancia i = filhos.get(j);
			i.podeMinimizar(atom);
			if (atom.get()) {
				return;
			}
		}

		if (atom.get()) {
			return;
		}

		if (!minimizado) {
			minimizado = true;
			atom.set(true);
		}
	}

	public boolean isMinimizado() {
		return minimizado;
	}

	public void setMinimizado(boolean minimizado) {
		this.minimizado = minimizado;
	}

	public void inverterMinMax() {
		minimizado = !minimizado;
	}

	public String getComentario() {
		if (comentario == null) {
			comentario = "";
		}

		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getObservacao() {
		if (observacao == null) {
			observacao = "";
		}

		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isDesenharComentario() {
		return desenharComentario;
	}

	public void setDesenharComentario(boolean desenharComentario) {
		this.desenharComentario = desenharComentario;
	}

	public boolean isDesenharObservacao() {
		return desenharObservacao;
	}

	public void setDesenharObservacao(boolean desenharObservacao) {
		this.desenharObservacao = desenharObservacao;
	}

	public boolean isDesenharDestacado() {
		return desenharDestacado;
	}

	public void setDesenharDestacado(boolean desenharDestacado) {
		this.desenharDestacado = desenharDestacado;
	}

	public Instancia getPonta() {
		if (estaVazio()) {
			return this;
		}

		return getFilho(getTamanho() - 1).getPonta();
	}

	public boolean isEsquerdo() {
		return esquerdo;
	}

	public void setEsquerdo(boolean esquerdo) {
		this.esquerdo = esquerdo;
	}

	public void imprimir(String tab, PrintWriter pw, boolean salvarLado) {
		ArquivoUtil.inicioTag(tab, this, pw, salvarLado);

		for (Instancia i : filhos) {
			i.imprimir(tab + "\t", pw, false);
		}

		ArquivoUtil.finalTag(tab, this, pw);
	}

	public int getAlturaComplementar() {
		return alturaComplementar;
	}

	public void setAlturaComplementar(int alturaComplementar) {
		this.alturaComplementar = alturaComplementar;
	}

	public int getLarguraRetanguloTotal() {
		return larguraRetanguloTotal;
	}

	public void setLarguraRetanguloTotal(int larguraRetanguloTotal) {
		this.larguraRetanguloTotal = larguraRetanguloTotal;
	}

	public void importar(String[] grafo) {
		if (grafo == null) {
			return;
		}

		Instancia sel = this;

		for (int i = 0; i < grafo.length; i++) {
			String string = grafo[i];

			if (Util.estaVazio(string)) {
				break;
			}

			Instancia obj = sel.getFilho(string);

			if (obj == null) {
				obj = new Instancia(string);
				sel.adicionar(obj);
			}

			sel = obj;
		}
	}

	public boolean isDesenharAparencia() {
		return desenharAparencia;
	}

	public void setDesenharAparencia(boolean desenharAparencia) {
		this.desenharAparencia = desenharAparencia;
	}

	public int getMargemSuperior() {
		return margemSuperior;
	}

	public void setMargemSuperior(int margemSuperior) {
		this.margemSuperior = margemSuperior;
	}

	public int getMargemInferior() {
		return margemInferior;
	}

	public void setMargemInferior(int margemInferior) {
		this.margemInferior = margemInferior;
	}

	public boolean isMarcado() {
		return marcado;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

	@Override
	public String toString() {
		return descricao;
	}
}