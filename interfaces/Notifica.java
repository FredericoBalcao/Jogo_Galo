package interfaces;
/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */

/**
 * interface usada para notificar um jogador
 */
public interface Notifica {

	void adicionaAlerta(Alertas a);

	void removeAlertas(Alertas a);

	void enviaNotificacao();
}
