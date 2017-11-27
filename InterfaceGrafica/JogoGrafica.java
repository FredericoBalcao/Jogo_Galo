package InterfaceGrafica;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */

public class JogoGrafica {
	private static final String ERRO_CONEXAO = "Erro de conexão";

	protected JFrame frmJogo;
	protected JTextArea textArea;

	public JogoGrafica() {
		frmJogo = new JFrame();
		initFrame();
		textArea = new JTextArea();
	}

    /**
     * método que define a visibilidade da janela, o container
     * @param value
     */
	public void setVisible(boolean value) {
		this.frmJogo.setVisible(value);
	}

    /**
     * método que fecha a janela do container
     */
	public void close() {
		this.frmJogo.setVisible(false);
	}

	public void setStatusBar(String msg) {
		textArea.setText(msg);
	}

    /**
     * mostra o titulo do jogo e uma variavel definida
     * @param value
     */
	public void setTitle(String value) {
		this.frmJogo.setTitle("JOGO DO GALO - " + value);
	}

	public void msgErroConexao() {
		setStatusBar(ERRO_CONEXAO);
	}

	private void initFrame() {
		frmJogo.setTitle("JOGO DO GALO");
		frmJogo.setResizable(false);
		frmJogo.setBounds(100, 100, 470, 470); //tamanho da janela
		frmJogo.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

}