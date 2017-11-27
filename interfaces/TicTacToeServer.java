package interfaces;

import java.rmi.*;
import java.util.*;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */

/**
 * interface do servidor
 */
public interface TicTacToeServer extends Remote {

    /**
     * regista um jogador para o servidor, dado um nickname
     * o registo não pode ter dois nicknames iguais
     * @param nickname
     * @return
     * @throws RemoteException
     */
	boolean registar(String nickname) throws RemoteException;

    /**
     * permite criar um novo jogo para um determinado jogador dado um id de jogo
     * @param j
     * @param idJogo
     * @throws RemoteException
     */
	void criaJogo(JogadorInterface j, String idJogo) throws RemoteException;

    /**
     * permite que um jogador entre um determinado jogo pelo codigo do jogo
     * @param j
     * @param idJogo
     * @throws RemoteException
     */
	void entrarNoJogo(JogadorInterface j, String idJogo) throws RemoteException;

    /**
     * retorna um lista de todos os jogadores que criaram um jogo
     * esperam que outro jogador os desafie ou que ainda estejam a jogar
     * @return
     * @throws RemoteException
     */
	ArrayList<String> getJogos() throws RemoteException;

    ArrayList<String> getListaJogadores() throws RemoteException;


    /**
     * permite que seja possivel remover um jogo da lista de jogos que estão no servidor
     * @param idJogo
     * @return
     * @throws RemoteException
     */
	ArrayList<String> removeJogo(String idJogo) throws RemoteException;

    /**
     * permite o logout de um jogador dado um nickname
     * @param nickname
     * @throws RemoteException
     */
	void logout(String nickname) throws RemoteException;
}
