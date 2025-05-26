import java.util.Scanner;

// Clase principal con pruebas para validar expresiones
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce una expresion regular valida: ");
        String valor = sc.nextLine();

            //Verificamos si la expresión es correcta usando LL1
            System.out.println("Probando: " + valor);
            try {
                //Creación de Objetos Parser y Lexer.
                Lexer lexer = new Lexer(valor);
                Parser parser = new Parser(lexer);
                parser.parse(); //Acá comenzamos el parseo de la expresión
                System.out.println("Válido");
            } catch (RuntimeException e) { // Si es válido retornamos el mensaje "válido" sino un error message.
                System.out.println("Inválido: " + e.getMessage());
            }
    }
}