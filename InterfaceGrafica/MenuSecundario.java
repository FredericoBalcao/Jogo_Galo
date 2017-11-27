package InterfaceGrafica;

import client.TicTacToeClient;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.border.LineBorder;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */

public class MenuSecundario extends JogoGrafica {
	private static final String JOGO_CRIADO = "Jogo criado com sucesso";
	private static final String JOGO_REMOVIDO = "Jogo removido com sucesso";
	private static final String SELECIONAR_JOGO = "Tem que selecionar um jogador";
	private static final String SEM_JOGOS = "Nenhum jogo criado na lista de jogos";
    private static final String SEM_DISPONIVEIS = "Nenhum jogador disponivel na lista de jogadores";
	private initGrafica ctrl = null;
    private JButton btnActualizaJogadores;
	private JButton btnCriaJogo;
	private JButton btnRemoveJogo;
	private JButton btnSeleciona;
	private JButton btnActualizaJogos;
    private JButton btnAlterarEstado;
	private JPanel panelListaJogos;
    private ArrayList<String> list;
	private DefaultListModel listModel;
	private JList jlistJogadores;
    private TicTacToeClient client;
	private JMenuItem mnitem2, mnitem3, mnitem4;

	public MenuSecundario(initGrafica ctrl) {
		super();
		this.ctrl = ctrl;
		init();
	}

	public void msgJogoCriado() {
		setStatusBar(JOGO_CRIADO);
	}

	public void msgJogoRemovido() {
		setStatusBar(JOGO_REMOVIDO);
	}

	public void msgSemJogos() {
		setStatusBar(SEM_JOGOS);
	}

    public void msgDisponiveis() {
        setStatusBar(SEM_DISPONIVEIS);
    }

	public void mostraJogos(ArrayList<String> games) {
		list = games;
		listModel.clear();
		for (int i = 0; i < list.size(); i++)
			listModel.add(i, list.get(i));
	}


    public void mostraJogadores(String s) { //repete nicknames se o model não for limpo
        //listModel.clear();
        listModel.addElement(s);
    }

	public void ativaBotoes(boolean addGame, boolean removeJogo, boolean entrarNoJogo) {
		btnCriaJogo.setEnabled(addGame);
		mnitem2.setEnabled(addGame);
		btnRemoveJogo.setEnabled(removeJogo);
		mnitem3.setEnabled(removeJogo);
		btnSeleciona.setEnabled(entrarNoJogo);
		mnitem4.setEnabled(entrarNoJogo);
	}

