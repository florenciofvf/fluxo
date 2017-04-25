package br.com.florencio.fluxo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DialogoComentario extends JDialog {
	private static final long serialVersionUID = 1L;
	private JButton buttonCancelar = new JButton(Strings.get("label_cancelar"));
	private JButton buttonOk = new JButton(Strings.get("label_ok"));
	private JTextArea textArea = new JTextArea();
	private JLabel labelTitulo = new JLabel();
	private Instancia instancia;

	public DialogoComentario(Instancia obj) {
		setTitle(obj.getDescricao());
		setAlwaysOnTop(true);
		instancia = obj;
		montarLayout(obj);
		registrarEventos();
		setModal(true);
		setSize(300, 300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void montarLayout(Instancia obj) {
		setLayout(new BorderLayout());

		labelTitulo.setText(obj.getComentario());
		add(BorderLayout.NORTH, labelTitulo);

		textArea.setWrapStyleWord(true);
		textArea.setText(obj.getComentario());
		add(BorderLayout.CENTER, new JScrollPane(textArea));

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(BorderLayout.WEST, buttonCancelar);
		panel.add(BorderLayout.EAST, buttonOk);

		add(BorderLayout.SOUTH, panel);
	}

	private void registrarEventos() {
		buttonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				instancia.setComentario(textArea.getText());
				dispose();
			}
		});
	}
}
