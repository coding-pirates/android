package de.upb.codingpirates.battleships.android.network;

import android.annotation.SuppressLint;
import de.upb.codingpirates.battleships.logic.util.Pair;
import de.upb.codingpirates.battleships.network.Connection;
import de.upb.codingpirates.battleships.network.message.Message;
import de.upb.codingpirates.battleships.network.util.ClientReaderMethod;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AndroidReader extends ClientReaderMethod {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Override
    public void get(Connection connection, Consumer<Pair<Connection, Message>> dispatch, Consumer<Throwable> error) {
        this.create(connection).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnDispose(()->get(connection,dispatch,error)).doOnComplete(()->get(connection,dispatch,error)).subscribe(dispatch, error);
    }
}
