package by.halatsevich.storage.model.pool;

import by.halatsevich.storage.exception.DaoException;
import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final String PROPERTIES = "database";
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String POOL_SIZE = "poolSize";
    private static final String SERVER_TIMEZONE = "serverTimezone";
    private static final String AUTO_RECONNECT = "autoReconnect";
    private static final String ENCODING = "encoding";
    private static final String UNICODE = "useUnicode";
    private int poolSize;
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenAwayConnections;
    private static ConnectionPool instance;

    private ConnectionPool() {
        initialization();
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void initialization() {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            logger.log(Level.FATAL, "Cannot register driver");
            throw new RuntimeException(e);
        }
        ResourceBundle resources = ResourceBundle.getBundle(PROPERTIES);
        String url = resources.getString(URL);
        String user = resources.getString(USER);
        String password = resources.getString(PASSWORD);
        String serverTimezone = resources.getString(SERVER_TIMEZONE);
        String autoReconnect = resources.getString(AUTO_RECONNECT);
        String encoding = resources.getString(ENCODING);
        String useUnicode = resources.getString(UNICODE);
        try {
            poolSize = Integer.parseInt(resources.getString(POOL_SIZE));
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, "Error while parsing pool size", e);
            poolSize = 32;
        }
        freeConnections = new LinkedBlockingDeque<>(poolSize);
        givenAwayConnections = new ArrayDeque<>();
        Properties properties = new Properties();
        properties.put(USER, user);
        properties.put(PASSWORD, password);
        properties.put(SERVER_TIMEZONE, serverTimezone);
        properties.put(AUTO_RECONNECT, autoReconnect);
        properties.put(ENCODING, encoding);
        properties.put(UNICODE, useUnicode);
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, properties);
                freeConnections.add(new ProxyConnection(connection));
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Cannot add connection to pool", e);
            }
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Error while taking connection from queue");
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws DaoException {
        if (connection.getClass() != ProxyConnection.class) {
            throw new DaoException("Invalid connection");
        }
        givenAwayConnections.remove(connection);
        freeConnections.offer((ProxyConnection) connection);
    }

    public void destroyPool() {
        for (int i = 0; i < poolSize; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Error while closing connection");
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Error while taking connection from queue");
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Cannot deregister driver", e);
            }
        }
    }
}
