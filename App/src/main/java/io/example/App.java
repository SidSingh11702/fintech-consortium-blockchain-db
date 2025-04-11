package io.example;

import io.ipfs.api.IPFS;
import io.ipfs.multiaddr.MultiAddress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            String url = "jdbc:mysql://root@localhost:3306/testDB";
            Connection conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false); // turn off the auto-commit mode
            IPFS ipfs = new IPFS(new MultiAddress("/ip4/127.0.0.1/tcp/5001"));
            var app = new SQLApp(conn,ipfs);
            var server = new GrpcServer(app, 26658);
            server.start();
            server.blockUntilShutdown();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
