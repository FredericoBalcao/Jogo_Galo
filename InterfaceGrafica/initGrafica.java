package InterfaceGrafica;

import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import interfaces.*;
import client.*;
import javax.swing.*;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */

/**
 * classe que controla os eventos de ação (action event) feitos pelo jogador
 * implementa todos métodos definidos para o action listener
 * chama a interface que verifica o estado do jogador, consoante um determinado estado do jogador assim será invocado um
 * determinado método sobre a interface gráfica
 */

public class initGrafica implements Alertas {
	private MenuPrincipal menuP;
	private MenuSecundario menuS;
	private TabuleiroJogo tabuleiroJogo;
	private TicTacToeClient client; //para receber a lista de jogos
	private String playerNickName;
    private String estadoJogador;
    private TicTacToeServer server;
    private Jogador pl;
    private DefaultListModel listModel;

    //icone X ou O
    public enum Botoes {
        X, O;
    }

	public initGrafica() {
		menuP = new MenuPrincipal(this);
		menuS = new MenuSecundario(this);
		tabuleiroJogo = new TabuleiroJogo(this);
	}

	public void setMenuPrincipal(boolean value) {
		this.menuP.setVisible(value);
	}

	public void setMenuSecundario(boolean value) {
		this.menuS.setTitle(playerNickName);
		this.menuS.setVisible(value);
	}

	public void setTabuleiroJogo(boolean value) {
		this.tabuleiroJogo = new TabuleiroJogo(this);
		this.tabuleiroJogo.setTitle(playerNickName);
		this.tabuleiroJogo.setVisible(value);
	}

