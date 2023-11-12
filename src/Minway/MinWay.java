package Minway;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;

public class MinWay extends Application {
    private Stage stage; // Об'єкт для управління вікнами

    private  String startVertex; // Початкова вершина для пошуку мінімального шляху

    @Override
    public void start(Stage stage)  {
        this.stage=stage;

        stage.setTitle("Знаходження мінімального шляху"); // Встановлюємо заголовок вікна
        stage.setHeight(500); // Встановлюємо висоту вікна
        stage.setWidth(500); // Встановлюємо ширину вікна
        stage.setScene(firstScene()); // Встановлюємо сцену першого етапу
        stage.setResizable(false); // Встановлюємо, що вікно не можна змінювати розмір
        stage.show(); // Показуємо вікно

    }

    // Сцена для введення кількості вершин у графі
    public Scene firstScene() {
        Text text = new Text("Скільки вершин буде у графі"); // Текстове поле для питання користувача
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Встановлення шрифту та розміру

        TextField textField = new TextField(); // Текстове поле для введення даних користувачем
        Button build = new Button("Побудувати матрицю для графа"); // Кнопка для переходу до наступного етапу
        build.setFont(Font.font("Arial", 16)); // Встановлення шрифту та розміру

        // Встановлюємо дію кнопки при натисканні
        build.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int num = Integer.parseInt(textField.getText()); // Отримуємо кількість вершин від користувача
                stage.setScene(secondScene(num)); // Перехід до другої сцени з відомою кількістю вершин
            }
        });

        VBox vbox = new VBox(text, textField, build); // Вертикальний контейнер для елементів у сцені
        vbox.setSpacing(10); // Встановлюємо відступи між елементами
        vbox.setPadding(new Insets(10)); // Встановлюємо зовнішні відступи
        vbox.setAlignment(Pos.CENTER); // Вирівнюємо елементи по центру
        return new Scene(vbox); // Повертаємо сцену
    }

    // Сцена для заповнення матриці графу
    public Scene secondScene(int num) {
        // Розмір вікна залежить від кількості вершин
        double windowSize = Math.max(num * 60, 400); // Мінімальний розмір 400
        stage.setHeight(windowSize); // Встановлюємо висоту вікна
        stage.setWidth(windowSize); // Встановлюємо ширину вікна
        int[][] matrixData = new int[num][num]; // Матриця для збереження даних з текстових полів

        Text text = new Text("Заповніть матрицю"); // Текстове поле з інструкціями
        text.setFont(Font.font(20)); // Встановлення шрифту та розміру

        TextField[][] matrixFields = new TextField[num][num]; // Масив текстових полів для введення даних
        GridPane grid = new GridPane(); // Контейнер сітки для розміщення текстових полів

        grid.setHgap(10); // Встановлюємо горизонтальний відступ між елементами
        grid.setVgap(10); // Встановлюємо вертикальний відступ між елементами
        grid.setPadding(new Insets(10, 10, 10, 10)); // Встановлюємо зовнішні відступи

        // Заповнюємо сітку текстовими полями для введення даних
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                matrixFields[i][j] = new TextField();
                matrixFields[i][j].setPrefSize(30, 30); // Зробити текстове поле квадратним
                grid.add(matrixFields[i][j], j, i);
            }
        }

        // Кнопка для побудови графу за введеною матрицею
        Button buildgraph = new Button("Побудувати граф за матрицею");
        buildgraph.setFont(Font.font(16)); // Встановлення шрифту та розміру

        // Встановлюємо дію кнопки при натисканні
        buildgraph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Отримуємо дані з текстових полів
                for (int i = 0; i < num; i++) {
                    for (int j = 0; j < num; j++) {
                        int value = Integer.parseInt(matrixFields[i][j].getText());
                        matrixData[i][j] = value; // Зберігаємо дані у матриці
                    }
                }
                stage.setScene(thirdScene(matrixData)); // Перехід до третьої сцени з відомою матрицею
            }
        });
        grid.setAlignment(Pos.CENTER); // Вирівнюємо елементи сітки по центру

        VBox vbox = new VBox(text, grid, buildgraph); // Вертикальний контейнер для елементів у сцені
        vbox.setSpacing(10); // Встановлюємо відступи між елементами
        vbox.setPadding(new Insets(10)); // Встановлюємо зовнішні відступи
        vbox.setAlignment(Pos.CENTER); // Розташування по центру

        return new Scene(vbox); // Повертаємо сцену
    }

    public Scene thirdScene (int [][] matrixData){

        Graph graph = new Graph(matrixData); // Створення графу за даними з матриці

        Text text = new Text("Виберіть стартову точку : "); // Текстове поле для вказівки користувачеві
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Встановлення шрифту та розміру

        TextField textField1 = new TextField(); // Текстове поле для введення даних

        // Кнопка для запуску алгоритму Дейкстри
        Button dijkstra = new Button("Алгоритм Дейкстри");
        dijkstra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startVertex = textField1.getText(); // Запам'ятовуємо початкову вершину
                stage.setScene(Dscene(graph)); // Перехід до сцени для виконання алгоритму Дейкстри
            }
        });

        // Кнопка для запуску алгоритму Флойда-Уоршелла
        Button floyd = new Button("Алгоритм Флойда-Уоршелла ");
        floyd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startVertex = textField1.getText(); // Запам'ятовуємо початкову вершину
                stage.setScene(Fscene(graph)); // Перехід до сцени для виконання алгоритму Флойда-Уоршелла
            }
        });
        dijkstra.setFont(Font.font("Arial", 16)); // Встановлення шрифту та розміру для кнопки
        floyd.setFont(Font.font("Arial", 16)); // Встановлення шрифту та розміру для кнопки

        drawGraph(graph); // Виклик методу для відображення графу

        VBox vBox = new VBox(text,textField1, dijkstra, floyd); // Вертикальний контейнер для елементів у сцені
        vBox.setSpacing(10); // Встановлюємо відступи між елементами
        vBox.setPadding(new Insets(10)); // Встановлюємо зовнішні відступи
        vBox.setAlignment(Pos.CENTER); // Розташування по центру

        return new Scene(vBox); // Повертаємо сцену
    }

    public void drawGraph(Graph graph){
        int numberOfNodes = graph.getVertex().size(); // Отримуємо кількість вершин у графі
        List<String> vertices = graph.getVertex(); // Отримуємо список вершин

        Stage newStage = new Stage(); // Створення нового вікна для відображення графу

        // Розмір вікна залежить від кількості вершин
        double windowSize = Math.max(graph.getVertex().size() * 60, 400); // Мінімальний розмір 400
        newStage.setHeight(windowSize); // Встановлюємо висоту вікна
        newStage.setWidth(windowSize); // Встановлюємо ширину вікна
        newStage.setMaxHeight(1000); // Встановлюємо максимальну висоту вікна
        newStage.setMaxWidth(1000); // Встановлюємо максимальну ширину вікна

        // Масштабування згідно кількості вершин
        double radius = Math.min(newStage.getWidth(), newStage.getHeight()) / 3;

        Pane pane = new Pane(); // Панель для відображення графу

        // Створення вершин та ребер
        Circle[] nodes = new Circle[numberOfNodes]; // Масив для зберігання вершин
        Text[] labels = new Text[numberOfNodes]; // Масив для зберігання текстових міток вершин

        // Цикл для створення кожної вершини
        for (int i = 0; i < numberOfNodes; i++) {
            double angle = 2 * Math.PI * i / numberOfNodes;
            double x = newStage.getWidth() / 2 + radius * Math.cos(angle);
            double y = newStage.getHeight() / 2.75 + radius * Math.sin(angle);

            nodes[i] = new Circle(x, y, 20); // Створення круга як вершини графу
            nodes[i].setFill(Color.BLACK); // Зміна кольору на чорний

            pane.getChildren().addAll(nodes[i]); // Додавання вершини до панелі
        }

        // Цикл для створення ребер та ваги
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                if (graph.isEdgeDirected(vertices.get(i), vertices.get(j))) { // Перевірка, чи є ребро між вершинами

                    // Визначення координат початку та кінця ребра
                    double startX = nodes[i].getCenterX();
                    double startY = nodes[i].getCenterY();
                    double endX = nodes[j].getCenterX();
                    double endY = nodes[j].getCenterY();

                    // Розташування ребра між кружками вершин
                    double offsetX = (endX - startX) * 0.1;
                    double offsetY = (endY - startY) * 0.1;
                    Line edge = new Line(startX + offsetX, startY + offsetY, endX - offsetX, endY - offsetY); // Створення лінії між вершинами
                    pane.getChildren().add(edge); // Додавання лінії до панелі

                    // Додавання тексту з вагою ребра
                    int distance = graph.getDistance(vertices.get(i), vertices.get(j)); // Отримання відстані між вершинами
                    Text weightText = new Text(String.valueOf(distance)); // Створення текстової мітки для ваги
                    weightText.setX((startX + endX) / 2);
                    weightText.setY((startY + endY) / 2);
                    weightText.setFont(new Font(18));
                    pane.getChildren().add(weightText); // Додавання текстової мітки до панелі


                }
                // Перевірка, чи існує ребро між вершинами
                if (graph.getDistance(vertices.get(i), vertices.get(j)) > 0) {
                    double startX = nodes[i].getCenterX();
                    double startY = nodes[i].getCenterY();
                    double endX = nodes[j].getCenterX();
                    double endY = nodes[j].getCenterY();

                    // Розрахунок координат для стрілки
                    double arrowLength = 10;
                    double arrowAngle = Math.atan2(endY - startY, endX - startX);
                    double arrowX1 = endX - arrowLength * Math.cos(arrowAngle - Math.toRadians(30));
                    double arrowY1 = endY - arrowLength * Math.sin(arrowAngle - Math.toRadians(30));
                    double arrowX2 = endX - arrowLength * Math.cos(arrowAngle + Math.toRadians(30));
                    double arrowY2 = endY - arrowLength * Math.sin(arrowAngle + Math.toRadians(30));

                    // Збільшення відстані від круга
                    double arrowOffsetX = (endX - startX) * 0.1;
                    double arrowOffsetY = (endY - startY) * 0.1;
                    arrowX1 -= arrowOffsetX;
                    arrowY1 -= arrowOffsetY;
                    arrowX2 -= arrowOffsetX;
                    arrowY2 -= arrowOffsetY;

                    Polygon arrow = new Polygon(endX, endY, arrowX1, arrowY1, arrowX2, arrowY2); // Створення стрілки
                    arrow.setFill(Color.BLACK); // Заповнення стрілки чорним кольором
                    pane.getChildren().add(arrow); // Додавання стрілки до панелі

                }
            }

        }

        // Цикл для розміщення текстових міток вершин
        for (int i = 0; i < numberOfNodes; i++) {
            double angle = 2 * Math.PI * i / numberOfNodes;
            double x = newStage.getWidth() / 2 + radius * Math.cos(angle);
            double y = newStage.getHeight() / 2.75 + radius * Math.sin(angle);

            labels[i] = new Text(vertices.get(i)); // Створення текстової мітки з назвою вершини
            labels[i].setFont(new Font(24)); // Встановлення шрифту для мітки
            labels[i].setFill(Color.PINK); // Встановлення кольору для мітки
            labels[i].setX(x - labels[i].getLayoutBounds().getWidth() / 2);
            labels[i].setY(y + labels[i].getLayoutBounds().getHeight() / 2);

            pane.getChildren().addAll( labels[i]); // Додавання текстової мітки до панелі
        }

        VBox vbox = new VBox();
        vbox.getChildren().add(pane);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox); // Створення сцени з панеллю
        newStage.setScene(scene); // Встановлення сцени для вікна
        newStage.show(); // Відображення вікна
    }

    public Scene Dscene(Graph graph) {
        stage.setHeight(600); // Встановлює висоту вікна
        stage.setWidth(600); // Встановлює ширину вікна
        Dijkstra dijkstra = new Dijkstra(); // Створення екземпляру класу Dijkstra
        Map<String, Integer> shortestDistances = dijkstra.findShortestDistances(graph, startVertex); // Знаходження найкоротших відстаней

        VBox vBox = new VBox(10); // Вертикальний контейнер для елементів
        vBox.setSpacing(10); // Встановлює відступи між елементами
        vBox.setPadding(new Insets(10)); // Встановлює зовнішні відступи
        vBox.setAlignment(Pos.CENTER); // Розташування по центру

        // Вивести інформацію про найкоротші відстані та шляхи
        Text text = new Text("Інформація про найкоротші відстані та шляхи з вершини " + startVertex + ":");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Встановлення шрифту та розміру
        vBox.getChildren().add(text); // Додавання текстової мітки до контейнера

        for (String targetVertex : graph.getVertex()) {
            if (!startVertex.equals(targetVertex)) {
                List<String> shortestPath = dijkstra.getShortestPath(targetVertex); // Отримання найкоротшого шляху
                int distance = shortestDistances.get(targetVertex); // Отримання найкоротшої відстані
                Text infoLabel;

                if (distance == Integer.MAX_VALUE) {
                    infoLabel = new Text("До вершини " + targetVertex + ": Немає шляху");
                    infoLabel.setFont(Font.font("Arial", 16)); // Встановлення шрифту та розміру для текстової мітки
                } else {
                    infoLabel = new Text("До вершини " + targetVertex + ": " + String.join(" -> ", shortestPath) + ", Дистанція: " + distance);
                    infoLabel.setFont(Font.font("Arial", 16)); // Встановлення шрифту та розміру для текстової мітки
                }
                vBox.getChildren().add(infoLabel); // Додавання текстової мітки до контейнера
            }
        }

        ScrollPane scrollPane = new ScrollPane(vBox); // Створення прокручуваної панелі з контейнером

        return new Scene(scrollPane); // Повернення сцени
    }

    public Scene Fscene(Graph graph) {
        stage.setHeight(600); // Встановлює висоту вікна
        stage.setWidth(600); // Встановлює ширину вікна

        FloydWarshall floydWarshall = new FloydWarshall(); // Створення екземпляру класу FloydWarshall
        int[][] shortestDistances = floydWarshall.findAllShortestDistances(graph); // Знаходження найкоротших відстаней за допомогою алгоритму Флойда-Уоршелла

        VBox vBox = new VBox(10); // Вертикальний контейнер для елементів
        vBox.setSpacing(10); // Встановлює відступи між елементами
        vBox.setPadding(new Insets(10)); // Встановлює зовнішні відступи
        vBox.setAlignment(Pos.CENTER); // Розташування по центру

        // Вивести інформацію про найкоротші відстані
        Text text = new Text("Інформація про найкоротші відстані та шляхи з вершини " + startVertex + ":");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Встановлення шрифту та розміру
        vBox.getChildren().add(text); // Додавання текстової мітки до контейнера

        for (String targetVertex : graph.getVertex()) {
            if (!startVertex.equals(targetVertex)) {
                int distance = shortestDistances[Integer.parseInt(startVertex)][Integer.parseInt(targetVertex)]; // Отримання найкоротшої відстані
                Text infoLabel;

                if (distance == Integer.MAX_VALUE) {
                    infoLabel = new Text("До вершини " + targetVertex + ": Немає шляху");
                    infoLabel.setFont(Font.font("Arial", 16)); // Встановлення шрифту та розміру для текстової мітки
                } else {
                    infoLabel = new Text("До вершини " + targetVertex + ": " + distance);
                    infoLabel.setFont(Font.font("Arial", 16)); // Встановлення шрифту та розміру для текстової мітки
                }
                vBox.getChildren().add(infoLabel); // Додавання текстової мітки до контейнера
            }
        }
        ScrollPane scrollPane = new ScrollPane(vBox); // Створення прокручуваної панелі з контейнером

        return new Scene(scrollPane); // Повернення сцени
    }

    public static void main(String[] args) {
        launch(args); // Запуск JavaFX додатку
    }
}

