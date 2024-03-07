package src.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.lib.LexicalException;
import src.lib.tokenHelper.Token;

/**
 * Clase LexicalAnalyzer encargada de generar los tokens encontrados en un archivo fuente de tinyRu
 * 
 * 
 * @author Federico Gimenez
 * @author Cristian Serrano
 */
public class LexicalAnalyzer {


    String currentLine;
    int numColumn;
    int numLine;

    ArrayList<Token> tokenList = new ArrayList<Token>();


    public static final String KEY_WORDS[] = new String[]{
        "struct", "impl", "else", "false", "if", "ret", "while", "true", "nil", "false",
        "new", "fn", "st", "pri", "self"};

    public LexicalAnalyzer(String path){
        
        FileReader reader = new FileReader(path);


    }

	/**
	 * add Token a tokenList
	 * 
	 * @param token Token a añadir a la lista de tokens
	 */
	public void addToken(Token token) {
		this.tokenList.add(token);
	}

    public Token nextToken(char[] line){

        // se genera una lista con las keywords
        // para poder verificar si un identificador que se este analizando pertenece
        // o no a las palabras reservadas 
        List key_words = Arrays.asList(KEY_WORDS);

        // se define un int que tomara el valor entero (ASCII) del simbolo leido caracter por caracter del codigo fuente
        int charAscii;

        // String utilizados para comprobar que tipo de lexema y token se esta formando o analizando 
        String currLexeme="";
        String currToken="";


        try {
            for (int i = 0; i < line.length; i++) {
                charAscii=line[i];
                char character = (char) charAscii;

                // Si lee EOF añade el token y termina el bucle asignando reading false
                if (charAscii==-1){
                    
                    
                    Token token= new Token(numLine, numColumn, "EOF", "EOF");
                    addToken(token);

                }

                //si viene una linea nueva se deben actualizar los indices de fila y columna
                if (charAscii == 10 || charAscii == 11) {
                    //con nueva línea o tab vertical
                    this.numLine += 1;
                    this.numColumn = 1;
                }
                if (charAscii == 32 || charAscii == 9) {
                    // espacio o tab horizontal 
                    this.numColumn += 1;
                }

                //si actualmente se analiza un literal cadena
                if (currToken=="lit_cad"){
                    currLexeme+=currLexeme;
                    //si llega una comilla doble significa el final de la cadena
                    if (charAscii == 34){
                        numColumn= i-currLexeme.length();
                        Token token= new Token(numLine, numColumn,currLexeme, currToken );
                        addToken(token);
                        currLexeme="";
                        currToken="";
                    }
                }

                if (currToken=="id_class" || currToken=="id_objeto"){
                    // si viene [a..z] o guion bajo _  o [A..Z] o numeros [0..9] entonces se sigue formando el lexema
                    if ((charAscii > 64 && charAscii < 91) || (charAscii == 95) ||(charAscii > 96 && charAscii < 123) || (charAscii > 47 && charAscii < 58)) {
                        currLexeme = currLexeme + character;
                    }
                    //caso contrario llego un simbolo invalido para la definicion de identificador
                    else{
                        throw new LexicalException(numLine, numColumn, "Identificador invalido: "+currLexeme);


                    }
                    //si el lexema es una palabra clave
                    if (key_words.contains(currLexeme)){
                        numColumn= i-currLexeme.length();
                        Token token= new Token(numLine, numColumn,currLexeme, currToken );
                        addToken(token);
                        currLexeme="";
                        currToken="";
                    }
                }

                //si hay un currToken analizando y llega un espacio alt+32 se deberia de guardar el currToken
                else if ( !currToken.equals("") && charAscii!=32 ){
                    numColumn=i-currLexeme.length();
                    Token token= new Token(numLine, numColumn,currLexeme, currToken );
                    addToken(token);
                    currLexeme="";
                    currToken="";

                }

                //ignora espacio alt+32, tab horizontal alt+9
                else if (charAscii != 32 && charAscii != 9) {
                    //si aparece un entero es un literal entero
                    if ( (charAscii>47 && charAscii<58 && (currToken=="" || currToken=="lit_ent" )  ) ){
                        currToken="lit_ent";
                        currLexeme+=character;


                    }
                    //si viene una comilla doble alt+34 es un literal cadena
                    else if ( (charAscii==34) ){


                    }

                    //si viene una comilla simple alt+39 es un literal caracter
                    else if ( (charAscii==39) ){


                    }

                    //si viene un simbolo puede ser simbolo invalido
                    // o puede ser un operador (suma, resta, modulo, division, etc)
                    else if (){


                    }

                    //si viene una barra inclinada / alt+47 
                    // puede ser operador div
                    // o puede ser comentario simple /?......
                    else if ( (charAscii==47) )


                        //si habia algun token se deberia de guardar
                        if (!currToken.equals("")){
                            numColumn=i-currLexeme.length();
                            Token token= new Token(numLine, numColumn,currLexeme, currToken );
                            addToken(token);
                            currLexeme="";
                            currToken="";
                        }

                        //es un comentario
                        else if (  ){

                        }

                        //es operador div /
                        else {
                            Token token=new Token(numLine,i,character,"op_div");
                            addToken(token);
                        }




                    //si aparece un caracter de letra [a..z] es un id_objeto
                    else if ( (charAscii>96 && charAscii<123) && currLexeme=="") {
                        currToken="id_objeto";
                        currLexeme+=character;
                    }
                    //si aparece un caracter de letra [A..Z] es un id_class
                    else if ( ((charAscii>64 && charAscii<91 && currLexeme=="") )) {
                        
                        currLexeme+=character;
                        currToken="id_class";

                    }

                }





                i++;

            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    
}