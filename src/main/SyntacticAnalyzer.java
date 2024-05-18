package src.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import src.lib.exceptionHelper.LexicalException;
import src.lib.exceptionHelper.SemanticException;
import src.lib.exceptionHelper.SyntacticException;
import src.lib.semanticHelper.SemanticManager;
import src.lib.semanticHelper.astHelper.SentenceBlock;
import src.lib.semanticHelper.astHelper.sentences.Assignation;
import src.lib.semanticHelper.astHelper.sentences.Block;
import src.lib.semanticHelper.astHelper.sentences.Conditional;
import src.lib.semanticHelper.astHelper.sentences.Loop;
import src.lib.semanticHelper.astHelper.sentences.Return;
import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.semanticHelper.astHelper.sentences.expressions.BinaryExpression;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.sentences.expressions.UnaryExpression;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.ArrayAccess;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.CreateArray;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.CreateInstance;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.MethodAccess;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.SimpleAccess;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.SimpleSentence;
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

        //Genera la tabla de símbolos y el ast
        semanticManager = new SemanticManager();

        //Comienza el análisis
        this.program();

        //Si el análisis no retorna error, ha sido correcto y consolida la tabla de símbolos y el ast
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
    private Token isID () {
        Token token = currentToken;
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
        return token;
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
        
        bloqueMetodo(token);

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
        Token token = currentToken;
        match(IDToken.pIMPL);
        token = currentToken;
        match(IDToken.idSTRUCT);

        //Genera la estructura en la tabla de simbolos
        semanticManager.addStruct(token, null, false);

        match(IDToken.sKEY_OPEN);
        miembroP();
        match(IDToken.sKEY_CLOSE);
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


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Constructor> ::= . <Argumentos-Formales> <Bloque-Método>  
    */
    private void constructor () {
        Token token = currentToken;
        match(IDToken.sDOT);
        
        //Agrega el metodo constructor
        semanticManager.addMethod(
            token, argumentosFormales(), 
            false, 
            new Token(IDToken.typeVOID, "void", token.getLine(), token.getColumn())
        );

        bloqueMetodo(token);
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
        semanticManager.addMethod(token, params, isStatic, tipoMetodo());

        bloqueMetodo(token);
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
    private void bloqueMetodo(Token idBlock) {
        //Inicializa el array de sentencias
        ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();

        match(IDToken.sKEY_OPEN);

        if (checkFirst(First.firstDeclVarLocalesP)){
            declVarLocalesP();
        }
        
        //Valida si debe agregar sentencias al array
        if (checkFirst(First.firstSentenciaP)){
            sentenceList = sentenciaP();
        }
        
        match(IDToken.sKEY_CLOSE);

        //Agrega el bloque (Aunque no posea sentencias)
        semanticManager.addBlock(new SentenceBlock(idBlock, sentenceList));
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
    private Sentence sentencia () {
        Sentence sentence = null, auxSentence1 = null, auxSentence2 = null;
        Expression exp = null;

        //  ;
        if (currentToken.getIDToken().equals(IDToken.sSEMICOLON)){
            match(IDToken.sSEMICOLON);
        }
        else{
            // <Asignación> ;
            if (checkFirst(First.firstAsignacion)){
                sentence = asignacion();
                match(IDToken.sSEMICOLON);
            }
            else{
                // <Sentencia-Simple> ;
                if (checkFirst(First.firstSentenciaSimple)){
                    sentence = sentenciaSimple();
                    match(IDToken.sSEMICOLON);
                }
                else{
                    //if ( <Expresión> ) <Sentencia> <MoreIF> y if ( <Expresión> ) <Sentencia> 
                    if (currentToken.getIDToken().equals(IDToken.pIF)){
                        Token token = currentToken;
                        match(IDToken.pIF);
                        match(IDToken.sPAR_OPEN);
                        exp = expresion(); //Condicion del if
                        match(IDToken.sPAR_CLOSE);
                        auxSentence1 = sentencia(); //Sentencia then

                        //Valida si posee sentencias else
                        if (checkFirst(First.firstMoreIF)){
                            auxSentence2 = moreIF();
                        }

                        sentence = new Conditional(token,exp, auxSentence1, auxSentence2);
                    }
                    //while ( <Expresión> ) <Sentencia> 
                    else{
                        if (currentToken.getIDToken().equals(IDToken.pWHILE)){
                            Token token = currentToken;
                            match(IDToken.pWHILE);
                            match(IDToken.sPAR_OPEN);
                            exp = expresion(); //Condicion del while
                            match(IDToken.sPAR_CLOSE);
                            sentence = new Loop(token, exp, sentencia());
                        }
                        //<Bloque> 
                        else{
                            if (checkFirst(First.firstBloque)){
                                sentence = bloque();
                            }
                            // ret <Expresión’> ;  y ret ;
                            else{
                                if (currentToken.getIDToken().equals(IDToken.pRET)){
                                    Token token = currentToken;
                                    match(IDToken.pRET);
                                    if (checkFirst(First.firstExpresionP)){
                                        exp = expresionP();
                                    }
                                    match(IDToken.sSEMICOLON);
                                    sentence = new Return(token, exp);
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
        return sentence;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <MoreIF> ::= else <Sentencia>
    */
    private Sentence moreIF () {
        match(IDToken.pELSE);
        return sentencia();
    }
    

    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Bloque> ::= { <Sentencia’> } | { }  
    */
    private Sentence bloque() {
        ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
        match(IDToken.sKEY_OPEN);
        if (checkFirst(First.firstSentenciaP)){
            sentenceList = sentenciaP();
        }
        match(IDToken.sKEY_CLOSE);

        //Agrega el bloque (Aunque no posea sentencias)
        return new Block(sentenceList);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Asignación> ::= <AccesoVar-Simple> = <Expresión> | <AccesoSelf-Simple>=<Expresión>  
    */
    private Assignation asignacion() {
        boolean pass = false;
        Primary leftSide=null;
        Expression expression=null;
        Token token;

        //Obtiene la parte izquierda
        if (checkFirst(First.firstAccesoVarSimple)){
            leftSide = accesoVarSimple();
            pass = true;
        }
        if (checkFirst(First.firstAccesoSelfSimple)){
            leftSide = accesoSelfSimple();
            pass = true;
        }

        //Obtiene la parte derecha
        if (pass) {
            token = currentToken;
            match(IDToken.ASSIGN);
            expression = expresion();
        } else {
            throw throwError(
                new HashSet<IDToken>(First.firstAccesoVarSimple){{
                    addAll(First.firstAccesoSelfSimple);
                }}
            );
        }

        //Genera el nodo de asignacion
        return new Assignation(token, leftSide, expression);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoVar-Simple> ::= id <Encadenado-Simple’> | id [ <Expresión> ] | id  
    */
    private Primary accesoVarSimple () {
        Token token = isID();
        Primary primary = new SimpleAccess(token, null);

        if (checkFirst(First.firstEncadenadoSimpleP)){
            primary = new SimpleAccess(token, encadenadoSimpleP());
        } else {
            if (currentToken.getIDToken().equals(IDToken.sCOR_OPEN)) {
                match(IDToken.sCOR_OPEN);
                primary = new ArrayAccess(token, expresion(), null) ;
                match(IDToken.sCOR_CLOSE);
            }
        }
        return primary;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <AccesoSelf-Simple> ::= self <Encadenado-Simple’> | self  
    */
    private SimpleAccess accesoSelfSimple () {
        Token token = currentToken;
        SimpleAccess rightSide = null;

        match(IDToken.pSELF);
        
        if (checkFirst(First.firstEncadenadoSimpleP)){
            rightSide = encadenadoSimpleP();
        }
        return new SimpleAccess(token, rightSide);
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado-Simple> ::= . id  
    */
    private Token encadenadoSimple () {
        match(IDToken.sDOT);
        Token token = isID();
        return token;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Sentencia-Simple> ::= ( <Expresión> )  
    */
    private Expression sentenciaSimple () {
        Expression exp;
        match(IDToken.sPAR_OPEN);
        exp = expresion();
        match(IDToken.sPAR_CLOSE);
        return exp;
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
        Expression expLeft = expAnd();
        if (checkFirst(First.firstExpOrP)) {
            expLeft = expOrP(expLeft);
        }
        return expLeft;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAnd> ::= <ExpIgual><ExpAnd’> | <ExpIgual>  
    */
    private Expression expAnd () {
        Expression exp = expIgual();
        if (checkFirst(First.firstExpAndP)) {
            exp = expAndP(exp);
        }
        return exp;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpIgual> ::= <ExpCompuesta><ExpIgual’> | <ExpCompuesta>  
    */
    private Expression expIgual () {
        Expression exp = expCompuesta();
        if (checkFirst(First.firstExpIgualP)) {
            exp = expIgualP(exp);
        }
        return exp;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAd> ::= <ExpMul><ExpAd’> | <ExpMul>  
    */
    private Expression expAd () {
        Expression exp = expMul();
        if (checkFirst(First.firstExpAdP)) {
            exp = expAdP(exp);
        }
        return exp;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpMul> ::= <ExpUn> <ExpMul’> | <ExpUn>  
    */
    private Expression expMul () {
        Expression exp= expUn();
        if (checkFirst(First.firstExpMulP)) {
            exp = expMulP(exp);
        }
        return exp;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpCompuesta> ::= <ExpAd> <OpCompuesto> <ExpAd> | <ExpAd>  
    */
    private Expression expCompuesta () {
        Expression leftSide = expAd();
        IDToken operator = null;
        Expression rightSide = null;

        if (checkFirst(First.firstOpCompuesto)) {
            Token token = currentToken;
            operator = opCompuesto();
            rightSide = expAd();
            return new BinaryExpression(token, leftSide, operator, rightSide);
        }
        return leftSide;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpUn> ::= <OpUnario> <ExpUn> | <Operando>  
    */
    private Expression expUn () {
        Expression exp;
        if (checkFirst(First.firstOpUnario)) {
            Token token = currentToken;
            exp = new UnaryExpression(token, opUnario(), expUn());
        } else {
            exp = operando();
        }
        return exp;
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
        Primary exp;

        if (checkFirst(First.firstLiteral)) {
            exp = new SimpleAccess(literal(), null);
        }
        else {
            exp = primario();
            if (checkFirst(First.firstEncadenadoP)) {
                exp.setChained(encadenadoP());
            }
        }
        return exp;
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
        Primary exp = primarioP();
        if (checkFirst(First.firstEncadenadoP)) {
            exp.setChained(encadenadoP());
        }
        return exp;
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
        Primary exp = null;
        Token token = currentToken;

        switch (currentToken.getIDToken()) {
            case sPAR_OPEN:
                match(IDToken.sPAR_OPEN);
                exp = new SimpleSentence(token, expresion(), null);
                match(IDToken.sPAR_CLOSE);
                break;
            case pSELF:
                match(IDToken.pSELF);
                exp = new SimpleAccess(token, null);
                break;
            case idSTRUCT:
                match(IDToken.idSTRUCT);
                match(IDToken.sDOT);
                exp = new SimpleAccess(token, new MethodAccess(isID(), argumentosActuales(), null));
                break;
            case pNEW:
                match(IDToken.pNEW);
                if (checkFirst(First.firstTipoPrimitivo)) {
                    token = tipoPrimitivo();
                    match(IDToken.sCOR_OPEN);
                    exp = new CreateArray(token, expresion(), null);
                    match(IDToken.sCOR_CLOSE);
                } else {
                    //Valida que sea idStruct o palabra reservada Object
                    if (checkFirst(First.firstTipoReferencia)){
                        token = currentToken;
                        
                        //Matchea segun lo que es
                        if (IDToken.idSTRUCT.equals(currentToken.getIDToken())) {
                            match(IDToken.idSTRUCT);
                        } else {
                            match(IDToken.spOBJECT);
                        }

                        //Genera la expresion
                        exp = new CreateInstance(token, argumentosActuales(), null);
                    }
                    else{
                        throw throwError( new HashSet<IDToken>(First.firstTipoPrimitivo){{ addAll(First.firstTipoReferencia); }} );
                    }
                }
                break;
            default:
                token = isID();
                exp = new SimpleAccess(token, null);

                if (checkFirst(First.firstArgumentosActuales)) {
                    exp = new MethodAccess(token, argumentosActuales(), null);
                }
                else {
                    if (currentToken.getIDToken().equals(IDToken.sCOR_OPEN)) {
                        match(IDToken.sCOR_OPEN);
                        exp = new ArrayAccess(token, expresion(), null);
                        match(IDToken.sCOR_CLOSE);
                    }
                }
                break;
        }
        return exp;
    }

    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Argumentos-Actuales> ::= ( <Lista-Expresiones’> ) | ( )  
    */
    private ArrayList<Expression> argumentosActuales () {
        ArrayList<Expression> result = new ArrayList<Expression>();
        match(IDToken.sPAR_OPEN);
        if (checkFirst(First.firstListaExpresionesP)) {
            result = listaExpresionesP();
        }
        match(IDToken.sPAR_CLOSE);

        return result;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Lista-Expresiones> ::= <Expresión> | <Expresión> , <Lista-Expresiones>   
    */
    private ArrayList<Expression> listaExpresiones (int position) {
        ArrayList<Expression> expressionsList = new ArrayList<Expression>();
        Expression exp = expresion();

        //Agrego la expresion
        exp.setPosition(position);
        expressionsList.add(exp);

        //Reviso si se debe agregar otro parametro
        if (currentToken.getIDToken().equals(IDToken.sCOM)) {
            match(IDToken.sCOM);
            expressionsList.addAll( listaExpresiones(position + 1) );
        }
        return expressionsList;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado> ::= <EncadenadoExtra> | <EncadenadoExtra> <Encadenado’>  
    */
    private Primary encadenado () {
        Primary exp = encadenadoExtra();
        if (checkFirst(First.firstEncadenadoP)) {
            exp.setChained(encadenadoP());
        }
        return exp;
    }
    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <EncadenadoExtra> ::= . id <Argumentos-Actuales>
			                | . id  
			                | . id [ <Expresión> ] 
    */
    private Primary encadenadoExtra () {
        Token token;
        Primary exp;

        match(IDToken.sDOT);
        
        token = isID();
        exp = new SimpleAccess(token, null);

        if (checkFirst(First.firstArgumentosActuales)) {
            exp = new MethodAccess(token, argumentosActuales(), null);
        }
        else{
            match(IDToken.sCOR_OPEN);
            exp = new ArrayAccess(token, expresion(), null);
            match(IDToken.sCOR_CLOSE);
        }

        return exp;
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
    private ArrayList<Sentence> sentenciaP () {
        //Genero el array de sentencias
        ArrayList<Sentence> sentenceList = new ArrayList<Sentence>() {{ add(sentencia()); }};

        //Verifico si se deben agregar mas y las concateno
        if (checkFirst(First.firstSentenciaP)) {
            sentenceList.addAll( sentenciaP() );
        }
        return sentenceList;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <Encadenado-Simple’> ::= <Encadenado-Simple><Encadenado-Simple’> | <Encadenado-Simple>  
    */
    private SimpleAccess encadenadoSimpleP () {
        Token token = encadenadoSimple();
        Primary rightSide = null;
        if (checkFirst(First.firstEncadenadoSimpleP)) {
            rightSide = encadenadoSimpleP();
        }
        return new SimpleAccess(token, rightSide);
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
    private Expression expresionP () {
        return expresion();
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
    private ArrayList<Expression> listaExpresionesP () {
        return listaExpresiones(0);
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
    private void miembroP () {
        miembro();
        if (checkFirst(First.firstMiembroP)) {
            miembroP();
        }
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpOr’> ::= || <ExpAnd> <ExpOr’> | || <ExpAnd>  
    */
    private Expression expOrP (Expression leftSide) {
        Token token = currentToken;
        match(IDToken.oOR);
        Expression exp = new BinaryExpression(token, leftSide, IDToken.oOR, expAnd());

        if (checkFirst(First.firstExpOrP)) {
            exp = expOrP(exp);
        }
        return exp;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAnd’> ::= && <ExpIgual><ExpAnd’> | && <ExpIgual>  
    */
    private Expression expAndP (Expression leftSide) {
        Token token = currentToken;
        match(IDToken.oAND);
        Expression exp = new BinaryExpression(token, leftSide, IDToken.oAND, expIgual());

        if (checkFirst(First.firstExpAndP)) {
            exp = expAndP(exp);
        }
        return exp;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpIgual’> ::= <OpIgual> <ExpCompuesta> <ExpIgual’> | <OpIgual> <ExpCompuesta>  
    */
    private Expression expIgualP (Expression leftSide) {
        Token token = currentToken;
        IDToken idToken= opIgual();
        Expression exp = new BinaryExpression(token, leftSide, idToken, expCompuesta());

        if (checkFirst(First.firstExpIgualP)) {
            exp = expIgualP(exp);
        }
        return exp;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpAd’> ::= <OpAd> <ExpMul> <ExpAd’> | <OpAd> <ExpMul>  
    */
    private Expression expAdP (Expression leftSide) {
        Token token=currentToken;
        Expression exp = new BinaryExpression(token, leftSide, opAd(), expMul());

        if (checkFirst(First.firstExpAdP)) {
            exp = expAdP(exp);
        }
        return exp;
    }


    /**
     * Método que ejecuta la regla de producción: <br/>
     * 
     * <ExpMul’> ::= <OpMul> <ExpUn> <ExpMul’> | <OpMul> <ExpUn>  
    */
    private Expression expMulP (Expression leftSide) {
        Token token = currentToken;
        Expression exp = new BinaryExpression(token, leftSide, opMul(), expUn());

        if (checkFirst(First.firstExpMulP)) {
            exp = expMulP(exp);
        }
        return exp;
    }

    /** 
     * Genera un ArrayList<String> con los json generados para tabla de símbolos y ast
     * @return ArrayList<String> con tabla de símbolos (posicion 0) y ast (posicion 1) en formato json
     */
    public ArrayList<String> toJSON(){
        return this.semanticManager.toJSON();
    }
}