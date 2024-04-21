package src.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import src.lib.exceptionHelper.LexicalException;
import src.lib.exceptionHelper.SyntacticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.symbolTableHelper.Param;
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
    SymbolTable symbolTable;
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
     * @throws LexicalException
     * @throws SyntacticException
     */
    public void run () throws LexicalException, SyntacticException{
        //Obtiene el token inicial
        currentToken = lexicalAnalyzer.nextToken();

        //Genera la tabla de símbolos y asigna el token actual
        symbolTable = new SymbolTable();

        //Comienza el análisis
        this.program();

        //Si el análisis no retorna error, ha sido correcto
        System.out.println("CORRECTO: ANALISIS SINTACTICO");
    }

    /**
     * Función de macheo.
     * 
     * Matchea un idToken pasado por parámetro y lo compara con el que se está
     * analizando en ese momento.
     * 
     * @param idToken IDToken
     * @return Booleano que indica si los tokens matchean
     */
    private void match(IDToken idToken){
        //Si matchean, solicita el siguiente token, sino es error
        if(currentToken.getIDToken().equals(idToken)){
            this.currentToken = this.lexicalAnalyzer.nextToken();
        } else {
            throw throwError(createHashSet(idToken));
        }
    }

    /**
     * Función auxiliar que retorna una excepción con la descripción
     * correspondiente.
     * 
     * @param expected HashSet<IDToken>
     * @return Excepción tipo SyntacticException
     */
    private SyntacticException throwError(HashSet<IDToken> expected){
        return new SyntacticException(currentToken, expected.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    /**
     * Función auxiliar que crea un HashSet
     * 
     * @param elements IDToken
     * @return HashSet con IDTokens pasados por parámetro
     */
    private HashSet<IDToken> createHashSet(IDToken... elements) {
        return new HashSet<IDToken>(Arrays.asList(elements));
    }

    /**
     * Compara si un idToken pasado como parámetro pertenece a
     * un HashSet de primeros de un no terminal firsts
     * @param firsts HashSet de IDToken
     * @return boolean
     */
    private boolean checkFirst(HashSet<IDToken> firsts){
        return firsts.contains(currentToken.getIDToken());
    }

    /**
     * Compara si el token actual es un 
     * idObject o idStruct o spIO o spOBJECT
     */
    private void isID () {
        switch (currentToken.getIDToken()) {
            case idOBJECT:
                match(IDToken.idOBJECT);
                break;
            case idSTRUCT:
                match(IDToken.idSTRUCT);
                break;
            case spIO:
                match(IDToken.spIO);
                break;
            case spOBJECT:
                match(IDToken.spOBJECT);
                break;
            default:
                throw throwError(createHashSet(IDToken.idOBJECT, IDToken.idSTRUCT, IDToken.spIO, IDToken.spOBJECT));
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <program> ::= <Lista-Definiciones><Start> | <Start>
    */
    private void program() {
        if (checkFirst(First.firstListaDefiniciones)){
            listaDefiniciones();
        }
        start();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Start> ::= start <Bloque-Método>  
    */
    private void start() {
        match(IDToken.idSTART);
        bloqueMetodo();
        if (!currentToken.getIDToken().equals(IDToken.EOF)){
            throw throwError(createHashSet(IDToken.EOF));
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Struct> ::= struct idStruct <Struct’>  
    */
    private void struct() {
        Token token;
        match(IDToken.pSTRUCT);
        token = currentToken;
        match(IDToken.idSTRUCT);
        structP(token);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Struct’> ::= <Herencia’> { <Atributo’> } | <Herencia’> { } | { <Atributo’> }  
    */
    private void structP(Token token) {
        IDToken parent = IDToken.spOBJECT;

        if (checkFirst(First.firstHerenciaP)){
            parent = herenciaP();
        }
        
        //Genera la estructura en la tabla de simbolos
        symbolTable.addStruct(token, parent, true);

        match(IDToken.sKEY_OPEN);

        if (checkFirst(First.firstAtributoP)){
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
        Token token = currentToken;
        match(IDToken.pIMPL);
        token = currentToken;
        match(IDToken.idSTRUCT);

        //Genera la estructura en la tabla de simbolos
        symbolTable.addStruct(token, IDToken.spOBJECT, false);

        match(IDToken.sKEY_OPEN);
        miembroP();
        match(IDToken.sKEY_CLOSE);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Herencia> ::= : <Tipo>  
    */
    private IDToken herencia () {
        match(IDToken.sCOLON);
        return tipo();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Miembro> ::= <Método> | <Constructor>  
    */
    private void miembro () {
        if (checkFirst(First.firstMetodo)){
            metodo();
        }
        else{
            if (checkFirst(First.firstConstructor)){
                constructor();
            }
            else{
                throw throwError(
                    new HashSet<IDToken>(First.firstMetodo){{
                        addAll(First.firstConstructor);
                    }}
                );
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Constructor> ::= . <Argumentos-Formales> <Bloque-Método>  
    */
    private void constructor () {
        Token token = currentToken;
        match(IDToken.sDOT);
        
        //Agrega el metodo constructor
        symbolTable.addMethod(token, argumentosFormales(), false, IDToken.typeVOID);

        bloqueMetodo();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Atributo> ::= <Visibilidad’> <Tipo> <Lista-Declaración-Variables> ;  | <Tipo> <Lista-Declaración-Variables> ;  
    */
    private void atributo () {
        boolean isPrivate = false;

        if (checkFirst(First.firstVisibilidadP)){
            visibilidadP();
            isPrivate = true;
        }

        listaDeclaracionVariables(tipo(), isPrivate);

        match(IDToken.sSEMICOLON);
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Método> ::= fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  | <Forma-Método’>fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  
    */
    private void metodo () {
        boolean isStatic = false;
        ArrayList<Param> params;
        Token token;

        if (checkFirst(First.firstFormaMetodoP)){
            formaMetodoP();
            isStatic = true;
        }

        match(IDToken.pFN);
        token = currentToken;
        match(IDToken.idOBJECT);
        params = argumentosFormales();
        match(IDToken.sARROW_METHOD);

        //Agrega el método a la tabla de símbolos
        symbolTable.addMethod(token, params, isStatic, tipoMetodo());

        bloqueMetodo();
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

        if (checkFirst(First.firstDeclVarLocalesP)){
            declVarLocalesP();
        }

        if (checkFirst(First.firstSentenciaP)){
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
    private void listaDeclaracionVariables (IDToken type, boolean isPrivate) {
        Token token = currentToken;
        match(IDToken.idOBJECT);

        symbolTable.addVar(token, type, isPrivate);

        if (currentToken.getIDToken().equals(IDToken.sCOM)){
            match(IDToken.sCOM);
            listaDeclaracionVariables(type, isPrivate);
        }
    }
    //Se genera polimorfismo para que se utilice en declVarLocales ya que esta no genera variables en la estructura
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
    private ArrayList<Param> argumentosFormales () {
        ArrayList<Param> result = new ArrayList<Param>();

        match(IDToken.sPAR_OPEN);
        if (checkFirst(First.firstListaArgumentosFormalesP)){
            result = listaArgumentosFormalesP();
        }
        match(IDToken.sPAR_CLOSE);

        return result;
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Argumentos-Formales> ::= <Argumento-Formal> , <Lista-Argumentos-Formales> | <Argumento-Formal>  
    */
    private ArrayList<Param> listaArgumentosFormales () {
        ArrayList<Param> result = new ArrayList<Param>();
        
        //Agrega el parametro actual
        result.add(argumentoFormal());

        if (currentToken.getIDToken().equals(IDToken.sCOM)){
            match(IDToken.sCOM);

            //Agrega los parametros que se han obtenido recursivamente
            result.addAll(listaArgumentosFormales());
        }

        //Lista de parametros ordenados
        return result;
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumento-Formal> ::= <Tipo> idMetAt  
    */
    private Param argumentoFormal () {
        Param param = new Param(tipo(), currentToken);
        match(IDToken.idOBJECT);
        return param;
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Método> ::= <Tipo> | void  
    */
    private IDToken tipoMetodo () {
        IDToken result = IDToken.typeVOID;

        if (checkFirst(First.firstTipo)){
            result = tipo();
        }
        else{
            match(IDToken.typeVOID);
        }
        return result;
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo> ::= <Tipo-Primitivo> | <Tipo-Referencia> | <Tipo-Arreglo>  
    */
    private IDToken tipo () {
        if (checkFirst(First.firstTipoPrimitivo)){
            return tipoPrimitivo();
        }
        else{
            if (checkFirst(First.firstTipoReferencia)){
                return tipoReferencia();
            }
            else{
                if (checkFirst(First.firstTipoArreglo)){
                    return tipoArreglo();
                }
                else{
                    throw throwError(First.firstTipo);
                }
            }
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Primitivo> ::= Str | Bool | Int | Char  
    */
    private IDToken tipoPrimitivo () {
        switch (currentToken.getIDToken()) {
            case typeINT:
                match(IDToken.typeINT);
                return IDToken.typeINT;
            case typeBOOL:
                match(IDToken.typeBOOL);
                return IDToken.typeBOOL;
            case typeSTR:
                match(IDToken.typeSTR);
                return IDToken.typeSTR;
            case typeCHAR:
                match(IDToken.typeCHAR);
                return IDToken.typeCHAR;
            default:
                throw throwError(First.firstTipoPrimitivo);
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Referencia> ::= idStruct  
    */
    private IDToken tipoReferencia () {
        IDToken idStruct = IDToken.idSTRUCT;
        //Guarda el nombre del struct
        idStruct.setDescripcion(currentToken.getLexema());

        match(IDToken.idSTRUCT);
        return idStruct;
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Arreglo> ::= Array <Tipo-Primitivo>  
    */
    private IDToken tipoArreglo () {
        match(IDToken.typeARRAY);
        //Accion para avisar que es array
        switch (tipoPrimitivo()) {
            case IDToken.typeINT:
                return IDToken.typeArrayINT;
            case IDToken.typeSTR:
                return IDToken.typeArraySTR;
            case IDToken.typeBOOL:
                return IDToken.typeArrayBOOL;
            case IDToken.typeCHAR:
                return IDToken.typeArrayCHAR;
            default:
                throw throwError(First.firstTipoPrimitivo);
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
        //  ;
        if (currentToken.getIDToken().equals(IDToken.sSEMICOLON)){
            match(IDToken.sSEMICOLON);
        }
        else{
            // <Asignación> ;
            if (checkFirst(First.firstAsignacion)){
                asignacion();
                match(IDToken.sSEMICOLON);
            }
            else{
                // <Sentencia-Simple> ;
                if (checkFirst(First.firstSentenciaSimple)){
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
                        if (checkFirst(First.firstMoreIF)){
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
                            if (checkFirst(First.firstBloque)){
                                bloque();
                            }
                            // ret <Expresión’> ;  y ret ;
                            else{
                                if (currentToken.getIDToken().equals(IDToken.pRET)){
                                    match(IDToken.pRET);
                                    if (checkFirst(First.firstExpresionP)){
                                        expresionP();
                                    }
                                    match(IDToken.sSEMICOLON);
                                } 
                                else {
                                    throw throwError(First.firstSentencia);
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
        if (checkFirst(First.firstSentenciaP)){
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
        if (checkFirst(First.firstAccesoVarSimple)){
            accesoVarSimple();
            pass = true;
        }
        if (checkFirst(First.firstAccesoSelfSimple)){
            accesoSelfSimple();
            pass = true;
        }

        if (pass) {
            match(IDToken.ASSIGN);
            expresion();
        } else {
            throw throwError(
                new HashSet<IDToken>(First.firstAccesoVarSimple){{
                    addAll(First.firstAccesoSelfSimple);
                }}
            );
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoVar-Simple> ::= id <Encadenado-Simple’> | id [ <Expresión> ] | id  
    */
    private void accesoVarSimple () {
        isID();
        if (checkFirst(First.firstEncadenadoSimpleP)){
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
        if (checkFirst(First.firstEncadenadoSimpleP)){
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
        expAnd();
        if (checkFirst(First.firstExpOrP)) {
            expOrP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAnd> ::= <ExpIgual><ExpAnd’> | <ExpIgual>  
    */
    private void expAnd () {
        expIgual();
        if (checkFirst(First.firstExpAndP)) {
            expAndP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpIgual> ::= <ExpCompuesta><ExpIgual’> | <ExpCompuesta>  
    */
    private void expIgual () {
        expCompuesta();
        if (checkFirst(First.firstExpIgualP)) {
            expIgualP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAd> ::= <ExpMul><ExpAd’> | <ExpMul>  
    */
    private void expAd () {
        expMul();
        if (checkFirst(First.firstExpAdP)) {
            expAdP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpMul> ::= <ExpUn> <ExpMul’> | <ExpUn>  
    */
    private void expMul () {
        expUn();
        if (checkFirst(First.firstExpMulP)) {
            expMulP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpCompuesta> ::= <ExpAd> <OpCompuesto> <ExpAd> | <ExpAd>  
    */
    private void expCompuesta () {
        expAd();
        if (checkFirst(First.firstOpCompuesto)) {
            opCompuesto();
            expAd();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpUn> ::= <OpUnario> <ExpUn> | <Operando>  
    */
    private void expUn () {
        if (checkFirst(First.firstOpUnario)) {
            opUnario();
            expUn();
        } else {
            operando();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpIgual> ::= == | !=  
    */
    private void opIgual () {
        switch (currentToken.getIDToken()) {
            case oEQUAL:
                match(IDToken.oEQUAL);
                break;
            case oNOT_EQ:
                match(IDToken.oNOT_EQ);
                break;
            default:
            throw throwError(First.firstOpIgual);
        }
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
                throw throwError(First.firstOpCompuesto);
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
                throw throwError(First.firstOpAd);
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
                throw throwError(First.firstOpUnario);
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
                throw throwError(First.firstOpMul);
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Operando> ::= <Literal> | <Primario> <Encadenado’> | <Primario>  
    */
    private void operando () {
        if (checkFirst(First.firstLiteral)) {
            literal();
        }
        else {
            primario();
            if (checkFirst(First.firstEncadenadoP)) {
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
                throw throwError(First.firstLiteral);
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Primario> :: = <Primario’> <Encadenado’> | <Primario’>
    */
    private void primario () {
        primarioP();
        if (checkFirst(First.firstEncadenadoP)) {
            encadenadoP();
        }
    }

    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Primario’> ::=  ( <Expresión> ) 
     *              | self 
     *              | id 
     *              | id  [ <Expresión> ]
     *              | id <Argumentos-Actuales>
     *              | idStruct . id <Argumentos-Actuales>
     *              | new idStruct <Argumentos-Actuales>
     *              | new <Tipo-Primitivo> [ <Expresión> ]
     */
    private void primarioP () {
        boolean checkExpresion = false;
        switch (currentToken.getIDToken()) {
            case sPAR_OPEN:
                match(IDToken.sPAR_OPEN);
                expresion();
                match(IDToken.sPAR_CLOSE);
                break;
            case pSELF:
                match(IDToken.pSELF);
                break;
            case idSTRUCT:
                match(IDToken.idSTRUCT);
                match(IDToken.sDOT);
                isID();
                argumentosActuales();
                break;
            case pNEW:
                match(IDToken.pNEW);
                if (checkFirst(First.firstTipoPrimitivo)) {
                    tipoPrimitivo();
                    checkExpresion = true;
                } else {
                    if (currentToken.getIDToken().equals(IDToken.idSTRUCT)){
                        match(IDToken.idSTRUCT);
                        argumentosActuales();
                    }
                    else{
                        throw throwError(
                            new HashSet<IDToken>(First.firstTipoPrimitivo){{
                                add(IDToken.idSTRUCT);
                            }}
                        );
                    }
                }
                break;
            default:
                isID();
                if (checkFirst(First.firstArgumentosActuales)) {
                    argumentosActuales();
                }
                else {
                    if (currentToken.getIDToken().equals(IDToken.sCOR_OPEN)) {
                        checkExpresion = true;
                    }
                }
                break;
        }
        if (checkExpresion) {
            match(IDToken.sCOR_OPEN);
            expresion();
            match(IDToken.sCOR_CLOSE);
        }
    }

    // /*
    //  * Método que ejecuta la regla de producción: <br/>
    //  * 
    //  * <ExpresionParentizada> ::= ( <Expresión> ) <Encadenado’> | ( <Expresión> )   
    // */
    // private void expresionParentizada () {
    //     match(IDToken.sPAR_OPEN);
    //     expresion();
    //     match(IDToken.sPAR_CLOSE);
    //     if (checkFirst(First.firstEncadenadoP)) {
    //         encadenadoP();
    //     }
    // }


    // /*
    //  * Método que ejecuta la regla de producción: <br/>
    //  * 
    //  * <AccesoSelf> ::= self <Encadenado’> | self  
    // */
    // private void accesoSelf () {
    //     match(IDToken.pSELF);
    //     if (checkFirst(First.firstEncadenadoP)) {
    //         encadenadoP();
    //     }
    // }


    // /*
    //  * Método que ejecuta la regla de producción: <br/>
    //  * 
    //  * <AccesoVar> ::= id <Encadenado’> | id  |  id [ <Expresión> ] <Encadenado’> | id [ <Expresión> ]  
    //  * 
    //  * La regla es igual a acceso accesoVariableEncadenado, asi que se reutiliza el código.
    // */
    // private void accesoVar () {
    //     accesoVariableEncadenado();
    // }


    // /*
    //  * Método que ejecuta la regla de producción: <br/>
    //  * 
    //  * <Llamada-Método> ::= id <Argumentos-Actuales> <Encadenado’> | id <Argumentos-Actuales>  
    // */
    // private void llamadaMetodo () {
    //     isID();
    //     argumentosActuales();
    //     if (checkFirst(First.firstEncadenadoP)) {
    //         encadenadoP();
    //     }
    // }


    // /*
    //  * Método que ejecuta la regla de producción: <br/>
    //  * 
    //  * <Llamada-Método-Estático> ::= idStruct . <Llamada-Método> <Encadenado’>  |  idStruct . <Llamada-Método>  
    // */
    // private void llamadaMetodoEstatico () {
    //     match(IDToken.idSTRUCT);
    //     match(IDToken.sDOT);
    //     llamadaMetodo();
    //     if (checkFirst(First.firstEncadenadoP)) {
    //         encadenadoP();
    //     }
    // }


    // /*
    //  * Método que ejecuta la regla de producción: <br/>
    //  * 
    //  * <Llamada-Constructor> ::= new idStruct <Argumentos-Actuales> <Encadenado’>  
    //  *              | new <Tipo-Primitivo> [ <Expresión> ]
    //  *              | new idStruct <Argumentos-Actuales>  
    // */
    // private void llamadaConstructor () {
    //     match(IDToken.pNEW);
    //     if (currentToken.getIDToken().equals(IDToken.idSTRUCT)) {
    //         match(IDToken.idSTRUCT);
    //         argumentosActuales();
    //         if (checkFirst(First.firstEncadenadoP)) {
    //             encadenadoP();
    //         }
    //     }
    //     else {
    //         tipoPrimitivo();
    //         match(IDToken.sCOR_OPEN);
    //         expresion();
    //         match(IDToken.sCOR_CLOSE);
    //     }
    // }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumentos-Actuales> ::= ( <Lista-Expresiones’> ) | ( )  
    */
    private void argumentosActuales () {
        match(IDToken.sPAR_OPEN);
        if (checkFirst(First.firstListaExpresionesP)) {
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
        if (currentToken.getIDToken().equals(IDToken.sCOM)) {
            match(IDToken.sCOM);
            listaExpresiones();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado> ::= <EncadenadoExtra> | <EncadenadoExtra> <Encadenado’>  
    */
    private void encadenado () {
        encadenadoExtra();
        if (checkFirst(First.firstEncadenadoP)) {
            encadenadoP();
        }
    }
    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <EncadenadoExtra> ::= . id <Argumentos-Actuales>
			                | . id  
			                | . id [ <Expresión> ] 
    */
    private void encadenadoExtra () {
        match(IDToken.sDOT);
        isID();
        if (checkFirst(First.firstArgumentosActuales)) {
            
            argumentosActuales();
        }
        else{
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
     * <Lista-Definiciones>::= <Struct><Lista-Definiciones> | <Struct> | <Impl><Lista-Definiciones> | <Impl>  
    */
    private void listaDefiniciones() {
        boolean pass = false;

        //Valida si empieza con struct
        if (checkFirst(First.firstStruct)){
            struct();
            pass = true;
        } else {
            //Valida si empieza con impl
            if (checkFirst(First.firstImpl)){
                impl();
                pass = true;
            }
        }
        
        if (pass) {
            if (checkFirst(First.firstListaDefiniciones)) {
                listaDefiniciones();
            }
        } 
        else {
            throw throwError(
                new HashSet<IDToken>(First.firstStruct){{
                    addAll(First.firstImpl);
                }}
            );
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Atributo’> ::= <Atributo><Atributo’> | <Atributo>  
    */
    private void atributoP () {
        atributo();
        if (checkFirst(First.firstAtributoP)) {
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
        if (checkFirst(First.firstDeclVarLocalesP)) {
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
        if (checkFirst(First.firstSentenciaP)) {
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
        if (checkFirst(First.firstEncadenadoSimpleP)) {
            encadenadoSimpleP();
        }
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Herencia’> ::= <Herencia>  
    */
    private IDToken herenciaP () {
        return herencia();
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
    private ArrayList<Param> listaArgumentosFormalesP () {
        return listaArgumentosFormales();
    }


    /*
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Miembro’> ::= <Miembro> | <Miembro><Miembro’>  
    */
    private void miembroP () {
        miembro();
        if (checkFirst(First.firstMiembroP)) {
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
        if (checkFirst(First.firstExpOrP)) {
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
        if (checkFirst(First.firstExpAndP)) {
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
        if (checkFirst(First.firstExpIgualP)) {
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
        if (checkFirst(First.firstExpAdP)) {
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
        if (checkFirst(First.firstExpMulP)) {
            expMulP();
        }
    }
}