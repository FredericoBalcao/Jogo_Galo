package InterfaceGrafica;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */

public class MenuPrincipal extends JogoGrafica {
    private static final String CONEXAO_MSG = "Conexão com o servidor rejeitada";
    private static final String NICKNAME_IGUAL = "Nickname já selecionado";
    private static final String NICKNAME_TAMANHO = "Nickname tem de ter pelo menos 3 caracteres";
    private static final String NICKNAME_ESPACOS = "Não são permitidos espaços no registo do nickname";
    private static final String INIT_MSG = "Insira um nickname para se registar";

    private JTextField nickname;
    private initGrafica ctrl = null;

    public MenuPrincipal(initGrafica ctrl) {
        super();
        this.ctrl = ctrl;
        init();
    }

    public void msgConexaoErro() {
        super.setStatusBar(CONEXAO_MSG);
    }

    public void msgNicknameIgual() {
        super.setStatusBar(NICKNAME_IGUAL);
    }

    private boolean verificaNickname(String nickname) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        return p.matcher(nickname).matches();
    }

    private void init() {
        frmJogo.addWindowListener( new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                JFrame frame = (JFrame)e.getSource();
                int result = JOptionPane.showConfirmDialog(frame, "Deseja sair do jogo?",
                        "Sair",JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                    ctrl.sairActionPerformed(null, false);
            }
        });

        JPanel panelToolBar = new JPanel();
        JPanel panelStatusBar = new JPanel();
        panelStatusBar.setBorder(null);

        JPanel textfieldPanel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(frmJogo.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(textfieldPanel, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(panelToolBar, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(panelStatusBar, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(textfieldPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(panelToolBar, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)))
                                .addGap(9)
                                .addComponent(panelStatusBar, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(8, Short.MAX_VALUE))
        );
        panelToolBar.setLayout(new BorderLayout(0, 0));

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setOrientation(SwingConstants.VERTICAL);
        panelToolBar.add(toolBar, BorderLayout.NORTH);
        textfieldPanel.setLayout(new BorderLayout(0, 0));


        JButton btnServidor = new JButton("Conectar ao servidor");
        btnServidor.setHorizontalAlignment(SwingConstants.RIGHT);
        btnServidor.setMinimumSize(new Dimension(175, 29));
        btnServidor.setMaximumSize(new Dimension(175, 29));
        btnServidor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = nickname.getText();
                if (str.trim().equals("") || str.trim().length() < 3)
                    setStatusBar(NICKNAME_TAMANHO);
                else {
                    if (verificaNickname(str))
                        ctrl.initToListActionPerformed(e,str);
                    else
                        setStatusBar(NICKNAME_ESPACOS);
                }
            }
        });
        toolBar.add(btnServidor);

        JButton btnSair = new JButton(" Sair");
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.sairActionPerformed(e, false);
            }
        });

        btnSair.setMinimumSize(new Dimension(175, 29));
        btnSair.setMaximumSize(new Dimension(175, 29));
        btnSair.setHorizontalAlignment(SwingConstants.RIGHT);
        toolBar.add(btnSair);

        JLabel lblNicknameLabel = new JLabel("Nickname:");
        lblNicknameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        textfieldPanel.add(lblNicknameLabel, BorderLayout.NORTH);

        nickname = new JTextField(10);
        nickname.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        textfieldPanel.add(nickname, BorderLayout.SOUTH);
        nickname.setColumns(12);
        GridBagLayout gbl_panelStatusBar = new GridBagLayout();
        panelStatusBar.setLayout(gbl_panelStatusBar);

        //textarea definido em JogoGrafica
        textArea.setText("");
        textArea.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        textArea.setBackground(UIManager.getColor("Button.caretForeground"));
        panelStatusBar.add(textArea);

        JLabel labelOk = new JLabel("");
        labelOk.setEnabled(false);
        panelStatusBar.add(labelOk);


        frmJogo.getContentPane().setLayout(groupLayout);

        JMenuBar menuBar = new JMenuBar();
        frmJogo.setJMenuBar(menuBar);

        JMenu mn1 = new JMenu("Ficheiro");
        menuBar.add(mn1);

        JMenuItem mnitem1 = new JMenuItem("Conectar ao servidor");
        mn1.add(mnitem1);
        mnitem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = nickname.getText();
                if (str.trim().equals("") || str.trim().length() < 3)
                    setStatusBar(NICKNAME_TAMANHO);
                else {
                    if (verificaNickname(str))
                        ctrl.initToListActionPerformed(e,str);
                    else
                        setStatusBar(NICKNAME_ESPACOS);
                }
            }
        });

        JMenuItem mnitem2 = new JMenuItem("Criar jogo");
        mnitem2.setEnabled(false);
        mn1.add(mnitem2);

        JMenuItem mnitem3 = new JMenuItem("Remover jogo");
        mnitem3.setEnabled(false);
        mn1.add(mnitem3);

        JMenuItem mnitem4 = new JMenuItem("Selecionar um jogador");
        mnitem4.setEnabled(false);
        mn1.add(mnitem4);

        JMenuItem mnitem5 = new JMenuItem("Atualizar lista jogadores");
        mnitem5.setEnabled(false);

        mn1.add(mnitem5);

        JMenuItem mnitem6 = new JMenuItem("Terminar jogo");
        mnitem6.setEnabled(false);

        mn1.add(mnitem6);

        JMenuItem mnitem7 = new JMenuItem("Sair");
        mnitem7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.sairActionPerformed(e, false);
            }
        });

        mn1.add(mnitem7);

        JMenu mn2 = new JMenu("Sobre");

        menuBar.add(mn2);

        setStatusBar(INIT_MSG);
    }
}