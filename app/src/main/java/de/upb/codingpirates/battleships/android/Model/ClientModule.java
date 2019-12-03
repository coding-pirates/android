package de.upb.codingpirates.battleships.android.Model;

import de.upb.codingpirates.battleships.client.Handler;
import de.upb.codingpirates.battleships.client.network.AbstractClientModule;
import de.upb.codingpirates.battleships.network.util.ClientReaderMethod;

public class ClientModule extends AbstractClientModule<ClientConnector> {
    public ClientModule() {
        super(ClientConnector.class);
    }
    @Override
    protected void configure() {
        super.configure();

        this.bind(Handler.class).toInstance(new MessageHandler());
        this.bind(ClientReaderMethod.class).to(AndroidReader.class);
    }
}
