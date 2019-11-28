package de.upb.codingpirates.battleships.android.Model;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.upb.codingpirates.battleships.client.Handler;
import de.upb.codingpirates.battleships.client.network.AbstractClientModule;
import de.upb.codingpirates.battleships.client.network.ClientConnector;
import de.upb.codingpirates.battleships.network.ConnectionHandler;
import de.upb.codingpirates.battleships.network.network.module.ClientNetworkModule;

public class ClientModule extends AbstractClientModule {
    public ClientModule() {
        super(ClientConnector.class);
    }
    @Override
    protected void configure() {
        super.configure();
        //this.install(new ClientNetworkModule());
        //this.bind(ConnectionHandler.class).to(ClientConnector.class).in(Singleton.class);
        this.bind(Handler.class).toInstance(new MessageHandler());
    }
}
