package org.example;

import java.util.*;

public class Grafo {
    private int[][] distancias;
    private Map<String, Integer> indiceCiudades;
    private int numVertices;

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.distancias = new int[numVertices][numVertices];
        this.indiceCiudades = new HashMap<>();
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(distancias[i], Integer.MAX_VALUE);
            distancias[i][i] = 0;
        }
    }

    public void agregarConexion(String origen, String destino, int distancia) {
        if (!indiceCiudades.containsKey(origen)) {
            indiceCiudades.put(origen, indiceCiudades.size());
        }
        if (!indiceCiudades.containsKey(destino)) {
            indiceCiudades.put(destino, indiceCiudades.size());
        }
        int i = indiceCiudades.get(origen);
        int j = indiceCiudades.get(destino);
        distancias[i][j] = distancia;
    }

    public void floydWarshall() {
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (distancias[i][k] != Integer.MAX_VALUE && distancias[k][j] != Integer.MAX_VALUE) {
                        distancias[i][j] = Math.min(distancias[i][j], distancias[i][k] + distancias[k][j]);
                    }
                }
            }
        }
    }

    public String encontrarCentro() {
        int minEcc = Integer.MAX_VALUE;
        String centro = null;
        for (String key : indiceCiudades.keySet()) {
            int i = indiceCiudades.get(key);
            int maxDist = 0;
            for (int j = 0; j < numVertices; j++) {
                if (distancias[i][j] > maxDist && distancias[i][j] != Integer.MAX_VALUE) {
                    maxDist = distancias[i][j];
                }
            }
            if (maxDist < minEcc) {
                minEcc = maxDist;
                centro = key;
            }
        }
        return centro;
    }

    public List<String> obtenerRuta(String origen, String destino) {
        List<String> ruta = new ArrayList<>();
        if (!indiceCiudades.containsKey(origen) || !indiceCiudades.containsKey(destino)) {
            return ruta; // Retorna lista vacía si las ciudades no existen
        }
        int start = indiceCiudades.get(origen);
        int end = indiceCiudades.get(destino);
        if (distancias[start][end] == Integer.MAX_VALUE) {
            return ruta; // Retorna lista vacía si no hay ruta
        }
        // Reconstruir la ruta
        int actual = start;
        ruta.add(origen);
        while (actual != end) {
            for (int j = 0; j < numVertices; j++) {
                if (distancias[actual][j] == distancias[start][end] - distancias[j][end]) {
                    actual = j;
                    ruta.add(getKeyByValue(indiceCiudades, j));
                    break;
                }
            }
        }
        return ruta;
    }

    private String getKeyByValue(Map<String, Integer> map, Integer value) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}

