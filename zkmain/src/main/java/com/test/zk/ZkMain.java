package com.test.zk;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;

import java.io.File;
import java.io.IOException;

public class ZkMain {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        File directory = new File(".");
        System.out.println(directory.getAbsolutePath());

//        new ZkMain().startZkServer();
    }

    public void startZkServer() throws IOException {
        String[] args = new String[]{"2181", "./zk"};
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.parse(args);
        new ZooKeeperServerMain().runFromConfig(serverConfig);
    }
}
