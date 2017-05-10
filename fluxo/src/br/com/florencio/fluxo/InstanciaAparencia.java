package br.com.florencio.fluxo;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.florencio.fluxo.util.Constantes;

public class InstanciaAparencia {

	public void desenhar(Instancia i, Graphics2D g2) {
		final boolean contemComentario = i.getComentario().length() > 0;
		final boolean naoEstaVazio = !i.estaVazio();
		final byte raio = 8;

		g2.setColor(i.cor);

		if (i.braco) {
			return;
		}

		int x = i.localizacaoAparencia.getX();
		int y = i.localizacaoAparencia.getY();
		int l = i.dimensaoAparencia.getLargura();
		int a = i.dimensaoAparencia.getAltura();
		final byte auxIcone = 2;
		final byte auxIcone2 = 4;

		if (naoEstaVazio) {
			l -= Constantes.LARGURA_MIN_MAX;
			if (i.esquerdo) {
				x += Constantes.LARGURA_MIN_MAX;
			}
		}

		if (Constantes.DESENHAR_RETANGULO_PADRAO) {
			g2.drawRect(i.localizacao.getX(), i.localizacao.getY(), l, i.dimensao.getAltura());
		}

		if (i.cor != null) {
			g2.fillRoundRect(x, y, l, a, raio, raio);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(x, y, l, a, raio, raio);
			g2.setColor(i.cor);

			if (naoEstaVazio) {
				if (i.esquerdo) {
					g2.fillOval(x - Constantes.LARGURA_MIN_MAX, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX, Constantes.LARGURA_MIN_MAX);
					g2.setColor(Color.BLACK);
					g2.drawOval(x - Constantes.LARGURA_MIN_MAX, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX, Constantes.LARGURA_MIN_MAX);
					if (i.minimizado) {
						g2.drawRect(x - Constantes.LARGURA_MIN_MAX + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA, Constantes.LARGURA_MIN_MAX - auxIcone2, 0);
						g2.drawRect(x - Constantes.LARGURA_MIN_MAX + Constantes.METADE_MIN_MAX, y + Constantes.MARGEM_MIN_MAX + auxIcone, 0, Constantes.LARGURA_MIN_MAX - auxIcone2);
					} else {
						g2.drawRect(x - Constantes.LARGURA_MIN_MAX + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA, Constantes.LARGURA_MIN_MAX - auxIcone2, 0);
					}
				} else {
					g2.fillOval(x + l, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX, Constantes.LARGURA_MIN_MAX);
					g2.setColor(Color.BLACK);
					g2.drawOval(x + l, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX, Constantes.LARGURA_MIN_MAX);
					if (i.minimizado) {
						g2.drawRect(x + l + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA, Constantes.LARGURA_MIN_MAX - auxIcone2, 0);
						g2.drawRect(x + l + Constantes.METADE_MIN_MAX, y + Constantes.MARGEM_MIN_MAX + auxIcone, 0, Constantes.LARGURA_MIN_MAX - auxIcone2);
					} else {
						g2.drawRect(x + l + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA, Constantes.LARGURA_MIN_MAX - auxIcone2, 0);
					}
				}
			}
		} else {
			g2.drawRoundRect(x, y, l, a, raio, raio);
			if (naoEstaVazio) {
				if (i.esquerdo) {
					g2.drawOval(x - Constantes.LARGURA_MIN_MAX, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX, Constantes.LARGURA_MIN_MAX);
					if (i.minimizado) {
						g2.drawRect(x - Constantes.LARGURA_MIN_MAX + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA, Constantes.LARGURA_MIN_MAX - auxIcone2, 0);
						g2.drawRect(x - Constantes.LARGURA_MIN_MAX + Constantes.METADE_MIN_MAX, y + Constantes.MARGEM_MIN_MAX + auxIcone, 0, Constantes.LARGURA_MIN_MAX - auxIcone2);
					} else {
						g2.drawRect(x - Constantes.LARGURA_MIN_MAX + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA, Constantes.LARGURA_MIN_MAX - auxIcone2, 0);
					}
				} else {
					g2.drawOval(x + l, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX, Constantes.LARGURA_MIN_MAX);
					if (i.minimizado) {
						g2.drawRect(x + l + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA, Constantes.LARGURA_MIN_MAX - auxIcone2, 0);
						g2.drawRect(x + l + Constantes.METADE_MIN_MAX, y + Constantes.MARGEM_MIN_MAX + auxIcone, 0, Constantes.LARGURA_MIN_MAX - auxIcone2);
					} else {
						g2.drawRect(x + l + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA, Constantes.LARGURA_MIN_MAX - auxIcone2, 0);
					}
				}
			}
		}

		if (i.cor != null) {
			g2.setColor(Color.WHITE);
		}

		g2.drawString(i.descricao, x + 3, y + Constantes.ALTURA_FONTE);

		g2.setColor(i.cor);

		if (contemComentario) {
			if (i.desenharComentario) {
				g2.drawString(i.comentario, x + 3, y + Constantes.ALTURA_FONTE + Constantes.ALTURA_FONTE + 3);
			} else {
				g2.fillOval(x, y, Constantes.TAMANHO_SINAL_ICONE_COMENTARIO, Constantes.TAMANHO_SINAL_ICONE_COMENTARIO);
			}
		}
	}

}