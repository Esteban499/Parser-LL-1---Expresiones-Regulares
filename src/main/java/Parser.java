class Parser {
    private final Lexer lexer;
    private Token currentToken;

    Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.nextToken();
    }

    private void eat(TokenType expected) {
        if (currentToken.type == expected) {
            currentToken = lexer.nextToken();
        } else {
            throw new RuntimeException("Error de sintaxis: se esperaba " + expected + " pero se encontró " + currentToken.type);
        }
    }

    public TreeNode parse() {
        TreeNode root = parseE();
        if (currentToken.type != TokenType.EOF) {
            throw new RuntimeException("Error de sintaxis: entrada no consumida");
        }
        return root;
    }

    private TreeNode parseE() {
        TreeNode node = new TreeNode("E");
        node.addChild(parseT());
        node.addChild(parseEPrime());
        return node;
    }

    private TreeNode parseEPrime() {
        TreeNode node = new TreeNode("E'");
        if (currentToken.type == TokenType.UNION) {
            node.addChild(new TreeNode("|"));
            eat(TokenType.UNION);
            node.addChild(parseT());
            node.addChild(parseEPrime());
        }
        return node;
    }

    private TreeNode parseT() {
        TreeNode node = new TreeNode("T");
        node.addChild(parseF());
        node.addChild(parseTPrime());
        return node;
    }

    private TreeNode parseTPrime() {
        TreeNode node = new TreeNode("T'");
        if (currentToken.type == TokenType.DOT) {
            node.addChild(new TreeNode("."));
            eat(TokenType.DOT);
            node.addChild(parseF());
            node.addChild(parseTPrime());
        }
        return node;
    }

    private TreeNode parseF() {
        TreeNode node = new TreeNode("F");
        node.addChild(parseP());
        node.addChild(parseFPrime());
        return node;
    }

    private TreeNode parseFPrime() {
        TreeNode node = new TreeNode("F'");
        if (currentToken.type == TokenType.STAR) {
            node.addChild(new TreeNode("*"));
            eat(TokenType.STAR);
            if (currentToken.type == TokenType.STAR) {
                throw new RuntimeException("Error: operador * duplicado");
            }
            node.addChild(parseFPrime());
        }
        return node;
    }

    private TreeNode parseP() {
        TreeNode node = new TreeNode("P");
        if (currentToken.type == TokenType.LPAREN) {
            node.addChild(new TreeNode("("));
            eat(TokenType.LPAREN);
            node.addChild(parseE());
            node.addChild(new TreeNode(")"));
            eat(TokenType.RPAREN);
        } else if (currentToken.type == TokenType.CHAR) {
            node.addChild(new TreeNode(currentToken.value));
            eat(TokenType.CHAR);
        } else {
            throw new RuntimeException("Error: se esperaba '(' o CHAR");
        }
        return node;
    }
}