	public void sairActionPerformed(ActionEvent e, boolean logout) {
		try {
			if (logout) {
				client.logoutPlayer(playerNickName);
				client.removeJogo();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		} finally {
			menuP.close();
			menuS.close();
			tabuleiroJogo.close();
			System.exit(0);
		}
	}


	public void initToListActionPerformed(ActionEvent e, String nickname) {
		try {
			client = new TicTacToeClient();
			client.adicionaAlerta(this);
			if (!client.registarPlayer(nickname)) //se já existir um jogador com o mesmo nickname ele não regista
				menuP.msgNicknameIgual();
			else {
				playerNickName = client.getPlayer().getName();
                estadoJogador = client.getPlayer().getEstado();
                menuP.setVisible(false);
				setMenuSecundario(true);
				ArrayList<String> c = client.receiveList(); //recebe a lista de jogos
                menuS.mostraJogos(c);
			}
		} catch (MalformedURLException e1) {
			menuP.msgConexaoErro();
			e1.printStackTrace();
		} catch (RemoteException e2) {
			menuP.msgConexaoErro();
			e2.printStackTrace();
		} catch (NotBoundException e3) {
			menuP.msgConexaoErro();
			e3.printStackTrace();
		}

	}

	public void addGameActionPerformed(ActionEvent e) {
		try {
			String idJogo = client.getPlayer().getName();
			client.criaJogo(idJogo);
			menuS.ativaBotoes(false, true, false);
			menuS.msgJogoCriado();
			refreshListActionPerformed(e);
		} catch (MalformedURLException e1) {
			menuS.msgErroConexao();
			e1.printStackTrace();
		} catch (RemoteException e2) {
			menuS.msgErroConexao();
			e2.printStackTrace();
		} catch (NotBoundException e3) {
			menuS.msgErroConexao();
			e3.printStackTrace();
		}

	}


	public void removeJogoActionPermorfed(ActionEvent e) {
		try {
			ArrayList<String> c = client.removeJogo();
			menuS.ativaBotoes(true, false, true);
			menuS.msgJogoRemovido();
			menuS.mostraJogos(c);
		} catch (MalformedURLException e1) {
			menuS.msgErroConexao();
			e1.printStackTrace();
		} catch (RemoteException e2) {
			menuS.msgErroConexao();
			e2.printStackTrace();
		} catch (NotBoundException e3) {
			menuS.msgErroConexao();
			e3.printStackTrace();
		}

	}

	public void refreshListActionPerformed(ActionEvent e) {
		try {
			ArrayList<String> c = client.receiveList();
			menuS.mostraJogos(c);
			if (c.size() == 0)
				menuS.msgSemJogos();
		} catch (MalformedURLException e1) {
			menuS.msgErroConexao();
			e1.printStackTrace();
		} catch (RemoteException e2) {
			menuS.msgErroConexao();
			e2.printStackTrace();
		} catch (NotBoundException e3) {
			menuS.msgErroConexao();
			e3.printStackTrace();
		}
	}

    public void refreshListPlayerActionPerformed(ActionEvent e) throws RemoteException, NotBoundException, MalformedURLException {
            ArrayList<String> s = client.recevelistaJogadores();

            for(int i = 0; i < s.size(); i++){
                if ((!(s.get(i).toString().equals(client.getPlayer().getName()))) && s.size() != 0){
                    menuS.mostraJogadores(s.get(i));
            }else{
                    menuS.msgDisponiveis();
                }
        }
    }

    public void alterarEstado(ActionEvent e) {
         if(client.getPlayer().getEstado().equals("disponivel")){
            client.getPlayer().setEstado("indisponivel");
             System.out.println("estado do jogador = " + client.getPlayer().getEstado());
         }else{
             client.getPlayer().setEstado("disponivel");
             System.out.println("estado do jogador = " + client.getPlayer().getEstado());
         }
    }

	public void entrarNoJogoActionPermorfed(ActionEvent e, String idJogo) {
		try {
			client.entrarNoJogo(idJogo);
            client.getPlayer().setEstado("ocupado");
		} catch (MalformedURLException e1) {
			menuS.msgErroConexao();
			e1.printStackTrace();
		} catch (RemoteException e2) {
			menuS.msgErroConexao();
			e2.printStackTrace();
		} catch (NotBoundException e3) {
			menuS.msgErroConexao();
			e3.printStackTrace();
		}

	}

	public void leaveGameActionPerformed(ActionEvent e) {
		try {
			ArrayList<String> c = client.removeJogo();
			tabuleiroJogo.close();
			client.resetmatriz();
			menuS.setVisible(true);
			menuS.ativaBotoes(true, false, true);
			menuS.mostraJogos(c);
		} catch (MalformedURLException e1) {
			menuS.msgErroConexao();
			e1.printStackTrace();
		} catch (RemoteException e2) {
			menuS.msgErroConexao();
			e2.printStackTrace();
		} catch (NotBoundException e3) {
			menuS.msgErroConexao();
			e3.printStackTrace();
		}

	}

	public void clickBotaoActionPerformed(ActionEvent e) {
		int button = Integer.parseInt(e.getActionCommand());
		Botoes l;

		if (client.criadorJogo())
			l = Botoes.X;
		else
			l = Botoes.O;

		tabuleiroJogo.setBotao(button, l);

		try {
			client.enviaMov(button);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}


	@Override
	public void atualiza() {
		switch (client.getalerta()) {
		case JOGO_INICIADO:
			menuS.close();
			menuP.close();
			setTabuleiroJogo(true);
			break;

		case SUA_VEZ:
			ativaBotoes(client.getmatriz(), true);
			break;

		case VEZ_ADV:
			ativaBotoes(client.getmatriz(), false);
			break;

		case ADV_DESCONECTADO:
			tabuleiroJogo.msgAdvSaiu();
			tabuleiroJogo.desativaBotoes();
			break;

		case ADV_MOVE:
			client.recebeMov();
			int move = client.getPlayer().getadv_jogada_selecionada();

			Botoes l;
			if (client.criadorJogo()) {
				if (client.getmatriz()[move / TabuleiroJogo.M][move % TabuleiroJogo.M] == Jogo.J1_CEL)
					l = Botoes.X;
				else
					l = Botoes.O;
			} else {
				if (client.getmatriz()[move / TabuleiroJogo.M][move % TabuleiroJogo.M] == Jogo.J1_CEL)
					l = Botoes.O;
				else
					l = Botoes.X;
			}

			tabuleiroJogo.setBotao(move, l);
			client.getPlayer().minhaVez();
			break;

		case EMPATE:
			tabuleiroJogo.desativaBotoes();

			tabuleiroJogo.msgEmpate();
			break;

		case VENCEU:
			tabuleiroJogo.desativaBotoes();

			tabuleiroJogo.msgVencedor();
			break;

		case PERDEU:
			tabuleiroJogo.desativaBotoes();

			tabuleiroJogo.msgPerdeu();
			break;


		}

	}

	private void ativaBotoes(int m[][], boolean enable) {
		for (int i = 0; i < TabuleiroJogo.M; i++)
			for (int j = 0; j < TabuleiroJogo.M; j++)
				if (m[i][j] == Jogo.CEL_LIVRE)
					tabuleiroJogo.ativaBotao(i, j, enable);
	}

}