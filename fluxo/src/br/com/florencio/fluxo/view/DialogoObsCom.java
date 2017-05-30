package br.com.florencio.fluxo.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.com.florencio.fluxo.Instancia;
import br.com.florencio.fluxo.util.Strings;

public class DialogoObsCom extends JDialog {
	private static final long serialVersionUID = 1L;
	private JCheckBox checkBoxDesenhar = new JCheckBox(Strings.get("label_desenhar_comentario"));
	private JButton buttonCancelar = new JButton(Strings.get("label_cancelar"));
	private JButton buttonOk = new JButton(Strings.get("label_ok"));
	private JTextArea textArea = new JTextArea();
	private JLabel labelTitulo = new JLabel();
	private Instancia instancia;
	private boolean comentario;

	public DialogoObsCom(Formulario formulario, Instancia obj, boolean comentario) {
		setTitle(obj.getDescricao());
		this.comentario = comentario;
		setAlwaysOnTop(true);
		instancia = obj;
		montarLayout(obj);
		registrarEventos();
		setModal(true);
		setSize(300, 300);
		setLocationRelativeTo(formulario);
		setVisible(true);
	}

	private void montarLayout(Instancia obj) {
		setLayout(new BorderLayout());

		labelTitulo.setText(comentario ? obj.getComentario() : obj.getObservacao());
		add(BorderLayout.NORTH, labelTitulo);

		textArea.setWrapStyleWord(true);
		textArea.setText(comentario ? obj.getComentario() : obj.getObservacao());
		add(BorderLayout.CENTER, new JScrollPane(textArea));

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(BorderLayout.WEST, buttonCancelar);
		checkBoxDesenhar.setSelected(comentario ? obj.isDesenharComentario() : obj.isDesenharObservacao());
		if (!comentario) {
			checkBoxDesenhar.setText(Strings.get("label_desenhar_observacao"));
		}
		panel.add(BorderLayout.CENTER, checkBoxDesenhar);
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
				if (comentario) {
					instancia.setComentario(textArea.getText());
				} else {
					instancia.setObservacao(textArea.getText());
				}
				dispose();
			}
		});

		checkBoxDesenhar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comentario) {
					instancia.setDesenharComentario(checkBoxDesenhar.isSelected());
				} else {
					instancia.setDesenharObservacao(checkBoxDesenhar.isSelected());
				}
			}
		});
	}
}