package br.com.florencio.fluxo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

public class Painel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JCheckBoxMenuItem menuItemDesenharComentario = new JCheckBoxMenuItem(
			Strings.get("label_desenhar_comentario"));
	private JMenuItem menuItemExcluirHierarquia = new JMenuItem(Strings.get("label_excluir_hierarquia"));
	private JMenuItem menuItemPadraoHierarquia = new JMenuItem(Strings.get("label_padrao_hierarquia"));
	private JMenuItem menuItemMinimizarTodos2 = new JMenuItem(Strings.get("label_minimizar_todos"));
	private JMenuItem menuItemMaximizarTodos2 = new JMenuItem(Strings.get("label_maximizar_todos"));
	private JMenuItem menuItemMargemInferior = new JMenuItem(Strings.get("label_margem_inferior"));
	private JMenuItem menuItemMinimizarTodos = new JMenuItem(Strings.get("label_minimizar_todos"));
	private JMenuItem menuItemMaximizarTodos = new JMenuItem(Strings.get("label_maximizar_todos"));
	private JMenuItem menuItemExcluirFilhos = new JMenuItem(Strings.get("label_excluir_filhos"));
	private JMenuItem menuItemExcluirAbaixo = new JMenuItem(Strings.get("label_excluir_abaixo"));
	private JMenuItem menuItemExcluirOutros = new JMenuItem(Strings.get("label_excluir_outros"));
	private JMenuItem menuItemExcluirAcima = new JMenuItem(Strings.get("label_excluir_acima"));
	private JMenuItem menuItemGerarImagem = new JMenuItem(Strings.get("label_gerar_imagem"));
	private JMenuItem menuItemComentario = new JMenuItem(Strings.get("label_comentario"));
	private JMenuItem menuItemPrimeiro = new JMenuItem(Strings.get("label_primeiro"));
	private JMenuItem menuItemVermelho = new JMenuItem(Strings.get("label_vermelho"));
	private JMenuItem menuItemRecortar = new JMenuItem(Strings.get("label_recortar"));
	private JMenuItem menuItemNovoPai = new JMenuItem(Strings.get("label_novo_pai"));
	private JMenuItem menuItemAmarelo = new JMenuItem(Strings.get("label_amarelo"));
	private JMenuItem menuItemLaranja = new JMenuItem(Strings.get("label_laranja"));
	private JMenuItem menuItemExcluir = new JMenuItem(Strings.get("label_excluir"));
	private JMenuItem menuItemPadrao = new JMenuItem(Strings.get("label_padrao"));
	private JMenuItem menuItemCopiar = new JMenuItem(Strings.get("label_copiar"));
	private JMenuItem menuItemUltimo = new JMenuItem(Strings.get("label_ultimo"));
	private JMenuItem menuItemDescer = new JMenuItem(Strings.get("label_descer"));
	private JMenuItem menuItemSubir = new JMenuItem(Strings.get("label_subir"));
	private JMenuItem menuItemVerde = new JMenuItem(Strings.get("label_verde"));
	private JMenuItem menuItemColar = new JMenuItem(Strings.get("label_colar"));
	private JMenuItem menuItemCinza = new JMenuItem(Strings.get("label_cinza"));
	private JMenuItem menuItemPreto = new JMenuItem(Strings.get("label_preto"));
	private JMenuItem menuItemNovo = new JMenuItem(Strings.get("label_novo"));
	private JMenuItem menuItemAzul = new JMenuItem(Strings.get("label_azul"));
	private JPopupMenu popupPainel = new JPopupMenu();
	private JPopupMenu popup = new JPopupMenu();
	private short margemLarguraImagem = 139;
	private short margemAlturaImagem = 139;
	private Instancia copiado;
	private Instancia raiz;
	private String arquivo;
	private Local local;

	public Painel(Instancia raiz) {
		this.raiz = raiz;
		registrarEventos();
		popup.add(menuItemNovo);
		popup.add(menuItemNovoPai);
		popup.addSeparator();
		popup.add(menuItemExcluir);
		popup.add(menuItemExcluirFilhos);
		popup.add(menuItemExcluirOutros);
		popup.add(menuItemExcluirAcima);
		popup.add(menuItemExcluirAbaixo);
		popup.add(menuItemExcluirHierarquia);
		popup.addSeparator();
		popup.add(menuItemCopiar);
		popup.add(menuItemRecortar);
		popup.add(menuItemColar);
		popup.addSeparator();
		popup.add(menuItemMargemInferior);
		popup.addSeparator();
		popup.add(menuItemMinimizarTodos2);
		popup.add(menuItemMaximizarTodos2);
		popup.addSeparator();
		popup.add(menuItemPrimeiro);
		popup.add(menuItemSubir);
		popup.add(menuItemDescer);
		popup.add(menuItemUltimo);
		popup.addSeparator();
		popup.add(menuItemVermelho);
		popup.add(menuItemLaranja);
		popup.add(menuItemAmarelo);
		popup.add(menuItemVerde);
		popup.add(menuItemAzul);
		popup.add(menuItemCinza);
		popup.add(menuItemPreto);
		popup.add(menuItemPadrao);
		popup.add(menuItemPadraoHierarquia);
		popup.addSeparator();
		popup.add(menuItemComentario);
		popup.add(menuItemDesenharComentario);

		popupPainel.add(menuItemMinimizarTodos);
		popupPainel.add(menuItemMaximizarTodos);
		popupPainel.addSeparator();
		popupPainel.add(menuItemGerarImagem);
	}

	private void tamanhoPainel() {
		Dimension d = new Dimension(Dimensao.larguraTotal + margemLarguraImagem,
				Dimensao.alturaTotal + margemAlturaImagem);
		setPreferredSize(d);
		setMinimumSize(d);
		SwingUtilities.updateComponentTreeUI(getParent());
	}

	private void organizar() {
		raiz.controlarMargemInferior();
		raiz.organizar(getFontMetrics(getFont()));
	}

	private void registrarEventos() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					menuItemDesenharComentario.setSelected(false);
					local = new Local(e.getX(), e.getY());
					Instancia objeto = procurar();
					if (objeto != null) {
						menuItemDesenharComentario.setSelected(objeto.isDesenharComentario());
						popup.show(Painel.this, e.getX(), e.getY());
					} else {
						popupPainel.show(Painel.this, e.getX(), e.getY());
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					menuItemDesenharComentario.setSelected(false);
					local = new Local(e.getX(), e.getY());
					Instancia objeto = procurar();
					if (objeto != null) {
						menuItemDesenharComentario.setSelected(objeto.isDesenharComentario());
						popup.show(Painel.this, e.getX(), e.getY());
					} else {
						popupPainel.show(Painel.this, e.getX(), e.getY());
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && raiz != null) {
					Instancia objeto = raiz.procurar(e.getX(), e.getY());

					if (objeto == null) {
						return;
					}

					if (!objeto.duploClickValido(e.getX())) {
						return;
					}

					String descricao = JOptionPane.showInputDialog(Painel.this, objeto.getDescricao(),
							objeto.getDescricao());

					if (descricao == null || descricao.trim().length() == 0) {
						return;
					}

					objeto.setDescricao(descricao);
					organizar();
					tamanhoPainel();
					repaint();
				} else {

					Instancia objeto = raiz.procurar(e.getX(), e.getY());

					if (objeto == null) {
						return;
					}

					if (objeto.clicadoNoIcone(e.getX(), e.getY())) {
						objeto.inverterIcone();
						organizar();
						tamanhoPainel();
						repaint();
					}
				}
			}
		});

		menuItemNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				String descricao = JOptionPane.showInputDialog(Painel.this, objeto.getDescricao());

				if (descricao == null || descricao.trim().length() == 0) {
					return;
				}

				objeto.adicionar(new Instancia(descricao));
				organizar();
				tamanhoPainel();
				repaint();
			}
		});

		menuItemNovoPai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null || objeto.getPai() == null) {
					return;
				}

				String descricao = JOptionPane.showInputDialog(Painel.this, objeto.getDescricao());

				if (descricao == null || descricao.trim().length() == 0) {
					return;
				}

				Instancia pai = objeto.getPai();
				int indice = pai.getIndice(objeto);

				Instancia novoPai = new Instancia(descricao);
				novoPai.adicionar(objeto);

				pai.adicionar(novoPai, indice);
				organizar();
				tamanhoPainel();
				repaint();
			}
		});

		menuItemComentario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				new DialogoComentario(objeto);
				organizar();
				tamanhoPainel();
				repaint();
			}
		});

		menuItemDesenharComentario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				objeto.setDesenharComentario(menuItemDesenharComentario.isSelected());
				organizar();
				tamanhoPainel();
				repaint();
			}
		});

		menuItemMargemInferior.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				String valor = JOptionPane.showInputDialog(Painel.this, objeto.getDescricao(), objeto.margemInferior);

				if (valor == null || valor.trim().length() == 0) {
					return;
				}

				try {
					objeto.margemInferior = Integer.parseInt(valor);
					organizar();
					tamanhoPainel();
					repaint();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(Painel.this, ex.getMessage());
				}
			}
		});

		menuItemExcluirAcima.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				int resp = JOptionPane.showConfirmDialog(Painel.this, Strings.get("label_confirma"),
						Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);

				if (JOptionPane.OK_OPTION == resp && objeto.getPai() != null) {
					objeto.getPai().excluirAcima(objeto);
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemExcluirAbaixo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				int resp = JOptionPane.showConfirmDialog(Painel.this, Strings.get("label_confirma"),
						Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);

				if (JOptionPane.OK_OPTION == resp && objeto.getPai() != null) {
					objeto.getPai().excluirAbaixo(objeto);
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemExcluirOutros.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				int resp = JOptionPane.showConfirmDialog(Painel.this, Strings.get("label_confirma"),
						Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);

				if (JOptionPane.OK_OPTION == resp && objeto.getPai() != null) {
					objeto.getPai().excluirOutros(objeto);
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				int resp = JOptionPane.showConfirmDialog(Painel.this, Strings.get("label_confirma"),
						Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);

				if (JOptionPane.OK_OPTION == resp && objeto.getPai() != null) {
					objeto.getPai().excluir(objeto);
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemExcluirFilhos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				int resp = JOptionPane.showConfirmDialog(Painel.this, Strings.get("label_confirma"),
						Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);

				if (JOptionPane.OK_OPTION == resp) {
					objeto.limpar();
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemExcluirHierarquia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				int resp = JOptionPane.showConfirmDialog(Painel.this, Strings.get("label_confirma"),
						Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);

				if (JOptionPane.OK_OPTION == resp) {
					objeto.sairDaHierarquia();
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemCopiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					copiado = null;
					return;
				}

				copiado = objeto.clonar();
			}
		});

		menuItemRecortar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					copiado = null;
					return;
				}

				copiado = objeto.clonar();

				if (objeto.getPai() != null) {
					objeto.getPai().excluir(objeto);
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemColar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null || copiado == null) {
					return;
				}

				objeto.adicionar(copiado.clonar());
				organizar();
				tamanhoPainel();
				repaint();
			}
		});

		menuItemPrimeiro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				if (objeto.getPai() != null) {
					objeto.getPai().primeiro(objeto);
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemSubir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				if (objeto.getPai() != null) {
					objeto.getPai().subir(objeto);
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemDescer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				if (objeto.getPai() != null) {
					objeto.getPai().descer(objeto);
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemUltimo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				if (objeto.getPai() != null) {
					objeto.getPai().ultimo(objeto);
					organizar();
					tamanhoPainel();
					repaint();
				}
			}
		});

		menuItemVermelho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					objeto.setCor(new Color(255, 50, 50));
					repaint();
				}
			}
		});

		menuItemVerde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					objeto.setCor(new Color(0, 150, 0));
					repaint();
				}
			}
		});

		menuItemLaranja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					objeto.setCor(new Color(255, 170, 70));
					repaint();
				}
			}
		});

		menuItemAmarelo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					objeto.setCor(new Color(240, 210, 100));
					repaint();
				}
			}
		});

		menuItemAzul.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					objeto.setCor(new Color(80, 80, 255));
					repaint();
				}
			}
		});

		menuItemPreto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					objeto.setCor(new Color(78, 78, 78));
					repaint();
				}
			}
		});

		menuItemCinza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					objeto.setCor(new Color(180, 180, 180));
					repaint();
				}
			}
		});

		menuItemPadrao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					objeto.setCor(null);
					repaint();
				}
			}
		});

		menuItemPadraoHierarquia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					objeto.setCorHierarquia(null);
					repaint();
				}
			}
		});

		menuItemMinimizarTodos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				raiz.minMaxTodos(true);
				organizar();
				tamanhoPainel();
				repaint();
			}
		});

		menuItemMaximizarTodos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				raiz.minMaxTodos(false);
				organizar();
				tamanhoPainel();
				repaint();
			}
		});

		menuItemMinimizarTodos2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}
			
				objeto.minMaxTodos(true);
				organizar();
				tamanhoPainel();
				repaint();
			}
		});

		menuItemMaximizarTodos2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}
			
				objeto.minMaxTodos(false);
				organizar();
				tamanhoPainel();
				repaint();
			}
		});
		
		menuItemGerarImagem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int i = fileChooser.showSaveDialog(Painel.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (file != null) {
						BufferedImage bi = new BufferedImage(Dimensao.larguraTotal + margemLarguraImagem,
								Dimensao.alturaTotal + margemAlturaImagem, BufferedImage.TYPE_INT_ARGB);
						Graphics2D g2 = bi.createGraphics();
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setColor(Color.WHITE);
						g2.fillRect(0, 0, Dimensao.larguraTotal + margemLarguraImagem,
								Dimensao.alturaTotal + margemAlturaImagem);
						g2.setColor(Color.BLACK);
						g2.setFont(getFont());
						raiz.desenhar(g2);
						try {
							ImageIO.write(bi, "png", file);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(Painel.this, e1.getMessage());
						}
					}
				}
			}
		});

	}

	public void setArquivo(String arq) {
		arquivo = Arquivo.semSufixo(arq);

		if (arquivo != null && arquivo.trim().length() > 0) {
			arquivo += Arquivo.SUFIXO;
			abrirArquivo();
		} else {
			arquivo = null;
		}
	}

	private void abrirArquivo() {
		try {
			File file = new File(arquivo);

			if (!file.exists()) {
				Arquivo.salvarArquivo(new Instancia(file.getName()), file);
			}

			raiz = Arquivo.lerArquivo(file);
			organizar();
			tamanhoPainel();
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	public void salvarArquivo(String alternativo) {
		if (alternativo != null && alternativo.trim().length() > 0) {
			arquivo = Arquivo.semSufixo(alternativo);
			arquivo += Arquivo.SUFIXO;
		}

		if (arquivo == null || raiz == null) {
			return;
		}

		try {
			Arquivo.salvarArquivo(raiz, new File(arquivo));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private Instancia procurar() {
		return raiz.procurar(local.getX(), local.getY());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		raiz.desenhar((Graphics2D) g);
	}

	public String getArquivo() {
		return arquivo;
	}

	public void processarSQL(String arquivo) {
		File file = new File(arquivo);

		if (!file.exists()) {
			return;
		}

		try {
			StringBuilder sb = new StringBuilder();

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String linha = br.readLine();

			while (linha != null) {
				sb.append(linha);
				linha = br.readLine();
			}

			br.close();
			raiz.limpar();
			processar(sb.toString());
			organizar();
			tamanhoPainel();
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private void processar(String sql) throws Exception {
		if (sql.trim().length() == 0) {
			return;
		}

		Class.forName(Config.get("driver"));
		Connection conn = DriverManager.getConnection(Config.get("url"), Config.get("usuario"), Config.get("senha"));
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData meta = rs.getMetaData();

		int colunas = meta.getColumnCount();

		while (rs.next()) {
			String[] grafo = new String[colunas];

			for (int i = 0; i < colunas; i++) {
				grafo[i] = rs.getString(i + 1);
			}

			raiz.importar(grafo);
		}

		rs.close();
		ps.close();
		conn.close();
	}
}