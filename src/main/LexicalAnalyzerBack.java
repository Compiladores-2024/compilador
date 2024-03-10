package src.main;

import src.lib.Const;
import src.lib.CustomError;
import src.lib.exceptionHelper.LexicalException;
import src.lib.lexicalHelper.FileManager;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Clase LexicalAnalyzer encargada de generar los tokens encontrados en un
 * archivo fuente de tinyRu
 * 
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 6/03/2024
 */
public class LexicalAnalyzerBack {
    char[] currentLineString;
    int numColumn = 0;
    int numLine = 0;
    FileManager reader;

    public LexicalAnalyzerBack(String path) {
        reader = new FileManager(path);
    }

    public Token nextToken() throws LexicalException {
        // Tomara el valor entero (ASCII) del simbolo leido caracter por caracter del
        // codigo fuente
        int charAscii;

        // String utilizados para comprobar que tipo de lexema y token se esta formando
        // o analizando
        String currLexeme = "";
        String currToken = "";

        // Flags
        boolean reading = true;
        boolean flagNextLine = false;
        boolean incrementCol = true;

        // Guardara el token a retornar
        Token token = null;

        // Valida si debe leer la siguiente linea del codigo fuente
        if (currentLineString.length == 0) {
            // System.out.println("LECTURA");
            currentLineString = reader.getLine();
        }

        while (reading && currentLineString != null) {
            System.out.println("Currtoken: " + currToken);

            if (numColumn >= currentLineString.length ) {
                // System.out.println("FINAL LINEA");

                if (!currToken.equals("")) {
                    // int col= numColumn-currLexeme.length();
                    System.out.println("AFUEEERA");
                    token = new Token(IDToken.constSTR, currLexeme, numLine, numColumn);

                    reading = false;
                    currentLineString = new char[] {};
                    flagNextLine = true;
                } else {

                    reading = true;

                    currentLineString = reader.getLine();
                    System.out.println(currentLineString);

                }
                currLexeme = "";
                currToken = "";

                numColumn = 0;
                numLine += 1;

            }

            if (this.currentLineString == null || this.currentLineString.length == 0) {
                reading = false;
            }

            else if (currentLineString.length > 0) {

                System.out.println("bbb");
                System.out.println("Current Line:  " + currentLineString.toString());
                char[] line = currentLineString;

                charAscii = line[numColumn];
                System.out.println("CharASCII: " + charAscii);
                char character = (char) charAscii;
                System.out.println(character);

                if (charAscii == 32 || charAscii == 9) {
                    // espacio o tab horizontal
                    // this.numColumn += 1;
                    System.out.println("Espacio");
                }
                if (!currToken.equals("") && charAscii == 32) {
                    // int col= numColumn-currLexeme.length();
                    System.out.println("SE FUE");
                    token = new Token(IDToken.constSTR, currLexeme, numLine, numColumn);

                    currLexeme = "";
                    currToken = "";

                    reading = false;

                }
                // si actualmente se analiza un literal cadena
                else if (currToken == "lit_cadena") {
                    // System.out.println("LITERAL CADENA: "+character);
                    currLexeme += character;
                    // si llega una comilla doble significa el final de la cadena
                    if (charAscii == 34) {
                        // int col= numColumn-currLexeme.length();
                        token = new Token(IDToken.constSTR, currLexeme, numLine, numColumn);

                        reading = false;

                        currLexeme = "";
                        currToken = "";
                    }
                }

                else if (currToken == "id_class" || currToken == "id_objeto") {
                    System.out.println("ACA");
                    // si viene [a..z] o guion bajo _ o [A..Z] o numeros [0..9] entonces se sigue
                    // formando el lexema
                    if ((charAscii > 64 && charAscii < 91) || (charAscii == 95) || (charAscii > 96 && charAscii < 123) || (charAscii > 47 && charAscii < 58)) {
                        currLexeme = currLexeme + character;
                    }
                    // llega un espacio o punto y coma o coma, o parentesis
                    else if (charAscii == 32 || charAscii == 59 || charAscii == 44 || charAscii == 40 || charAscii == 41) {
                        token = new Token(IDToken.constSTR, currLexeme, numLine, numColumn);

                        reading = false;
                        incrementCol = false;
                    }
                    // caso contrario llego un simbolo invalido para la definicion de identificador
                    else {
                        currLexeme = currLexeme + character;
                        throw new LexicalException(new CustomError(numLine, numColumn, "Identificador invalido: " + currLexeme), "");

                    }
                    // si hay mas caracteres pero no son letras ni guion bajo
                    // entonces se comprueba si es una keyword
                    if (numColumn + 1 < line.length &&
                            !((line[numColumn + 1] > 64 && line[numColumn + 1] < 91) ||
                                    (line[numColumn + 1] > 96 && line[numColumn + 1] < 123 ||
                                            line[numColumn + 1] == 95))) {
                        // si el lexema es una palabra clave
                        if (Const.KEY_WORDS.contains(currLexeme)) {
                            // int col= numColumn-currLexeme.length();
                            token = new Token(IDToken.constSTR, currLexeme, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }
                    }
                }
                // ignora espacio alt+32, tab horizontal alt+9
                else if (charAscii != 32 && charAscii != 9) {
                    System.out.println("curr token: " + currToken);
                    // si se esta analizando lit_ent y aparece otra cosa es un error
                    if (!(charAscii > 47 && charAscii < 58) && (currToken == "lit_ent")) {
                        throw new LexicalException(new CustomError(numLine, numColumn, "Literal entero invalido"), "");
                    }
                    // si aparece un entero es un literal entero
                    else if ((charAscii > 47 && charAscii < 58 && (currToken == "" || currToken == "lit_ent"))) {
                        currToken = "lit_ent"; // IDToken.constINT
                        currLexeme += character;

                    }
                    // si viene una comilla doble alt+34 es un literal cadena
                    else if ((charAscii == 34)) {
                        // si se estaba analizando algo que no sea literal cadena se guarda el token

                        if (currToken != "" && currToken != "lit_cadena") {
                            token = new Token(IDToken.constSTR, currLexeme, numLine, numColumn);
                            currLexeme = "";
                            currToken = "";

                            reading = false;
                        }

                        // despues se setea el currToken="lit_cadena"
                        // y se añade al lexema+=character

                        currToken = "lit_cadena";
                        currLexeme += character;
                    }

                    // si viene una comilla simple alt+39 es un literal caracter
                    else if ((charAscii == 39)) {

                    }

                    // si viene un simbolo puede ser simbolo invalido
                    // o puede ser un operador (suma, resta, modulo, division, etc)
                    // los simbolos aceptados: +, -, *,++,–, / , %, && (and), || (or) y ! (not), <,
                    // <=,==,>= y !=
                    // ( parentesisAbre ) parentesisCierra [CorchetesAbre ]CorchetesCierra

                    // y distinto de ñ y Ñ

                    else if ((charAscii == 33) || (charAscii > 34 && charAscii < 39) ||
                            (charAscii > 39 && charAscii < 47) ||
                            (charAscii > 57 && charAscii < 65) || (charAscii > 90 && charAscii < 97)
                            || (charAscii > 122) && charAscii != 164 && charAscii != 165) {

                        System.out.println("simbolos");

                        // operador not !
                        if (charAscii == 33) {

                            // si currToken esta vacio
                            if (currToken == "") {

                                currToken = "OpNot";
                                currLexeme += character;
                            }
                        }

                        // es modulo %
                        else if (charAscii == 37) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.oMOD, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }
                        // es parentesis abre ( * alt+40
                        else if (charAscii == 40) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.sPAR_OPEN, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";

                        }
                        // parentesis cierra ) alt+41
                        else if (charAscii == 41) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.sPAR_CLOSE, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";

                        }

                        // es multiplicacion * alt+42
                        else if (charAscii == 42) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.oMULT, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }

                        // es suma + alt+43
                        else if (charAscii == 43) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.oSUM, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }

                        // es coma , alt+44
                        else if (charAscii == 44) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.sCOM, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";

                        }

