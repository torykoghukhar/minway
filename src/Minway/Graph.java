package Minway;

import java.util.*;

public class Graph {
    private Map<String, Map<String, Integer>> vertexConnections;

    public Graph(int[][] adjacencyMatrix) {
        vertexConnections = new HashMap<>();
        int n = adjacencyMatrix.length;

        for (int i = 0; i < n; i++) {
            vertexConnections.put(String.valueOf(i), new HashMap<>());
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] > 0) {
                    vertexConnections.get(String.valueOf(i)).put(String.valueOf(j), adjacencyMatrix[i][j]);
                }
            }
        }
    }

    // Метод для отримання відстані між двома вершинами
    public int getDistance(String startStation, String endStation) {
        if (vertexConnections.containsKey(startStation) && vertexConnections.get(startStation).containsKey(endStation)) {
            return vertexConnections.get(startStation).get(endStation);
        }
        return -1; // Повернути -1, якщо зв'язок не існує
    }

    // Метод для отримання списку з'єднаних вершин з вказаної вершини
    public List<String> getConnectedVertices(String vertex) {
        List<String> connectedVertices = new ArrayList<>();
        if (vertexConnections.containsKey(vertex)) {
            connectedVertices.addAll(vertexConnections.get(vertex).keySet());
        }
        return connectedVertices;
    }

    // Метод для отримання списку вершин у графі
    public List<String> getVertex() {
        List<String> sortedVertices = new ArrayList<>(vertexConnections.keySet());
        sortedVertices.sort(Comparator.comparingInt(Integer::parseInt));
        return sortedVertices;
    }

    // Метод для перевірки, чи ребро орієнтоване
    public boolean isEdgeDirected(String startStation, String endStation) {
        if (vertexConnections.containsKey(startStation) && vertexConnections.get(startStation).containsKey(endStation)) {
            return true; // Якщо ребро існує, то воно орієнтоване
        }
        return false; // Якщо ребра не існує, воно не орієнтоване
    }

}
