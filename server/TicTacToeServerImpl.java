package server;

import java.rmi.*;
import java.rmi.server.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import interfaces.*;
import client.Jogador;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */
public class TicTacToeServerImpl extends UnicastRemoteObject implements TicTacToeServer {
    private static final long serialVersionUID = -4811628104786217427L;
	private Map<String, Jogo> listaJogos;
	private ArrayList<String> listaJogadores;
    Jogador pl = new Jogador();

	public TicTacToeServerImpl() throws RemoteException {
		listaJogos = new HashMap<String, Jogo>();
		listaJogadores = new ArrayList<String>();
	}

	@Override
	public synchronized void criaJogo(JogadorInterface p, String idJogo)
			throws RemoteException {
		Jogo g = new JogoDoGaloImpl(p, idJogo);
		listaJogos.put(idJogo, g);
	}


	public synchronized void entrarNoJogo(JogadorInterface p, String idJogo)
			throws RemoteException{
		//assume que o jogador j já está registado
		if (listaJogos.get(idJogo) != null) {
			JogoDoGaloImpl g = (JogoDoGaloImpl) listaJogos.get(idJogo);
			//se o jogo ainda não está completo com os dois jogadores, acrescenta o jogador j
			if (g.getjogador2() == null) {
				//verifica se os jogadores são os mesmos
				if (g.getjogador1().equals(p))
                    try {
                        throw new Exception("Same players.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else {
					g.setjogador2(p);
					new Jogadores(g.getjogador1(), g.getjogador2(), idJogo);
					new Jogadores(g.getjogador2(), g.getjogador1(), idJogo);
					//verificajogador1.start();
					//verificajogador2.start();
				}
            }
        }
	}

	@Override
	public synchronized boolean registar(String nickname) {
		if (!listaJogadores.contains(nickname)) {
			listaJogadores.add(nickname);
            pl.setEstado("disponivel");
            //mostra na consola do servidor
            System.out.println("Jogador com nickname " + nickname + " registou-se" + "|" + " Estado: " + pl.getEstado());
            return true;
        }
		return false;
	}

	@Override
	public synchronized ArrayList<String> getJogos() {
		Set<String> games = listaJogos.keySet();
		ArrayList<String> s = new ArrayList<String>(games);
		Collections.sort(s, new Comparator<String>() {
			DateFormat df = new SimpleDateFormat("HH:mm:ss");

			@Override
			public int compare(String s1, String s2) {
				try {
					Date d1 = df.parse(s1.split("-")[1].trim());
					Date d2 = df.parse(s2.split("-")[1].trim());
					return d2.compareTo(d1);
				} catch (ParseException pe) {
					return 0;
				}
			}
		});
		return s;
	}

    /**
     * retorna a lista de jogadores registados
     * @return
     */
    public synchronized ArrayList<String> getListaJogadores() {
        return listaJogadores;
    }

	@Override
	public synchronized void logout(String nickname) {
		listaJogadores.remove(nickname);
        pl.setEstado("indisponivel");
        //mostra na consola do servidor
        System.out.println("Jogador com nickname " + nickname + " fez logout");
        System.out.println("Jogador com nickname " + nickname + " registou-se" + "|" + " Estado: " + pl.getEstado());
    }

	@Override
	public synchronized ArrayList<String> removeJogo(String idJogo) {
		listaJogos.remove(idJogo);
		return getJogos();
	}


    /**
     * retorna a lista de jogos criados
     * @return
     */
	public synchronized Map<String, Jogo> getlistaJogos() {
		return listaJogos;
	}

    /**
     * verificação dos jogadores com threads
     */
	private class Jogadores  {
		private JogadorInterface jogador1;
		private JogadorInterface jogador2;
		private String idJogo;

		public Jogadores(JogadorInterface jogador1, JogadorInterface jogador2, String idJogo) {
			super();

			this.idJogo = idJogo;

			this.jogador1 = jogador1;

			this.jogador2 = jogador2;
		}
	}

}
