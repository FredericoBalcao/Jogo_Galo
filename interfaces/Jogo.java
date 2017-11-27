package interfaces;

import java.rmi.*;

public interface Jogo extends Remote {

    /**
     * variavel que indica um célula vazia na matriz
     */
	static final int CEL_LIVRE = 0;

    /**
     * variavel que indica a célula na matriz que o jogador 1 escolheu
     */
	static final int J1_CEL = 1;

    /**
     * variavel que indica a célula na matriz que o jogador 2 escolheu
     */
	static final int J2_CEL = 2;

    /**
     * variavel que indica a dimensão da matriz 3x3
     */
	static final int DIM_MATRIZ = 3;

    /**
     * representa a jogada feita por um jogador
     * através de um determinado jogador e de uma posição escolhida
     * posição representada por uma variavel do tipo inteiro
     * por exemplo, se m = 0, significa que é um movimento feito pelo jogador na posição (0,0) da matriz
     * @param j
     * @param p
     * @throws RemoteException
     */
	void enviaJogada(JogadorInterface j, int p) throws RemoteException ;
	
}
