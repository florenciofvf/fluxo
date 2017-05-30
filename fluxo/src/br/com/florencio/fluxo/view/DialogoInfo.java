package br.com.florencio.fluxo.view;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import br.com.florencio.fluxo.Instancia;
import br.com.florencio.fluxo.util.Strings;

public class DialogoInfo extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTable table = new JTable();

	public DialogoInfo(Formulario formulario, Instancia objeto) {
		setAlwaysOnTop(true);
		montarLayout(objeto);
		setModal(true);
		pack();
		setLocationRelativeTo(formulario);
		setVisible(true);
	}

	private void montarLayout(Instancia objeto) {
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, new JScrollPane(table));
		table.setModel(new ModeloObjeto(objeto));
	}

	private class ModeloObjeto implements TableModel {
		private final String[] COLUNAS = { "PROPRIEDADE", "VALOR" };
		private final int TOTAL_PROPRIEDADES = 17;
		private final Instancia objeto;

		public ModeloObjeto(Instancia objeto) {
			this.objeto = objeto;
		}

		@Override
		public int getRowCount() {
			return TOTAL_PROPRIEDADES;
		}

		@Override
		public int getColumnCount() {
			return COLUNAS.length;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return COLUNAS[columnIndex];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			int linha = 0;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Nome";
				if (columnIndex == 1)
					return objeto.getDescricao();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return Strings.get("label_comentario");
				if (columnIndex == 1)
					return objeto.getComentario();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return Strings.get("label_desenhar_comentario");
				if (columnIndex == 1)
					return objeto.isDesenharComentario();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return Strings.get("label_observacao");
				if (columnIndex == 1)
					return objeto.getObservacao();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return Strings.get("label_desenhar_observacao");
				if (columnIndex == 1)
					return objeto.isDesenharObservacao();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Largura";
				if (columnIndex == 1)
					return objeto.getDimensao().getLargura();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Altura";
				if (columnIndex == 1)
					return objeto.getDimensao().getAltura();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Largura Desenho";
				if (columnIndex == 1)
					return objeto.getDimensaoAparencia().getLargura();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Altura Desenho";
				if (columnIndex == 1)
					return objeto.getDimensaoAparencia().getAltura();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "X";
				if (columnIndex == 1)
					return objeto.getLocalizacao().getX();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Y";
				if (columnIndex == 1)
					return objeto.getLocalizacao().getY();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "X Desenho";
				if (columnIndex == 1)
					return objeto.getLocalizacaoAparencia().getX();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Y Desenho";
				if (columnIndex == 1)
					return objeto.getLocalizacaoAparencia().getY();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Total filhos";
				if (columnIndex == 1)
					return objeto.getTamanho();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Esquerdo";
				if (columnIndex == 1)
					return objeto.isEsquerdo();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Altura Complementar";
				if (columnIndex == 1)
					return objeto.getAlturaComplementar();
			}

			linha++;
			if (rowIndex == linha) {
				if (columnIndex == 0)
					return "Minimizado";
				if (columnIndex == 1)
					return objeto.isMinimizado();
			}

			return null;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
		}
	}
}