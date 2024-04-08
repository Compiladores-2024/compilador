package src.main;

import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

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
     * compara si un idToken pasado como parametro pertenece a
     * un ArrayList de primeros de un no terminal firsts
     * @param firsts ArrayList de IDToken
     * @param idToken IDToken a comprobar
     * @return boolean
     */
    private boolean compare(ArrayList<IDToken> firsts){
        return firsts.contains(currentToken.getIDToken());
    }

    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <program> ::= <Lista-Definiciones><Start> | <Start>
    */
    private void program() {
        if (compare(First.firstListaDefiniciones)){
            listaDefiniciones();
            start();
        }
        else{
            if (compare(First.firstStart)){
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
        if (compare(First.firstHerenciaP)){
            herenciaP();
        }
        if (match(IDToken.sKEY_OPEN)){
            if (compare(First.firstAtributoP)){
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
        if (match(IDToken.pIMPL)){
            if (match(IDToken.idSTRUCT)){
                if (match(IDToken.sKEY_OPEN)){
                    miembroP();
                    if (!match(IDToken.sKEY_CLOSE)){
                        throw throwError("}");
                    }
                }
                else{
                    throw throwError("Token sKEY_OPEN");
                }
            }
            else{
                throw throwError("Token idSTRUCT");
            }
        }
        else{
            throw throwError("Token pIMPL");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Herencia> ::= : <Tipo>  
    */
    private void herencia () {
        if (match(IDToken.sCOLON)){
            tipo();
        }
        else{
            throw throwError("Token sCOLON");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Miembro> ::= <Método> | <Constructor>  
    */
    private void miembro () {
        if (compare(First.firstMetodo)){
            metodo();
        }
        else{
            if (compare(First.firstConstructor)){
                constructor();
            }
            else{
                throw throwError("MIEMBRO o CONSTRUCTOR");
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Constructor> ::= . <Argumentos-Formales> <Bloque-Método>  
    */
    private void constructor () {
        if (match(IDToken.sDOT)){
            argumentosActuales();
            bloqueMetodo();
        }
        else{
            throw throwError("Token sDOT");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Atributo> ::= <Visibilidad’> <Tipo> <Lista-Declaración-Variables> ;  | <Tipo> <Lista-Declaración-Variables> ;  
    */
    private void atributo () {
        if (compare(First.firstVisibilidadP)){
            visibilidadP();
            tipo();
            listaDeclaracionVariables();
            if (!match(IDToken.sSEMICOLON)){
                throw throwError("Token sSEMICOLON");
            }
        }
        else{
            if (compare(First.firstTipo)){
                tipo();
                listaDeclaracionVariables();
                if (!match(IDToken.sSEMICOLON)){
                    throw throwError("Token sSEMICOLON");
                }
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Método> ::= fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  | <Forma-Método’>fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  
    */
    private void metodo () {
        if (match(IDToken.pFN)){
            if (match(IDToken.idOBJECT)){
                argumentosFormales();
                if (match(IDToken.sARROW_METHOD)){
                    tipoMetodo();
                    bloqueMetodo();
                }
                else{
                    throw throwError("Token sARROW_METHOD");
                }
            }
            else{
                throw throwError("Token idOBJECT");
            }
        }
        else{
            if (compare(First.firstFormaMetodoP)){
                if (match(IDToken.pFN)){
                    if (match(IDToken.idOBJECT)){
                        argumentosFormales();
                        if (match(IDToken.sARROW_METHOD)){
                            tipoMetodo();
                            bloqueMetodo();
                        }
                        else{
                            throw throwError("Token sARROW_METHOD");
                        }
                    }
                    else{
                        throw throwError("Token idOBJECT");
                    }
                }
            }
            else{
                throw throwError("METODO");
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Visibilidad> ::= pri  
    */
    private void visibilidad () {
        if (!match(IDToken.pPRI)){
            throw throwError("Token pPRI");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Forma-Método> ::= st  
    */
    private void formaMetodo () {
        if (!match(IDToken.pST)){
            throw throwError("Token pST");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Bloque-Método> ::= { <Decl-Var-Locales’> <Sentencia’> } | { <Sentencia’> } | { <Decl-Var-Locales’> }  
    */
    private void bloqueMetodo() throws SyntacticException{
        if (match(IDToken.sKEY_OPEN)){
            if (compare(First.firstDeclVarLocalesP)){
                declVarLocalesP();
            }
            if (compare(First.firstSentenciaP)){
                sentenciaP();
            }
            if (!match(IDToken.sKEY_CLOSE)){
                throwError("Token sKEY_CLOSE");
            }
        }
        else{
            throwError("Token sKEY_OPEN");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Decl-Var-Locales> ::= <Tipo> <Lista-Declaración-Variables> ;   
    */
    private void declVarLocales () {
        if (compare(First.firstTipo)){
            tipo();
            listaDeclaracionVariables();
            if (!match(IDToken.sSEMICOLON)){
                throw throwError("Token sSEMICOLON");
            }
        }
        else{
            throw throwError("firstTipo");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Declaración-Variables>::= idMetAt | idMetAt , <Lista-Declaración-Variables>  
    */
    private void listaDeclaracionVariables () {
        if (match(IDToken.idOBJECT)){
            if (match(IDToken.sCOM)){
                listaDeclaracionVariables();
            }
        }
        else{
            throw throwError("idOBJECT");
        }
    }



    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumentos-Formales>::= ( <Lista-Argumentos-Formales’> ) | ( )  
    */
    private void argumentosFormales () {
        if (match(IDToken.sPAR_OPEN)){
            if (compare(First.firstListaArgumentosFormalesP)){
                listaArgumentosFomalesP();
            }
            if (!match(IDToken.sPAR_CLOSE)){
                throw throwError("Token sPAR_CLOSE");
            }
        }
        else{
            throw throwError("sPAR_OPEN");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Argumentos-Formales> ::= <Argumento-Formal> , <Lista-Argumentos-Formales> | <Argumento-Formal>  
    */
    private void listaArgumentosFomales () {
        if (compare(First.firstArgumentoFormal)){
            argumentoFormal();
            if (match(IDToken.sCOM)){
                listaArgumentosFomales();
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumento-Formal> ::= <Tipo> idMetAt  
    */
    private void argumentoFormal () {
        if (compare(First.firstTipo)){
            tipo();
            if (!match(IDToken.idOBJECT)){
                throw throwError("idOBJECT");
            }
        }
        else{
            throw throwError("firstTipo");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Método> ::= <Tipo> | void  
    */
    private void tipoMetodo () {
        if (compare(First.firstTipo)){
            tipo();
        }
        else{

            if (!match(IDToken.typeVOID)){
                throw throwError("typeVOID");
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo> ::= <Tipo-Primitivo> | <Tipo-Referencia> | <Tipo-Arreglo>  
    */
    private void tipo () {
        if (compare(First.firstTipoPrimitivo)){
            tipoPrimitivo();
        }
        else{
            if (compare(First.firstTipoReferencia)){
                tipoReferencia();
            }
            else{
                if (compare(First.firstTipoArreglo)){
                    tipoArreglo();
                }
                else{
                    throw throwError("firstTipo");
                }

            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Primitivo> ::= Str | Bool | Int | Char  
    */
    private void tipoPrimitivo () {
        if (!match(IDToken.typeSTR)
        || (!match(IDToken.typeBOOL))
        || (!match(IDToken.typeINT))
        || ((!match(IDToken.typeCHAR)))
        ){
            throw throwError("Token typeSTR o typeBOOL o typeINT o typeCHAR");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Referencia> ::= idStruct  
    */
    private void tipoReferencia () {
        if (!match(IDToken.idSTRUCT)){
            throw throwError("Token idSTRUCT");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Arreglo> ::= Array <Tipo-Primitivo>  
    */
    private void tipoArreglo () {
        if (match(IDToken.typeARRAY)){
            tipoPrimitivo();
        }
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
        boolean flagOkey=false;
        //  ;
        if (match(IDToken.sSEMICOLON)){
            flagOkey =true;
        }
        else{
            // <Asignación> ;
            if (compare(First.firstAsignacion)){
                asignacion();
                if (!match(IDToken.sSEMICOLON)){
                    throw throwError("Token sSEMICOLON");
                }
                flagOkey =true;
            }
            else{
                // <Sentencia-Simple> ;
                if (compare(First.firstSentenciaSimple)){
                    sentenciaSimple();
                    if (!match(IDToken.sSEMICOLON)){
                        throw throwError("Token sSEMICOLON");
                    }
                    flagOkey =true;
                }
                else{
                    //if ( <Expresión> ) <Sentencia> <MoreIF> y if ( <Expresión> ) <Sentencia> 
                    if (match(IDToken.pIF)){
                        if (match(IDToken.sPAR_OPEN)){
                            expresion();
                            if (match(IDToken.sPAR_CLOSE)){
                                sentencia();
                                if (compare(First.firstMoreIF)){
                                    moreIF();
                                }
                                
                            } 
                            else{
                                throw throwError("Token sPAR_CLOSE");
                            }
                        }
                        else{
                            throw throwError("Token sPAR_OPEN");
                        }
                        flagOkey=true;
                    }
                    //while ( <Expresión> ) <Sentencia> 
                    else{
                        if (match(IDToken.pWHILE)){
                            if (match(IDToken.sPAR_OPEN)){
                                expresion();
                                if (match(IDToken.sPAR_CLOSE)){
                                    sentencia();
                                }
                            }
                            flagOkey=true;
                        }
                        //<Bloque> 
                        else{
                            if (compare(First.firstBloque)){
                                bloque();
                                flagOkey=true;
                            }
                            // ret <Expresión’> ;  y ret ;
                            else{
                                if (match(IDToken.pRET)){
                                    if (compare(First.firstExpresion)){
                                        expresion();
                                    }
                                    if (!match(IDToken.sSEMICOLON)){
                                        throw throwError("Token sSEMICOLON");
                                    }
                                    flagOkey=true;
                                }
                            }
                        }
                    }

                }
            }

        }
        if(flagOkey==false){
            throw throwError("SENTENCIA");
        }
    }

    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <MoreIF> ::= else <Sentencia>
    */
    private void moreIF () {
        if(match(IDToken.pELSE)){
            sentencia();
        }
        else{
            throw throwError("Token pELSE");
        }
    }

    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Bloque> ::= { <Sentencia’> } | { }  
    */
    private void bloque () {
        if (match(IDToken.sKEY_OPEN)){
            if (compare(First.firstSentenciaP)){
                sentenciaP();
            }
            if (!match(IDToken.sKEY_CLOSE)){
                throwError("Token sKEY_CLOSE");
            }
        } 
        else{
            throwError("Token sKEY_OPEN");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Asignación> ::= <AccesoVar-Simple> = <Expresión> | <AccesoSelf-Simple>=<Expresión>  
    */
    private void asignacion () {
        if (compare(First.firstAccesoVarSimple)){
            accesoVarSimple();
            if (match(IDToken.ASSIGN)){
                expresion();
            }
            else{
                throwError("Token ASSIGN");
            }
        }
        else{
            if (compare(First.firstAccesoSelfSimple)){
                accesoSelfSimple();
                if (match(IDToken.ASSIGN)){
                    expresion();
                }
                else{
                    throwError("Token ASSIGN");
                }
            }
        }
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
        if (match(IDToken.pSELF)){
            if (compare(First.firstEncadenadoSimpleP)){
                encadenadoSimpleP();
            }
        }
        else{
            throwError("Token pSELF");
        }
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
        if (match(IDToken.sPAR_OPEN)){
            expresion();
            if (!match(IDToken.sPAR_CLOSE)){
                throwError("Token sPAR_CLOSE");
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Expresión> ::= <ExpOr>  
    */
    private void expresion () {
        expOr();
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
        boolean pass = false;
        if (compare(First.firstExpresionParentizada)) {
            expresionParentizada();
            pass = true;
        }
        if (compare(First.firstAccesoSelf)) {
            accesoSelf();
            pass = true;
        }
        if (compare(First.firstAccesoVar)) {
            accesoVar();
            pass = true;
        }
        if (compare(First.firstLlamadaMetodo)) {
            llamadaMetodo();
            pass = true;
        }
        if (compare(First.firstLlamadaMetodoEstatico)) {
            llamadaMetodoEstatico();
            pass = true;
        }
        if (compare(First.firstLlamadaConstructor)) {
            llamadaConstructor();
            pass = true;
        }

        if (!pass) {
            throw throwError("Token sPAR_OPEN, pSELF, idOBJECT, idSTRUCT o pNEW");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpresionParentizada> ::= ( <Expresión> ) <Encadenado’> | ( <Expresión> )   
    */
    private void expresionParentizada () {
        match(IDToken.sPAR_OPEN);
        expresion();
        match(IDToken.sPAR_CLOSE);
        if (compare(First.firstEncadenadoP)) {
            encadenadoP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoSelf> ::= self <Encadenado’> | self  
    */
    private void accesoSelf () {
        match(IDToken.pSELF);
        if (compare(First.firstEncadenadoP)) {
            encadenadoP();
        }
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
        match(IDToken.idSTRUCT);
        match(IDToken.sDOT);
        llamadaMetodo();
        if (compare(First.firstEncadenadoP)) {
            encadenadoP();
        }
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
        match(IDToken.sPAR_OPEN);
        if (compare(First.firstListaExpresionesP)) {
            listaExpresionesP();
        }
        match(IDToken.sPAR_CLOSE);
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
        match(IDToken.sDOT);
        if (compare(First.firstLlamadaMetodoEncadenado)) {
            llamadaMetodoEncadenado();
        }
        else {
            if (compare(First.firstAccesoVariableEncadenado)) {
                accesoVariableEncadenado();
            } else {
                throw throwError("Token idOBJECT o idSTRUCT");
            }
        }
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
        boolean pass = false;

        //Valida si empieza con struct
        if (compare(First.firstStruct)){
            struct();
            pass = true;
        }
        
        //Valida si empieza con impl
        if (compare(First.firstImpl)){
            impl();
            pass = true;
        }

        if (pass) {
            if (compare(First.firstListaDefiniciones)) {
                listaDefiniciones();
            }
        } 
        else {
            throw throwError("Token 'impl' o 'struct'");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Atributo’> ::= <Atributo><Atributo’> | <Atributo>  
    */
    private void atributoP () {
        atributo();
        if (compare(First.firstAtributoP)) {
            atributoP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Decl-Var-Locales’> ::= <Decl-Var-Locales><Decl-Var-Locales’> | <Decl-Var-Locales>  
    */
    private void declVarLocalesP () {
        declVarLocales();
        if (compare(First.firstDeclVarLocalesP)) {
            declVarLocalesP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Sentencia’> ::= <Sentencia><Sentencia’> | <Sentencia>  
    */
    private void sentenciaP () {
        sentencia();
        if (compare(First.firstSentenciaP)) {
            sentenciaP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado-Simple’> ::= <Encadenado-Simple><Encadenado-Simple’> | <Encadenado-Simple>  
    */
    private void encadenadoSimpleP () {
        encadenadoSimple();
        if (compare(First.firstEncadenadoSimpleP)) {
            encadenadoSimpleP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Herencia’> ::= <Herencia>  
    */
    private void herenciaP () {
        herencia();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Visibilidad’> ::= <Visibilidad>  
    */
    private void visibilidadP () {
        visibilidad();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Forma-Método’> ::= <Forma-Método>  
    */
    private void formaMetodoP () {
        formaMetodo();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Expresión’> ::= <Expresión>  
    */
    private void expresionP () {
        expresion();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado’> ::= <Encadenado>  
    */
    private void encadenadoP () {
        encadenado();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Expresiones’> ::= <Lista-Expresiones>  
    */
    private void listaExpresionesP () {
        listaExpresiones();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Argumentos-Formales’> ::= <Lista-Argumentos-Formales>  
    */
    private void listaArgumentosFomalesP () {
        listaArgumentosFomales();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Miembro’> ::= <Miembro> | <Miembro><Miembro’>  
    */
    private void miembroP () {
        miembro();
        if (compare(First.firstMiembroP)) {
            miembroP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpOr’> ::= || <ExpAnd> <ExpOr’> | || <ExpAnd>  
    */
    private void expOrP () {
        match(IDToken.oOR);
        expAnd();
        if (compare(First.firstExpOrP)) {
            expOrP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAnd’> ::= && <ExpIgual><ExpAnd’> | && <ExpIgual>  
    */
    private void expAndP () {
        match(IDToken.oAND);
        expIgual();
        if (compare(First.firstExpAndP)) {
            expAndP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpIgual’> ::= <OpIgual> <ExpCompuesta> <ExpIgual’> | <OpIgual> <ExpCompuesta>  
    */
    private void expIgualP () {
        opIgual();
        expCompuesta();
        if (compare(First.firstExpIgualP)) {
            expIgualP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAd’> ::= <OpAd> <ExpMul> <ExpAd’> | <OpAd> <ExpMul>  
    */
    private void expAdP () {
        opAd();
        expMul();
        if (compare(First.firstExpAdP)) {
            expAdP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpMul’> ::= <OpMul> <ExpUn> <ExpMul’> | <OpMul> <ExpUn>  
    */
    private void expMulP () {
        opMul();
        expUn();
        if (compare(First.firstExpMulP)) {
            expMulP();
        }
    }
}

