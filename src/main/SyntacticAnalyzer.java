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
    private void match(IDToken idToken){
        //Si matchean, solicita el siguiente token, sino es error
        if(currentToken.getIDToken().equals(idToken)){
            this.currentToken = this.lexicalAnalyzer.nextToken();
        } else {
            throw throwError("Token: " + idToken.toString());
        }
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
     * @return boolean
     */
    private boolean compare(ArrayList<IDToken> firsts){
        return firsts.contains(currentToken.getIDToken());
    }

    /**
     * Compara si el token actual es un idObject o idStruct
     */
    private void isID () {
        switch (currentToken.getIDToken()) {
            case idOBJECT:
                match(IDToken.idOBJECT);
                break;
            case idSTRUCT:
                match(IDToken.idSTRUCT);
                break;
            default:
                throw throwError("Token: idOBJECT o idSTRUCT");
        }
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
        match(IDToken.idSTART);
        bloqueMetodo();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Struct> ::= struct idStruct <Struct’>  
    */
    private void struct() throws SyntacticException{
        match(IDToken.pSTRUCT);
        match(IDToken.idSTRUCT);
        structP();
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
        
        match(IDToken.sKEY_OPEN);

        if (compare(First.firstAtributoP)){
            atributoP();
        }

        match(IDToken.sKEY_CLOSE);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Impl> ::= impl idStruct { <Miembro’> }  
    */
    private void impl () {
        match(IDToken.pIMPL);
        match(IDToken.idSTRUCT);
        match(IDToken.sKEY_OPEN);
        miembroP();
        match(IDToken.sKEY_CLOSE);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Herencia> ::= : <Tipo>  
    */
    private void herencia () {
        match(IDToken.sCOLON);
        tipo();
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
        match(IDToken.sDOT);
        argumentosActuales();
        bloqueMetodo();

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
            match(IDToken.sSEMICOLON);
            
        }
        else{
            if (compare(First.firstTipo)){
                tipo();
                listaDeclaracionVariables();
                match(IDToken.sSEMICOLON);
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Método> ::= fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  | <Forma-Método’>fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  
    */
    private void metodo () {
        if (compare(First.firstFormaMetodoP)){
            match(IDToken.pFN);
            match(IDToken.idOBJECT);
            argumentosFormales();
            match(IDToken.sARROW_METHOD);
            tipoMetodo();
            bloqueMetodo();
        }
        else{
            match(IDToken.pFN);
            match(IDToken.idOBJECT);
            argumentosFormales();
            match(IDToken.sARROW_METHOD);
            tipoMetodo();
            bloqueMetodo();
            
        }
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Visibilidad> ::= pri  
    */
    private void visibilidad () {
        match(IDToken.pPRI);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Forma-Método> ::= st  
    */
    private void formaMetodo () {
        match(IDToken.pST);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Bloque-Método> ::= { <Decl-Var-Locales’> <Sentencia’> } | { <Sentencia’> } | { <Decl-Var-Locales’> }  
    */
    private void bloqueMetodo() {
        match(IDToken.sKEY_OPEN);

        if (compare(First.firstDeclVarLocalesP)){
                declVarLocalesP();
  
        }
        if (compare(First.firstSentenciaP)){
            sentenciaP();
  
        }
        match(IDToken.sKEY_CLOSE);
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Decl-Var-Locales> ::= <Tipo> <Lista-Declaración-Variables> ;   
    */
    private void declVarLocales () {
        tipo();
        listaDeclaracionVariables();
        match(IDToken.sSEMICOLON);
    
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Declaración-Variables>::= idMetAt | idMetAt , <Lista-Declaración-Variables>  
    */
    private void listaDeclaracionVariables () {
        match(IDToken.idOBJECT);
        if (currentToken.getIDToken().equals(IDToken.sCOM)){
            match(IDToken.sCOM);
            listaDeclaracionVariables();
        }
    }



    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumentos-Formales>::= ( <Lista-Argumentos-Formales’> ) | ( )  
    */
    private void argumentosFormales () {
        match(IDToken.sPAR_OPEN);
        if (compare(First.firstListaArgumentosFormalesP)){
            listaArgumentosFomalesP();
        }
        match(IDToken.sPAR_CLOSE);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Argumentos-Formales> ::= <Argumento-Formal> , <Lista-Argumentos-Formales> | <Argumento-Formal>  
    */
    private void listaArgumentosFomales () {
  
        argumentoFormal();
        if (currentToken.getIDToken().equals(IDToken.sCOM)){
            match(IDToken.sCOM);
            listaArgumentosFomales();
        }
    
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumento-Formal> ::= <Tipo> idMetAt  
    */
    private void argumentoFormal () {
        tipo();
        match(IDToken.idOBJECT);
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
            match(IDToken.typeVOID);
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
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Primitivo> ::= Str | Bool | Int | Char  
    */
    private void tipoPrimitivo () {
        switch (currentToken.getIDToken()) {
            case typeINT:
                match(IDToken.typeINT);
                break;
            case typeBOOL:
                match(IDToken.typeBOOL);
                break;
            case typeSTR:
                match(IDToken.typeSTR);
                break;
            case typeCHAR:
                match(IDToken.typeCHAR);
                break;
                
            default:
                throw throwError("Tipo tipoPrimitivo");
                
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Referencia> ::= idStruct  
    */
    private void tipoReferencia () {
        match(IDToken.idSTRUCT);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Arreglo> ::= Array <Tipo-Primitivo>  
    */
    private void tipoArreglo () {
        match(IDToken.typeARRAY);
        tipoPrimitivo();
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
        if (currentToken.getIDToken().equals(IDToken.sSEMICOLON)){

            match(IDToken.sSEMICOLON);
        }
        else{
            // <Asignación> ;
            if (compare(First.firstAsignacion)){
                asignacion();
                match(IDToken.sSEMICOLON);

            }
            else{
                // <Sentencia-Simple> ;
                if (compare(First.firstSentenciaSimple)){
                    sentenciaSimple();
                    match(IDToken.sSEMICOLON);
                }
                else{
                    //if ( <Expresión> ) <Sentencia> <MoreIF> y if ( <Expresión> ) <Sentencia> 
                    if (currentToken.getIDToken().equals(IDToken.pIF)){

                        match(IDToken.pIF);
                        match(IDToken.sPAR_OPEN);
                        expresion();
                        match(IDToken.sPAR_CLOSE);
                        sentencia();
                        if (compare(First.firstMoreIF)){
                            moreIF();
                        }
                    }
                    //while ( <Expresión> ) <Sentencia> 
                    else{
                        if (currentToken.getIDToken().equals(IDToken.pWHILE)){

                            match(IDToken.pWHILE);
                            match(IDToken.sPAR_OPEN);
                            expresion();
                            match(IDToken.sPAR_CLOSE);
                            sentencia();
                        }
                        //<Bloque> 
                        else{
                            if (compare(First.firstBloque)){
                                bloque();
                            }
                            // ret <Expresión’> ;  y ret ;
                            else{
                                if (currentToken.getIDToken().equals(IDToken.pRET)){

                                    match(IDToken.pRET);
                                    if (compare(First.firstExpresionP)){
                                        expresionP();
                                    }
                                    match(IDToken.sSEMICOLON);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <MoreIF> ::= else <Sentencia>
    */
    private void moreIF () {
        match(IDToken.pELSE);
        sentencia();
    }
    

    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Bloque> ::= { <Sentencia’> } | { }  
    */
    private void bloque () {
        match(IDToken.sKEY_OPEN);
        if (compare(First.firstSentenciaP)){
            sentenciaP();
        }
        match(IDToken.sKEY_CLOSE);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Asignación> ::= <AccesoVar-Simple> = <Expresión> | <AccesoSelf-Simple>=<Expresión>  
    */
    private void asignacion () {
        boolean pass = false;
        if (compare(First.firstAccesoVarSimple)){
            accesoVarSimple();
            pass = true;
        }
        if (compare(First.firstAccesoSelfSimple)){
            accesoSelfSimple();
            pass = true;
        }

        if (pass) {
            match(IDToken.ASSIGN);
            expresion();
        } else {
            throw throwError("Acceso self o var simple");
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoVar-Simple> ::= id <Encadenado-Simple’> | id [ <Expresión> ] | id  
    */
    private void accesoVarSimple () {
        isID();
        if (compare(First.firstEncadenadoSimpleP)){
            encadenadoSimpleP();
        } else {
            if (currentToken.getIDToken().equals(IDToken.sCOR_OPEN)) {
                match(IDToken.sCOR_OPEN);
                expresion();
                match(IDToken.sCOR_CLOSE);
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoSelf-Simple> ::= self <Encadenado-Simple’> | self  
    */
    private void accesoSelfSimple () {
        match(IDToken.pSELF);
        if (compare(First.firstEncadenadoSimpleP)){
            encadenadoSimpleP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado-Simple> ::= . id  
    */
    private void encadenadoSimple () {
        match(IDToken.sDOT);
        isID();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Sentencia-Simple> ::= ( <Expresión> )  
    */
    private void sentenciaSimple () {
        match(IDToken.sPAR_OPEN);
        expresion();
        match(IDToken.sPAR_CLOSE);
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
        switch (currentToken.getIDToken()) {
            case oMIN:
                match(IDToken.oMIN);
                break;
            case oMAX:
                match(IDToken.oMAX);
                break;
            case oMIN_EQ:
                match(IDToken.oMIN_EQ);
                break;
            case oMAX_EQ:
                match(IDToken.oMAX_EQ);
                break;
            default:
                throw throwError(First.firstOpCompuesto.toString());
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpAd> ::= + | -  
    */
    private void opAd () {
        switch (currentToken.getIDToken()) {
            case oSUM:
                match(IDToken.oSUM);
                break;
            case oSUB:
                match(IDToken.oSUB);
                break;
            default:
                throw throwError(First.firstOpAd.toString());
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpUnario> ::= + | - | ! | ++ | --  
    */
    private void opUnario () {
        switch (currentToken.getIDToken()) {
            case oSUM:
                match(IDToken.oSUM);
                break;
            case oSUB:
                match(IDToken.oSUB);
                break;
            case oNOT:
                match(IDToken.oNOT);
                break;
            case oSUM_SUM:
                match(IDToken.oSUM_SUM);
                break;
            case oSUB_SUB:
                match(IDToken.oSUB_SUB);
                break;
            default:
                throw throwError(First.firstOpUnario.toString());
        }
        
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpMul> ::= * | / | %  
    */
    private void opMul () {
        switch (currentToken.getIDToken()) {
            case oMULT:
                match(IDToken.oMULT);
                break;
            case oDIV:
                match(IDToken.oDIV);
                break;
            case oMOD:
                match(IDToken.oMOD);
                break;
            default:
                throw throwError(First.firstOpMul.toString());
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Operando> ::= <Literal> | <Primario> <Encadenado’> | <Primario>  
    */
    private void operando () {
        if (compare(First.firstLiteral)) {
            literal();
        }
        else {
            primario();
            if (compare(First.firstEncadenadoP)) {
                encadenadoP();
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Literal> ::= nil | true | false | intLiteral | StrLiteral | charLiteral
    */
    private void literal () {
        switch (currentToken.getIDToken()) {
            case pNIL:
                match(IDToken.pNIL);
                break;
            case pTRUE:
                match(IDToken.pTRUE);
                break;
            case pFALSE:
                match(IDToken.pFALSE);
                break;
            case constINT:
                match(IDToken.constINT);
                break;
            case constSTR:
                match(IDToken.constSTR);
                break;
            case constCHAR:
                match(IDToken.constCHAR);
                break;
            default:
                throw throwError(First.firstLiteral.toString());
        }
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
     * 
     * La regla es igual a acceso var simple, asi que se reutiliza el código.
    */
    private void accesoVar () {
        accesoVarSimple();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Llamada-Método> ::= id <Argumentos-Actuales> <Encadenado’> | id <Argumentos-Actuales>  
    */
    private void llamadaMetodo () {
        isID();
        argumentosActuales();
        if (compare(First.firstEncadenadoP)) {
            encadenadoP();
        }
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
     * <Llamada-Constructor> ::= new idStruct <Argumentos-Actuales> <Encadenado’>  
     *              | new <Tipo-Primitivo> [ <Expresión> ]
     *              | new idStruct <Argumentos-Actuales>  
    */
    private void llamadaConstructor () {
        match(IDToken.pNEW);
        if (currentToken.getIDToken().equals(IDToken.idSTRUCT)) {
            match(IDToken.idSTRUCT);
            argumentosActuales();
            if (compare(First.firstEncadenadoP)) {
                encadenadoP();
            }
        }
        else {
            tipoPrimitivo();
            match(IDToken.sCOR_OPEN);
            expresion();
            match(IDToken.sCOR_CLOSE);
        }
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
        expresion();
        if (currentToken.getIDToken().equals(IDToken.sSEMICOLON)) {
            match(IDToken.sSEMICOLON);
            listaExpresiones();
        }
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
            accesoVariableEncadenado();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Llamada-Método-Encadenado> ::= id <Argumentos-Actuales> <Encadenado’>  |  id <Argumentos-Actuales>  
    */
    private void llamadaMetodoEncadenado () {
        isID();
        argumentosActuales();
        if (compare(First.firstEncadenadoP)) {
            encadenadoP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Acceso-Variable-Encadenado> ::= id <Encadenado’>  | id   |  id [ <Expresión> ] <Encadenado’> | id [ <Expresión> ]   
    */
    private void accesoVariableEncadenado () {
        isID();
        if (currentToken.getIDToken().equals(IDToken.sCOR_OPEN)) {
            match(IDToken.sCOR_OPEN);
            expresion();
            match(IDToken.sCOR_CLOSE);
        }

        if (compare(First.firstEncadenadoP)) {
            encadenadoP();
        }
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
        } else {
            //Valida si empieza con impl
            if (compare(First.firstImpl)){
                impl();
                pass = true;
            }
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