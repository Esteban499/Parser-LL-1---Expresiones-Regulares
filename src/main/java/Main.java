import java.util.*;

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

            try {
                GraphvizTreeBuilder.generateImage(
                        tree,
                        "C:\\Users\\florf\\OneDrive\\Documentos\\ll1\\Parser-LL-1---Expresiones-Regulares/arbol.png",
                        terminales
                );
            } catch (Exception e) {
                System.err.println("Error generando imagen: " + e.getMessage());
            }

        } catch (RuntimeException e) {
            System.out.println("Inválido: " + e.getMessage());
        }
    }
}
