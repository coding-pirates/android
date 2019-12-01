package de.upb.codingpirates.battleships.android.Model;

import android.os.AsyncTask;

import com.google.inject.Inject;
import de.upb.codingpirates.battleships.network.ConnectionHandler;
import de.upb.codingpirates.battleships.network.connectionmanager.ClientConnectionManager;
import de.upb.codingpirates.battleships.network.exceptions.BattleshipException;
import de.upb.codingpirates.battleships.network.message.Message;

import java.io.IOException;

public class ClientConnector implements ConnectionHandler {
    @Inject
    private ClientConnectionManager clientConnector;
    private Boolean connected = false;

    public boolean isConnected(){
        return !clientConnector.getConnection().isClosed();
    }

    public void connect(String host, int port) throws IOException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    synchronized (clientConnector) {
                        clientConnector.create(host, port);
                    }
                    Model.getInstance().setConnected(true);

                }
                catch(IOException e){}
            }});
        thread.start();
    }

    public void sendMessageToServer(Message message) throws IOException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    synchronized (clientConnector) {
                        clientConnector.send(message);
                    }
                }
                catch(IOException e){}
            }});
        thread.start();
    }

    public void disconnect() throws IOException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    synchronized (clientConnector) {
                        clientConnector.disconnect();
                    }
                    Model.getInstance().setConnected(false);
                }
                catch(IOException e){}
            }});
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