package br.com.florencio.fluxo.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.sun.glass.events.KeyEvent;

import br.com.florencio.fluxo.InstanciaRaiz;
import br.com.florencio.fluxo.util.ArquivoUtil;
import br.com.florencio.fluxo.util.Strings;

public class Formulario extends JFrame {
	private static final long serialVersionUID = 1L;
	private JMenuItem menuItemSalvarComoArquivo = new JMenuItem(Strings.get("label_salvar_como"));
	private JMenuItem menuItemSalvarArquivo = new JMenuItem(Strings.get("label_salvar"));
	private JMenuItem menuItemAbrirArquivo = new JMenuItem(Strings.get("label_abrir"));
	private JMenu menuArquivo = new JMenu(Strings.get("label_arquivo"));
	private JTextField textFieldArquivo = new JTextField();
	private JTextField textFieldSQL = new JTextField();
	private JMenuBar menuBarra = new JMenuBar();
	private boolean COM_PAINEL_SQL = false;
	private Painel painel;

	public Formulario(InstanciaRaiz raiz) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		painel = new Painel(raiz);
		montarMenu();
		montarLayout();
		registrarEventos();
		setSize(800, 600);
		setVisible(true);
	}

	private void montarMenu() {
		setJMenuBar(menuBarra);
		menuBarra.add(menuArquivo);
		menuArquivo.add(menuItemSalvarArquivo);
		menuArquivo.add(menuItemSalvarComoArquivo);
		menuArquivo.add(menuItemAbrirArquivo);
	}

	private void montarLayout() {
		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, textFieldArquivo);
		add(BorderLayout.CENTER, new JScrollPane(painel));
		if (COM_PAINEL_SQL) {
			add(BorderLayout.SOUTH, textFieldSQL);
		}
	}

	private void registrarEventos() {
		textFieldArquivo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A && e.isControlDown()) {
					abrirArquivo();
				} else if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
					painel.salvarArquivo(textFieldArquivo.getText());
				}
			}
		});

		textFieldArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				painel.setArquivo(textFieldArquivo.getText());
			}
		});

		textFieldSQL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				painel.processarSQL(textFieldSQL.getText());
			}
		});

		menuItemAbrirArquivo.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		menuItemAbrirArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirArquivo();
			}
		});

		menuItemSalvarArquivo.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		menuItemSalvarArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				painel.salvarArquivo(null);
			}
		});

		menuItemSalvarComoArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String descricao = JOptionPane.showInputDialog(Formulario.this, textFieldArquivo.getText(),
						textFieldArquivo.getText());

				if (descricao == null || descricao.trim().length() == 0) {
					return;
				}

				textFieldArquivo.setText(descricao);
				painel.salvarArquivo(textFieldArquivo.getText());
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				painel.reorganizar();
			}
		});
	}

	protected void abrirArquivo() {
		JFileChooser fileChooser = new JFileChooser(painel.getArquivo());
		int opcao = fileChooser.showOpenDialog(Formulario.this);

		if (opcao == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			if (file != null) {
				textFieldArquivo.setText(ArquivoUtil.semSufixo(file.getAbsolutePath()));
				painel.setArquivo(file.getAbsolutePath());
			}
		}
	}
}