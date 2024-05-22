import org.junit.Before;
import org.example.Graph;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class GraphTest {

    private static final String TEST_GRAPH_FILE = "src/test/resources/test_guategrafo.txt";
    private Graph graph;

    @Before
    public void setUp() throws IOException {
        // Verificar si el archivo de prueba existe
        File testFile = new File(TEST_GRAPH_FILE);
        if (!testFile.exists()) {
            testFile.getParentFile().mkdirs();
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
        assertEquals(8, path.size());
        assertEquals("Guatemala_City", path.get(0));
        assertEquals("Antigua", path.get(1));
        assertEquals("Chimaltenango", path.get(2));
        assertEquals("Escuintla", path.get(3));
        assertEquals("Santa_Lucia", path.get(4));
    }

    @Test
    public void testShortestDistance() {
        int distance = graph.getShortestDistance("Guatemala_City", "Quetzaltenango");
        assertEquals(305, distance);
    }

    @Test
    public void testGraphCenter() {
        String center = graph.getGraphCenter();
        assertEquals("Antigua", center);
    }

}
