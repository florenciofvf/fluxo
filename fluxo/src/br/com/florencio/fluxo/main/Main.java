package br.com.florencio.fluxo.main;

import javax.swing.UIManager;

import br.com.florencio.fluxo.InstanciaRaiz;
import br.com.florencio.fluxo.util.Strings;
import br.com.florencio.fluxo.view.Formulario;

public class Main {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		InstanciaRaiz raiz = new InstanciaRaiz(Strings.get("label_raiz"));

		new Formulario(raiz);
	}

}