                        // es resta - alt+45
                        else if (charAscii == 45) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.oSUB, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }
                        // es punto . alt+46
                        else if (charAscii == 46) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.sDOT, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }

                        // dos puntos : alt+58
                        else if (charAscii == 58) {
                            String s = String.valueOf(character);
                            token = new Token(IDToken.sCOLON, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }
                        // es punto y coma ; alt+59
                        else if (charAscii == 59) {
                            String s = String.valueOf(character);
                            token = new Token(IDToken.sSEMICOLON, s, numLine, numColumn);

                            reading = false;
                            currLexeme += character;
                            currToken = "sSEMICOL";
                        }

                        // es corchete abre [ alt+91
                        else if (charAscii == 91) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.sCOR_OPEN, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }
                        // es corchete cierra ] ; alt+93
                        else if (charAscii == 93) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.sCOR_CLOSE, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }
                        // es llave abre { ; alt+123
                        else if (charAscii == 123) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.sKEY_OPEN, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }

                        // es llave cierra } ; alt+125
                        else if (charAscii == 125) {

                            String s = String.valueOf(character);
                            token = new Token(IDToken.sKEY_CLOSE, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }

                        // operador comparacion menor < alt+60
                        else if (charAscii == 60) {

                            currLexeme += character;
                            if ((currToken == "op_menor" || currToken == "op_mayor")) {

                                throw new LexicalException(new CustomError(numLine, numColumn, "Operador mal formado: se esperaba signo = para el lexema: " + currLexeme), "");
                            }

                            if (currToken == "") {
                                currToken = "op_menor";

                            }

                        }

                        // operador comparacion mayor > alt+62
                        else if (charAscii == 62) {

                            currLexeme += character;
                            if ((currToken == "op_menor" || currToken == "op_mayor")) {

                                throw new LexicalException(new CustomError(numLine, numColumn, "Operador mal formado: se esperaba signo = para el lexema: " + currLexeme), "");
                            }

                            if (currToken == "") {
                                currToken = "op_mayor";

                            }

                        }

                        // operador igual = alt+61

                        // puede que se haya estado analizando un < entonces menor-igual

                        // puede que se haya estado analizando un > entonces mayor-igual

                        // puede que se haya estado analizando un ! entonces distinto

                        // puede que se haya estado analizando un = entonces igual

                        // sino sera un operador asignacion

                        // operador and &&

                        // operador or ||

                        // Simbolo invalido
                        else {
                            throw new LexicalException(new CustomError(numLine, numColumn, "Simbolo invalido: " + character), "");

                        }

                    }

                    // si viene una barra inclinada / alt+47
                    // puede ser operador div
                    // o puede ser comentario simple /?......
                    else if ((charAscii == 47))

                        // si habia algun token se deberia de guardar
                        if (!currToken.equals("")) {
                            // int col= numColumn-currLexeme.length();

                            token = new Token(IDToken.idVAR, currLexeme, numLine, numColumn);

                            reading = false;
                        }

                        // es un comentario
                        else if (numColumn + 1 < line.length && (line[numColumn + 1] == '?')) {
                            reading = true;
                            currentLineString = reader.getLine();
                            numColumn = 0;
                            flagNextLine = true;
                            numLine += 1;
                        }

                        // es operador div /
                        else {

                            System.out.println("LLEGAMOS");

                            String s = String.valueOf(character);
                            token = new Token(IDToken.oDIV, s, numLine, numColumn);

                            reading = false;
                            currLexeme = "";
                            currToken = "";
                        }

                    //

                    // si aparece un caracter de letra [a..z] es un id_objeto
                    else if ((charAscii > 96 && charAscii < 123) && currLexeme == "") {
                        System.out.println("aaaaaaaaaaa");
                        currToken = "id_objeto";
                        currLexeme += character;
                    }
                    // si aparece un caracter de letra [A..Z] es un id_class
                    else if (((charAscii > 64 && charAscii < 91 && currLexeme == ""))) {

                        currLexeme += character;
                        currToken = "id_class";

                    }

                }
            }

            if (flagNextLine) {
                numColumn = 0;
            } else {
                if (incrementCol == true) {
                    numColumn++;
                }
                incrementCol = true;

            }
            flagNextLine = false;

        }

        return token;

    }

}