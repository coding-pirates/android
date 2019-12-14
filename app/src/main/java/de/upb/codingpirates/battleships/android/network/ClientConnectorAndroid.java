package de.upb.codingpirates.battleships.android.network;


import com.google.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.upb.codingpirates.battleships.android.model.Model;
import de.upb.codingpirates.battleships.client.Handler;
import de.upb.codingpirates.battleships.client.network.ClientConnector;
import de.upb.codingpirates.battleships.network.connectionmanager.ClientConnectionManager;
import de.upb.codingpirates.battleships.network.exceptions.BattleshipException;
import de.upb.codingpirates.battleships.network.message.Message;

import java.io.IOException;

public class ClientConnectorAndroid extends ClientConnector {
    private static final Logger LOGGER = LogManager.getLogger();

    private Boolean connected = false;

    @Inject
    public ClientConnectorAndroid(ClientConnectionManager clientConnector) {
        super(clientConnector);
    }

    public boolean isConnected(){
        return !clientConnector.getConnection().isClosed();
    }

    public void connect(String host, int port) {
        Thread thread = new Thread(() -> {
            try {
                synchronized (clientConnector) {
                    clientConnector.create(host, port);
                }
                Model.getInstance().setConnected(true);

            }
            catch(IOException e){
                LOGGER.error("Could not connect to Server",e);
            }
        });
        thread.start();
    }

    public void sendMessageToServer(Message message) {
        Thread thread = new Thread(() -> {
            try {
                synchronized (clientConnector) {
                    clientConnector.send(message);
                }
            }
            catch(IOException e){
                LOGGER.error("Could not send " + message.getClass() + " to Server",e);
            }
        });
        thread.start();
    }

    public void disconnect() {
        Thread thread = new Thread(() -> {
            try {
                synchronized (clientConnector) {
                    clientConnector.disconnect();
                }
                Model.getInstance().setConnected(false);
            }
            catch(IOException e){
                LOGGER.error("Could not disconnect from Server",e);
            }
        });
        thread.start();
    }
    @Override
    public void handleBattleshipException(BattleshipException e) {

    }
    protected boolean doInBackground(String ip){
        return true;
    }
    protected void onPostExecute(boolean result){

    }
}