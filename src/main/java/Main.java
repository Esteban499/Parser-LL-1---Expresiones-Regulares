import java.util.Scanner;

// Clase principal con pruebas para validar expresiones
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce una expresion regular valida: ");
        String valor = sc.nextLine();

            System.out.println("Probando: " + valor);
            try {
                Lexer lexer = new Lexer(valor);
                Parser parser = new Parser(lexer);
                parser.parse();
                System.out.println("Válido");
            } catch (RuntimeException e) {
                System.out.println("Inválido: " + e.getMessage());
            }
    }
}