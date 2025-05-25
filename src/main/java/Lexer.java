// Analizador léxico: convierte una cadena de entrada en una secuencia de tokens
class Lexer {
    private final String input;
    private int pos = 0;

    Lexer(String input) {
        this.input = input;
    }

    Token nextToken() {
        while (pos < input.length()) {
            char c = input.charAt(pos++);
            switch (c) {
                case '|': return new Token(TokenType.UNION, "|");
                case '*': return new Token(TokenType.STAR, "*");
                case '.': return new Token(TokenType.DOT, ".");
                case '(': return new Token(TokenType.LPAREN, "(");
                case ')': return new Token(TokenType.RPAREN, ")");
                default:
                    if (Character.isLetterOrDigit(c) || "!@#$%^&?_-=+<>".indexOf(c) >= 0) {
                        return new Token(TokenType.CHAR, String.valueOf(c));
                    }
                    throw new RuntimeException("Carácter no válido: " + c);
            }
        }
        return new Token(TokenType.EOF, "");
    }
}