package src.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import src.lib.exceptionHelper.LexicalException;
import src.lib.exceptionHelper.SemanticException;
import src.lib.exceptionHelper.SyntacticException;
import src.lib.semanticHelper.SemanticManager;
import src.lib.semanticHelper.astHelper.SentenceBlock;
import src.lib.semanticHelper.astHelper.sentences.Assignation;
import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.semanticHelper.astHelper.sentences.expressions.BinaryExpression;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.sentences.expressions.UnaryExpression;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.ArrayAccess;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.MethodAccess;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.SimpleAccess;
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
    SemanticManager semanticManager;
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
     * @throws LexicalException Error léxico
     * @throws SyntacticException Error sintáctico
     * @throws SemanticException Error semántico
     */
    public void run () throws LexicalException, SyntacticException, SemanticException{
        //Obtiene el token inicial
        currentToken = lexicalAnalyzer.nextToken();

        //Genera la tabla de símbolos
        semanticManager = new SemanticManager();

        //Comienza el análisis
        this.program();

        //Si el análisis no retorna error, ha sido correcto y consolida la tabla de símbolos
        semanticManager.consolidate();
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


    /**
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


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Start> ::= start <Bloque-Método>  
    */
    private void start() {
        Token token = currentToken;
        match(IDToken.idSTART);
        
        //Agrega el metodo start
        semanticManager.addMethod(
            token, 
            new ArrayList<Param>(), 
            false, 
            new Token(IDToken.typeVOID, "void", token.getLine(), token.getColumn())
        );
        semanticManager.cleanCurrentStruct();
        HashMap<String, SentenceBlock> hashMap= new HashMap<String, SentenceBlock> ();
        bloqueMetodo("start",hashMap);
        semanticManager.addBlock(hashMap);
        if (!currentToken.getIDToken().equals(IDToken.EOF)){
            throw throwError(createHashSet(IDToken.EOF));
        }
    }


    /**
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


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Struct’> ::= <Herencia’> { <Atributo’> } | <Herencia’> { } | { <Atributo’> }
     * 
     * @param token
     */
    private void structP(Token token) {
        Token aux = null;

        if (checkFirst(First.firstHerenciaP)){
            aux = herenciaP();
        }
        
        //Genera la estructura en la tabla de simbolos
        semanticManager.addStruct(token, aux, true);

        match(IDToken.sKEY_OPEN);

        if (checkFirst(First.firstAtributoP)){
            atributoP();
        }

        match(IDToken.sKEY_CLOSE);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Impl> ::= impl idStruct { <Miembro’> }  
    */
    private void impl () {
        HashMap<String, SentenceBlock> hashMap= new HashMap<String, SentenceBlock> ();
        Token token = currentToken;
        match(IDToken.pIMPL);
        token = currentToken;
        match(IDToken.idSTRUCT);

        //Genera la estructura en la tabla de simbolos
        semanticManager.addStruct(token, null, false);

        match(IDToken.sKEY_OPEN);
        miembroP(hashMap);
        match(IDToken.sKEY_CLOSE);
        semanticManager.addBlock(hashMap);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Herencia> ::= : <Tipo>  
     * @return Token
    */
    private Token herencia () {
        match(IDToken.sCOLON);
        return tipo();
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Miembro> ::= <Método> | <Constructor>  
    */
    private void miembro ( HashMap<String, SentenceBlock> hashMap) {
        
        if (checkFirst(First.firstMetodo)){
            metodo(hashMap);
        }
        else{
            if (checkFirst(First.firstConstructor)){
                constructor(hashMap);
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


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Constructor> ::= . <Argumentos-Formales> <Bloque-Método>  
    */
    private void constructor (HashMap<String, SentenceBlock> hashMap) {
        Token token = currentToken;
        match(IDToken.sDOT);
        
        //Agrega el metodo constructor
        semanticManager.addMethod(
            token, argumentosFormales(), 
            false, 
            new Token(IDToken.typeVOID, "void", token.getLine(), token.getColumn())
        );

        bloqueMetodo("constructor", hashMap);
    }


    /**
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

        listaDeclaracionVariables(tipo(), isPrivate, true);

        match(IDToken.sSEMICOLON);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Método> ::= fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  | <Forma-Método’>fn idMetAt<Argumentos-Formales>-><Tipo-Método><Bloque-Método>  
    */
    private void metodo ( HashMap<String, SentenceBlock> hashMap) {
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
        semanticManager.addMethod(token, params, isStatic, tipoMetodo());

        bloqueMetodo(token.getLexema(),hashMap);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Visibilidad> ::= pri  
    */
    private void visibilidad () {
        match(IDToken.pPRI);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Forma-Método> ::= st  
    */
    private void formaMetodo () {
        match(IDToken.pST);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Bloque-Método> ::= { <Decl-Var-Locales’> <Sentencia’> } | { <Sentencia’> } | { <Decl-Var-Locales’> }  
    */
    private void bloqueMetodo(String nameMethod, HashMap<String, SentenceBlock> hashMap) {
        match(IDToken.sKEY_OPEN);

        if (checkFirst(First.firstDeclVarLocalesP)){
            declVarLocalesP();
        }
        
        if (checkFirst(First.firstSentenciaP)){
            ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
            sentenciaP(sentenceList);
            SentenceBlock sentenceBlock = new SentenceBlock(sentenceList);
            hashMap.put(nameMethod, sentenceBlock);
        }
        match(IDToken.sKEY_CLOSE);  
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Decl-Var-Locales> ::= <Tipo> <Lista-Declaración-Variables> ;   
    */
    private void declVarLocales () {
        listaDeclaracionVariables(tipo(),false, false);
        match(IDToken.sSEMICOLON);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * <Lista-Declaración-Variables>::= idMetAt | idMetAt , <Lista-Declaración-Variables>
     * 
     * @param type Tipo de dato
     * @param isPrivate Booleano para indicar si la variable es privada o no
     * @param isAtribute Booleano que indica si es un atributo o variable local.
    */
    private void listaDeclaracionVariables (Token type, boolean isPrivate, boolean isAtribute) {
        Token token = currentToken;
        match(IDToken.idOBJECT);

        semanticManager.addVar(token, type, isPrivate, isAtribute);

        if (currentToken.getIDToken().equals(IDToken.sCOM)){
            match(IDToken.sCOM);
            listaDeclaracionVariables(type, isPrivate, isAtribute);
        }
    }



    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumentos-Formales>::= ( <Lista-Argumentos-Formales’> ) | ( )  
     * 
     * @return ArrayList<Param> Returna una lista de parámetros.
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


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Argumentos-Formales> ::= <Argumento-Formal> , <Lista-Argumentos-Formales> | <Argumento-Formal>  
     * @param index
     * @return ArrayList<Param>
    */
    private ArrayList<Param> listaArgumentosFormales (int index) {
        ArrayList<Param> result = new ArrayList<Param>();
        
        //Agrega el parametro actual
        result.add(argumentoFormal(index));

        if (currentToken.getIDToken().equals(IDToken.sCOM)){
            match(IDToken.sCOM);

            //Agrega los parametros que se han obtenido recursivamente
            result.addAll(listaArgumentosFormales(index + 1));
        }

        //Lista de parametros ordenados
        return result;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumento-Formal> ::= <Tipo> idMetAt  
     * @param index
     * @return Param
    */
    private Param argumentoFormal (int index) {
        Token type = tipo();
        Param param = new Param(currentToken, type, index);
        match(IDToken.idOBJECT);
        return param;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Método> ::= <Tipo> | void  
     * @return Token
    */
    private Token tipoMetodo () {
        Token result;

        if (checkFirst(First.firstTipo)){
            result = tipo();
        }
        else{
            result = new Token(
                IDToken.typeVOID,
                "void",
                currentToken.getLine(),
                currentToken.getColumn()
            );
            match(IDToken.typeVOID);
        }
        return result;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo> ::= <Tipo-Primitivo> | <Tipo-Referencia> | <Tipo-Arreglo>  
     * @return Token
    */
    private Token tipo () {
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


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Primitivo> ::= Str | Bool | Int | Char  
     * @return Token
    */
    private Token tipoPrimitivo () {
        Token token = currentToken;
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
                throw throwError(First.firstTipoPrimitivo);
        }
        return token;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Referencia> ::= idStruct  
     * @return Token
    */
    private Token tipoReferencia () {
        Token token = currentToken;

        //Matchea idStruct o palabra reservada Object
        if (IDToken.idSTRUCT.equals(token.getIDToken())) {
            match(IDToken.idSTRUCT);
        }
        else {
            match(IDToken.spOBJECT);
        }

        return token;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Tipo-Arreglo> ::= Array <Tipo-Primitivo>  
     * @return Token
     */
    private Token tipoArreglo () {
        Token token = currentToken;
        match(IDToken.typeARRAY);

        //Accion para avisar que es array
        switch (tipoPrimitivo().getIDToken()) {
            case typeINT:
                token.setName(IDToken.typeArrayINT);
                break;
            case typeSTR:
                token.setName(IDToken.typeArraySTR);
                break;
            case typeBOOL:
                token.setName(IDToken.typeArrayBOOL);
                break;
            case typeCHAR:
                token.setName(IDToken.typeArrayCHAR);
                break;
            default:
                throw throwError(First.firstTipoPrimitivo);
        }

        return token;
    }


    /**
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
    private void sentencia (ArrayList<Sentence> sentenceList) {
        //  ;
        if (currentToken.getIDToken().equals(IDToken.sSEMICOLON)){
            match(IDToken.sSEMICOLON);
        }
        else{
            // <Asignación> ;
            if (checkFirst(First.firstAsignacion)){
                asignacion(sentenceList);
                match(IDToken.sSEMICOLON);
            }
            else{
                // <Sentencia-Simple> ;
                if (checkFirst(First.firstSentenciaSimple)){
                    sentenciaSimple(sentenceList);
                    match(IDToken.sSEMICOLON);
                }
                else{
                    //if ( <Expresión> ) <Sentencia> <MoreIF> y if ( <Expresión> ) <Sentencia> 
                    if (currentToken.getIDToken().equals(IDToken.pIF)){
                        match(IDToken.pIF);
                        match(IDToken.sPAR_OPEN);
                        expresion();
                        match(IDToken.sPAR_CLOSE);
                        sentencia(sentenceList);
                        if (checkFirst(First.firstMoreIF)){
                            moreIF(sentenceList);
                        }
                    }
                    //while ( <Expresión> ) <Sentencia> 
                    else{
                        if (currentToken.getIDToken().equals(IDToken.pWHILE)){
                            match(IDToken.pWHILE);
                            match(IDToken.sPAR_OPEN);
                            expresion();
                            match(IDToken.sPAR_CLOSE);
                            sentencia(sentenceList);
                        }
                        //<Bloque> 
                        else{
                            if (checkFirst(First.firstBloque)){
                                bloque(sentenceList);
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


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <MoreIF> ::= else <Sentencia>
    */
    private void moreIF (ArrayList<Sentence> sentenceList) {
        match(IDToken.pELSE);
        sentencia(sentenceList);
    }
    

    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Bloque> ::= { <Sentencia’> } | { }  
    */
    private void bloque (ArrayList<Sentence> sentenceList) {
        match(IDToken.sKEY_OPEN);
        if (checkFirst(First.firstSentenciaP)){
            sentenciaP(sentenceList);
        }
        match(IDToken.sKEY_CLOSE);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Asignación> ::= <AccesoVar-Simple> = <Expresión> | <AccesoSelf-Simple>=<Expresión>  
    */
    private void asignacion (ArrayList<Sentence> sentenceList) {
        boolean pass = false;
        SimpleAccess leftSide=null;
        Expression expression=null;
        if (checkFirst(First.firstAccesoVarSimple)){
            leftSide = (SimpleAccess) accesoVarSimple();
            pass = true;
        }
        if (checkFirst(First.firstAccesoSelfSimple)){
            leftSide = accesoSelfSimple();
            pass = true;
        }

        if (pass) {
            match(IDToken.ASSIGN);
            expression = expresion();
        } else {
            throw throwError(
                new HashSet<IDToken>(First.firstAccesoVarSimple){{
                    addAll(First.firstAccesoSelfSimple);
                }}
            );
        }
        sentenceList.add(new Assignation(leftSide, expression, semanticManager.getCurrentStructName(), 
            semanticManager.getCurrentMethodName()));

    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoVar-Simple> ::= id <Encadenado-Simple’> | id [ <Expresión> ] | id  
    */
    private Primary accesoVarSimple () {
        Token token = currentToken;
        SimpleAccess rightSide = null;
        isID();
        if (checkFirst(First.firstEncadenadoSimpleP)){
            rightSide = encadenadoSimpleP();
            
        } else {
            if (currentToken.getIDToken().equals(IDToken.sCOR_OPEN)) {
                match(IDToken.sCOR_OPEN);
                Expression indexArray = expresion();
                match(IDToken.sCOR_CLOSE);
                return new ArrayAccess(token, indexArray, null, semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
            }
        }
        return new SimpleAccess(token, rightSide, semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoSelf-Simple> ::= self <Encadenado-Simple’> | self  
    */
    private SimpleAccess accesoSelfSimple () {
        Token token = currentToken;
        match(IDToken.pSELF);
        SimpleAccess rightSide = null;
        if (checkFirst(First.firstEncadenadoSimpleP)){
            rightSide = encadenadoSimpleP();
        }
        return new SimpleAccess(token, rightSide, semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado-Simple> ::= . id  
    */
    private Token encadenadoSimple () {
        match(IDToken.sDOT);
        Token token = currentToken;
        isID();
        return token;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Sentencia-Simple> ::= ( <Expresión> )  
    */
    private void sentenciaSimple (ArrayList<Sentence> sentenceList) {
        match(IDToken.sPAR_OPEN);
        sentenceList.add(expresion());
        match(IDToken.sPAR_CLOSE);
        
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Expresión> ::= <ExpOr>  
    */
    private Expression expresion () {
        return expOr();
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpOr> ::= <ExpAnd> <ExpOr’> | <ExpAnd>  
    */
    private Expression expOr () {
        Expression expresionLeft = expAnd();
        if (checkFirst(First.firstExpOrP)) {
            expOrP(expresionLeft);
        }
        return expresionLeft;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAnd> ::= <ExpIgual><ExpAnd’> | <ExpIgual>  
    */
    private Expression expAnd () {
        Expression expression= expIgual();
        if (checkFirst(First.firstExpAndP)) {
            expAndP();
        }
        return expression;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpIgual> ::= <ExpCompuesta><ExpIgual’> | <ExpCompuesta>  
    */
    private Expression expIgual () {
        Expression expression = expCompuesta();
        if (checkFirst(First.firstExpIgualP)) {
            expIgualP();
        }
        return expression;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAd> ::= <ExpMul><ExpAd’> | <ExpMul>  
    */
    private Expression expAd () {
        Expression expression = expMul();
        if (checkFirst(First.firstExpAdP)) {
            expAdP();
        }
        return expression;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpMul> ::= <ExpUn> <ExpMul’> | <ExpUn>  
    */
    private Expression expMul () {
        Expression expression= expUn();
        if (checkFirst(First.firstExpMulP)) {
            expMulP();
        }
        return expression;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpCompuesta> ::= <ExpAd> <OpCompuesto> <ExpAd> | <ExpAd>  
    */
    private Expression expCompuesta () {
        Expression leftSide = expAd();
        boolean pass=false;
        IDToken idToken=null;
        Expression rightSide=null;
        if (checkFirst(First.firstOpCompuesto)) {
            pass=true;
            idToken = opCompuesto();
            rightSide = expAd();
        }
        return (pass==true ? new BinaryExpression(leftSide, idToken, rightSide,semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName())
             : leftSide);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpUn> ::= <OpUnario> <ExpUn> | <Operando>  
    */
    private Expression expUn () {

        if (checkFirst(First.firstOpUnario)) {
            return new UnaryExpression(opUnario(), expUn(), semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
        } else {
            return operando();
        }
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpIgual> ::= == | !=  
    */
    private IDToken opIgual () {
        IDToken idToken = currentToken.getIDToken();
        switch (idToken) {
            case oEQUAL:
                match(IDToken.oEQUAL);
                break;
            case oNOT_EQ:
                match(IDToken.oNOT_EQ);
                break;
            default:
            throw throwError(First.firstOpIgual);
        }
        return idToken;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpCompuesto> ::= < |> | <= |>=  
    */
    private IDToken opCompuesto () {
        IDToken idToken = currentToken.getIDToken();
        switch (idToken) {
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
        return idToken;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpAd> ::= + | -  
    */
    private IDToken opAd () {
        IDToken idToken = currentToken.getIDToken();
        switch (idToken) {
            case oSUM:
                match(IDToken.oSUM);
                break;
            case oSUB:
                match(IDToken.oSUB);
                break;
            default:
                throw throwError(First.firstOpAd);
        }
        return idToken;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpUnario> ::= + | - | ! | ++ | --  
    */
    private IDToken opUnario () {
        IDToken idToken = currentToken.getIDToken();
        switch (idToken) {
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
        return idToken;
        
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <OpMul> ::= * | / | %  
    */
    private IDToken opMul () {
        IDToken idToken = currentToken.getIDToken();
        switch (idToken) {
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
        return idToken;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Operando> ::= <Literal> | <Primario> <Encadenado’> | <Primario>  
    */
    private Primary operando () {
        if (checkFirst(First.firstLiteral)) {
            Token token = literal();
            return new SimpleAccess(token, null,semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
        }
        else {
            Primary primaryNode = primario();
            if (checkFirst(First.firstEncadenadoP)) {
                primaryNode.setChained(encadenadoP());
            }
            return primaryNode;
        }
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Literal> ::= nil | true | false | intLiteral | StrLiteral | charLiteral
    */
    private Token literal () {
        Token token = currentToken;
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
        return token;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Primario> :: = <Primario’> <Encadenado’> | <Primario’>
    */
    private Primary primario () {
        Primary primary = primarioP();
        if (checkFirst(First.firstEncadenadoP)) {
            primary.setChained(encadenadoP());
        }
        return primary;
    }

    /**
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
    private Primary primarioP () {
        Primary primary = null;
        boolean checkExpresion = false;
        ArrayList<Expression> expressionsList = new ArrayList<Expression>();
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
                argumentosActuales(expressionsList);
                break;
            case pNEW:
                match(IDToken.pNEW);
                if (checkFirst(First.firstTipoPrimitivo)) {
                    tipoPrimitivo();
                    checkExpresion = true;
                } else {
                    if (checkFirst(First.firstTipoReferencia)){
                        if (IDToken.idSTRUCT.equals(currentToken.getIDToken())) {
                            match(IDToken.idSTRUCT);
                        } else {
                            match(IDToken.spOBJECT);
                        }
                        argumentosActuales(expressionsList);
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
                    argumentosActuales(expressionsList);
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
        return primary;
    }

    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumentos-Actuales> ::= ( <Lista-Expresiones’> ) | ( )  
    */
    private void argumentosActuales (ArrayList<Expression> expressionsList) {
        match(IDToken.sPAR_OPEN);
        if (checkFirst(First.firstListaExpresionesP)) {
            listaExpresionesP(expressionsList);
        }
        match(IDToken.sPAR_CLOSE);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Expresiones> ::= <Expresión> | <Expresión> , <Lista-Expresiones>   
    */
    private void listaExpresiones (ArrayList<Expression> expressionsList) {
        expressionsList.add(expresion());
        if (currentToken.getIDToken().equals(IDToken.sCOM)) {
            match(IDToken.sCOM);
            listaExpresiones(expressionsList);
        }
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado> ::= <EncadenadoExtra> | <EncadenadoExtra> <Encadenado’>  
    */
    private Primary encadenado () {
        Primary primary = encadenadoExtra();
        if (checkFirst(First.firstEncadenadoP)) {
            primary.setChained(encadenadoP());
        }
        return primary;
    }
    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <EncadenadoExtra> ::= . id <Argumentos-Actuales>
			                | . id  
			                | . id [ <Expresión> ] 
    */
    private Primary encadenadoExtra () {
        match(IDToken.sDOT);
        Token token=currentToken;
        isID();
        if (checkFirst(First.firstArgumentosActuales)) {
            ArrayList<Expression> expressionsList = new ArrayList<Expression>();
            argumentosActuales(expressionsList);
            return new MethodAccess(token, expressionsList, null,semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
        }
        else{
            if (currentToken.getIDToken().equals(IDToken.sCOR_OPEN)) {
                match(IDToken.sCOR_OPEN);
                Expression expression= expresion();
                match(IDToken.sCOR_CLOSE);
                return new ArrayAccess(token, expression, null,semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
            }
        }
        return new SimpleAccess(token, null,semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());

    }


    /**
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


    /**
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


    /**
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


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Sentencia’> ::= <Sentencia><Sentencia’> | <Sentencia>  
    */
    private void sentenciaP (ArrayList<Sentence> sentenceList) {
        sentencia(sentenceList);
        if (checkFirst(First.firstSentenciaP)) {
            sentenciaP(sentenceList);
        }
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado-Simple’> ::= <Encadenado-Simple><Encadenado-Simple’> | <Encadenado-Simple>  
    */
    private SimpleAccess encadenadoSimpleP () {
        Token token= encadenadoSimple();
        if (checkFirst(First.firstEncadenadoSimpleP)) {
            return new SimpleAccess(token, encadenadoSimpleP(),semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
        }
        return new SimpleAccess(token,null,semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Herencia’> ::= <Herencia>  
    */
    private Token herenciaP () {
        return herencia();
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Visibilidad’> ::= <Visibilidad>  
    */
    private void visibilidadP () {
        visibilidad();
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Forma-Método’> ::= <Forma-Método>  
    */
    private void formaMetodoP () {
        formaMetodo();
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Expresión’> ::= <Expresión>  
    */
    private void expresionP () {
        expresion();
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado’> ::= <Encadenado>  
    */
    private Primary encadenadoP () {
        return encadenado();
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Expresiones’> ::= <Lista-Expresiones>  
    */
    private void listaExpresionesP (ArrayList<Expression> expressionsList) {
        listaExpresiones(expressionsList);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Argumentos-Formales’> ::= <Lista-Argumentos-Formales>  
    */
    private ArrayList<Param> listaArgumentosFormalesP () {
        return listaArgumentosFormales(0);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Miembro’> ::= <Miembro> | <Miembro><Miembro’>  
    */
    private void miembroP (HashMap<String, SentenceBlock> hashMap) {
        miembro(hashMap);
        if (checkFirst(First.firstMiembroP)) {
            miembroP(hashMap);
        }
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpOr’> ::= || <ExpAnd> <ExpOr’> | || <ExpAnd>  
    */
    private Expression expOrP (Expression expressionLeft) {
        match(IDToken.oOR);
        Expression expressionRight = expAnd();
        BinaryExpression binaryExpression =new BinaryExpression(expressionLeft, IDToken.oOR, expressionRight,  semanticManager.getCurrentStructName(), semanticManager.getCurrentMethodName());
        if (checkFirst(First.firstExpOrP)) {
            expOrP(binaryExpression);
        }
        return binaryExpression;
    }


    /**
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


    /**
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


    /**
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


    /**
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

    /** 
     * Genera un ArrayList<String> con los json generados para tabla de símbolos y ast
     * @return ArrayList<String> con tabla de símbolos (posicion 0) y ast (posicion 1) en formato json
     */
    public ArrayList<String> toJSON(){
        return this.semanticManager.toJSON();
    }
}