// Analizador sintáctico recursivo basado en una gramática LL(1)
class Parser {
    private final Lexer lexer;
    private Token currentToken;

    Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.nextToken();
    }

    private void eat(TokenType type) {
        if (currentToken.type == type) {
            currentToken = lexer.nextToken();
        } else {
            throw new RuntimeException("Error de sintaxis: se esperaba " + type + " pero se encontró ");
        }
    }

    // S → E EOF
    public void parse() {
        E();
        if (currentToken.type != TokenType.EOF) {
            throw new RuntimeException("Error: entrada inesperada después de la expresión válida: '" + currentToken.value+"'");
        }
    }

    // E → T E'
    private void E() {
        T();
        EPrime();
    }

    // E' → | T E' | ε
    private void EPrime() {
        if (currentToken.type == TokenType.UNION) {
            eat(TokenType.UNION);
            T();
            EPrime();
        }
    }

    // T → F T'
    private void T() {
        F();
        TPrime();
    }

    // T' → . F T' | ε
    private void TPrime() {
        if (currentToken.type == TokenType.DOT) {
            eat(TokenType.DOT);
            F();
            TPrime();
        }
    }

    // F → P F'
    private void F() {
        P();
        FPrime();
    }

    // F' → * F' | ε
    private void FPrime() {
        if (currentToken.type == TokenType.STAR) {
            eat(TokenType.STAR);
            FPrime();
        }
    }

    // P → (E) | L
    private void P() {
        if (currentToken.type == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            E();
            eat(TokenType.RPAREN);
        } else {
            L();
        }
    }

    // L → CHAR
    private void L() {
        if (currentToken.type == TokenType.CHAR) {
            eat(TokenType.CHAR);
        } else {
            throw new RuntimeException("Error: se esperaba un literal válido (letra, número o símbolo especial)");
        }
    }
}