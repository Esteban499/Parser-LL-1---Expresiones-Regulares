import org.junit.Test;
import static org.junit.Assert.*;

public class ParserTest {

    // Método auxiliar para verificar si una expresión es válida
    private void assertValid(String regex) {
        try {
            Lexer lexer = new Lexer(regex);
            Parser parser = new Parser(lexer);
            parser.parse();
        } catch (RuntimeException e) {
            fail("Se esperaba que la expresión fuera válida: " + regex + " pero falló con: " + e.getMessage());
        }
    }

    // Método auxiliar para verificar si una expresión es inválida
    private void assertInvalid(String regex) {
        try {
            Lexer lexer = new Lexer(regex);
            Parser parser = new Parser(lexer);
            parser.parse();
            fail("Se esperaba que la expresión fuera inválida: " + regex);
        } catch (RuntimeException e) {
            // Esperado, no hacer nada
        }
    }

    // Casos válidos
    @Test public void testCharOnly() { assertValid("a"); }

    @Test public void testUnion() { assertValid("a|b"); }

    @Test public void testConcatenation() { assertValid("a.b"); }

    @Test public void testStar() { assertValid("a*"); }

    @Test public void testComplexExpr() { assertValid("(a|b).c*"); }

    @Test public void testNestedParentheses() { assertValid("((a.b)|c)*"); }

    @Test public void testSymbolsAsChar() { assertValid("a.@.1"); }

    @Test public void testLongValidExpr() { assertValid("a.b|c.d*|(e.f)*"); }

    // Casos inválidos
    @Test public void testDoubleStar() { assertInvalid("a**"); }

    @Test public void testUnclosedParenthesis() { assertInvalid("(a.b"); }

    @Test public void testUnexpectedToken() { assertInvalid("a|"); }

    @Test public void testEmptyExpr() { assertInvalid(""); }

    @Test public void testOnlyStar() { assertInvalid("*"); }

    @Test public void testOnlyUnion() { assertInvalid("|"); }

    @Test public void testOnlyDot() { assertInvalid("."); }

    @Test public void testOperatorWithoutOperands() { assertInvalid("|*"); }

    @Test public void testExtraClosingParen() { assertInvalid("a)"); }

    @Test public void testInvalidChar() { assertInvalid("a b"); } // contiene espacio

}
