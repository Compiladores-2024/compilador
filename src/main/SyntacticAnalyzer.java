package src.main;

import src.lib.exceptionHelper.SyntacticException;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;
import src.lib.syntaxHelper.First;

/**
 * Analizador sintáctico, se encargará de consultar tokens al analizador léxico
 * y validar que el código fuente posea una sintaxis correcta a patir de la 
 * gramática.
 * 
 * @since 06/04/2024
 * @author Cristian Serrano
 * @author Federico Gimenez
 */
public class SyntacticAnalyzer { 
    LexicalAnalyzer lexicalAnalyzer;
    Token currentToken;

    /**
     * Constructor de la clase.
     * 
     * @since 06/04/2024
     * @param path Ubicación del código fuente a leer.
     */
    public SyntacticAnalyzer(String path){
        lexicalAnalyzer = new LexicalAnalyzer(path);
    }

    /**
     * Método que comenzará la ejecución del análisis sintáctico.
     * 
     * @since 06/04/2024
     * @throws SyntacticException
     */
    public void run () throws SyntacticException {
        //Obtiene el token inicial
        currentToken = lexicalAnalyzer.nextToken();

        //Comienza el análisis
        this.program();

        //Si el análisis no retorna error, ha sido correcto
        System.out.println("CORRECTO: ANALISIS SINTACTICO");
    }

    /**
     * Funcion de macheo.
     * 
     * Matchea un idToken pasado por parámetro y lo compara con el que se está
     * analizando en ese momento.
     * 
     * @param idToken
     * @return Booleano que indica si los tokens matchean
     */
    private boolean match(IDToken idToken){
        boolean bMatching = currentToken.getIDToken().equals(idToken);

        //Si matchean, solicita el siguiente token
        if(bMatching){
            this.currentToken = this.lexicalAnalyzer.nextToken();
        }

        return bMatching;
    }

    /**
     * Funcion auxiliar que retorna una excepción con la descripción
     * correspondiente.
     * 
     * @param idToken
     * @return Excepción tipo SyntacticException
     */
    private SyntacticException throwError(String expected){
        return new SyntacticException(currentToken, expected);
    }

