# Parser LL(1) (Trabajo Práctico Obligatorio 1)  
**Gramática de expresiones regulares en LL(1)**

| No Terminal | Producción           | Conjunto de Anticipación (Lookahead)       |
|-------------|----------------------|--------------------------------------------|
| S           | → E#                 | `(`, `a`, `b`, `c`                         |
| E           | → TE'                | `(`, `a`, `b`, `c`                         |
| E'          | → `|`TE’            | `|`                                        |
| E'          | → ε                  | `#`, `)`                                   |
| T           | → FT'                | `(`, `a`, `b`, `c`                         |
| T'          | → FT’                | `.`                                        |
| T'          | → ε                  | `|`, `#`, `)`                              |
| F           | → PF'                | `(`, `a`, `b`, `c`                         |
| F'          | → `*`                | `*`                                        |
| F'          | → ε                  | `.`, `|`, `#`, `)`                         |
| P           | → `(`E`)`            | `(`                                        |
| P           | → L                  | `a`, `b`, `c`                              |
| P           | → L                  | `a`                                        |
| P           | → L                  | `b`                                        |
| P           | → L                  | `c`                                        |
