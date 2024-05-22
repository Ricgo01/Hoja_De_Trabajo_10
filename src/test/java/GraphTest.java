

import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class GraphTest {

    private static final String TEST_GRAPH_FILE = "src/test/resources/test_guategrafo.txt";
    private Graph graph;

    @Before
    public void setUp() throws IOException {
        // Crear un archivo de prueba para las pruebas unitarias
        try (FileWriter writer = new FileWriter(TEST_GRAPH_FILE)) {
            writer.write("Guatemala_City Antigua 45\n");
            writer.write("Antigua Chimaltenango 30\n");
            writer.write("Chimaltenango Escuintla 35\n");
            writer.write("Escuintla Santa_Lucia 40\n");
            writer.write("Santa_Lucia Mazatenango 75\n");
            writer.write("Mazatenango Retalhuleu 25\n");
            writer.write("Retalhuleu Quetzaltenango 55\n");
            writer.write("Quetzaltenango Huehuetenango 80\n");
            writer.write("Huehuetenango Coban 140\n");
            writer.write("Coban Guatemala_City 200\n");
            writer.write("Antigua Coban 110\n");
            writer.write("Quetzaltenango Guatemala_City 210\n");
        }
        graph = new Graph(TEST_GRAPH_FILE);
        graph.floydWarshall();
    }

    @Test
    public void testLoadGraph() {
        assertNotNull(graph);
    }

    @Test
    public void testShortestPath() {
        List<String> path = graph.getShortestPath("Guatemala_City", "Quetzaltenango");
        assertEquals(5, path.size());
        assertEquals("Guatemala_City", path.get(0));
        assertEquals("Antigua", path.get(1));
        assertEquals("Chimaltenango", path.get(2));
        assertEquals("Escuintla", path.get(3));
        assertEquals("Quetzaltenango", path.get(4));
    }

    @Test
    public void testShortestDistance() {
        int distance = graph.getShortestDistance("Guatemala_City", "Quetzaltenango");
        assertEquals(155, distance);
    }

    @Test
    public void testGraphCenter() {
        String center = graph.getGraphCenter();
        assertEquals("Escuintla", center);
    }

    @Test
    public void testModifyGraph() throws IOException {
        graph.modifyGraph("Quetzaltenango", "Guatemala_City", Integer.MAX_VALUE);
        graph.floydWarshall();
        int distance = graph.getShortestDistance("Quetzaltenango", "Guatemala_City");
        assertEquals(Integer.MAX_VALUE, distance);
        List<String> path = graph.getShortestPath("Quetzaltenango", "Guatemala_City");
        assertTrue(path.isEmpty());
    }
}
