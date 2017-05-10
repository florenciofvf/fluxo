package br.com.florencio.fluxo;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.florencio.fluxo.util.Constantes;

public class InstanciaAparenciaRaiz extends InstanciaAparencia {

	public void desenhar(Instancia i, Graphics2D g2) {
		final boolean contemComentario = i.getComentario().length() > 0;
		g2.setColor(i.cor);

		final byte raio = 8;

		int x = i.localizacaoAparencia.getX();
		int y = i.localizacaoAparencia.getY();
		int l = i.dimensaoAparencia.getLargura();
		int a = i.dimensaoAparencia.getAltura();
		int m = l / 2 - 8;

		g2.drawOval(x, y - m, l, l);

		if (i.cor != null) {
			g2.fillRoundRect(x, y, l, a, raio, raio);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(x, y, l, a, raio, raio);
			g2.setColor(i.cor);
		} else {
			g2.drawRoundRect(x, y, l, a, raio, raio);
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

		g2.setColor(i.cor);
	}

}