    /**
     * chequea el idToken del token actual con un idToken pasado por parametro. 
     * Sin consumir dicho token.
     * El idToken pasado por parametro pertenece a primeros 
     * de la funcion desde la que se llamo
     * * @param idToken
     * @return boolean
     */
    private boolean check(IDToken idToken){
        return (currentToken.getIDToken().equals(idToken));  
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <program> ::= <Lista-Definiciones><Start> | <Start>
    */
    private void program() {
        if (check(IDToken.idSTRUCT) || check(IDToken.pIMPL)){
            listaDefiniciones();
            start();
        }
        else{
            if (First.check(First.firstStart, currentToken.getIDToken())){
                start();
            }
            else{
                throw throwError("Token 'start' o 'impl' o 'struct'");
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Start> ::= start <Bloque-Método>  
    */
    private void start() {
        if (match(IDToken.idSTART)){
            //bloqueMetodo();
        }else{
            throw throwError("Token 'start'");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Struct> ::= struct idStruct <Struct’>  
    */
    private void struct() throws SyntacticException{
        if (match(IDToken.pSTRUCT)){
            if (match(IDToken.idSTRUCT)){
                structP();
            }
            else{
                throw throwError("Token idSTRUCT");
            }
        }
        else{
            throw throwError("Token pSTRUCT");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Struct’> ::= <Herencia’> { <Atributo’> } | <Herencia’> { } | { <Atributo’> }  
    */
    private void structP() throws SyntacticException{
        if (First.check(First.firstHerenciaP, currentToken.getIDToken())){
            herenciaP();
        }
        if (match(IDToken.sKEY_OPEN)){
            if ((First.check(First.firstAtributoP, currentToken.getIDToken()))){
                atributoP();
            }

        }
        if (!match(IDToken.sKEY_CLOSE)){
            throw throwError("HERENCIA o ATRIBUTO");
        }

    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Impl> ::= impl idStruct { <Miembro’> }  
    */
    private void impl () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Herencia> ::= : <Tipo>  
    */
    private void herencia () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Miembro> ::= <Método> | <Constructor>  
    */
    private void miembro () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Constructor> ::= . <Argumentos-Formales> <Bloque-Método>  
    */
    private void constructor () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Atributo> ::= <Visibilidad’> <Tipo> <Lista-Declaración-Variables> ;  | <Tipo> <Lista-Declaración-Variables> ;  
    */
    private void atributo () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Método> ::= fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  | <Forma-Método’>fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  
    */
    private void metodo () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Visibilidad> ::= pri  
    */
    private void visibilidad () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Forma-Método> ::= st  
    */
    private void formaMetodo () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Bloque-Método> ::= { <Decl-Var-Locales’> <Sentencia’> } | { <Sentencia’> } | { <Decl-Var-Locales’> }  
    */
    private void bloqueMetodo() throws SyntacticException{
        if (match(IDToken.sKEY_OPEN)){
            if (First.check(First.firstDeclVarLocalesP, currentToken.getIDToken())){
                declVarLocalesP();
            }
            if (First.check(First.firstSentenciaP, currentToken.getIDToken())){
                sentenciaP();
            }
            if (!match(IDToken.sKEY_CLOSE)){
                throwError("DECLARACION VARIABLES LOCALES O SENTENCIA");
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Decl-Var-Locales> ::= <Tipo> <Lista-Declaración-Variables> ;   
    */
    private void declVarLocales () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Declaración-Variables>::= idMetAt | idMetAt , <Lista-Declaración-Variables>  
    */
    private void listaDeclaracionVariables () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumentos-Formales>::= ( <Lista-Argumentos-Formales’> ) | ( )  
    */
    private void argumentosFormales () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Argumentos-Formales> ::= <Argumento-Formal> , <Lista-Argumentos-Formales> | <Argumento-Formal>  
    */
    private void listaArgumentosFomales () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumento-Formal> ::= <Tipo> idMetAt  
    */
    private void argumentoFormal () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Método> ::= <Tipo> | void  
    */
    private void tipoMetodo () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo> ::= <Tipo-Primitivo> | <Tipo-Referencia> | <Tipo-Arreglo>  
    */
    private void tipo () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Primitivo> ::= Str | Bool | Int | Char  
    */
    private void tipoPrimitivo () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Referencia> ::= idStruct  
    */
    private void tipoReferencia () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Arreglo> ::= Array <Tipo-Primitivo>  
    */
    private void tipoArreglo () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Sentencia> ::= ; 
     * | <Asignación> ; 
     * | <Sentencia-Simple> ; 
     * | if ( <Expresión> ) <Sentencia> 
     * | if ( <Expresión> ) <Sentencia> <MoreIF> 
     * | while ( <Expresión> ) <Sentencia> 
     * | <Bloque> 
     * | ret <Expresión’> ; 
     * | ret ;  
    */
    private void sentencia () {
        
    }

    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <MoreIF> ::= else <Sentencia>
    */
    private void moreIF () {
        
    }

    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Bloque> ::= { <Sentencia’> } | { }  
    */
    private void bloque () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Asignación> ::= <AccesoVar-Simple> = <Expresión> | <AccesoSelf-Simple>=<Expresión>  
    */
    private void asignacion () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoVar-Simple> ::= id <Encadenado-Simple’> | id [ <Expresión> ] | id  
    */
    private void accesoVarSimple () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoSelf-Simple> ::= self <Encadenado-Simple’> | self  
    */
    private void accesoSelfSimple () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado-Simple> ::= . id  
    */
    private void encadenadoSimple () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Sentencia-Simple> ::= ( <Expresión> )  
    */
    private void sentenciaSimple () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Expresión> ::= <ExpOr>  
    */
    private void expresion () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpOr> ::= <ExpAnd> <ExpOr’> | <ExpAnd>  
    */
    private void expOr () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAnd> ::= <ExpIgual><ExpAnd’> | <ExpIgual>  
    */
    private void expAnd () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpIgual> ::= <ExpCompuesta><ExpIgual’> | <ExpCompuesta>  
    */
    private void expIgual () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAd> ::= <ExpMul><ExpAd’> | <ExpMul>  
    */
    private void expAd () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpMul> ::= <ExpUn> <ExpMul’> | <ExpUn>  
    */
    private void expMul () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpCompuesta> ::= <ExpAd> <OpCompuesto> <ExpAd> | <ExpAd>  
    */
    private void expCompuesta () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpUn> ::= <OpUnario> <ExpUn> | <Operando>  
    */
    private void expUn () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpIgual> ::= == | !=  
    */
    private void opIgual () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpCompuesto> ::= < |> | <= |>=  
    */
    private void opCompuesto () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpAd> ::= + | -  
    */
    private void opAd () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpUnario> ::= + | - | ! | ++ | --  
    */
    private void opUnario () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpMul> ::= * | / | %  
    */
    private void opMul () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Operando> ::= <Literal> | <Primario> <Encadenado’> | <Primario>  
    */
    private void operando () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Literal> ::= nil | true | false | intLiteral | StrLiteral | charLiteral   
    */
    private void literal () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Primario> ::= <ExpresionParentizada> | <AccesoSelf> | <AccesoVar>  | <Llamada-Método>  | <Llamada-Método-Estático> | <Llamada-Constructor>  
    */
    private void primario () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpresionParentizada> ::= ( <Expresión> ) <Encadenado’> | ( <Expresión> )   
    */
    private void expresionParentizada () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoSelf> ::= self <Encadenado’> | self  
    */
    private void accesoSelf () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoVar> ::= id <Encadenado’> | id  |  id [ <Expresión> ] <Encadenado’> | id [ <Expresión> ]  
    */
    private void accesoVar () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Llamada-Método> ::= id <Argumentos-Actuales> <Encadenado’> | id <Argumentos-Actuales>  
    */
    private void llamadaMetodo () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Llamada-Método-Estático> ::= idStruct . <Llamada-Método> <Encadenado’>  |  idStruct . <Llamada-Método>  
    */
    private void llamadaMetodoEstatico () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Llamada-Constructor> ::= new idStruct <Argumentos-Actuales> <Encadenado’>  | new <Tipo-Primitivo> [ <Expresión> ] | new idStruct <Argumentos-Actuales>  
    */
    private void llamadaConstructor () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumentos-Actuales> ::= ( <Lista-Expresiones’> ) | ( )  
    */
    private void argumentosActuales () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Expresiones> ::= <Expresión> | <Expresión> , <Lista-Expresiones>   
    */
    private void listaExpresiones () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado> ::= . <Llamada-Método-Encadenado> | . <Acceso-Variable-Encadenado>   
    */
    private void encadenado () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Llamada-Método-Encadenado> ::= id <Argumentos-Actuales> <Encadenado’>  |  id <Argumentos-Actuales>  
    */
    private void llamadaMetodoEncadenado () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Acceso-Variable-Encadenado> ::= id <Encadenado’>  | id   |  id [ <Expresión> ] <Encadenado’> | id [ <Expresión> ]   
    */
    private void accesoVariableEncadenado () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Definiciones>::= <Struct><Lista-Definiciones> | <Struct> | <Impl><Lista-Definiciones> | <Impl>  
    */
    private void listaDefiniciones() throws SyntacticException {
        
        if (First.check(First.firstImpl, currentToken.getIDToken())){
            impl();
            listaDefiniciones();
        }
        else{
            if (First.check(First.firstStruct, currentToken.getIDToken())){
                struct();
                listaDefiniciones();
            }
            else{
                throw throwError("Token 'impl' o 'struct'");
            }
        }
    }



    // private void listaDefiniciones() {

    //     if (check(IDToken.pIMPL)){
    //         impl();
    //     }
    //     else{
    //         if (check(IDToken.idSTRUCT)){
    //             struct();
    //         }
    //     }
    // }




    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Atributo’> ::= <Atributo><Atributo’> | <Atributo>  
    */
    private void atributoP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Decl-Var-Locales’> ::= <Decl-Var-Locales><Decl-Var-Locales’> | <Decl-Var-Locales>  
    */
    private void declVarLocalesP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Sentencia’> ::= <Sentencia><Sentencia’> | <Sentencia>  
    */
    private void sentenciaP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado-Simple’> ::= <Encadenado-Simple><Encadenado-Simple’> | <Encadenado-Simple>  
    */
    private void encadenadoSimpleP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Herencia’> ::= <Herencia>  
    */
    private void herenciaP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Visibilidad’> ::= <Visibilidad>  
    */
    private void visibilidadP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Forma-Método’> ::= <Forma-Método>  
    */
    private void formaMetodoP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Expresión’> ::= <Expresión>  
    */
    private void expresionP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado’> ::= <Encadenado>  
    */
    private void encadenadoP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Expresiones’> ::= <Lista-Expresiones>  
    */
    private void listaExpresionesP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Argumentos-Formales’> ::= <Lista-Argumentos-Formales>  
    */
    private void listaArgumentosFomalesP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Miembro’> ::= <Miembro> | <Miembro><Miembro’>  
    */
    private void miembroP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpOr’> ::= || <ExpAnd> <ExpOr’> | || <ExpAnd>  
    */
    private void expOrP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAnd’> ::= && <ExpIgual><ExpAnd’> | && <ExpIgual>  
    */
    private void expAndP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpIgual’> ::= <OpIgual> <ExpCompuesta> <ExpIgual’> | <OpIgual> <ExpCompuesta>  
    */
    private void expIgualP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAd’> ::= <OpAd> <ExpMul> <ExpAd’> | <OpAd> <ExpMul>  
    */
    private void expAdP () {
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpMul’> ::= <OpMul> <ExpUn> <ExpMul’> | <OpMul> <ExpUn>  
    */
    private void expMulP () {
        
    }
}

