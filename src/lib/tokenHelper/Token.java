package src.lib.tokenHelper;

/**
 * La clase Token se utilizará para guardar la metadata de un Token específico.
 * Esta metadata contiene datos de relevancia para facilitar las operaciones
 * de estos en todo el proyecto.
 *  
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 07/03/2024
 */
public class Token {
    private IDToken name;
    private String lexema;
    private int line;
    private int column;

    public Token(){

    }

    /**
     * Constructor de la clase.
     * 
     * @since 07/03/2024
     * @param name Token.
     * @param lexema Lexema.
     * @param line Línea en la que se encuentra el token.
     * @param column Columna en la que se encuentra el token.
     */
    public Token (IDToken name, String lexema, int line, int column) {
        this.name = name;
        this.lexema = lexema;
        this.line = line;
        this.column = column;
    }

    /**
     * Método que retorna un string con los datos formateados de tal manera que
     * cumplan los estándares de la cátedra.
     * 
     * @since 07/03/2024
     */
    @Override
    public String toString() {
        return "| " + name + " | " + lexema + " | LINEA " + Integer.toString(line) + " (COLUMNA " + Integer.toString(column) + ") |";
    }

    public void setLexema(String lex){
        this.lexema=lex;
    }

    public void setLine(int numLine){
        this.line=numLine;
    }

    public void setColumn(int numCol){
        this.column=numCol;
    }

    public void setName(IDToken value){
        this.name=value;

    }

    public int getLine(){
        return line;
    }

    public String getLexema(){
        return lexema;
    }

}
