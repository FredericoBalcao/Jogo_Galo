package server;

import java.rmi.RemoteException;
import java.rmi.server.*;
import interfaces.*;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */
public class JogoDoGaloImpl extends UnicastRemoteObject implements Jogo {
    private static final long serialVersionUID = -4811628104786217427L;
	private static final int NR_MIN_MOV_VENCER = 5;
	private static final int MIN_MOV = 0;
	private static final int MAX_MOV = 8;
	private NrJogador jogadorAtual;
	private JogadorInterface jogador1, jogador2;
	private int nrMovimentos;
	private boolean fimJogo;
	private int m[][];
	private String idJogo;

	public enum NrJogador {
		J1, J2
	}

	//p1 é o jogador que cria o desafio
	public JogoDoGaloImpl(JogadorInterface p1, String id) throws RemoteException {
		m = new int[DIM_MATRIZ][DIM_MATRIZ];
		this.jogador1 = p1;
		nrMovimentos = 0;
		this.idJogo = id;
	}

	public String getidJogo() {
		return idJogo;
	}


	public void setidJogo(String id) {
		this.idJogo = id;
	}


	public boolean fimJogo() {
		return fimJogo;
	}


	public void setfimJogo(boolean fimJogo) {
		this.fimJogo = fimJogo;
	}


	public JogadorInterface getjogador1() {
		return jogador1;
	}


	public void setjogador1(JogadorInterface jogador1) {
		this.jogador1 = jogador1;
	}

	public void setjogador2(JogadorInterface jogador2) throws RemoteException {
		this.jogador2 = jogador2;
		comecarJogo();
	}


	public JogadorInterface getjogador2() {
		return jogador2;
	}


	public NrJogador getjogadorAtual() {
		return jogadorAtual;
	}

	@Override
	public synchronized void enviaJogada(JogadorInterface j, int p) throws RemoteException {
		if (!fimJogo && p >= MIN_MOV && p <= MAX_MOV) {
			int res = -1;
			if (j.equals(jogador1))
				verificaJogada(jogador1, jogador2, NrJogador.J1, p);
			else if (j.equals(jogador2))
				verificaJogada(jogador2, jogador1, NrJogador.J2, p);

			if (nrMovimentos >= NR_MIN_MOV_VENCER)
				res = verificaVencedor();

            if (res > 0)
				notificaResultado(res);
		}

	}


	private int verificaVencedor() {
		int matriz = 0; //matriz
		int resultado = 0;
		int i;
		boolean vencedor = false;

		//para linhas
		for (i = 0; i < DIM_MATRIZ; i++) {
			if ((m[i][0] == m[i][1]) && (m[i][1] == m[i][2])) {
				matriz = m[i][0];
				if (matriz != Jogo.CEL_LIVRE) {
					vencedor = true;
					resultado = matriz;
				}
			}
		}
		if (!vencedor) {
			//para colunas
			for (i = 0; i < DIM_MATRIZ; i++) {
				if ((m[0][i] == m[1][i]) && (m[1][i] == m[2][i])) {
					matriz = m[0][i];
					if (matriz != Jogo.CEL_LIVRE) {
						vencedor = true;
						resultado = matriz;
					}
				}
			}
		}
		//para diagonais
		if (!vencedor) {
			if ((m[0][0] == m[1][1] && m[1][1] == m[2][2])
					|| (m[0][2] == m[1][1] && m[1][1] == m[2][0])) {
				matriz = m[1][1];
				if (matriz != Jogo.CEL_LIVRE) {
					vencedor = true;
					resultado = matriz;
				}
			}
		}
		//verifica se o jogo terminou ou se é empate
		if (!vencedor) {
			resultado = 3;
			for (i = 0; i < DIM_MATRIZ; i++) {
				for (int j = 0; j < DIM_MATRIZ; j++) {
					if (m[j][i] == 0)
						resultado = 0;
				}
			}
		}
		return resultado;
	}

	private boolean atualizaMatriz(int matriz, int val) {
		boolean atualiza = false;
		int i = (int) matriz / DIM_MATRIZ;
		int j = (int) matriz % DIM_MATRIZ;
		if (m[i][j] == 0) {
			m[i][j] = val;
			atualiza = true;
		}
		return atualiza;
	}


	private void verificaJogada(JogadorInterface p1, JogadorInterface p2, NrJogador jogada, int p) throws RemoteException {
		int i;

		if (jogadorAtual.equals(NrJogador.J1)) {
			//Se o atual jogador é j1, corresponde ao movimento dele na matriz
			i = J1_CEL;
		} else {
            //Se o atual jogador é j1, corresponde ao movimento dele na matriz
			i = J2_CEL;
		}

		if (atualizaMatriz(p, i)) {
			nrMovimentos++;
			p2.adversarioJogada(p);
			if (jogada.equals(NrJogador.J1))
				jogadorAtual = NrJogador.J2;
			else if (jogada.equals(NrJogador.J2))
				jogadorAtual = NrJogador.J1;
			p1.vezAdversario();
			p2.minhaVez();
		}
	}

	private void notificaResultado(int res) throws RemoteException {
		if (res == 1) {
			jogador1.enviaResultadoJogo(Estados.VENCEU);
			jogador2.enviaResultadoJogo(Estados.PERDEU);
		} else if (res == 2) {
			jogador1.enviaResultadoJogo(Estados.PERDEU);
			jogador2.enviaResultadoJogo(Estados.VENCEU);
		} else {
			jogador1.enviaResultadoJogo(Estados.EMPATE);
			jogador2.enviaResultadoJogo(Estados.EMPATE);
		}
		
		fimJogo = true;
	}

    /**
     * envia avisos para os dois jogadores para avisá-los de que o jogo é para começar
     * e atribui a vez aleatoriamente para um dos dois jogadores para que seja um deles a dar o primeiro passo
     * @throws RemoteException
     */
	private void comecarJogo() throws RemoteException {
		//adiciona os dois jogadores no jogo
		jogador1.entrarNoJogo(this);
		jogador2.entrarNoJogo(this);

        //assume o jogador atual (falta condição switch)
		jogadorAtual = NrJogador.J1;
		jogador2.vezAdversario();
		jogador1.minhaVez();
		jogadorAtual = NrJogador.J2;
		jogador1.vezAdversario();
		jogador2.minhaVez();
		fimJogo = false;
	}
}
