package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import interfaces.*;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */
public class Jogador extends UnicastRemoteObject implements JogadorInterface, Notifica {
	private String name;
	private Jogo game;
    private String estado;
	private int adv_jogada_selecionada; //mostra a jogada feita pelo adversário
	private List<Alertas> listaAlertas;
	private Estados alerta;
    private String disponivel = "disponivel";
    private String indisponivel = "indisponivel";
    private String ocupado = "ocupado";


	public Jogador() throws RemoteException {
		listaAlertas = new LinkedList<Alertas>();
	}

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Jogo getGame() {
		return game;
	}

	public void setGame(Jogo g) {
		this.game = g;
	}

	public int getadv_jogada_selecionada() {
		return adv_jogada_selecionada;
	}

	public void setadv_jogada_selecionada(int adv_jogada_selecionada) {
		this.adv_jogada_selecionada = adv_jogada_selecionada;
	}

	public Estados getalerta() {
		return alerta;
	}

    /**
     * estado de um jogador
     * @param alerta
     */
	public void setalerta(Estados alerta) {
		this.alerta = alerta;
	}

	@Override
	public void entrarNoJogo(Jogo g) {
		game = g;
		alerta = Estados.JOGO_INICIADO;
		enviaNotificacao();
	}


	@Override
	public void minhaVez() {
		alerta = Estados.SUA_VEZ;
		enviaNotificacao();

	}

	@Override
	public void vezAdversario() {
		alerta = Estados.VEZ_ADV;
		enviaNotificacao();
	}

	@Override
	public void conectaJogador() {}

	@Override
	public void adversarioDesconectado() {
        alerta = Estados.ADV_DESCONECTADO;
        enviaNotificacao();
    }

	@Override
	public void adversarioJogada(int m) {
		adv_jogada_selecionada = m;
		alerta = Estados.ADV_MOVE;
		enviaNotificacao();
	}


	@Override
	public void enviaResultadoJogo(Estados res) {
		alerta = res;
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