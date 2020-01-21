package de.upb.codingpirates.battleships.android.network;

import android.annotation.SuppressLint;
import de.upb.codingpirates.battleships.logic.util.Pair;
import de.upb.codingpirates.battleships.network.Connection;
import de.upb.codingpirates.battleships.network.exceptions.parser.ParserException;
import de.upb.codingpirates.battleships.network.message.Message;
import de.upb.codingpirates.battleships.network.message.report.ReportBuilder;
import de.upb.codingpirates.battleships.network.util.ClientReaderMethod;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.net.SocketException;

public class AndroidReader implements ClientReaderMethod {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Override
    public void get(Connection connection, Consumer<Pair<Connection, Message>> dispatch, Consumer<Throwable> error) {
        Observable.create((ObservableEmitter<Pair<Connection, Message>> emitter) -> {
            while (!connection.isClosed()) {
                try {
                    Message message = connection.read();
                    emitter.onNext(new Pair<>(connection, message));
                    break;
                } catch (SocketException e) {
                    connection.close();
                    emitter.onNext(new Pair<>(connection, ReportBuilder.connectionClosedReport()));
                } catch (IOException | ParserException e) {
                    emitter.onError(e);
                }

            }
            emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnDispose(()->get(connection,dispatch,error)).doOnComplete(()->get(connection,dispatch,error)).subscribe(dispatch, error);
    }
}
