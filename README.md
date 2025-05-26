# Parser LL(1) (Trabajo Práctico Obligatorio 1)  
**Gramática de expresiones regulares en LL(1)**

| No Terminal | Producción           | Conjunto de Anticipación (Lookahead)       |
|-------------|----------------------|--------------------------------------------|
| S           | → E#                 | `(`, `a`, `b`, `c`                         |
| E           | → TE'                | `(`, `a`, `b`, `c`                         |
| E'          | → &#124;TE’             | &#124;                                        |
| E'          | → ε                  | `#`, `)`                                   |
| T           | → FT'                | `(`, `a`, `b`, `c`                         |
| T'          | → FT’                | `.`                                        |
| T'          | → ε                  | &#124;, `#`, `)`                              |
| F           | → PF'                | `(`, `a`, `b`, `c`                         |
| F'          | → `*`                | `*`                                        |
| F'          | → ε                  | `.`, &#124; , `#`, `)`                         |
| P           | → `(`E`)`            | `(`                                        |
| P           | → L                  | `a`, `b`, `c`                              |
| P           | → L                  | `a`                                        |
| P           | → L                  | `b`                                        |
| P           | → L                  | `c`                                        |
