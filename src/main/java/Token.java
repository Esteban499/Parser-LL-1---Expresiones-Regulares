// Representa un token escaneado por el lexer
class Token {
    TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return String.format("Token(%s, '%s')", type, value);
    }
}
