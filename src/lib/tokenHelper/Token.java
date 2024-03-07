package src.lib.tokenHelper;

public class Token {
    private IDToken name;
    private String lexema;
    private Integer line;
    private Integer column;

    public Token (IDToken name, String lexema, Integer line, Integer column) {
        this.name = name;
        this.lexema = lexema;
        this.line = line;
        this.column = column;
    }

    public String toString() {
        return "| " + name + " | " + lexema + " | LINEA " + line.toString() + " (COLUMNA " + column.toString() + ") |";
    }
}
