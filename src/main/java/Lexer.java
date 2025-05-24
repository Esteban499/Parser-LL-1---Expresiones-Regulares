// Analizador léxico que convierte la entrada en una secuencia de tokens
class Lexer {
    private final String input;
    private int pos = 0;

    Lexer(String input) {
        this.input = input;
    }

    // Determina si un carácter es válido como literal (letra, número o símbolo especial permitido)
    private boolean isChar(char c) {
        return Character.isLetterOrDigit(c) || "!@#$%^&_=<>?~".indexOf(c) >= 0;
    }

    // Devuelve el siguiente token en la entrada
    Token nextToken() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }

        if (pos >= input.length()) return new Token(TokenType.EOF, "");

        char current = input.charAt(pos++);

        switch (current) {
            case '(':
                return new Token(TokenType.LPAREN, "(");
            case ')':
                return new Token(TokenType.RPAREN, ")");
            case '|':
                return new Token(TokenType.UNION, "|");
            case '*':
                return new Token(TokenType.STAR, "*");
            case '.':
                return new Token(TokenType.DOT, ".");
            default:
                if (isChar(current)) {
                    return new Token(TokenType.CHAR, Character.toString(current));
                } else {
                    throw new RuntimeException("Carácter no válido: " + current);
                }
        }
    }
}