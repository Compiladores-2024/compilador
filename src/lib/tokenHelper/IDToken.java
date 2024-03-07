package src.lib.tokenHelper;

public enum IDToken {
    //Constructor de clase
    CONSTRUCTOR,
    //Comentario
    COMMENT, // /?
    //Operador de asignacion =
    ASSIGN,

    //Structuras predefinidas
    spOBJECT,
    spIO,
    
    //Tipos de datos
    typeINT,
    typeSTR,
    typeBOOL,
    typeCHAR,
    typeCLASS,
    
    
    //Para valores constantes
    constINT,
    constSTR,
    constBOOL,
    constCHAR,


    //Operadores aritmeticos
    oSUM, // +
    oSUB, // -
    oMULT, // *
    oSUM_SUM, // ++
    oSUB_SUB, // --
    oDIV, // /
    oMOD, // %


    //Operadores logicos
    oAND,
    oOR,
    oNOT,
    oMIN,
    oMIN_EQ,
    oEQUAL,
    oMAX,
    oMAX_EQ,
    oNOT_EQ,


    //Simbolos especiales
    sPAR_OPEN, // (
    sPAR_CLOSE, // )
    sCOR_OPEN, // [
    sCOR_CLOSE, // ]
    sKEY_OPEN, // {
    sKEY_CLOSE, // }
    sDOT, // .
    sCOM, // ,
    sCOLON, // :
    sSEMICOLON, // ;
    sARROW_METHOD, // ->
    

    //Identificadores
    idSTRUCT,
    idVAR,
    idMETHOD,


    //Texto plano
    tBLANK_SPACE,
    tNEW_LINE,
    tRETURN,
    tTAB,
    tVERTICAL_TAB,
    

    //Palabras clave o reservadas
    pSTRUCT,
    pIMPL,
    pWHILE,
    pIF,
    pELSE,
    pFALSE,
    pTRUE,
    pNIL,
    pNEW,
    pFN,
    pST,
    pPRI,
    pPUB,
    pSELF,
    pVOID,
    pSTART,
    pARRAY,
    pRET;
}
