package src.lib.tokenHelper;

/**
 * Un enum que contendra todos los tipos de tokens que pueden existir dentro
 * del código fuente del un proyecto tinyRu.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 06/03/2024
 */
public enum IDToken {
    /** EOF */ EOF("EOF"),

    //Operador de asignacion =
    /** = */ ASSIGN("="),

    //Structuras predefinidas
    /** Struct predefinida Object */ spOBJECT("Object"),
    /** Struct predefinida IO */ spIO("IO"),
    
    //Tipos de datos
    /** Tipo de dato INT */ typeINT("Int"),
    /** Tipo de dato STR */ typeSTR("Str"),
    /** Tipo de dato BOOL */ typeBOOL("Bool"),
    /** Tipo de dato CHAR */ typeCHAR("Char"),
    /** Tipo de dato ARRAY */ typeARRAY("Array"),
    /** Tipo de dato VOID */ typeVOID("void"),
    
    
    //Para valores constantes
    /** Para constantes de tipo INT */ constINT("literal Int"),
    /** Para constantes de tipo STR */ constSTR("literal Str"),
    /** Para constantes de tipo CHAR */ constCHAR("literal Char"),


    //Operadores aritmeticos
    /** + */ oSUM("+"),
    /** - */ oSUB("-"),
    /** <p>\u002A</p> */ oMULT("*"),
    /** ++ */ oSUM_SUM("++"),
    /** -- */ oSUB_SUB("--"),
    /** / */ oDIV("/"),
    /** % */ oMOD("%"),


    //Operadores logicos
    /** &amp;&amp; */ oAND("&&"),
    /** || */ oOR("||"),
    /** ! */ oNOT("!"),
    /** &lt; */ oMIN("<"),
    /** &le; */ oMIN_EQ("<="),
    /** == */ oEQUAL("=="),
    /** &gt; */ oMAX(">"),
    /** &ge; */ oMAX_EQ(">="),
    /** != */ oNOT_EQ("!="),


    //Simbolos especiales
    /** ( */ sPAR_OPEN("("),
    /** ) */ sPAR_CLOSE(")"),
    /** [ */ sCOR_OPEN("["),
    /** ] */ sCOR_CLOSE("]"),
    /** { */ sKEY_OPEN("{"),
    /** } */ sKEY_CLOSE("}"),
    /** . */ sDOT("."),
    /** , */ sCOM(","),
    /** : */ sCOLON(":"),
    /** ; */ sSEMICOLON(";"),
    /** -> */ sARROW_METHOD("->"),
    

    //Identificadores
    /** Para identificadores de estructuras*/ idSTRUCT("id Struct"),
    /** Para identificadores de variables o metodos*/ idOBJECT("id variable o método"),
    /** Para identificadores de metodo START */ idSTART("start"),
    

    //Palabras clave o reservadas
    /** Para palabra reservada STRUCT */ pSTRUCT("struct"),
    /** Para palabra reservada IMPL */ pIMPL("impl"),
    /** Para palabra reservada WHILE */ pWHILE("while"),
    /** Para palabra reservada IF */ pIF("if"),
    /** Para palabra reservada ELSE */ pELSE("else"),
    /** Para palabra reservada FALSE */ pFALSE("false"),
    /** Para palabra reservada TRUE */ pTRUE("true"),
    /** Para palabra reservada NIL */ pNIL("nil"),
    /** Para palabra reservada NEW */ pNEW("new"),
    /** Para palabra reservada FN */ pFN("fn"),
    /** Para palabra reservada ST */ pST("st"),
    /** Para palabra reservada PRI */ pPRI("pri"),
    /** Para palabra reservada PUB */ pPUB("pub"),
    /** Para palabra reservada SELF */ pSELF("self"),
    /** Para palabra reservada RET */ pRET("ret");

    private final String descripcion;
    IDToken(String descripcion) {
        this.descripcion = descripcion;
    }
    @Override
    public String toString() {
        return descripcion;
    }
}
