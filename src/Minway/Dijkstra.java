package Minway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;
public class Dijkstra {
    private Map<String, Integer> shortestDistances;
    private Map<String, String> predecessors; // Додаємо для збереження попередників

    public Map<String, Integer> findShortestDistances(Graph graph, String startVertex) {
        shortestDistances = new HashMap<>(); // Ініціалізуємо мапу для збереження найкоротших відстаней
        predecessors = new HashMap<>(); // Ініціалізуємо мапу для збереження попередників
        PriorityQueue<String> unvisitedVertices = new PriorityQueue<>((v1, v2) ->
                Integer.compare(shortestDistances.getOrDefault(v1, Integer.MAX_VALUE), shortestDistances.getOrDefault(v2, Integer.MAX_VALUE)));

        // Ініціалізуємо відстані з початкової вершини до всіх інших як нескінченні
        for (String vertex : graph.getVertex()) {
            shortestDistances.put(vertex, Integer.MAX_VALUE); // Встановлюємо відстань до кожної вершини як нескінченні
        }
        // Відстань з початкової вершини до неї самої - 0
        shortestDistances.put(startVertex, 0); // Встановлюємо відстань до початкової вершини як 0

        unvisitedVertices.add(startVertex); // Додаємо початкову вершину до невідвіданих

        while (!unvisitedVertices.isEmpty()) {
            String currentVertex = unvisitedVertices.poll(); // Беремо вершину з найменшою відстанню

            for (String neighbor : graph.getConnectedVertices(currentVertex)) {
                int distance = shortestDistances.get(currentVertex) + graph.getDistance(currentVertex, neighbor); // Обчислюємо відстань до сусідніх вершин
                if (distance < shortestDistances.get(neighbor)) {
                    shortestDistances.put(neighbor, distance); // Оновлюємо найкоротшу відстань до сусідньої вершини
                    predecessors.put(neighbor, currentVertex); // Зберігаємо попередника
                    unvisitedVertices.add(neighbor); // Додаємо сусідню вершину до списку невідвіданих
                }
            }
        }

        return shortestDistances; // Повертаємо мапу з найкоротшими відстанями
    }

    // Метод для отримання найкоротшого шляху
    public List<String> getShortestPath(String endVertex) {
        List<String> path = new ArrayList<>(); // Створюємо список для зберігання шляху
        for (String vertex = endVertex; vertex != null; vertex = predecessors.get(vertex)) {
            path.add(vertex); // Додаємо вершину до шляху
        }
        Collections.reverse(path); // Перевертаємо список, щоб отримати правильний порядок вершин шляху
        return path; // Повертаємо список шляху
    }
}







