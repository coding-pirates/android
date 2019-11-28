package de.upb.codingpirates.battleships.android.Model;

import com.google.inject.Inject;
import de.upb.codingpirates.battleships.network.ConnectionHandler;
import de.upb.codingpirates.battleships.network.connectionmanager.ClientConnectionManager;
import de.upb.codingpirates.battleships.network.exceptions.BattleshipException;
import de.upb.codingpirates.battleships.network.message.Message;

import java.io.IOException;

public class ClientConnector implements ConnectionHandler {
    @Inject
    private ClientConnectionManager clientConnector;

    public void connect(String host, int port) throws IOException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    clientConnector.create(host, port);
                }
                catch(IOException e){}
            }});
        thread.start();
    }

    public void sendMessageToServer(Message message) throws IOException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    clientConnector.send(message);
                }
                catch(IOException e){}
            }});
        thread.start();
    }

    public void disconnect() throws IOException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    clientConnector.disconnect();
                }
                catch(IOException e){}
            }});
        thread.start();
    }
    @Override
    public void handleBattleshipException(BattleshipException e) {

    }
}