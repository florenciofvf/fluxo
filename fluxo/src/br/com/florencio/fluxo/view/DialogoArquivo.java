package br.com.florencio.fluxo.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import br.com.florencio.fluxo.util.ArquivoUtil;
import br.com.florencio.fluxo.util.Util;

public class DialogoArquivo extends JDialog {
	private static final long serialVersionUID = 1L;
	private JList<String> listagem = new JList<>();
	private final Formulario formulario;

	public DialogoArquivo(Formulario formulario) {
		this.formulario = formulario;
		setAlwaysOnTop(true);
		montarLayout();
		registrarEventos();
		setModal(true);
		setSize(300, 300);
		setLocationRelativeTo(formulario);
		listagem.requestFocus();
		setVisible(true);
	}

	private void montarLayout() {
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, new JScrollPane(listagem));

		File file = new File(".");

		String[] arquivos = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				name = name.toLowerCase();
				return name.endsWith(ArquivoUtil.SUFIXO);
			}
		});

		List<String> lista = new ArrayList<>();

		if (arquivos != null) {
			for (String string : arquivos) {
				lista.add(get(string));
			}
		}

		listagem.setListData(lista.toArray(new String[] {}));
	}

	private String get(String string) {
		int pos = string.lastIndexOf(File.separator);
		if (pos == -1) {
			return string;
		}
		return string.substring(pos + 1);
	}

	private void registrarEventos() {
		listagem.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String string = listagem.getSelectedValue();
					if (!Util.estaVazio(string)) {
						formulario.setArquivo(string);
					}
					dispose();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}
}