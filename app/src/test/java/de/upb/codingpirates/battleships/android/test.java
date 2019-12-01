package de.upb.codingpirates.battleships.android;

import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import de.upb.codingpirates.battleships.client.network.ClientApplication;
import de.upb.codingpirates.battleships.client.network.ClientConnector;
import de.upb.codingpirates.battleships.logic.ClientType;
import de.upb.codingpirates.battleships.network.Properties;
import de.upb.codingpirates.battleships.network.message.request.GameJoinPlayerRequest;
import de.upb.codingpirates.battleships.network.message.request.ServerJoinRequest;

public class test {
    @Test
    public void test() throws IllegalAccessException, IOException, InstantiationException {

                try {
                    ClientApplication client = new ClientApplication();
                    ClientConnector connector = client.getClientConnector();
                    connector.connect(InetAddress.getLocalHost().getHostAddress(), Properties.PORT);
                    connector.sendMessageToServer(new ServerJoinRequest("peter", ClientType.SPECTATOR));
                    connector.sendMessageToServer(new GameJoinPlayerRequest(0));
                }
                catch(Exception e){
                    System.out.println("here is the exception " +e);
                }


    }
}
