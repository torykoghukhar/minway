package Minway;

import java.util.HashMap;
import java.util.Map;

public class FloydWarshall {

    public int[][] findAllShortestDistances(Graph graph) {
        int numVertices = graph.getVertex().size(); // Отримуємо кількість вершин у графі
        int[][] shortestDistances = new int[numVertices][numVertices]; // Створюємо двовимірний масив для зберігання найкоротших відстаней

        // Ініціалізуємо відстані між всіма парами вершин як нескінченні
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i == j) {
                    shortestDistances[i][j] = 0; // Відстань від вершини до самої себе - 0
                } else {
                    shortestDistances[i][j] = Integer.MAX_VALUE; // Встановлюємо відстань між іншими вершинами як нескінченні
                }
            }
        }

        // Заповнюємо відстані для існуючих ребер у графі
        for (String startVertex : graph.getVertex()) {
            for (String endVertex : graph.getConnectedVertices(startVertex)) {
                int distance = graph.getDistance(startVertex, endVertex); // Отримуємо відстань між вершинами
                shortestDistances[Integer.parseInt(startVertex)][Integer.parseInt(endVertex)] = distance; // Зберігаємо відстань у відповідну комірку масиву
            }
        }

        // Алгоритм Флойда-Уоршелла
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (shortestDistances[i][k] != Integer.MAX_VALUE &&
                            shortestDistances[k][j] != Integer.MAX_VALUE) {
                        int throughK = shortestDistances[i][k] + shortestDistances[k][j]; // Обчислюємо відстань через вершину k
                        if (throughK < shortestDistances[i][j]) {
                            shortestDistances[i][j] = throughK; // Оновлюємо найкоротшу відстань між вершинами
                        }
                    }
                }
            }
        }

        return shortestDistances; // Повертаємо масив найкоротших відстаней
    }
}

