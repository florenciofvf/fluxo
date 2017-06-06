package br.com.florencio.fluxo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import br.com.florencio.fluxo.util.Constantes;

public class InstanciaAparencia {

	public void desenhar(Instancia i, Graphics2D g2) {
		final boolean contemComentario = i.getComentario().length() > 0;
		final boolean contemObservacao = i.getObservacao().length() > 0;
		final boolean naoEstaVazio = !i.estaVazio();
		byte raio = 10;

		g2.setColor(i.cor);

		int dimensaoAltura = i.dimensao.getAltura();
		dimensaoAltura -= i.margemSuperior;
		dimensaoAltura -= i.margemInferior;

		if (i.braco) {
			if (i.desenharDestacado) {
				if (i.esquerdo) {
					int l = i.dimensaoAparencia.getLargura();
					g2.fillRoundRect(i.localizacao.getX() - (i.larguraRetanguloTotal - l),
							i.localizacao.getY() + i.margemSuperior, i.larguraRetanguloTotal, dimensaoAltura, raio,
							raio);

					g2.setColor(Constantes.COR_BORDA);
					g2.drawRoundRect(i.localizacao.getX() - (i.larguraRetanguloTotal - l),
							i.localizacao.getY() + i.margemSuperior, i.larguraRetanguloTotal, dimensaoAltura, raio,
							raio);

				} else {
					g2.fillRoundRect(i.localizacao.getX(), i.localizacao.getY() + i.margemSuperior,
							i.larguraRetanguloTotal, dimensaoAltura, raio, raio);
					g2.setColor(Constantes.COR_BORDA);
					g2.drawRoundRect(i.localizacao.getX(), i.localizacao.getY() + i.margemSuperior,
							i.larguraRetanguloTotal, dimensaoAltura, raio, raio);
				}
			}
			return;
		}

		int x = i.localizacaoAparencia.getX();
		int y = i.localizacaoAparencia.getY();
		int l = i.dimensaoAparencia.getLargura();
		int a = i.dimensaoAparencia.getAltura();

		if (i.desenharDestacado) {
			if (i.esquerdo) {
				g2.fillRoundRect(i.localizacao.getX() - (i.larguraRetanguloTotal - l),
						i.localizacao.getY() + i.margemSuperior, i.larguraRetanguloTotal, dimensaoAltura, raio, raio);
				g2.setColor(Constantes.COR_BORDA);
				g2.drawRoundRect(i.localizacao.getX() - (i.larguraRetanguloTotal - l),
						i.localizacao.getY() + i.margemSuperior, i.larguraRetanguloTotal, dimensaoAltura, raio, raio);
			} else {
				g2.fillRoundRect(i.localizacao.getX(), i.localizacao.getY() + i.margemSuperior, i.larguraRetanguloTotal,
						dimensaoAltura, raio, raio);
				g2.setColor(Constantes.COR_BORDA);
				g2.drawRoundRect(i.localizacao.getX(), i.localizacao.getY() + i.margemSuperior, i.larguraRetanguloTotal,
						dimensaoAltura, raio, raio);
			}
		}

		g2.setColor(i.cor);

		if (naoEstaVazio) {
			l -= Constantes.LARGURA_MIN_MAX;
			if (i.esquerdo) {
				x += Constantes.LARGURA_MIN_MAX;
			}
		}

		if (Constantes.DESENHAR_LIMITE) {
			g2.setColor(Constantes.COR_LIMITE);
			g2.drawRect(i.localizacao.getX(), i.localizacao.getY(), i.dimensao.getLargura(), i.dimensao.getAltura());
		}

		g2.setColor(i.cor);

		if (i.cor != null) {
			if (i.desenharAparencia) {
				g2.fillRoundRect(x, y, l, a, raio, raio);
				g2.setColor(Constantes.COR_BORDA);
				g2.drawRoundRect(x, y, l, a, raio, raio);
				g2.setColor(i.cor);
				desenharIcone(i, g2);
			}
		} else {
			if (i.desenharAparencia) {
				g2.drawRoundRect(x, y, l, a, raio, raio);
				desenharIcone(i, g2);
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
				g2.fillRect(x, y, Constantes.TAMANHO_SINAL_ICONE_COMENTARIO, Constantes.TAMANHO_SINAL_ICONE_COMENTARIO);
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

		if (i.selecionado || i.marcado) {
			raio += 2;
			Stroke stroke = g2.getStroke();
			g2.setStroke(Constantes.STROKE);
			g2.setColor(Constantes.COR_SELECIONADO);
			if (naoEstaVazio) {
				if (i.esquerdo) {
					g2.drawRoundRect(x - 2 - Constantes.LARGURA_MIN_MAX, y - 2, l + Constantes.LARGURA_MIN_MAX + 4,
							a + 4, raio, raio);
				} else {
					g2.drawRoundRect(x - 2, y - 2, l + Constantes.LARGURA_MIN_MAX + 4, a + 4, raio, raio);
				}
			} else {
				g2.drawRoundRect(x - 2, y - 2, l + 4, a + 4, raio, raio);
			}
			g2.setStroke(stroke);
		}

		g2.setColor(i.cor);
	}

	public void desenharIcone(Instancia i, Graphics2D g2) {
		final boolean naoEstaVazio = !i.estaVazio();

		g2.setColor(i.cor);

		int x = i.localizacaoAparencia.getX();
		int y = i.localizacaoAparencia.getY();
		int l = i.dimensaoAparencia.getLargura();
		final byte auxIcone = 2;
		final byte auxIcone2 = 4;

		if (naoEstaVazio) {
			l -= Constantes.LARGURA_MIN_MAX;
			if (i.esquerdo) {
				x += Constantes.LARGURA_MIN_MAX;
			}
		}

		if (i.cor != null) {
			if (naoEstaVazio) {
				if (i.esquerdo) {
					iconeLadoEsquerdo(g2, x, y, true);
					if (i.minimizado) {
						sinalMaisLadoEsquerdo(g2, x, y, auxIcone, auxIcone2, true);
					} else {
						sinalMaisLadoEsquerdo(g2, x, y, auxIcone, auxIcone2, false);
					}
				} else {
					iconeLadoDireito(g2, x, y, l, true);
					if (i.minimizado) {
						sinalMaisLadoDireito(g2, x, y, l, auxIcone, auxIcone2, true);
					} else {
						sinalMaisLadoDireito(g2, x, y, l, auxIcone, auxIcone2, false);
					}
				}
			}
		} else {
			if (naoEstaVazio) {
				if (i.esquerdo) {
					iconeLadoEsquerdo(g2, x, y, false);
					if (i.minimizado) {
						sinalMaisLadoEsquerdo(g2, x, y, auxIcone, auxIcone2, true);
					} else {
						sinalMaisLadoEsquerdo(g2, x, y, auxIcone, auxIcone2, false);
					}
				} else {
					iconeLadoDireito(g2, x, y, l, false);
					if (i.minimizado) {
						sinalMaisLadoDireito(g2, x, y, l, auxIcone, auxIcone2, true);
					} else {
						sinalMaisLadoDireito(g2, x, y, l, auxIcone, auxIcone2, false);
					}
				}
			}
		}
	}

	protected void iconeLadoDireito(Graphics2D g2, int x, int y, int l, final boolean colorido) {
		if (colorido) {
			g2.fillOval(x + l, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX, Constantes.LARGURA_MIN_MAX);
			g2.setColor(Constantes.COR_BORDA);
		}
		g2.drawOval(x + l, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX, Constantes.LARGURA_MIN_MAX);
	}

	protected void iconeLadoEsquerdo(Graphics2D g2, int x, int y, final boolean colorido) {
		if (colorido) {
			g2.fillOval(x - Constantes.LARGURA_MIN_MAX, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX,
					Constantes.LARGURA_MIN_MAX);
			g2.setColor(Constantes.COR_BORDA);
		}
		g2.drawOval(x - Constantes.LARGURA_MIN_MAX, y + Constantes.MARGEM_MIN_MAX, Constantes.LARGURA_MIN_MAX,
				Constantes.LARGURA_MIN_MAX);
	}

	protected void sinalMaisLadoDireito(Graphics2D g2, int x, int y, int l, final byte auxIcone, final byte auxIcone2,
			final boolean vertical) {
		g2.drawRect(x + l + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA, Constantes.LARGURA_MIN_MAX - auxIcone2,
				0);
		if (vertical) {
			g2.drawRect(x + l + Constantes.METADE_MIN_MAX, y + Constantes.MARGEM_MIN_MAX + auxIcone, 0,
					Constantes.LARGURA_MIN_MAX - auxIcone2);
		}
	}

	protected void sinalMaisLadoEsquerdo(Graphics2D g2, int x, int y, final byte auxIcone, final byte auxIcone2,
			final boolean vertical) {
		g2.drawRect(x - Constantes.LARGURA_MIN_MAX + auxIcone, y + Constantes.METADE_APARENCIA_ALTURA,
				Constantes.LARGURA_MIN_MAX - auxIcone2, 0);
		if (vertical) {
			g2.drawRect(x - Constantes.LARGURA_MIN_MAX + Constantes.METADE_MIN_MAX,
					y + Constantes.MARGEM_MIN_MAX + auxIcone, 0, Constantes.LARGURA_MIN_MAX - auxIcone2);
		}
	}
}