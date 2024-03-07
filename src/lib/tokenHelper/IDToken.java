package src.lib.tokenHelper;

/**
 * Un enum que contendra todos los tipos de tokens que pueden existir dentro
 * del código fuente del un proyecto tinyRu.
 * 
 * @author Cristian Serrano
 * @since 06/03/2024
 */
public enum IDToken {
    //Constructor de clase
    /** Para el nombre del constructor de la clase */ CONSTRUCTOR,
    //Comentario
    /** /? */ COMMENT,
    //Operador de asignacion =
    /** = */ ASSIGN,

    //Structuras predefinidas
    /** Struct predefinida Object */ spOBJECT,
    /** Struct predefinida IO */ spIO,
    
    //Tipos de datos
    /** Tipo de dato INT */ typeINT,
    /** Tipo de dato STR */ typeSTR,
    /** Tipo de dato BOOL */ typeBOOL,
    /** Tipo de dato CHAR */ typeCHAR,
    /** Para cualquier otro tipo de dato definido en una clase */ typeCLASS,
    
    
    //Para valores constantes
    /** Para constantes de tipo INT */ constINT,
    /** Para constantes de tipo STR */ constSTR,
    /** Para constantes de tipo BOOL */ constBOOL,
    /** Para constantes de tipo CHAR */ constCHAR,


    //Operadores aritmeticos
    /** + */ oSUM,
    /** - */ oSUB,
    /** <p>\u002A</p> */ oMULT,
    /** ++ */ oSUM_SUM,
    /** -- */ oSUB_SUB,
    /** / */ oDIV,
    /** % */ oMOD,


    //Operadores logicos
    /** &amp;&amp; */ oAND,
    /** || */ oOR,
    /** ! */ oNOT,
    /** &lt; */ oMIN,
    /** &le; */ oMIN_EQ,
    /** == */ oEQUAL,
    /** > */ oMAX,
    /** &ge; */ oMAX_EQ,
    /** != */ oNOT_EQ,


    //Simbolos especiales
    /** ( */ sPAR_OPEN,
    /** ) */ sPAR_CLOSE,
    /** [ */ sCOR_OPEN,
    /** ] */ sCOR_CLOSE,
    /** { */ sKEY_OPEN,
    /** } */ sKEY_CLOSE,
    /** . */ sDOT,
    /** , */ sCOM,
    /** : */ sCOLON,
    /** ; */ sSEMICOLON,
    /** -> */ sARROW_METHOD,
    

    //Identificadores
    /** Para identificadores de estructuras*/ idSTRUCT,
    /** Para identificadores de variables*/ idVAR,
    /** Para identificadores de métodos*/ idMETHOD,


    //Texto plano
    /** Espacio en blanco */ tBLANK_SPACE,
    /** \n */ tNEW_LINE,
    /** \r */ tRETURN,
    /** \t */ tTAB,
    /** \v */ tVERTICAL_TAB,
    

    //Palabras clave o reservadas
    /** Para palabra reservada STRUCT */ pSTRUCT,
    /** Para palabra reservada IMPL */ pIMPL,
    /** Para palabra reservada WHILE */ pWHILE,
    /** Para palabra reservada IF */ pIF,
    /** Para palabra reservada ELSE */ pELSE,
    /** Para palabra reservada FALSE */ pFALSE,
    /** Para palabra reservada TRUE */ pTRUE,
    /** Para palabra reservada NIL */ pNIL,
    /** Para palabra reservada NEW */ pNEW,
    /** Para palabra reservada FN */ pFN,
    /** Para palabra reservada ST */ pST,
    /** Para palabra reservada PRI */ pPRI,
    /** Para palabra reservada PUB */ pPUB,
    /** Para palabra reservada SELF */ pSELF,
    /** Para palabra reservada VOID */ pVOID,
    /** Para palabra reservada START */ pSTART,
    /** Para palabra reservada ARRAY */ pARRAY,
    /** Para palabra reservada RET */ pRET;
}
