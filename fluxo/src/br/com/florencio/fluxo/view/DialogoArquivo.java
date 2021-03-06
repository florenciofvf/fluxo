package br.com.florencio.fluxo.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		pack();
		setLocationRelativeTo(formulario);
		listagem.requestFocus();
		setVisible(true);
	}

	private void montarLayout() {
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, new JScrollPane(listagem));

		File file = new File(Util.estaVazio(formulario.getArquivo()) ? "." : formulario.getArquivo());
		File[] arquivos = getArquivos(file);

		List<String> lista = new ArrayList<>();
		if (arquivos != null) {
			for (File f : arquivos) {
				lista.add(f.getAbsolutePath());
			}
		}

		listagem.setListData(lista.toArray(new String[] {}));
		listagem.setVisibleRowCount(listagem.getModel().getSize());
		listagem.setSelectedValue(formulario.getArquivo() + ArquivoUtil.SUFIXO, true);
	}

	class Filtro implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			name = name.toLowerCase();
			return name.endsWith(ArquivoUtil.SUFIXO);
		}
	}

	private File[] getArquivos(File file) {
		File[] arquivos = null;

		while ((arquivos == null || arquivos.length == 0) && file != null) {
			arquivos = file.listFiles(new Filtro());
			file = file.getParentFile();
		}

		return arquivos;
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

		listagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int pos = listagem.locationToIndex(e.getPoint());
				if (pos != -1) {
					String string = listagem.getModel().getElementAt(pos);
					if (!Util.estaVazio(string)) {
						formulario.setArquivo(string);
					}
				}
				dispose();
			}
		});
	}
}