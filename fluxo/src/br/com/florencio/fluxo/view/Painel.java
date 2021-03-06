package br.com.florencio.fluxo.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import br.com.florencio.fluxo.Instancia;
import br.com.florencio.fluxo.InstanciaRaiz;
import br.com.florencio.fluxo.util.ArquivoUtil;
import br.com.florencio.fluxo.util.Constantes;
import br.com.florencio.fluxo.util.Localizacao;
import br.com.florencio.fluxo.util.SQLUtil;
import br.com.florencio.fluxo.util.Strings;
import br.com.florencio.fluxo.util.Util;

public class Painel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JMenuItem menuItemComentarioEmObservacao = new JMenuItem(
			Strings.get("label_transformar_comentario_observacao"));
	private JMenuItem menuItemObservacaoEmComentario = new JMenuItem(
			Strings.get("label_transformar_observacao_comentario"));
	private JCheckBoxMenuItem menuItemDesenharObservacao = new JCheckBoxMenuItem(
			Strings.get("label_desenhar_observacao"));
	private JCheckBoxMenuItem menuItemDesenharComentario = new JCheckBoxMenuItem(
			Strings.get("label_desenhar_comentario"));
	private JCheckBoxMenuItem menuItemDesenharAparencia = new JCheckBoxMenuItem(
			Strings.get("label_desenhar_aparencia"));
	private JRadioButtonMenuItem menuItemAlinhamentoSuperior = new JRadioButtonMenuItem(Strings.get("label_superior"));
	private JRadioButtonMenuItem menuItemAlinhamentoInferior = new JRadioButtonMenuItem(Strings.get("label_inferior"));
	private JMenuItem menuItemComentarioEmFilho = new JMenuItem(Strings.get("label_transformar_comentario_filho"));
	private JMenuItem menuItemObservacaoEmFilho = new JMenuItem(Strings.get("label_transformar_observacao_filho"));
	private JCheckBoxMenuItem menuItemDesenharLimite = new JCheckBoxMenuItem(Strings.get("label_desenhar_limite"));
	private JCheckBoxMenuItem menuItemDesenharFonte = new JCheckBoxMenuItem(Strings.get("label_desenhar_fonte"));
	private JCheckBoxMenuItem menuItemDestacadoRaiz = new JCheckBoxMenuItem(Strings.get("label_destacar_raiz"));
	private JCheckBoxMenuItem menuItemDesenharDestacado = new JCheckBoxMenuItem(Strings.get("label_destacar"));
	private JRadioButtonMenuItem menuItemAlinhamentoMeio = new JRadioButtonMenuItem(Strings.get("label_meio"));
	private JMenuItem menuItemComentarioEmPai = new JMenuItem(Strings.get("label_transformar_comentario_pai"));
	private JMenuItem menuItemObservacaoEmPai = new JMenuItem(Strings.get("label_transformar_observacao_pai"));
	private JMenuItem menuItemExcluirHierarquia = new JMenuItem(Strings.get("label_excluir_hierarquia"));
	private JMenuItem menuItemRecortarSFilhos = new JMenuItem(Strings.get("label_recortar_sem_filhos"));
	private JMenuItem menuItemPadraoHierarquia = new JMenuItem(Strings.get("label_padrao_hierarquia"));
	private JMenuItem menuItemSelecionadoLinha = new JMenuItem(Strings.get("label_selecionado_linha"));
	private JMenuItem menuItemComentarioVirar = new JMenuItem(Strings.get("label_virar_comentario"));
	private JMenuItem menuItemObservacaoVirar = new JMenuItem(Strings.get("label_virar_observacao"));
	private JMenuItem menuItemMinimizarTodos2 = new JMenuItem(Strings.get("label_minimizar_todos"));
	private JMenuItem menuItemMaximizarTodos2 = new JMenuItem(Strings.get("label_maximizar_todos"));
	private JMenuItem menuItemCopiarSFilhos = new JMenuItem(Strings.get("label_copiar_sem_filhos"));
	private JMenuItem menuItemMinimizarTodos = new JMenuItem(Strings.get("label_minimizar_todos"));
	private JMenuItem menuItemMaximizarTodos = new JMenuItem(Strings.get("label_maximizar_todos"));
	private JMenuItem menuItemMargemSuperior = new JMenuItem(Strings.get("label_margem_superior"));
	private JMenuItem menuItemMargemInferior = new JMenuItem(Strings.get("label_margem_inferior"));
	private JMenuItem menuItemColarPaiPonta = new JMenuItem(Strings.get("label_colar_pai_ponta"));
	private JMenuItem menuItemComentario = new JMenuItem(Strings.get("label_adicionar_alterar"));
	private JMenuItem menuItemObservacao = new JMenuItem(Strings.get("label_adicionar_alterar"));
	private JMenuItem menuItemExcluirFilhos = new JMenuItem(Strings.get("label_excluir_filhos"));
	private JMenuItem menuItemExcluirAbaixo = new JMenuItem(Strings.get("label_excluir_abaixo"));
	private JMenuItem menuItemExcluirOutros = new JMenuItem(Strings.get("label_excluir_outros"));
	private JMenuItem menuItemSelecionarCor = new JMenuItem(Strings.get("label_selecionar_cor"));
	private JMenuItem menuItemLarguraPadao = new JMenuItem(Strings.get("label_largura_padrao"));
	private JMenuItem menuItemExcluirAcima = new JMenuItem(Strings.get("label_excluir_acima"));
	private JMenuItem menuItemCorSelecionado = new JMenuItem(Strings.get("label_selecionado"));
	private JMenuItem menuItemAlturaPadao = new JMenuItem(Strings.get("label_altura_padrao"));
	private JMenuItem menuItemGerarImagem = new JMenuItem(Strings.get("label_gerar_imagem"));
	private JMenuItem menuItemComplemento = new JMenuItem(Strings.get("label_complemento"));
	private JMenuItem menuItemBordaLinha = new JMenuItem(Strings.get("label_borda_linha"));
	private JMenuItem menuItemColarPai = new JMenuItem(Strings.get("label_colar_pai"));
	private JMenuItem menuItemPrimeiro = new JMenuItem(Strings.get("label_primeiro"));
	private JMenuItem menuItemVermelho = new JMenuItem(Strings.get("label_vermelho"));
	private JMenuItem menuItemColar = new JMenuItem(Strings.get("label_colar_filho"));
	private JMenuItem menuItemCorLimite = new JMenuItem(Strings.get("label_limite"));
	private JMenuItem menuItemCopiarCor = new JMenuItem(Strings.get("label_copiar"));
	private JMenuItem menuItemAmarelo = new JMenuItem(Strings.get("label_amarelo"));
	private JMenuItem menuItemLaranja = new JMenuItem(Strings.get("label_laranja"));
	private JMenuItem menuItemNovoFilho = new JMenuItem(Strings.get("label_filho"));
	private JMenuItem menuItemCorFundo = new JMenuItem(Strings.get("label_fundo"));
	private JMenuItem menuItemColarCor = new JMenuItem(Strings.get("label_colar"));
	private JMenuItem menuItemCorBorda = new JMenuItem(Strings.get("label_borda"));
	private JMenuItem menuItemRecortar = new JMenuItem(Strings.get("label_este"));
	private JMenuItem menuItemPadrao = new JMenuItem(Strings.get("label_padrao"));
	private JMenuItem menuItemUltimo = new JMenuItem(Strings.get("label_ultimo"));
	private JMenuItem menuItemDescer = new JMenuItem(Strings.get("label_descer"));
	private JMenuItem menuItemExcluir = new JMenuItem(Strings.get("label_este"));
	private JMenuItem menuItemNovoPai = new JMenuItem(Strings.get("label_pai"));
	private JMenuItem menuItemCopiar = new JMenuItem(Strings.get("label_este"));
	private JMenuItem menuItemSubir = new JMenuItem(Strings.get("label_subir"));
	private JMenuItem menuItemVerde = new JMenuItem(Strings.get("label_verde"));
	private JMenuItem menuItemCinza = new JMenuItem(Strings.get("label_cinza"));
	private JMenuItem menuItemPreto = new JMenuItem(Strings.get("label_preto"));
	private JMenuItem menuItemAzul = new JMenuItem(Strings.get("label_azul"));
	private JMenuItem menuItemInfo = new JMenuItem(Strings.get("label_info"));
	private JPopupMenu popupPainel = new JPopupMenu();
	private JPopupMenu popup = new JPopupMenu();
	private Localizacao localizacao;
	private Instancia objetoMarcado;
	private Instancia selecionado;
	private Formulario formulario;
	private InstanciaRaiz raiz;
	private Instancia copiado;
	private boolean animando;
	private Color corCopiado;
	private String arquivo;

	public Painel(Formulario formulario, InstanciaRaiz raiz) {
		this.formulario = formulario;
		setBackground(Constantes.COR_FUNDO);
		this.raiz = raiz;
		montarPopups();
		registrarEventos();
	}

	private void montarPopups() {
		JMenu menuNovo = new JMenu(Strings.get("label_novo"));
		menuNovo.add(menuItemNovoFilho);
		menuNovo.add(menuItemNovoPai);
		popup.add(menuNovo);

		popup.addSeparator();
		JMenu menuExcluir = new JMenu(Strings.get("label_excluir"));
		menuExcluir.add(menuItemExcluir);
		menuExcluir.add(menuItemExcluirFilhos);
		menuExcluir.add(menuItemExcluirOutros);
		menuExcluir.add(menuItemExcluirAcima);
		menuExcluir.add(menuItemExcluirAbaixo);
		menuExcluir.add(menuItemExcluirHierarquia);
		popup.add(menuExcluir);

		popup.addSeparator();
		JMenu menuCopiar = new JMenu(Strings.get("label_copiar"));
		menuCopiar.add(menuItemCopiar);
		menuCopiar.add(menuItemCopiarSFilhos);
		popup.add(menuCopiar);

		JMenu menuRecortar = new JMenu(Strings.get("label_recortar"));
		menuRecortar.add(menuItemRecortar);
		menuRecortar.add(menuItemRecortarSFilhos);
		popup.add(menuRecortar);

		popup.addSeparator();
		JMenu menuColar = new JMenu(Strings.get("label_colar"));
		menuColar.add(menuItemColar);
		menuColar.add(menuItemColarPai);
		menuColar.add(menuItemColarPaiPonta);
		popup.add(menuColar);

		popup.addSeparator();
		JMenu menuAltura = new JMenu(Strings.get("label_altura"));
		menuAltura.add(menuItemMargemSuperior);
		menuAltura.add(menuItemMargemInferior);
		menuAltura.add(menuItemComplemento);
		popup.add(menuAltura);

		popup.addSeparator();
		popup.add(menuItemMinimizarTodos2);
		popup.add(menuItemMaximizarTodos2);

		popup.addSeparator();
		JMenu menuPosicao = new JMenu(Strings.get("label_posicao"));
		menuPosicao.add(menuItemPrimeiro);
		menuPosicao.add(menuItemSubir);
		menuPosicao.add(menuItemDescer);
		menuPosicao.add(menuItemUltimo);
		popup.add(menuPosicao);

		popup.addSeparator();
		JMenu menuCor = new JMenu(Strings.get("label_cor"));
		menuCor.add(menuItemCopiarCor);
		menuCor.add(menuItemColarCor);
		menuCor.addSeparator();
		menuCor.add(menuItemVermelho);
		menuCor.add(menuItemLaranja);
		menuCor.add(menuItemAmarelo);
		menuCor.add(menuItemVerde);
		menuCor.add(menuItemAzul);
		menuCor.add(menuItemCinza);
		menuCor.add(menuItemPreto);
		menuCor.add(menuItemPadrao);
		menuCor.add(menuItemPadraoHierarquia);
		menuCor.addSeparator();
		menuCor.add(menuItemSelecionarCor);
		popup.add(menuCor);

		popup.addSeparator();
		JMenu menuObservacao = new JMenu(Strings.get("label_observacao"));
		menuObservacao.add(menuItemObservacao);
		menuObservacao.add(menuItemDesenharObservacao);
		menuObservacao.add(menuItemObservacaoEmFilho);
		menuObservacao.add(menuItemObservacaoEmPai);
		menuObservacao.add(menuItemObservacaoVirar);
		menuObservacao.add(menuItemObservacaoEmComentario);
		popup.add(menuObservacao);

		popup.addSeparator();
		JMenu menuComentario = new JMenu(Strings.get("label_comentario"));
		menuComentario.add(menuItemComentario);
		menuComentario.add(menuItemDesenharComentario);
		menuComentario.add(menuItemComentarioEmFilho);
		menuComentario.add(menuItemComentarioEmPai);
		menuComentario.add(menuItemComentarioVirar);
		menuComentario.add(menuItemComentarioEmObservacao);
		popup.add(menuComentario);

		popup.addSeparator();
		popup.add(menuItemInfo);

		popup.addSeparator();
		popup.add(menuItemDesenharDestacado);
		popup.add(menuItemDesenharAparencia);

		popupPainel.add(menuItemMinimizarTodos);
		popupPainel.add(menuItemMaximizarTodos);

		popupPainel.addSeparator();
		popupPainel.add(menuItemDestacadoRaiz);

		popupPainel.addSeparator();
		popupPainel.add(menuItemDesenharLimite);

		popupPainel.addSeparator();
		popupPainel.add(menuItemDesenharFonte);

		popupPainel.addSeparator();
		popupPainel.add(menuItemLarguraPadao);
		popupPainel.add(menuItemAlturaPadao);

		popupPainel.addSeparator();
		JMenu menuAlinhamento = new JMenu(Strings.get("label_alinhamento"));
		menuAlinhamento.add(menuItemAlinhamentoSuperior);
		menuAlinhamento.add(menuItemAlinhamentoMeio);
		menuAlinhamento.add(menuItemAlinhamentoInferior);

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(menuItemAlinhamentoSuperior);
		grupo.add(menuItemAlinhamentoInferior);
		grupo.add(menuItemAlinhamentoMeio);

		menuItemAlinhamentoInferior.setSelected(Constantes.ALINHAMENTO == Constantes.APARENCIA_ABAIXO);
		menuItemAlinhamentoSuperior.setSelected(Constantes.ALINHAMENTO == Constantes.APARENCIA_ACIMA);
		menuItemAlinhamentoMeio.setSelected(Constantes.ALINHAMENTO == Constantes.APARENCIA_MEIO);
		popupPainel.add(menuAlinhamento);

		popupPainel.addSeparator();
		popupPainel.add(menuItemGerarImagem);

		popupPainel.addSeparator();
		JMenu menuCores = new JMenu(Strings.get("label_cor"));
		menuCores.add(menuItemSelecionadoLinha);
		menuCores.add(menuItemBordaLinha);
		menuCores.addSeparator();
		menuCores.add(menuItemCorSelecionado);
		menuCores.add(menuItemCorLimite);
		menuCores.add(menuItemCorFundo);
		menuCores.add(menuItemCorBorda);
		popupPainel.add(menuCores);
	}

	public void reorganizar() {
		raiz.processar(getFontMetrics(getFont()));

		AtomicInteger largura = new AtomicInteger(0);
		AtomicInteger altura = new AtomicInteger(0);

		raiz.calcularLarguraTotal(largura);
		raiz.calcularAlturaTotal(altura);

		Dimension d = new Dimension(largura.get(), altura.get());
		setPreferredSize(d);
		setMinimumSize(d);

		SwingUtilities.updateComponentTreeUI(getParent());
	}

	private int showConfirmDialog(Instancia objeto) {
		if (objeto instanceof InstanciaRaiz) {
			return JOptionPane.NO_OPTION;
		}

		return JOptionPane.showConfirmDialog(formulario, Strings.get("label_confirma"), Strings.get("label_atencao"),
				JOptionPane.YES_NO_OPTION);
	}

	private void registrarEventos() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (selecionado != null) {
					selecionado.selecionado = false;
				}
				selecionado = raiz.procurar(e.getX(), e.getY());
				if (selecionado != null) {
					selecionado.selecionado = true;
				}
				repaint();
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					localizacao = new Localizacao(e.getX(), e.getY());
					Instancia objeto = procurar();
					if (objeto != null) {
						menuItemDesenharComentario.setSelected(objeto.isDesenharComentario());
						menuItemDesenharObservacao.setSelected(objeto.isDesenharObservacao());
						menuItemDesenharAparencia.setSelected(objeto.isDesenharAparencia());
						menuItemDesenharDestacado.setSelected(objeto.isDesenharDestacado());
						popup.show(Painel.this, e.getX(), e.getY());
					} else {
						menuItemAlinhamentoInferior.setSelected(Constantes.ALINHAMENTO == Constantes.APARENCIA_ABAIXO);
						menuItemAlinhamentoSuperior.setSelected(Constantes.ALINHAMENTO == Constantes.APARENCIA_ACIMA);
						menuItemAlinhamentoMeio.setSelected(Constantes.ALINHAMENTO == Constantes.APARENCIA_MEIO);
						menuItemDesenharLimite.setSelected(Constantes.DESENHAR_LIMITE);
						menuItemDesenharFonte.setSelected(Constantes.DESENHAR_FONTE);
						menuItemDestacadoRaiz.setSelected(Constantes.DESTACAR_RAIZ);
						popupPainel.show(Painel.this, e.getX(), e.getY());
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					localizacao = new Localizacao(e.getX(), e.getY());
					Instancia objeto = procurar();
					if (objeto != null) {
						menuItemDesenharComentario.setSelected(objeto.isDesenharComentario());
						menuItemDesenharObservacao.setSelected(objeto.isDesenharObservacao());
						menuItemDesenharAparencia.setSelected(objeto.isDesenharAparencia());
						menuItemDesenharDestacado.setSelected(objeto.isDesenharDestacado());
						popup.show(Painel.this, e.getX(), e.getY());
					} else {
						menuItemAlinhamentoInferior.setSelected(Constantes.ALINHAMENTO == Constantes.APARENCIA_ABAIXO);
						menuItemAlinhamentoSuperior.setSelected(Constantes.ALINHAMENTO == Constantes.APARENCIA_ACIMA);
						menuItemAlinhamentoMeio.setSelected(Constantes.ALINHAMENTO == Constantes.APARENCIA_MEIO);
						menuItemDesenharLimite.setSelected(Constantes.DESENHAR_LIMITE);
						menuItemDesenharFonte.setSelected(Constantes.DESENHAR_FONTE);
						menuItemDestacadoRaiz.setSelected(Constantes.DESTACAR_RAIZ);
						popupPainel.show(Painel.this, e.getX(), e.getY());
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (animando) {
					return;
				}

				if (e.isControlDown() && raiz != null) {
					Instancia objeto = raiz.procurar(e.getX(), e.getY());

					if (objetoMarcado != null) {
						objetoMarcado.setMarcado(false);
						repaint();
					}

					if (objeto == null) {
						return;
					}

					objeto.setMarcado(true);
					objetoMarcado = objeto;
					repaint();
				} else if (e.getClickCount() == 2 && raiz != null) {
					Instancia objeto = raiz.procurar(e.getX(), e.getY());

					if (objetoMarcado != null) {
						objetoMarcado.setMarcado(false);
						repaint();
					}

					if (objeto == null) {
						return;
					}

					if (objeto.clicadoAreaIcone(e.getX(), e.getY())) {
						return;
					}

					objeto.setMarcado(true);
					objetoMarcado = objeto;

					String descricao = JOptionPane.showInputDialog(formulario, objeto.getDescricao(),
							objeto.getDescricao());

					if (Util.estaVazio(descricao)) {
						return;
					}

					objeto.setDescricao(descricao);
					reorganizar();
				} else {
					Instancia objeto = raiz.procurar(e.getX(), e.getY());

					if (objeto == null) {
						return;
					}

					if (objeto.clicadoNoIcone(e.getX(), e.getY())) {
						animando = true;
						objeto = objeto.getClicadoNoIcone();
						if (objeto.isMinimizado()) {
							objeto.minMaxTodos(true);
							animar(objeto, true, 100);
						} else {
							animar(objeto, false, 100);
						}
					}
				}
			}
		});

		menuItemNovoFilho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				String descricao = JOptionPane.showInputDialog(formulario, objeto.getDescricao());

				if (Util.estaVazio(descricao)) {
					return;
				}

				if (objeto instanceof InstanciaRaiz) {
					int resp = JOptionPane.showConfirmDialog(formulario, Strings.get("msg_lado_direito"),
							Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);
					boolean direito = JOptionPane.OK_OPTION == resp;

					Instancia instancia = new Instancia(descricao, !direito);
					((InstanciaRaiz) objeto).adicionarInstancia(instancia);
				} else {
					objeto.adicionar(new Instancia(descricao));
				}

				reorganizar();
			}
		});

		menuItemNovoPai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null || objeto.getPai() == null) {
					return;
				}

				String descricao = JOptionPane.showInputDialog(formulario, objeto.getDescricao());

				if (Util.estaVazio(descricao)) {
					return;
				}

				Instancia pai = objeto.getPai();
				int indice = pai.getIndice(objeto);

				Instancia novoPai = new Instancia(descricao, pai.isEsquerdo());
				novoPai.adicionar(objeto);

				pai.adicionar(novoPai, indice);
				reorganizar();
			}
		});

		menuItemComentario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				new DialogoObsCom(formulario, objeto, true);
				reorganizar();
			}
		});

		menuItemInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				new DialogoInfo(formulario, objeto);
			}
		});

		menuItemObservacao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				new DialogoObsCom(formulario, objeto, false);
				reorganizar();
			}
		});

		menuItemDesenharDestacado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				objeto.setDesenharDestacado(menuItemDesenharDestacado.isSelected());

				if (objeto.isDesenharDestacado()) {
					objeto.calcularLarguraTotal();
				}

				repaint();
			}
		});

		menuItemDesenharAparencia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				objeto.setDesenharAparencia(menuItemDesenharAparencia.isSelected());
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
				reorganizar();
			}
		});

		menuItemDesenharObservacao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				objeto.setDesenharObservacao(menuItemDesenharObservacao.isSelected());
				reorganizar();
			}
		});

		menuItemDesenharLimite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Constantes.DESENHAR_LIMITE = menuItemDesenharLimite.isSelected();
				repaint();
			}
		});

		menuItemDestacadoRaiz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Constantes.DESTACAR_RAIZ = menuItemDestacadoRaiz.isSelected();
				repaint();
			}
		});

		menuItemAlinhamentoSuperior.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (menuItemAlinhamentoSuperior.isSelected()) {
					Constantes.ALINHAMENTO = Constantes.APARENCIA_ACIMA;
					reorganizar();
				}
			}
		});

		menuItemAlinhamentoInferior.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (menuItemAlinhamentoInferior.isSelected()) {
					Constantes.ALINHAMENTO = Constantes.APARENCIA_ABAIXO;
					reorganizar();
				}
			}
		});

		menuItemAlinhamentoMeio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (menuItemAlinhamentoMeio.isSelected()) {
					Constantes.ALINHAMENTO = Constantes.APARENCIA_MEIO;
					reorganizar();
				}
			}
		});

		menuItemDesenharFonte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Constantes.DESENHAR_FONTE = menuItemDesenharFonte.isSelected();
				repaint();
			}
		});

		menuItemComentarioEmFilho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				if (objeto.getComentario().length() > 0) {

					if (objeto instanceof InstanciaRaiz) {
						int resp = JOptionPane.showConfirmDialog(formulario, Strings.get("msg_lado_direito"),
								Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);
						boolean direito = JOptionPane.OK_OPTION == resp;

						Instancia filho = new Instancia(objeto.getComentario(), !direito);
						((InstanciaRaiz) objeto).adicionarInstancia(filho);
					} else {
						Instancia filho = new Instancia(objeto.getComentario());
						objeto.adicionar(filho);
					}

					objeto.setComentario(null);
					reorganizar();
				}
			}
		});

		menuItemComentarioEmObservacao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				if (objeto.getComentario().length() > 0) {
					objeto.setObservacao(objeto.getComentario());
					objeto.setDesenharObservacao(objeto.isDesenharComentario());
					objeto.setDesenharComentario(false);
					objeto.setComentario(null);
					reorganizar();
				}
			}
		});

		menuItemObservacaoEmComentario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				if (objeto.getObservacao().length() > 0) {
					objeto.setComentario(objeto.getObservacao());
					objeto.setDesenharComentario(objeto.isDesenharObservacao());
					objeto.setDesenharObservacao(false);
					objeto.setObservacao(null);
					reorganizar();
				}
			}
		});

		menuItemObservacaoEmFilho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				if (objeto.getObservacao().length() > 0) {

					if (objeto instanceof InstanciaRaiz) {
						int resp = JOptionPane.showConfirmDialog(formulario, Strings.get("msg_lado_direito"),
								Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);
						boolean direito = JOptionPane.OK_OPTION == resp;

						Instancia filho = new Instancia(objeto.getObservacao(), !direito);
						((InstanciaRaiz) objeto).adicionarInstancia(filho);
					} else {
						Instancia filho = new Instancia(objeto.getObservacao());
						objeto.adicionar(filho);
					}

					objeto.setObservacao(null);
					reorganizar();
				}
			}
		});

		menuItemComentarioEmPai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null || objeto.getPai() == null) {
					return;
				}

				if (objeto.getComentario().length() > 0) {
					Instancia pai = objeto.getPai();
					int indice = pai.getIndice(objeto);

					Instancia novoPai = new Instancia(objeto.getComentario(), pai.isEsquerdo());
					novoPai.adicionar(objeto);

					pai.adicionar(novoPai, indice);

					objeto.setComentario(null);
					reorganizar();
				}
			}
		});

		menuItemObservacaoEmPai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null || objeto.getPai() == null) {
					return;
				}

				if (objeto.getObservacao().length() > 0) {
					Instancia pai = objeto.getPai();
					int indice = pai.getIndice(objeto);

					Instancia novoPai = new Instancia(objeto.getObservacao(), pai.isEsquerdo());
					novoPai.adicionar(objeto);

					pai.adicionar(novoPai, indice);

					objeto.setObservacao(null);
					reorganizar();
				}
			}
		});

		menuItemComentarioVirar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null || objeto.getPai() == null) {
					return;
				}

				if (!objeto.estaVazio()) {
					int resp = JOptionPane.showConfirmDialog(formulario, Strings.get("msg_contem_filhos"),
							Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);

					if (JOptionPane.OK_OPTION != resp) {
						return;
					}
				}

				Instancia pai = objeto.getPai();
				if (pai.getPai() instanceof InstanciaRaiz) {
					pai.getPai().setComentario(objeto.getDescricao());
					pai.getPai().setDesenharComentario(true);
				} else {
					pai.setComentario(objeto.getDescricao());
					pai.setDesenharComentario(true);
				}
				pai.excluir(objeto);
				reorganizar();
			}
		});

		menuItemObservacaoVirar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null || objeto.getPai() == null) {
					return;
				}

				if (!objeto.estaVazio()) {
					int resp = JOptionPane.showConfirmDialog(formulario, Strings.get("msg_contem_filhos"),
							Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);

					if (JOptionPane.OK_OPTION != resp) {
						return;
					}
				}

				Instancia pai = objeto.getPai();
				if (pai.getPai() instanceof InstanciaRaiz) {
					pai.getPai().setObservacao(objeto.getDescricao());
					pai.getPai().setDesenharObservacao(true);
				} else {
					pai.setObservacao(objeto.getDescricao());
					pai.setDesenharObservacao(true);
				}
				pai.excluir(objeto);
				reorganizar();
			}
		});

		menuItemComplemento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				String valor = JOptionPane.showInputDialog(formulario, objeto.getDescricao(),
						objeto.getAlturaComplementar());

				if (Util.estaVazio(valor)) {
					return;
				}

				try {
					objeto.setAlturaComplementar(Integer.parseInt(valor));
					reorganizar();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(formulario, ex.getMessage());
				}
			}
		});

		menuItemMargemSuperior.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				String valor = JOptionPane.showInputDialog(formulario, objeto.getDescricao(),
						objeto.getMargemSuperior());

				if (Util.estaVazio(valor)) {
					return;
				}

				try {
					int i = Integer.parseInt(valor);
					if (i >= 0) {
						objeto.setMargemSuperior(i);
						reorganizar();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(formulario, ex.getMessage());
				}
			}
		});

		menuItemMargemInferior.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				String valor = JOptionPane.showInputDialog(formulario, objeto.getDescricao(),
						objeto.getMargemInferior());

				if (Util.estaVazio(valor)) {
					return;
				}

				try {
					int i = Integer.parseInt(valor);
					if (i >= 0) {
						objeto.setMargemInferior(i);
						reorganizar();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(formulario, ex.getMessage());
				}
			}
		});

		menuItemSelecionadoLinha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String valor = JOptionPane.showInputDialog(formulario, Strings.get("label_selecionado_linha"),
						((BasicStroke) Constantes.STROKE_SELECIONADO).getLineWidth());

				if (Util.estaVazio(valor)) {
					return;
				}

				try {
					float f = Float.parseFloat(valor);
					if (f > 0) {
						Constantes.STROKE_SELECIONADO = new BasicStroke(f);
						reorganizar();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(formulario, ex.getMessage());
				}
			}
		});

		menuItemBordaLinha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String valor = JOptionPane.showInputDialog(formulario, Strings.get("label_borda_linha"),
						((BasicStroke) Constantes.STROKE_PADRAO).getLineWidth());

				if (Util.estaVazio(valor)) {
					return;
				}

				try {
					float f = Float.parseFloat(valor);
					if (f > 0) {
						Constantes.STROKE_PADRAO = new BasicStroke(f);
						reorganizar();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(formulario, ex.getMessage());
				}
			}
		});

		menuItemLarguraPadao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String valor = JOptionPane.showInputDialog(formulario, Strings.get("label_largura_padrao"),
						Constantes.LARGURA_PADRAO);

				if (Util.estaVazio(valor)) {
					return;
				}

				try {
					Constantes.LARGURA_PADRAO = Integer.parseInt(valor);
					Constantes.USAR_LARGURA_PADRAO = Constantes.LARGURA_PADRAO > 0;
					if (!Constantes.USAR_LARGURA_PADRAO) {
						Constantes.LARGURA_PADRAO = 0;
					}
					reorganizar();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(formulario, ex.getMessage());
				}
			}
		});

		menuItemAlturaPadao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String valor = JOptionPane.showInputDialog(formulario, Strings.get("label_altura_padrao"),
						Constantes.ALTURA_PADRAO);

				if (Util.estaVazio(valor)) {
					return;
				}

				try {
					int alturaPadrao = Integer.parseInt(valor);
					if (alturaPadrao > Constantes.APARENCIA_ALTURA_PADRAO) {
						Constantes.ALTURA_PADRAO = alturaPadrao;
						reorganizar();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(formulario, ex.getMessage());
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

				int resp = showConfirmDialog(objeto);

				if (JOptionPane.OK_OPTION == resp && objeto.excluirAcima()) {
					reorganizar();
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

				int resp = showConfirmDialog(objeto);

				if (JOptionPane.OK_OPTION == resp && objeto.excluirAbaixo()) {
					reorganizar();
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

				int resp = showConfirmDialog(objeto);

				if (JOptionPane.OK_OPTION == resp && objeto.excluirOutros()) {
					reorganizar();
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

				int resp = showConfirmDialog(objeto);

				if (JOptionPane.OK_OPTION == resp && objeto.getPai() != null) {
					if (objeto.getPai().excluir(objeto)) {
						reorganizar();
					}
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

				int resp = showConfirmDialog(null);

				if (JOptionPane.OK_OPTION == resp) {
					objeto.limpar();
					reorganizar();
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

				int resp = showConfirmDialog(objeto);

				if (JOptionPane.OK_OPTION == resp && objeto.sairDaHierarquia()) {
					reorganizar();
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

				try {
					copiado = objeto.clonar();
				} catch (Exception ex) {
					mensagem("msg_objeto_nao_copiado");
				}
			}
		});

		menuItemCopiarCor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					corCopiado = null;
					return;
				}

				corCopiado = objeto.getCor();
			}
		});

		menuItemCopiarSFilhos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					copiado = null;
					return;
				}

				try {
					copiado = objeto.clonar();
					copiado.limpar();
				} catch (Exception ex) {
					mensagem("msg_objeto_nao_copiado");
				}
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

				try {
					copiado = objeto.clonar();
					if (objeto.getPai() != null && objeto.getPai().excluir(objeto)) {
						reorganizar();
					}
				} catch (Exception ex) {
					mensagem("msg_objeto_nao_recortado");
				}
			}
		});

		menuItemRecortarSFilhos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					copiado = null;
					return;
				}

				try {
					copiado = objeto.clonar();
					copiado.limpar();
					if (objeto.getPai() != null && objeto.getPai().excluir(objeto)) {
						reorganizar();
					}
				} catch (Exception ex) {
					mensagem("msg_objeto_nao_recortado");
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

				Instancia instancia = copiado.clonar();

				if (objeto instanceof InstanciaRaiz) {
					int resp = JOptionPane.showConfirmDialog(formulario, Strings.get("msg_lado_direito"),
							Strings.get("label_atencao"), JOptionPane.YES_NO_OPTION);
					boolean direito = JOptionPane.OK_OPTION == resp;

					instancia.setEsquerdo(!direito);
					((InstanciaRaiz) objeto).adicionarInstancia(instancia);
					instancia.replicarLado();
				} else {
					objeto.adicionar(instancia);
					instancia.replicarLado();
				}

				reorganizar();
			}
		});

		menuItemColarPai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null || objeto.getPai() == null || copiado == null) {
					return;
				}

				Instancia pai = objeto.getPai();
				int indice = pai.getIndice(objeto);

				Instancia novoPai = copiado.clonar();
				novoPai.adicionar(objeto);

				pai.adicionar(novoPai, indice);
				pai.replicarLado();
				reorganizar();
			}
		});

		menuItemColarPaiPonta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null || objeto.getPai() == null || copiado == null) {
					return;
				}

				Instancia pai = objeto.getPai();
				int indice = pai.getIndice(objeto);

				Instancia novoPai = copiado.clonar();
				Instancia ponta = novoPai.getPonta();
				ponta.adicionar(objeto);

				pai.adicionar(novoPai, indice);
				pai.replicarLado();
				reorganizar();
			}
		});

		menuItemPrimeiro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto == null) {
					return;
				}

				if (objeto.primeiro()) {
					reorganizar();
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

				if (objeto.subir()) {
					reorganizar();
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

				if (objeto.descer()) {
					reorganizar();
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

				if (objeto.ultimo()) {
					reorganizar();
				}
			}
		});

		menuItemColarCor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCor(procurar(), corCopiado);
			}
		});

		menuItemVermelho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCor(procurar(), new Color(255, 50, 50));
			}
		});

		menuItemVerde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCor(procurar(), new Color(0, 150, 0));
			}
		});

		menuItemLaranja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCor(procurar(), new Color(255, 170, 70));
			}
		});

		menuItemAmarelo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCor(procurar(), new Color(240, 210, 100));
			}
		});

		menuItemAzul.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCor(procurar(), new Color(80, 80, 255));
			}
		});

		menuItemPreto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCor(procurar(), new Color(78, 78, 78));
			}
		});

		menuItemCinza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCor(procurar(), new Color(180, 180, 180));
			}
		});

		menuItemPadrao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCor(procurar(), null);
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

		menuItemSelecionarCor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Instancia objeto = procurar();

				if (objeto != null) {
					class OK implements ActionListener {
						JColorChooser chooser;

						public OK(JColorChooser c) {
							chooser = c;
						}

						public void actionPerformed(ActionEvent e) {
							Color cor = chooser.getColor();
							objeto.setCor(cor);
						}
					}
					JColorChooser colorChooser = new JColorChooser(
							objeto.getCor() != null ? objeto.getCor() : Color.ORANGE);
					JDialog dialog = JColorChooser.createDialog(formulario, Strings.get("label_selecionar_cor"), true,
							colorChooser, new OK(colorChooser), null);
					dialog.setVisible(true);
				}
			}
		});

		menuItemCorSelecionado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				class OK implements ActionListener {
					JColorChooser chooser;

					public OK(JColorChooser c) {
						chooser = c;
					}

					public void actionPerformed(ActionEvent e) {
						Color cor = chooser.getColor();
						if (cor != null) {
							Constantes.COR_SELECIONADO = cor;
						}
					}
				}

				JColorChooser colorChooser = new JColorChooser(Constantes.COR_SELECIONADO);
				JDialog dialog = JColorChooser.createDialog(formulario, Strings.get("label_selecionado"), true,
						colorChooser, new OK(colorChooser), null);
				dialog.setVisible(true);
			}
		});

		menuItemCorLimite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				class OK implements ActionListener {
					JColorChooser chooser;

					public OK(JColorChooser c) {
						chooser = c;
					}

					public void actionPerformed(ActionEvent e) {
						Color cor = chooser.getColor();
						if (cor != null) {
							Constantes.COR_LIMITE = cor;
							repaint();
						}
					}
				}

				JColorChooser colorChooser = new JColorChooser(Constantes.COR_LIMITE);
				JDialog dialog = JColorChooser.createDialog(formulario, Strings.get("label_limite"), true, colorChooser,
						new OK(colorChooser), null);
				dialog.setVisible(true);
			}
		});

		menuItemCorFundo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				class OK implements ActionListener {
					JColorChooser chooser;

					public OK(JColorChooser c) {
						chooser = c;
					}

					public void actionPerformed(ActionEvent e) {
						Color cor = chooser.getColor();
						if (cor != null) {
							Constantes.COR_FUNDO = cor;
							setBackground(Constantes.COR_FUNDO);
						}
					}
				}

				JColorChooser colorChooser = new JColorChooser(Constantes.COR_FUNDO);
				JDialog dialog = JColorChooser.createDialog(formulario, Strings.get("label_fundo"), true, colorChooser,
						new OK(colorChooser), null);
				dialog.setVisible(true);
			}
		});

		menuItemCorBorda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				class OK implements ActionListener {
					JColorChooser chooser;

					public OK(JColorChooser c) {
						chooser = c;
					}

					public void actionPerformed(ActionEvent e) {
						Color cor = chooser.getColor();
						if (cor != null) {
							Constantes.COR_BORDA = cor;
							repaint();
						}
					}
				}

				JColorChooser colorChooser = new JColorChooser(Constantes.COR_BORDA);
				JDialog dialog = JColorChooser.createDialog(formulario, Strings.get("label_selecionar_cor"), true,
						colorChooser, new OK(colorChooser), null);
				dialog.setVisible(true);
			}
		});

		menuItemMinimizarTodos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				raiz.minMaxTodos(true);
				reorganizar();
			}
		});

		menuItemMaximizarTodos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				raiz.minMaxTodos(false);
				reorganizar();
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
				reorganizar();
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
				reorganizar();
			}
		});

		menuItemGerarImagem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int i = fileChooser.showSaveDialog(formulario);

				if (i == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();

					if (file != null) {
						BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

						Graphics2D g2 = bi.createGraphics();
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setColor(Constantes.COR_FUNDO);
						g2.fillRect(0, 0, getWidth(), getHeight());
						g2.setColor(Constantes.COR_BORDA);
						g2.setFont(getFont());

						raiz.desenhar(g2, g2.getColor());

						try {
							ImageIO.write(bi, "png", file);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(formulario, e1.getMessage());
						}
					}
				}
			}
		});
	}

	private void animar(Instancia i, boolean maximizar, int velocidade) {
		if (maximizar) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					AtomicBoolean atom = new AtomicBoolean();
					i.podeMaximizar(atom);

					while (atom.get()) {
						reorganizar();
						if (i.isEsquerdo()) {
							formulario.maxScroll(0);
						} else {
							formulario.maxScroll(Painel.this.getWidth());
						}
						try {
							Thread.sleep(velocidade);
						} catch (Exception e) {
							e.printStackTrace();
						}

						atom.set(false);
						i.podeMaximizar(atom);
					}

					animando = false;
				}
			}).start();
		} else {
			new Thread(new Runnable() {
				@Override
				public void run() {
					AtomicBoolean atom = new AtomicBoolean();
					i.podeMinimizar(atom);

					while (atom.get()) {
						reorganizar();
						formulario.maxScroll(i.getLocalizacao().getX());
						try {
							Thread.sleep(velocidade);
						} catch (Exception e) {
							e.printStackTrace();
						}

						atom.set(false);
						i.podeMinimizar(atom);
					}

					animando = false;
				}
			}).start();
		}
	}

	private void setCor(Instancia i, Color cor) {
		if (i != null) {
			i.setCor(cor);
			repaint();
		}
	}

	public void setArquivo(String arq) {
		arquivo = ArquivoUtil.semSufixo(arq);

		if (!Util.estaVazio(arquivo)) {
			arquivo += ArquivoUtil.SUFIXO;
			abrirArquivo();
		} else {
			arquivo = null;
		}
	}

	private void abrirArquivo() {
		try {
			File file = new File(arquivo);

			if (!file.exists()) {
				JOptionPane.showMessageDialog(formulario, "Inexistente!");
				return;
			}

			raiz = ArquivoUtil.lerArquivo(file);
			setBackground(Constantes.COR_FUNDO);
			reorganizar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(formulario, e.getMessage());
		}
	}

	public void salvarArquivo(String alternativo) {
		if (!Util.estaVazio(alternativo)) {
			arquivo = ArquivoUtil.semSufixo(alternativo);
			arquivo += ArquivoUtil.SUFIXO;
		}

		if (Util.estaVazio(arquivo) || raiz == null) {
			return;
		}

		try {
			ArquivoUtil.salvarArquivo(raiz, new File(arquivo));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(formulario, e.getMessage());
		}
	}

	private Instancia procurar() {
		return raiz.procurar(localizacao.getX(), localizacao.getY());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (!raiz.processado) {
			return;
		}

		Graphics2D g2 = (Graphics2D) g;

		g2.setStroke(Constantes.STROKE_PADRAO);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		raiz.desenhar(g2, g2.getColor());
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
			processar(sb.toString());
			reorganizar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(formulario, e.getMessage());
		}
	}

	private void processar(String sql) throws Exception {
		if (Util.estaVazio(sql)) {
			return;
		}
		SQLUtil.importar(raiz, sql);
	}

	private void mensagem(String chave) {
		JOptionPane.showMessageDialog(formulario, Strings.get(chave));
	}
}