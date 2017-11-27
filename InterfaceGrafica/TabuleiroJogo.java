package InterfaceGrafica;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
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

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */

public class TabuleiroJogo extends JogoGrafica {
    private final static String VENCEU_MSG = "VENCEU";
    private final static String EMPATE_MSG = "EMPATE";
    private final static String PERDEU_MSG = "PERDEU";
    private final static String ADV_DISC_MSG = "Jogador Indisponivel";
    private final static String ADV_SAIU_MSG = "Jogador saiu do jogo";
    private static final String ESPERA_JOGADA_MSG = "Espere pela jogada do adversário";
    private static final String VEZ_MSG = "É a sua vez de jogar";

    public final static int M = 3; //matriz

    private initGrafica ctrl = null;
    private JPanel panelTab;
    private JLabel labelTab;
    private JButton btns[][];

    public TabuleiroJogo(initGrafica ctrl) {
        super();
        this.ctrl = ctrl;
        btns = new JButton[M][M];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < M; j++) {
                btns[i][j] = new JButton("");
                ativaBotao(i, j, true);
            }
        init();
    }


    public void msgVencedor() {
        setStatusBar(VENCEU_MSG);
    }


    public void msgEmpate() {
        setStatusBar(EMPATE_MSG);
    }

    public void msgPerdeu() {
        setStatusBar(PERDEU_MSG);
    }

    public void msgAdvDesconectado() {
        setStatusBar(ADV_DISC_MSG);
    }

    public void msgAdvSaiu() {
        setStatusBar(ADV_SAIU_MSG);
    }


    public void ativaBotao(int i,int j,boolean enable) {
        btns[i][j].setEnabled(enable);
    }

    public void desativaBotoes() {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < M; j++)
                btns[i][j].setEnabled(false);
    }

    public void setBotao(int b, initGrafica.Botoes btn) {
        String label;
        int i, j;

        i = b / M;
        j = b % M;

        if (btn == initGrafica.Botoes.X)
            label = "X";
        else label = "O";

        btns[i][j].setText(label);
        btns[i][j].setEnabled(false);
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
        panelTab = new JPanel();
        panelTab.setAutoscrolls(true);
        panelTab.setBorder(null);

        JPanel panelToolBar = new JPanel();

        labelTab = new JLabel("Jogo Do Galo");
        labelTab.setForeground(Color.BLACK);
        labelTab.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

        JPanel panelStatusBar = new JPanel();
        panelStatusBar.setBorder(null);
        GroupLayout groupLayout = new GroupLayout(frmJogo.getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(labelTab, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                .addComponent(panelTab, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(panelToolBar, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
                        .addComponent(panelStatusBar, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelTab)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(panelTab, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        JLabel labelStatusBat = new JLabel("");
        GridBagConstraints gbc_labelStatusBat = new GridBagConstraints();
        gbc_labelStatusBat.insets = new Insets(0, 0, 0, 5);
        gbc_labelStatusBat.gridx = 0;
        gbc_labelStatusBat.gridy = 0;
        panelStatusBar.add(labelStatusBat, gbc_labelStatusBat);

        textArea.setText("");
        textArea.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        textArea.setBackground(UIManager.getColor("Button.background")); //definido pelas color keys no UIManager
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.insets = new Insets(0, 0, 0, 5);
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 1;
        gbc_textArea.gridy = 0;
        panelStatusBar.add(textArea, gbc_textArea);

        JPanel pane1 = new JPanel();
        pane1.setBorder(null);

        JPanel pane2 = new JPanel();
        pane2.setBorder(null);

        JPanel pane3 = new JPanel();
        pane3.setBorder(null);

        JPanel pane4 = new JPanel();
        pane4.setBorder(null);

        JPanel pane5 = new JPanel();
        pane5.setBorder(null);

        JPanel pane6 = new JPanel();
        pane6.setBorder(null);

        JPanel pane7 = new JPanel();
        pane7.setBorder(null);

        JPanel pane8 = new JPanel();
        pane8.setBorder(null);

        JPanel pane9 = new JPanel();
        pane9.setBorder(null);

        JSeparator separator_3 = new JSeparator();
        GroupLayout gl_panelTab = new GroupLayout(panelTab);
        gl_panelTab.setHorizontalGroup(
                gl_panelTab.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelTab.createSequentialGroup()
                                .addGroup(gl_panelTab.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelTab.createSequentialGroup()
                                                .addComponent(pane1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(pane2, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(pane3, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelTab.createSequentialGroup()
                                                .addComponent(pane4, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(pane5, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(pane6, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelTab.createSequentialGroup()
                                                .addComponent(pane7, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(pane8, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(pane9, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(separator_3, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
        );
        gl_panelTab.setVerticalGroup(
                gl_panelTab.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelTab.createSequentialGroup()
                                .addGroup(gl_panelTab.createParallelGroup(Alignment.LEADING)
                                        .addComponent(pane1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pane2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pane3, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_panelTab.createParallelGroup(Alignment.LEADING)
                                        .addComponent(pane4, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pane5, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pane6, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_panelTab.createParallelGroup(Alignment.LEADING)
                                        .addComponent(pane7, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pane8, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pane9, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(separator_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        pane1.setLayout(new BorderLayout(0, 0));
        pane2.setLayout(new BorderLayout(0, 0));
        pane3.setLayout(new BorderLayout(0, 0));
        pane4.setLayout(new BorderLayout(0, 0));
        pane5.setLayout(new BorderLayout(0, 0));
        pane6.setLayout(new BorderLayout(0, 0));
        pane7.setLayout(new BorderLayout(0, 0));
        pane8.setLayout(new BorderLayout(0, 0));
        pane9.setLayout(new BorderLayout(0, 0));

        pane1.add(btns[0][0], BorderLayout.CENTER);
        pane2.add(btns[0][1], BorderLayout.CENTER);
        pane3.add(btns[0][2], BorderLayout.CENTER);
        pane4.add(btns[1][0], BorderLayout.CENTER);
        pane5.add(btns[1][1], BorderLayout.CENTER);
        pane6.add(btns[1][2], BorderLayout.CENTER);
        pane7.add(btns[2][0], BorderLayout.CENTER);
        pane8.add(btns[2][1], BorderLayout.CENTER);
        pane9.add(btns[2][2], BorderLayout.CENTER);

        panelTab.setLayout(gl_panelTab);

        for (int i = 0, id = 0; i < M; i++) {
            for (int j = 0; j < M; j++, id++) {
                btns[i][j].setActionCommand(Integer.toString(id));
                btns[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        ctrl.clickBotaoActionPerformed(arg0);
                    }
                });
            }
        }

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setOrientation(SwingConstants.VERTICAL);
        panelToolBar.add(toolBar, BorderLayout.NORTH);

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.sairActionPerformed(e,true);
            }
        });

        JButton btnTerminarJogo = new JButton("Terminar jogo");

        btnTerminarJogo.setMinimumSize(new Dimension(175, 29));
        btnTerminarJogo.setMaximumSize(new Dimension(175, 29));
        btnTerminarJogo.setHorizontalAlignment(SwingConstants.LEFT);
        btnTerminarJogo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.leaveGameActionPerformed(e);
            }
        });
        toolBar.add(btnTerminarJogo);

        btnSair.setMinimumSize(new Dimension(175, 29));
        btnSair.setMaximumSize(new Dimension(175, 29));
        btnSair.setHorizontalAlignment(SwingConstants.LEFT);
        toolBar.add(btnSair);

        frmJogo.getContentPane().setLayout(groupLayout);

        JMenuBar menuBar = new JMenuBar();
        frmJogo.setJMenuBar(menuBar);

        JMenu mn1 = new JMenu("Ficheiro");
        menuBar.add(mn1);

        JMenuItem mnitem1 = new JMenuItem("Conectar ao servidor");
        mnitem1.setEnabled(false);

        mn1.add(mnitem1);

        JMenuItem mnitem2 = new JMenuItem("Criar jogo");
        mnitem2.setEnabled(false);

        mn1.add(mnitem1);

        JMenuItem mnitem3 = new JMenuItem("Remover jogo");

        mnitem3.setEnabled(false);
        mn1.add(mnitem3);

        JMenuItem mnitem4 = new JMenuItem("Selecionar jogador");
        mnitem4.setEnabled(false);

        mn1.add(mnitem4);

        JMenuItem mnitem5 = new JMenuItem("Actualizar lista");
        mnitem5.setEnabled(false);

        mn1.add(mnitem5);

        JMenuItem mnitem6 = new JMenuItem("Terminar jogo");
        mnitem6.setEnabled(true);

        mn1.add(mnitem6);
        mnitem6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.leaveGameActionPerformed(e);
            }
        });


        JMenuItem mnitem7 = new JMenuItem("Sair");
        mnitem7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.sairActionPerformed(e,true);
            }
        });

        mn1.add(mnitem7);

        JMenu mn2 = new JMenu("Sobre");

        menuBar.add(mn2);
    }

}