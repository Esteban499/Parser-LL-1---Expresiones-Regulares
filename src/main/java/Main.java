import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths; // Clases para implementacion de ruta dinamica

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce una expresion regular valida: ");
        String valor = sc.nextLine();

        System.out.println("Probando: " + valor);
        try {
            Lexer lexer = new Lexer(valor);
            Parser parser = new Parser(lexer);
            TreeNode tree = parser.parse();
            System.out.println("Válido");

            // Paso 1: construir lista de terminales reales
            List<String> terminales = new ArrayList<>();
            for (char c : valor.toCharArray()) {
                if (!Character.isWhitespace(c)) {
                    terminales.add(String.valueOf(c));
                }
            }

            // --- Implementación de Ruta Dinámica ---
            // Obtiene el directorio de trabajo actual (desde donde se lanzó tu aplicación)
            String currentWorkingDirectory = System.getProperty("user.dir");

            // Construye la ruta completa a arbol.png de forma independiente de la plataforma
            Path imagePath = Paths.get(currentWorkingDirectory, "arbol.png");
            String imagePathString = imagePath.toString(); // Convierte la ruta a String para usarla
            // --- Fin de Implementación de Ruta Dinámica ---

            try {
                GraphvizTreeBuilder.generateImage(
                        tree,
                        imagePathString, // Ruta dinamica
                        terminales
                );
            } catch (Exception e) {
                System.err.println("Error generando imagen: " + e.getMessage());
                e.printStackTrace(); // Imprime el rastro completo del error para depurar
            }

        } catch (RuntimeException e) {
            System.out.println("Inválido: " + e.getMessage());
        } finally {
            sc.close(); // Cierra el scanner para liberar recursos
        }
    }
}