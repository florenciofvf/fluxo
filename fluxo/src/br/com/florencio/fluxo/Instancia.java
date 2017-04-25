package br.com.florencio.fluxo;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Instancia {
	private boolean desenharComentario;
	private List<Instancia> filhos;
	private boolean minimizado;
	private Dimensao dimensao;
	private List<Linha> linhas;
	private String comentario;
	private String descricao;
	private Instancia pai;
	int margemInferior;
	private Local local;
	private Color cor;

	public Instancia(String descricao) {
		this.descricao = Arquivo.semSufixo(descricao);
		filhos = new ArrayList<>();
		linhas = new ArrayList<>();
		dimensao = new Dimensao();
		local = new Local();
	}

	public void adicionar(Instancia i) {
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
		filhos.add(i);
	}

	public void sairDaHierarquia() {
		if (pai == null) {
			return;
		}

		List<Instancia> lista = new ArrayList<>();

		for (Instancia i : filhos) {
			Instancia o = i.clonar();
			lista.add(o);
		}

		for (Instancia i : lista) {
			pai.adicionar(i);
		}

		pai.excluir(this);
	}

	public void controlarMargemInferior() {
		if (pai != null && pai.getTamanho() < 2) {
			if (margemInferior == Dimensao.MARGEM_INFERIOR) {
				margemInferior = 0;
			}
		}

		if (filhos.size() > 1) {
			for (Instancia obj : filhos) {
				if (obj.margemInferior == 0) {
					obj.margemInferior = Dimensao.MARGEM_INFERIOR;
				}
			}
		}

		for (Instancia obj : filhos) {
			obj.controlarMargemInferior();
		}
	}

	public void primeiro(Instancia i) {
		if (i == null || i.pai != this) {
			return;
		}

		int pos = filhos.indexOf(i);

		if (pos > 0) {
			filhos.remove(pos);
			filhos.add(0, i);
		}
	}

	public void subir(Instancia i) {
		if (i == null || i.pai != this) {
			return;
		}

		int pos = filhos.indexOf(i);

		if (pos > 0) {
			Instancia objAnterior = filhos.get(pos - 1);
			filhos.set(pos, objAnterior);
			filhos.set(pos - 1, i);
		}
	}

	public void descer(Instancia i) {
		if (i == null || i.pai != this) {
			return;
		}

		int pos = filhos.indexOf(i);

		if (pos < filhos.size() - 1) {
			Instancia objPosterior = filhos.get(pos + 1);
			filhos.set(pos, objPosterior);
			filhos.set(pos + 1, i);
		}
	}

	public void ultimo(Instancia i) {
		if (i == null || i.pai != this) {
			return;
		}

		int pos = filhos.indexOf(i);

		if (pos < filhos.size() - 1) {
			filhos.remove(pos);
			filhos.add(i);
		}
	}

	public void excluir(Instancia i) {
		if (i.pai != this) {
			return;
		}

		i.pai = null;
		filhos.remove(i);
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

	public Instancia getFilho(String descricao) {
		if (descricao == null || descricao.trim().length() == 0) {
			return null;
		}

		for (Instancia i : filhos) {
			if (descricao.equals(i.descricao)) {
				return i;
			}
		}

		return null;
	}

	public void importar(String[] grafo) {
		if (grafo == null) {
			return;
		}

		Instancia sel = this;

		for (int i = 0; i < grafo.length; i++) {
			String string = grafo[i];

			if (string == null || string.trim().length() == 0) {
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

	public int getTamanho() {
		return filhos.size();
	}

	public boolean isVazio() {
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

	public Local getLocal() {
		return local;
	}

	public void desenhar(Graphics2D g2) {
		int largura = dimensao.largura - Dimensao.TAMANHO_ICONE;

		if (cor != null) {
			Color c = g2.getColor();

			g2.setColor(cor);
			g2.fillRoundRect(local.x, local.y, largura, dimensao.altura, 8, 8);

			g2.setColor(Color.BLACK);
			g2.drawRoundRect(local.x, local.y, largura, dimensao.altura, 8, 8);

			if (getComentario().length() > 0) {
				g2.fillOval(local.x, local.y, Dimensao.TAMANHO_ICONE_COMENTARIO, Dimensao.TAMANHO_ICONE_COMENTARIO);
				if (desenharComentario) {
					g2.drawString(comentario, local.x + 2, local.y + 15 + Dimensao.MARGEM_PARA_COMENTARIO - 3);
				}
			}

			if (filhos.size() > 0) {
				g2.setColor(cor);
				g2.fillOval(local.x + largura, local.y + Dimensao.MARGEM_ICONE, Dimensao.TAMANHO_ICONE,
						Dimensao.TAMANHO_ICONE);
				g2.setColor(Color.BLACK);
				g2.drawOval(local.x + largura, local.y + Dimensao.MARGEM_ICONE, Dimensao.TAMANHO_ICONE,
						Dimensao.TAMANHO_ICONE);

				g2.setColor(Color.BLACK);
				if (minimizado) {
					g2.drawLine(local.x + largura + Dimensao.METADE_ICONE, local.y + Dimensao.MARGEM_ICONE + 3,
							local.x + largura + Dimensao.METADE_ICONE,
							local.y + Dimensao.MARGEM_ICONE + Dimensao.TAMANHO_ICONE - 3);
					g2.drawLine(local.x + largura + 3, local.y + Dimensao.MARGEM_ICONE + Dimensao.METADE_ICONE,
							local.x + largura + Dimensao.TAMANHO_ICONE - 3,
							local.y + Dimensao.MARGEM_ICONE + Dimensao.METADE_ICONE);
				} else {
					g2.drawLine(local.x + largura + 3, local.y + Dimensao.MARGEM_ICONE + Dimensao.METADE_ICONE,
							local.x + largura + Dimensao.TAMANHO_ICONE - 3,
							local.y + Dimensao.MARGEM_ICONE + Dimensao.METADE_ICONE);
				}
			}

			g2.setColor(Color.WHITE);
			g2.drawString(descricao, local.x + 2, local.y + 15);

			if (!minimizado) {
				g2.setColor(cor);
				for (Instancia i : filhos) {
					i.desenhar(g2);
				}

				g2.setColor(cor);
				for (Linha l : linhas) {
					l.desenhar(g2);
				}
			}

			g2.setColor(c);
		} else {
			g2.drawRoundRect(local.x, local.y, largura, dimensao.altura, 8, 8);
			if (getComentario().length() > 0) {
				g2.fillOval(local.x, local.y, Dimensao.TAMANHO_ICONE_COMENTARIO, Dimensao.TAMANHO_ICONE_COMENTARIO);
				if (desenharComentario) {
					g2.drawString(comentario, local.x + 2, local.y + 15 + Dimensao.MARGEM_PARA_COMENTARIO - 3);
				}
			}

			if (filhos.size() > 0) {
				g2.drawOval(local.x + largura, local.y + Dimensao.MARGEM_ICONE, Dimensao.TAMANHO_ICONE,
						Dimensao.TAMANHO_ICONE);
				if (minimizado) {
					g2.drawLine(local.x + largura + Dimensao.METADE_ICONE, local.y + Dimensao.MARGEM_ICONE + 3,
							local.x + largura + Dimensao.METADE_ICONE,
							local.y + Dimensao.MARGEM_ICONE + Dimensao.TAMANHO_ICONE - 3);
					g2.drawLine(local.x + largura + 3, local.y + Dimensao.MARGEM_ICONE + Dimensao.METADE_ICONE,
							local.x + largura + Dimensao.TAMANHO_ICONE - 3,
							local.y + Dimensao.MARGEM_ICONE + Dimensao.METADE_ICONE);
				} else {
					g2.drawLine(local.x + largura + 3, local.y + Dimensao.MARGEM_ICONE + Dimensao.METADE_ICONE,
							local.x + largura + Dimensao.TAMANHO_ICONE - 3,
							local.y + Dimensao.MARGEM_ICONE + Dimensao.METADE_ICONE);
				}
			}

			g2.drawString(descricao, local.x + 2, local.y + 15);

			if (!minimizado) {
				for (Instancia i : filhos) {
					i.desenhar(g2);
				}

				for (Linha l : linhas) {
					l.desenhar(g2);
				}
			}
		}
	}

	public boolean clicadoNoIcone(int x, int y) {
		int X = local.x + dimensao.largura - Dimensao.TAMANHO_ICONE;
		int Y = local.y + Dimensao.MARGEM_ICONE;
		return (x >= X && x <= X + Dimensao.TAMANHO_ICONE) && (y >= Y && y <= Y + Dimensao.TAMANHO_ICONE);
	}

	boolean duploClickValido(int x) {
		return x < local.x + dimensao.largura - Dimensao.TAMANHO_ICONE;
	}

	public void organizar(FontMetrics metrics) {
		Dimensao.larguraTotal = 0;
		Dimensao.alturaTotal = 0;
		inicializar();
		calcularAltura();
		Dimensao.alturaTotal = dimensao.altura;
		calcularLargura(metrics);
		calcularX();
		calcularY(local.y);
		centralizarY();
		afastar(0);
		calcularLarguraTotal();
		criarLinhas();
	}

	private void inicializar() {
		linhas = new ArrayList<>();
		dimensao = new Dimensao();
		local = new Local();

		for (Instancia i : filhos) {
			i.inicializar();
		}
	}

	public int calcularAltura() {
		int total = 0;

		if (!minimizado) {
			for (Instancia i : filhos) {
				total += i.calcularAltura();
			}
		}

		dimensao.altura = total;

		if (dimensao.altura == 0) {
			dimensao.altura = Dimensao.ALTURA_PADRAO;
		}

		dimensao.altura += margemInferior;

		if (desenharComentario && getComentario().length() > 0 && filhos.isEmpty()) {
			dimensao.altura += Dimensao.MARGEM_PARA_COMENTARIO;
		}

		return dimensao.altura;
	}

	public void calcularLargura(FontMetrics metrics) {
		int larguraComentario = metrics.stringWidth(getComentario()) + 3 + Dimensao.TAMANHO_ICONE;
		dimensao.largura = metrics.stringWidth(descricao) + 3 + Dimensao.TAMANHO_ICONE;

		if (desenharComentario) {
			dimensao.largura = Math.max(dimensao.largura, larguraComentario);
		}

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.calcularLargura(metrics);
			}
		}
	}

	public void calcularX() {
		local.x = pai == null ? 0 : pai.local.x + pai.dimensao.largura;

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.calcularX();
			}
		}
	}

	public void calcularY(int yPai) {
		if (!minimizado) {
			for (Instancia i : filhos) {
				i.local.y = yPai;
				yPai += i.dimensao.altura;
				i.calcularY(i.local.y);
			}
		}
	}

	public void centralizarY() {
		local.y += (dimensao.altura - margemInferior - Dimensao.ALTURA_PADRAO) / 2;
		local.y += local.yTop;
		dimensao.altura = Dimensao.ALTURA_PADRAO;

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.centralizarY();
			}
		}
	}

	private void afastar(int acumulado) {
		int totalAcumulado = acumulado + Dimensao.MARGEM_DIREITA;
		local.x += totalAcumulado;

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.afastar(totalAcumulado);
			}
		}
	}

	private void calcularLarguraTotal() {
		if (local.x + dimensao.largura > Dimensao.larguraTotal) {
			Dimensao.larguraTotal = local.x + dimensao.largura;
		}

		if (!minimizado) {
			for (Instancia i : filhos) {
				i.calcularLarguraTotal();
			}
		}
	}

	private void criarLinhas() {
		int x1 = local.x + dimensao.largura;
		int y1 = local.y + dimensao.altura / 2;

		if (!minimizado) {
			for (Instancia i : filhos) {
				int x2 = i.local.x;
				int y2 = i.local.y + i.dimensao.altura / 2;

				linhas.add(new Linha(x1, y1, x2, y2));
				i.criarLinhas();
			}
		}
	}

	public Instancia procurar(int x, int y) {
		if (x >= local.x && y >= local.y && x <= local.x + dimensao.largura && y <= local.y + dimensao.altura) {
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

	public Instancia clonar() {
		Instancia obj = new Instancia(descricao);
		obj.cor = cor;

		for (Instancia i : filhos) {
			Instancia o = i.clonar();
			obj.adicionar(o);
		}

		return obj;
	}

	public String getDescricao() {
		return descricao;
	}

	public void imprimir(PrintWriter pw) {
		imprimir("", pw);
	}

	public void imprimir(String tab, PrintWriter pw) {
		tab += pai != null ? "\t" : "";

		Arquivo.inicioTag(tab, this, pw);

		for (Instancia i : filhos) {
			i.imprimir(tab, pw);
		}

		Arquivo.finalTag(tab, this, pw);
	}

	public void setDescricao(String descricao) {
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

	public boolean isMinimizado() {
		return minimizado;
	}

	public void setMinimizado(boolean minimizado) {
		this.minimizado = minimizado;
	}

	public void inverterIcone() {
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

	public boolean isDesenharComentario() {
		return desenharComentario;
	}

	public void setDesenharComentario(boolean desenharComentario) {
		this.desenharComentario = desenharComentario;
	}

	public int getMargemInferior() {
		return margemInferior;
	}
}