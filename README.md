# Parser LL(1) (Trabajo Práctico Obligatorio 1)  
**Gramática de expresiones regulares en LL(1)**

| No Terminal   | Producción             | Conjunto de Anticipación (Lookahead)       |
|:-------------:|:----------------------:|:------------------------------------------:|
| S             | → E#                   | { `(`, `a`, `b`, `c` }                     |
| E             | → TE'                  | { `(`, `a`, `b`, `c` }                     |
| E'            | → &#124; TE’           | { &#124; }                                 |
| E'            | → ε                    | { `#`, `)` }                               |
| T             | → FT'                  | { `(`, `a`, `b`, `c` }                     |
| T'            | → FT’                  | { `.` }                                    |
| T'            | → ε                    | { &#124; , `#`, `)` }                      |
| F             | → PF'                  | { `(`, `a`, `b`, `c` }                     |
| F'            | → `*`                  | { `*` }                                    |
| F'            | → ε                    | { `.`, &#124; , `#`, `)` }                 |
| P             | → (E)                  | { `(` }                                    |
| P             | → L                    | { `a`, `b`, `c` }                          |
| P             | → L                    | { `a` }                                    |
| P             | → L                    | { `b` }                                    |
| P             | → L                    | { `c` }                                    |
<hr>

# ¿Que se adaptó en el programa?

1. **Concatenación explicita con `.`:**
   - La gramática original admite implícitamente la concatenación (por ejemplo, `ab` se interpreta como `a` concatenado con `b`), pero **el programa exige el uso explícito del punto (`.`)**.
   - Esto **no cambia la gramática**, pero restringe su uso práctico a entradas que contengan `.` como símbolo de concatenación, lo cual **facilita el análisis sintáctico LL(1)**.
2. **Ampliación del conjunto de terminales `(L)`**:
   - Originalmente la gramática define `L → a | b | c`.
   - En el programa se acepta **cualquier letra, dígito y algunos símbolos especiales válidos en expresiones regulares** (como `!`, `?`, `%`, etc.).
   - Esta es una **extensión del conjunto de terminales**, pero **no modifica la estructura de la gramática**. Es simplemente una forma más amplia de definir la regla `L → <carácter válido>`.
<hr>
     
# Explicación del codigo
🔍 1. Analizador Léxico (Lexer)
     
**Objetivo:**
Convierte una cadena de entrada (una expresión regular) en una secuencia de tokens que el parser puede entender. Cada token representa un símbolo terminal de la gramática.
     
**Caracteristicas
- **Acepta letras (`a`–`z`, `A`–`Z`), números (`0`–`9`) y ciertos caracteres especiales válidos**.
- **Caracteres especiales reconocidos**:
  - `(`, `)`: Paréntesis para agrupar expresiones
  - `|`: Alternativa (or)
  - `*`: Cierre de Kleene
  - `.`: Concatenación explícita

🔠 **Lista de TokenType y ejemplo correspondiente**
|TokenType|Significado|Ejemplo de Entrada|Token Generado|
|---------|-----------|------------------|--------------|
|CHAR     |Cualquier carácter válido|a   |Token(CHAR, "a")|
|DOT      |Operador de concatenación|a.b |Token(DOT, ".")|
|OR       |Operador de alternativa|a&#124;b|Token(OR, "&#124;")|
|STAR     |Operador de cierre de Kleene|a*|Token(STAR, "*")|
|LPAREN   |Parentesis izquierdo|(a)      |Token(LPAREN, "(")|
|RPAREN   |Parentesis derecho|(a)        |Token(RPAREN, ")")|
|EOF      |Fin de la entrada|a#          |Token(RPAREN, ")")|

📘 2. Analizador Sintáctico (Parser)

### Objetivo:

Toma la secuencia de tokens del lexer y los analiza conforme a una **gramática LL(1)** para verificar si la expresión es válida y seguir su estructura recursivamente.

💡 **Principios de Diseño Usados**

- **Orientación a objetos:** Clases `Token`, `Lexer`, `Parser`.
- **Modularidad:** Cada componente (lexer, parser) está bien separado.
- **Comentarios extensivos:** Explican cada parte del código para facilitar su comprensión y mantenimiento.
- **Recursión:** Se usa en todas las reglas de la gramática LL(1) para reflejar su naturaleza jerárquica.

Realizado por:
   - [@Esteban499](https://github.com/Esteban499)
   - [@SantiMolina9](https://github.com/SantiMolina9) 
   - [@flordelcastillo](https://github.com/flordelcastillo)
   - [@JoelRodriguez174](https://github.com/JoelRodriguez174)

