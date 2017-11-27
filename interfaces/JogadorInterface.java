package interfaces;

import java.rmi.*;


/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */
public interface JogadorInterface extends Remote {

    /**
     * subscrição de um jogador num determinado jogo
     * @param j
     * @throws RemoteException
     */
	void entrarNoJogo(Jogo j) throws RemoteException;

    /**
     * notifica se é a vez do jogador atual
     * @throws RemoteException
     */
	void minhaVez() throws RemoteException;

    /**
     * notifica se é a vez do adversário a jogar
     * @throws RemoteException
     */
	void vezAdversario() throws RemoteException;

    /**
     * testa a ligação de um jogador, para eventuais interrupções
     * @throws RemoteException
     */
	void conectaJogador() throws RemoteException;

    /**
     * notifica que um adversário está desconetado
     * @throws RemoteException
     */
	void adversarioDesconectado() throws RemoteException;

    /**
     * notifica ao jogador que o seu adversário fez um
     * determinado movimento na matriz. O movimento é baseado numa variavel
     * que representa uma posição na matriz do tabuleiro
     * Por exemplo m = 0, representa o movimento na posição (0,0)
     * @param m
     * @throws RemoteException
     */
	void adversarioJogada(int m) throws RemoteException;

    /**
     * notifica o resutado final do jogo com base num estado definido
     * estados possiveis para o jogo: GANHOU, PERDEU, EMPATE
     * @param res
     * @throws RemoteException
     */
	void enviaResultadoJogo(Estados res) throws RemoteException;
}
