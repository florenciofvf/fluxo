package br.com.florencio.fluxo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import br.com.florencio.fluxo.util.Constantes;

public class InstanciaAparenciaRaiz extends InstanciaAparencia {

	public void desenhar(Instancia i, Graphics2D g2) {
		final boolean contemComentario = i.getComentario().length() > 0;
		final boolean contemObservacao = i.getObservacao().length() > 0;
		byte raio = 10;

		g2.setColor(i.cor);

		int x = i.localizacaoAparencia.getX();
		int y = i.localizacaoAparencia.getY();
		int l = i.dimensaoAparencia.getLargura();
		int a = i.dimensaoAparencia.getAltura();
		final int raioRaiz = (int) (l * .80);
		int m = l / 2 - 10;

		if (Constantes.DESENHAR_LIMITE) {
			g2.setColor(Constantes.COR_LIMITE);
			g2.drawRect(i.localizacao.getX(), i.localizacao.getY(), i.dimensao.getLargura(), i.dimensao.getAltura());
		}

		g2.setColor(i.cor);

		InstanciaRaiz raiz = (InstanciaRaiz) i;

		if (raiz.raizEsquerda.estaVazio() && raiz.raizDireita.estaVazio()) {
			g2.drawRoundRect(x, y - m, l, l, raioRaiz, raioRaiz);

			if (i.cor != null) {
				if (i.desenharAparencia) {
					g2.fillRoundRect(x, y, l, a, raio, raio);
					g2.setColor(Constantes.COR_BORDA);
					g2.drawRoundRect(x, y, l, a, raio, raio);
					g2.setColor(i.cor);
				}
			} else {
				if (i.desenharAparencia) {
					g2.drawRoundRect(x, y, l, a, raio, raio);
				}
			}

			if (i.cor != null) {
				g2.setColor(Color.WHITE);
			}

			if (Constantes.DESENHAR_FONTE) {
				g2.drawString(i.descricao, x + 3, y + Constantes.ALTURA_FONTE);
			}

			g2.setColor(i.cor);

			if (contemObservacao && Constantes.DESENHAR_FONTE) {
				if (i.desenharObservacao) {
					g2.drawString(i.observacao, x + 3, y - 2);
				} else {
					if (i.cor != null) {
						g2.setColor(Constantes.COR_BORDA);
					}
					g2.fillRect(x, y, Constantes.TAMANHO_SINAL_ICONE_COMENTARIO,
							Constantes.TAMANHO_SINAL_ICONE_COMENTARIO);
				}
			}

			g2.setColor(i.cor);

			if (contemComentario && Constantes.DESENHAR_FONTE) {
				if (i.desenharComentario) {
					g2.drawString(i.comentario, x + 3, y + Constantes.ALTURA_FONTE + Constantes.ALTURA_FONTE + 3);
				} else {
					if (i.cor != null) {
						g2.setColor(Constantes.COR_BORDA);
					}
					g2.fillRect(x, y + a - 3, Constantes.TAMANHO_SINAL_ICONE_COMENTARIO,
							Constantes.TAMANHO_SINAL_ICONE_COMENTARIO);
				}
			}

		} else if (!raiz.raizEsquerda.estaVazio() && raiz.raizDireita.estaVazio()) {
			g2.drawRoundRect(x + Constantes.LARGURA_MIN_MAX, y - m, l - Constantes.LARGURA_MIN_MAX, l, raioRaiz,
					raioRaiz);

			boolean braco = raiz.raizEsquerda.braco;
			raiz.raizEsquerda.setDesenharObservacao(i.desenharObservacao);
			raiz.raizEsquerda.setObservacao(i.observacao);
			raiz.raizEsquerda.setDesenharComentario(i.desenharComentario);
			raiz.raizEsquerda.setComentario(i.comentario);
			raiz.raizEsquerda.desenharAparencia = i.desenharAparencia;
			raiz.raizEsquerda.braco = false;
			raiz.raizEsquerda.desenhar2(g2);
			raiz.raizEsquerda.braco = braco;
		} else if (raiz.raizEsquerda.estaVazio() && !raiz.raizDireita.estaVazio()) {
			g2.drawRoundRect(x, y - m, l - Constantes.LARGURA_MIN_MAX, l, raioRaiz, raioRaiz);

			boolean braco = raiz.raizDireita.braco;
			raiz.raizDireita.setDesenharObservacao(i.desenharObservacao);
			raiz.raizDireita.setObservacao(i.observacao);
			raiz.raizDireita.setDesenharComentario(i.desenharComentario);
			raiz.raizDireita.setComentario(i.comentario);
			raiz.raizDireita.desenharAparencia = i.desenharAparencia;
			raiz.raizDireita.braco = false;
			raiz.raizDireita.desenhar2(g2);
			raiz.raizDireita.braco = braco;
		} else {
			g2.drawRoundRect(x + Constantes.LARGURA_MIN_MAX, y - m, l - Constantes.LARGURA_MIN_MAX * 2, l, raioRaiz,
					raioRaiz);

			l -= Constantes.LARGURA_MIN_MAX * 2;
			x += Constantes.LARGURA_MIN_MAX;

			if (i.cor != null) {
				if (i.desenharAparencia) {
					g2.fillRoundRect(x, y, l, a, raio, raio);
					g2.setColor(Constantes.COR_BORDA);
					g2.drawRoundRect(x, y, l, a, raio, raio);
					g2.setColor(i.cor);
				}
			} else {
				if (i.desenharAparencia) {
					g2.drawRoundRect(x, y, l, a, raio, raio);
				}
			}

			if (i.cor != null) {
				g2.setColor(Color.WHITE);
			}

			if (Constantes.DESENHAR_FONTE) {
				g2.drawString(i.descricao, x + 3, y + Constantes.ALTURA_FONTE);
			}

			g2.setColor(i.cor);

			if (contemObservacao && Constantes.DESENHAR_FONTE) {
				if (i.desenharObservacao) {
					g2.drawString(i.observacao, x + 3, y - 2);
				} else {
					if (i.cor != null) {
						g2.setColor(Constantes.COR_BORDA);
					}
					g2.fillRect(x, y, Constantes.TAMANHO_SINAL_ICONE_COMENTARIO,
							Constantes.TAMANHO_SINAL_ICONE_COMENTARIO);
				}
			}

			g2.setColor(i.cor);

			if (contemComentario && Constantes.DESENHAR_FONTE) {
				if (i.desenharComentario) {
					g2.drawString(i.comentario, x + 3, y + Constantes.ALTURA_FONTE + Constantes.ALTURA_FONTE + 3);
				} else {
					if (i.cor != null) {
						g2.setColor(Constantes.COR_BORDA);
					}
					g2.fillRect(x, y + a - 3, Constantes.TAMANHO_SINAL_ICONE_COMENTARIO,
							Constantes.TAMANHO_SINAL_ICONE_COMENTARIO);
				}
			}

			g2.setColor(i.cor);

			raiz.raizEsquerda.desenharIcone(g2);
			raiz.raizDireita.desenharIcone(g2);
		}

		if (i.selecionado || i.marcado) {
			raio += 2;
			Stroke stroke = g2.getStroke();
			g2.setStroke(Constantes.STROKE_SELECIONADO);
			g2.setColor(Constantes.COR_SELECIONADO);
			if (raiz.raizEsquerda.estaVazio() ^ raiz.raizDireita.estaVazio()) {
				if (raiz.raizEsquerda.estaVazio()) {
					g2.drawRoundRect(x - 2, y - 2, l + 4, a + 4, raio, raio);
				} else {
					g2.drawRoundRect(x - 2, y - 2, l + 4, a + 4, raio, raio);
				}
			} else {
				if (raiz.raizEsquerda.estaVazio() && raiz.raizDireita.estaVazio()) {
					g2.drawRoundRect(x - 2, y - 2, l + 4, a + 4, raio, raio);
				} else {
					g2.drawRoundRect(x - 2 - Constantes.LARGURA_MIN_MAX, y - 2, l + Constantes.LARGURA_MIN_MAX * 2 + 4,
							a + 4, raio, raio);
				}
			}
			g2.setStroke(stroke);
		}

		g2.setColor(i.cor);
	}
}