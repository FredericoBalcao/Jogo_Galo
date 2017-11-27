package client;

import InterfaceGrafica.initGrafica;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */
public class Client {
    public static void main(String[] args) {
                try {
                    initGrafica ctrl = new initGrafica();
                    ctrl.setMenuPrincipal(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
}
