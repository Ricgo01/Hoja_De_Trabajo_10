package org.example;

import java.io.*;
import java.util.*;

public class Graph {
    private static final int INF = Integer.MAX_VALUE;
    private int[][] dist;
    private int[][] next;
    private int V;
    private List<String> cities = new ArrayList<>();
    private Map<String, Integer> cityIndex = new HashMap<>();
    private String filename;
    private List<String[]> edges = new ArrayList<>();

    public Graph(String filename) throws IOException {
        this.filename = filename;
        loadGraph(filename);
        V = cities.size();
        dist = new int[V][V];
        next = new int[V][V];
        for (int i = 0; i < V; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
            Arrays.fill(next[i], -1);
        }
        buildGraph(filename);
    }

    private void loadGraph(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            if (!cityIndex.containsKey(parts[0])) {
                cityIndex.put(parts[0], cityIndex.size());
                cities.add(parts[0]);
            }
            if (!cityIndex.containsKey(parts[1])) {
                cityIndex.put(parts[1], cityIndex.size());
                cities.add(parts[1]);
            }
            edges.add(parts);
        }
        br.close();
    }

    private void buildGraph(String filename) throws IOException {
        for (String[] edge : edges) {
            int u = cityIndex.get(edge[0]);
            int v = cityIndex.get(edge[1]);
            int weight = Integer.parseInt(edge[2]);
            dist[u][v] = weight;
            next[u][v] = v;
        }
    }

    public void floydWarshall() {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public List<String> getShortestPath(String source, String destination) {
        List<String> path = new ArrayList<>();
        if (!cityIndex.containsKey(source) || !cityIndex.containsKey(destination)) {
            return path;
        }
        int u = cityIndex.get(source);
        int v = cityIndex.get(destination);
        if (next[u][v] == -1) {
            return path;
        }
        path.add(source);
        while (u != v) {
            u = next[u][v];
            path.add(cities.get(u));
        }
        return path;
    }

    public int getShortestDistance(String source, String destination) {
        if (!cityIndex.containsKey(source) || !cityIndex.containsKey(destination)) {
            return INF;
        }
        return dist[cityIndex.get(source)][cityIndex.get(destination)];
    }

    public String getGraphCenter() {
        int minMaxDist = INF;
        int center = -1;
        for (int i = 0; i < V; i++) {
            int maxDist = 0;
            for (int j = 0; j < V; j++) {
                if (i != j && dist[i][j] > maxDist) {
                    maxDist = dist[i][j];
                }
            }
            if (maxDist < minMaxDist) {
                minMaxDist = maxDist;
                center = i;
            }
        }
        return center == -1 ? null : cities.get(center);
    }

    public void modifyGraph(String city1, String city2, int distance) throws IOException {
        if (!cityIndex.containsKey(city1) || !cityIndex.containsKey(city2)) {
            return;
        }
        int u = cityIndex.get(city1);
        int v = cityIndex.get(city2);
        dist[u][v] = distance;
        next[u][v] = distance == INF ? -1 : v;

        // Actualiza la lista de aristas
        boolean found = false;
        for (String[] edge : edges) {
            if (edge[0].equals(city1) && edge[1].equals(city2)) {
                if (distance == INF) {
                    edges.remove(edge);
                } else {
                    edge[2] = String.valueOf(distance);
                }
                found = true;
                break;
            }
        }
        if (!found && distance != INF) {
            edges.add(new String[]{city1, city2, String.valueOf(distance)});
        }
        saveGraph();
    }

    private void saveGraph() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        for (String[] edge : edges) {
            bw.write(String.join(" ", edge));
            bw.newLine();
        }
        bw.close();
    }
}
