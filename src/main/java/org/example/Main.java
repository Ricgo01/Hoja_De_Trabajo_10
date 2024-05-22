package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Ajustar la ruta del archivo según tu estructura de directorios
        String filePath = "src/main/resources/guategrafo.txt";
        Graph graph = new Graph(filePath);
        graph.floydWarshall();

        while (true) {
            System.out.println("Opciones:");
            System.out.println("1. Mostrar ruta más corta entre dos ciudades");
            System.out.println("2. Mostrar ciudad centro del grafo");
            System.out.println("3. Modificar el grafo");
            System.out.println("4. Salir");

            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Ciudad origen: ");
                String source = scanner.nextLine();
                System.out.print("Ciudad destino: ");
                String destination = scanner.nextLine();
                List<String> path = graph.getShortestPath(source, destination);
                if (path.isEmpty()) {
                    System.out.println("No hay ruta disponible.");
                } else {
                    System.out.println("Ruta más corta: " + String.join(" -> ", path));
                    System.out.println("Distancia: " + graph.getShortestDistance(source, destination));
                }
            } else if (option == 2) {
                System.out.println("Ciudad centro del grafo: " + graph.getGraphCenter());
            } else if (option == 3) {
                System.out.println("Modificar grafo:");
                System.out.println("a. Interrupción de tráfico");
                System.out.println("b. Establecer nueva conexión");
                String subOption = scanner.nextLine();
                System.out.print("Ciudad 1: ");
                String city1 = scanner.nextLine();
                System.out.print("Ciudad 2: ");
                String city2 = scanner.nextLine();
                if (subOption.equals("a")) {
                    graph.modifyGraph(city1, city2, Integer.MAX_VALUE);
                } else if (subOption.equals("b")) {
                    System.out.print("Distancia (KM): ");
                    int distance = scanner.nextInt();
                    scanner.nextLine();
                    graph.modifyGraph(city1, city2, distance);
                }
                graph.floydWarshall();
            } else if (option == 4) {
                break;
            }
        }
        scanner.close();
    }
}
