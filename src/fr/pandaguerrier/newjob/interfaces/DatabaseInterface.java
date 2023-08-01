package fr.pandaguerrier.newjob.interfaces;

import fr.pandaguerrier.newjob.ConodiaJobs;
import io.netty.handler.codec.http.Cookie;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseInterface {
    void connect(ConodiaJobs plugin);
    void init(Connection connection) throws SQLException;
    void initPlayers(Connection connection) throws SQLException;
}
