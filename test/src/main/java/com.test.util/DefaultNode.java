package com.test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultNode {
    private static volatile Map<String, String> clusterNodeMap = new HashMap<>();

    private static AtomicInteger i = new AtomicInteger(0);

    static {
        clusterNodeMap.put("k", "v");
    }

    private volatile Map<String, String> map = new HashMap<String, String>(10);

    public static Map<String, String> getClusterNodeMap() {
        return clusterNodeMap;
    }

    public static void main(String[] args) throws JsonProcessingException {
        DefaultNode node1 = new DefaultNode();
        node1.print();
        DefaultNode node2 = new DefaultNode();
        node2.print();
        DefaultNode.getClusterNodeMap().put("k12", "v12");

        node1.print();
        node2.print();
    }

    public void print() throws JsonProcessingException {
        System.out.println("clusterNodeMap:" + new ObjectMapper().writeValueAsString(clusterNodeMap));
        System.out.println("map:" + new ObjectMapper().writeValueAsString(map));
        map.put("k" + i.get(), "v" + i.get());
        i.incrementAndGet();
    }
}