	private void init() {
		frmJogo.addWindowListener( new WindowAdapter(){
		    public void windowClosing(WindowEvent e){
		        JFrame frame = (JFrame)e.getSource();
		        int sair = JOptionPane.showConfirmDialog(frame, "Deseja sair do jogo?",
		            "Sair",JOptionPane.YES_NO_OPTION);
		        if (sair == JOptionPane.YES_OPTION)
		            ctrl.sairActionPerformed(null, true);
		    }
		});
		panelListaJogos = new JPanel();
		panelListaJogos.setAutoscrolls(true);
		panelListaJogos.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		
		JPanel panelToolBar = new JPanel();

        JLabel labelTabSelect = new JLabel("Selecionar um jogador a desafiar, criar um jogo ou esperar que seja desafiado");
		labelTabSelect.setForeground(Color.BLACK);
		labelTabSelect.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		JPanel panelStatusBar = new JPanel();
		panelStatusBar.setBorder(null);
		GroupLayout groupLayout = new GroupLayout(frmJogo.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(labelTabSelect, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(panelListaJogos, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelToolBar, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
						.addComponent(panelStatusBar, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelTabSelect)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panelListaJogos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelToolBar, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
					.addGap(9)
					.addComponent(panelStatusBar, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		GridBagLayout gbl_panelStatusBar = new GridBagLayout();
		gbl_panelStatusBar.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelStatusBar.rowHeights = new int[]{0, 0};
		gbl_panelStatusBar.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelStatusBar.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelStatusBar.setLayout(gbl_panelStatusBar);

		
		textArea.setText("");
		textArea.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textArea.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 0;
		panelStatusBar.add(textArea, gbc_textArea);
		
		listModel = new DefaultListModel();
		
		JLabel labelOk = new JLabel("");
		labelOk.setEnabled(false);

		GridBagConstraints gbc_labelOk = new GridBagConstraints();
		gbc_labelOk.insets = new Insets(0, 0, 0, 5);
		gbc_labelOk.gridx = 3;
		gbc_labelOk.gridy = 0;
		panelStatusBar.add(labelOk, gbc_labelOk);

		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		panelToolBar.add(toolBar, BorderLayout.NORTH);
		
		btnCriaJogo = new JButton("Criar jogo");
		btnCriaJogo.setHorizontalAlignment(SwingConstants.LEFT);
		btnCriaJogo.setMinimumSize(new Dimension(175, 29));
		btnCriaJogo.setMaximumSize(new Dimension(175, 29));

		toolBar.add(btnCriaJogo);
		btnCriaJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.addGameActionPerformed(e);
			}
		});

		
		btnRemoveJogo = new JButton("Remover jogo");
		btnRemoveJogo.setEnabled(false);
		btnRemoveJogo.setMaximumSize(new Dimension(175, 29));
		btnRemoveJogo.setMinimumSize(new Dimension(175, 29));
		btnRemoveJogo.setHorizontalAlignment(SwingConstants.LEFT);

		toolBar.add(btnRemoveJogo);

        btnRemoveJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.removeJogoActionPermorfed(e);
			}
		});

		btnSeleciona = new JButton("Desafiar jogador");

		btnSeleciona.setMinimumSize(new Dimension(175, 29));
		btnSeleciona.setMaximumSize(new Dimension(175, 29));
		btnSeleciona.setHorizontalAlignment(SwingConstants.LEFT);
		toolBar.add(btnSeleciona);

        btnSeleciona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] index = jlistJogadores.getSelectedIndices();

				    String idJogo=(String)jlistJogadores.getSelectedValue();
				    if (index.length==0)
					    setStatusBar(SELECIONAR_JOGO);
				    else ctrl.entrarNoJogoActionPermorfed(e, idJogo);
                }

		});
		


		btnActualizaJogos = new JButton("Atualizar lista jogos");

        btnActualizaJogos.setMinimumSize(new Dimension(175, 29));
        btnActualizaJogos.setMaximumSize(new Dimension(175, 29));
        btnActualizaJogos.setHorizontalAlignment(SwingConstants.LEFT);
        toolBar.add(btnActualizaJogos);
        btnActualizaJogos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.refreshListActionPerformed(e);
            }
        });

        btnActualizaJogadores = new JButton("Atualizar lista jogadores");

        btnActualizaJogadores.setMinimumSize(new Dimension(175, 29));
        btnActualizaJogadores.setMaximumSize(new Dimension(175, 29));
        btnActualizaJogadores.setHorizontalAlignment(SwingConstants.LEFT);
        toolBar.add(btnActualizaJogadores);
        btnActualizaJogadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ctrl.refreshListPlayerActionPerformed(e);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                } catch (NotBoundException e1) {
                    e1.printStackTrace();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnAlterarEstado = new JButton("Mudar estado jogador");

        btnAlterarEstado.setMinimumSize(new Dimension(175, 29));
        btnAlterarEstado.setMaximumSize(new Dimension(175, 29));
        btnAlterarEstado.setHorizontalAlignment(SwingConstants.LEFT);
        toolBar.add(btnAlterarEstado);
        btnAlterarEstado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    ctrl.alterarEstado(e);
            }
        });

		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.sairActionPerformed(e, true);
			}
		});

		btnSair.setMinimumSize(new Dimension(175, 29));
		btnSair.setMaximumSize(new Dimension(175, 29));
		btnSair.setHorizontalAlignment(SwingConstants.LEFT);
		toolBar.add(btnSair);
		
		GridBagLayout gbl_panelListaJogos = new GridBagLayout();
		gbl_panelListaJogos.columnWidths = new int[]{0, 0};
		gbl_panelListaJogos.rowHeights = new int[]{0, 0};
		gbl_panelListaJogos.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelListaJogos.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelListaJogos.setLayout(gbl_panelListaJogos);
		
		jlistJogadores = new JList(listModel);

        jlistJogadores = new JList(listModel);
        GridBagConstraints gbc_gamesList = new GridBagConstraints();
        gbc_gamesList.fill = GridBagConstraints.BOTH;
        gbc_gamesList.gridx = 0;
        gbc_gamesList.gridy = 0;
        panelListaJogos.add(jlistJogadores, gbc_gamesList);
		
		frmJogo.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmJogo.setJMenuBar(menuBar);
		
		JMenu mn1 = new JMenu("Ficheiro");

		menuBar.add(mn1);
		
		JMenuItem mnitem1 = new JMenuItem("Conectar ao servidor");
		mnitem1.setEnabled(false);
		mn1.add(mnitem1);

		mnitem2 = new JMenuItem("Criar jogo");
		mn1.add(mnitem2);
		mnitem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.addGameActionPerformed(e);
			}
		});
		
		mnitem3 = new JMenuItem("Remover jogo");
		mnitem3.setEnabled(false);
		mn1.add(mnitem3);
		mnitem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.removeJogoActionPermorfed(e);
			}
		});
		
		mnitem4 = new JMenuItem("Desafiar jogador selecionado");
		mn1.add(mnitem4);
		mnitem4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] index = jlistJogadores.getSelectedIndices();
				String idJogo=(String)jlistJogadores.getSelectedValue();
				if (index.length==0)
					setStatusBar(SELECIONAR_JOGO);
				else ctrl.entrarNoJogoActionPermorfed(e, idJogo);
			}
		});


		JMenuItem mnitem5 = new JMenuItem("Atualizar lista jogos");

		mn1.add(mnitem5);
		mnitem5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.refreshListActionPerformed(e);
			}
		});

        JMenuItem mnitem6 = new JMenuItem("Atualizar lista jogadores");

        mn1.add(mnitem6);
        mnitem6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ctrl.refreshListPlayerActionPerformed(e);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                } catch (NotBoundException e1) {
                    e1.printStackTrace();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        });


		JMenuItem mnitem7 = new JMenuItem("Terminar jogo");
		mnitem7.setEnabled(false);

		mn1.add(mnitem7);

		JMenuItem mnitem8 = new JMenuItem("Sair");
		mnitem8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.sairActionPerformed(e, true);
			}
		});

		mn1.add(mnitem8);

        JMenu mn2 = new JMenu("Sobre");

        menuBar.add(mn2);

	}
}