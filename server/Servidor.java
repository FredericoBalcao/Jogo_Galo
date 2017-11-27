package server;

import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

import interfaces.*;

/**
 * Created by ASUS on 22-04-2017.
 * @author Frederico Balcão
 */
public class Servidor {
	public static void main(String[] args) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(1099);
		TicTacToeServer server = new TicTacToeServerImpl();
		Naming.rebind("tictactoe", server);
        System.out.println("Server RMI ok!!!");
	}

}
