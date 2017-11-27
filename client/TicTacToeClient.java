package client;

import interfaces.*;
import server.TicTacToeServerImpl;

import java.net.*;
import java.rmi.*;
import java.util.*;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */
public class TicTacToeClient implements Alertas, Notifica {
	private Jogo g;
	private Jogador p;
	private int m[][];
	private List<Alertas> listaAlertas;
	private Estados alerta;
	private TicTacToeServer server;
	private boolean criador;
	private String idJogoIniciado;
	private String idJogoCriado;
    private TicTacToeServerImpl tti = new TicTacToeServerImpl();

	public TicTacToeClient() throws MalformedURLException, RemoteException, NotBoundException{
		idJogoIniciado="";
		idJogoCriado="";
		m = new int[Jogo.DIM_MATRIZ][Jogo.DIM_MATRIZ];
		for (int i=0; i< Jogo.DIM_MATRIZ; i++)
			for (int j=0;j<Jogo.DIM_MATRIZ;j++)
				m[i][j] = Jogo.CEL_LIVRE;
		listaAlertas = new LinkedList<Alertas>();
		server = (TicTacToeServer)Naming.lookup("rmi://"+"127.0.0.1"+"/tictactoe");
	}

	public void enviaMov(int matriz) throws RemoteException{
		g.enviaJogada(p, matriz);
		m[matriz / Jogo.DIM_MATRIZ][matriz % Jogo.DIM_MATRIZ] = Jogo.J1_CEL;

	}

	public void resetmatriz() {
		for (int i = 0; i < Jogo.DIM_MATRIZ; i++)
			for (int j = 0; j < Jogo.DIM_MATRIZ; j++)
				m[i][j] = Jogo.CEL_LIVRE;
	}

	public void recebeMov() {
		int matriz = p.getadv_jogada_selecionada();
		m[matriz / Jogo.DIM_MATRIZ][matriz % Jogo.DIM_MATRIZ] = Jogo.J2_CEL;
	}

	public void criaJogo(String idJogo) throws MalformedURLException, RemoteException, NotBoundException{
		idJogoIniciado=idJogo;
		criador=true;
		server.criaJogo(p, idJogo);
	}

	public void entrarNoJogo(String idJogo) throws MalformedURLException, RemoteException, NotBoundException{
		idJogoCriado=idJogo;
		criador=false;
		server.entrarNoJogo(p, idJogo);
	}


	public boolean registarPlayer(String nickname) throws RemoteException {
		p = new Jogador();
		p.setName(nickname);
		p.adicionaAlerta(this);
        p.setEstado("disponivel");
		return server.registar(nickname);
	}
	

	public void logoutPlayer(String nickname) {
		try {
			 server.logout(nickname);
             p.setEstado("indisponivel");
		} catch (RemoteException e) {
			System.out.println("Impossible to logout."+e.getMessage());

		}
	}

	//recebe uma lista de jogos criados para possiveis desafiantes
	public ArrayList<String> receiveList() throws MalformedURLException,RemoteException, NotBoundException {
		return (ArrayList<String>) server.getJogos();
	}

	public ArrayList<String> removeJogo() throws MalformedURLException,RemoteException, NotBoundException {
		if (criador)
			return (ArrayList<String>) server.removeJogo(idJogoIniciado);
		else
			return (ArrayList<String>) server.removeJogo(idJogoCriado);

	}

    //recebe uma lista de jogos criados para possiveis desafiantes
    public ArrayList<String> recevelistaJogadores() throws MalformedURLException, RemoteException, NotBoundException {
        ArrayList<String> s = server.getListaJogadores();
        return s;
    }


	public Jogo getGame() {
		return g;
	}


	public void setGame(Jogo g) {
		this.g = g;
	}


	public Jogador getPlayer() {
		return p;
	}


	public void setPlayer(Jogador p) {
		this.p = p;
	}


	public int[][] getmatriz() {
		return m;
	}


	public Estados getalerta() {
		return alerta;
	}


	public boolean criadorJogo() {
		return criador;
	}


	public String getidJogoIniciado() {
		return idJogoIniciado;
	}


	public void setidJogoIniciado(String idJogoIniciado) {
		this.idJogoIniciado =
                idJogoIniciado;
	}


	public String getidJogoCriado() {
		return idJogoCriado;
	}


	public void setidJogoCriado(String idJogoCriado) {
		this.idJogoCriado = idJogoCriado;
	}

	@Override
	public void atualiza() {
		switch (p.getalerta()) {
            case ADV_MOVE:
                recebeMov();
                break;
            case JOGO_INICIADO:
                setGame(p.getGame());
                break;
            }
            alerta = p.getalerta();
            enviaNotificacao();
	}

	@Override
	public void adicionaAlerta(Alertas a) {
		listaAlertas.add(a);

	}


	@Override
	public void removeAlertas(Alertas a) {
		listaAlertas.remove(a);

	}


	@Override
	public void enviaNotificacao() {
		for (Alertas w : listaAlertas)
			w.atualiza();
	}
}
