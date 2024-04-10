package src.lib.syntaxHelper;

import java.util.HashSet;

import src.lib.tokenHelper.IDToken;

public class First {
    public static final HashSet<IDToken> firstProgram = new HashSet<IDToken>(){{
        add(IDToken.idSTART);
        add(IDToken.pSTRUCT);
        add(IDToken.pIMPL);
    }};
    public static final HashSet<IDToken> firstStart = new HashSet<IDToken>(){{
        add(IDToken.idSTART);
        add(IDToken.pSTRUCT);
        add(IDToken.pIMPL);

    }};
    public static final HashSet<IDToken> firstStruct=  new HashSet<IDToken>(){{
        add(IDToken.pSTRUCT);
    }};
    public static final HashSet<IDToken> firstStructP=  new HashSet<IDToken>(){{
        add(IDToken.sCOLON);
        add(IDToken.sKEY_OPEN);
    }};
    public static final HashSet<IDToken> firstImpl =  new HashSet<IDToken>(){{
        add(IDToken.pIMPL);
    }}; 
    public static final HashSet<IDToken> firstHerencia =  new HashSet<IDToken>(){{
        add(IDToken.sCOLON);
    }}; 
    public static final HashSet<IDToken> firstMiembro =  new HashSet<IDToken>(){{
        add(IDToken.sDOT);
        add(IDToken.pFN);
        add(IDToken.pST);
    }}; 
    public static final HashSet<IDToken> firstConstructor =  new HashSet<IDToken>(){{
        add(IDToken.sDOT);
    }}; 
    public static final HashSet<IDToken> firstAtributo =  new HashSet<IDToken>(){{
        add(IDToken.pPRI);
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }}; 
    public static final HashSet<IDToken> firstMetodo =  new HashSet<IDToken>(){{
        add(IDToken.pFN);
        add(IDToken.pST);
    }}; 
    public static final HashSet<IDToken> firstVisibilidad = new HashSet<IDToken>(){{
        add(IDToken.pPRI);
    }}; 

    public static final HashSet<IDToken> firstFormaMetodo = new HashSet<IDToken>(){{
        add(IDToken.pST);
    }}; 
    public static final HashSet<IDToken> firstBloqueMetodo = new HashSet<IDToken>(){{
        add(IDToken.sKEY_OPEN);
    }};
    public static final HashSet<IDToken> firstDeclVarLocales = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    public static final HashSet<IDToken> firstListaDeclaracionVariables = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
    }};
    public static final HashSet<IDToken> firstArgumentosFormales = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    public static final HashSet<IDToken> firstListaArgumentosFormales = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    public static final HashSet<IDToken> firstArgumentoFormal = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    public static final HashSet<IDToken> firstTipoMetodo = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
        add(IDToken.typeVOID);
    }};
    public static final HashSet<IDToken> firstTipo = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    public static final HashSet<IDToken> firstTipoPrimitivo = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
    }};
    public static final HashSet<IDToken> firstTipoReferencia = new HashSet<IDToken>(){{
        add(IDToken.idSTRUCT);
    }};
    public static final HashSet<IDToken> firstTipoArreglo = new HashSet<IDToken>(){{
        add(IDToken.typeARRAY);
    }};
    public static final HashSet<IDToken> firstSentencia = new HashSet<IDToken>(){{
        add(IDToken.sSEMICOLON);
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.pSELF);
        add(IDToken.sPAR_OPEN);
        add(IDToken.pIF);
        add(IDToken.pWHILE);
        add(IDToken.sKEY_OPEN);
        add(IDToken.pRET);
    }};
    public static final HashSet<IDToken> firstMoreIF = new HashSet<IDToken>(){{
        add(IDToken.pELSE);
    }};
    public static final HashSet<IDToken> firstBloque = new HashSet<IDToken>(){{
        add(IDToken.sKEY_OPEN);
    }};
    public static final HashSet<IDToken>  firstAsignacion = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.pSELF);
    }};
    public static final HashSet<IDToken>  firstAccesoVarSimple = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final HashSet<IDToken> firstAccesoSelfSimple = new HashSet<IDToken>(){{
        add(IDToken.pSELF);
    }};
    public static final HashSet<IDToken> firstEncadenadoSimple = new HashSet<IDToken>(){{
        add(IDToken.sDOT);
    }};
    public static final HashSet<IDToken> firstSentenciaSimple = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    public static final HashSet<IDToken> firstExpresion = new HashSet<IDToken>(){{
        add(IDToken.oSUM);
        add(IDToken.oSUB);
        add(IDToken.oNOT);
        add(IDToken.oSUM_SUM);
        add(IDToken.oSUB_SUB);
        add(IDToken.pNIL);
        add(IDToken.pTRUE);
        add(IDToken.pFALSE);
        add(IDToken.constINT);
        add(IDToken.constSTR);
        add(IDToken.constCHAR);
        add(IDToken.sPAR_OPEN);
        add(IDToken.pSELF);
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.pNEW);
    }};
    public static final HashSet<IDToken> firstExpOr = firstExpresion;
    public static final HashSet<IDToken> firstExpAnd = firstExpresion;
    public static final HashSet<IDToken> firstExpIgual = firstExpresion;
    public static final HashSet<IDToken> firstExpCompuesta = firstExpresion;
    public static final HashSet<IDToken> firstExpAd = firstExpresion;
    public static final HashSet<IDToken> firstExpMul = firstExpresion;
    public static final HashSet<IDToken> firstExpUn = firstExpresion;
    public static final HashSet<IDToken> firstOpIgual = new HashSet<IDToken>(){{
        add(IDToken.oEQUAL);
        add(IDToken.oNOT_EQ);
    }};
    public static final HashSet<IDToken> firstOpCompuesto = new HashSet<IDToken>(){{
        add(IDToken.oMIN);
        add(IDToken.oMAX);
        add(IDToken.oMIN_EQ);
        add(IDToken.oMAX_EQ);
    }};
    public static final HashSet<IDToken> firstOpAd = new HashSet<IDToken>(){{
        add(IDToken.oSUM);
        add(IDToken.oSUB);
    }};
    public static final HashSet<IDToken> firstOpUnario = new HashSet<IDToken>(){{
        add(IDToken.oSUM);
        add(IDToken.oSUB);            
        add(IDToken.oNOT);            
        add(IDToken.oSUM_SUM);
        add(IDToken.oSUB_SUB);
    }};
    public static final HashSet<IDToken> firstOpMul = new HashSet<IDToken>(){{
        add(IDToken.oMULT);
        add(IDToken.oDIV);
        add(IDToken.oMOD);
    }};
    public static final HashSet<IDToken> firstOperando = new HashSet<IDToken>(){{
        add(IDToken.pNIL);
        add(IDToken.pTRUE);
        add(IDToken.pFALSE);
        add(IDToken.constINT);
        add(IDToken.constSTR);
        add(IDToken.constCHAR);
        add(IDToken.sPAR_OPEN);
        add(IDToken.pSELF);
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.pNEW);
    }};
    public static final HashSet<IDToken> firstLiteral = new HashSet<IDToken>(){{
        add(IDToken.pNIL);
        add(IDToken.pTRUE);
        add(IDToken.pFALSE);
        add(IDToken.constINT);
        add(IDToken.constSTR);
        add(IDToken.constCHAR);
    }};
    public static final HashSet<IDToken> firstPrimario = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
        add(IDToken.pSELF);
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.pNEW);
    }};
    public static final HashSet<IDToken> firstExpresionParentizada = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    public static final HashSet<IDToken> firstAccesoSelf = new HashSet<IDToken>(){{
        add(IDToken.pSELF);
    }};
    public static final HashSet<IDToken> firstAccesoVar = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final HashSet<IDToken> firstLlamadaMetodo = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final HashSet<IDToken> firstLlamadaMetodoEstatico = new HashSet<IDToken>(){{
        add(IDToken.idSTRUCT);
    }};
    public static final HashSet<IDToken> firstLlamadaConstructor = new HashSet<IDToken>(){{
        add(IDToken.pNEW);
    }};
    public static final HashSet<IDToken> firstArgumentosActuales = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    public static final HashSet<IDToken> firstListaExpresiones = firstExpresion;
    public static final HashSet<IDToken> firstEncadenado = new HashSet<IDToken>(){{
        add(IDToken.sDOT);
    }};
    public static final HashSet<IDToken> firstLlamadaMetodoEncadenado = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final HashSet<IDToken> firstAccesoVariableEncadenado = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final HashSet<IDToken> firstListaDefiniciones = new HashSet<IDToken>(){{
        add(IDToken.pIMPL);
        add(IDToken.pSTRUCT);
    }};
    public static final HashSet<IDToken> firstAtributoP = firstAtributo;
    public static final HashSet<IDToken> firstDeclVarLocalesP = firstDeclVarLocales;
    public static final HashSet<IDToken> firstSentenciaP = firstSentencia;
    public static final HashSet<IDToken> firstEncadenadoSimpleP = firstEncadenadoSimple;
    public static final HashSet<IDToken> firstHerenciaP = firstHerencia;
    public static final HashSet<IDToken> firstVisibilidadP = firstVisibilidad;
    public static final HashSet<IDToken> firstFormaMetodoP = firstFormaMetodo;
    public static final HashSet<IDToken> firstExpresionP = firstExpresion;
    public static final HashSet<IDToken> firstEncadenadoP = firstEncadenado;
    public static final HashSet<IDToken> firstListaExpresionesP = firstListaExpresiones;
    public static final HashSet<IDToken> firstListaArgumentosFormalesP = firstListaArgumentosFormales;
    public static final HashSet<IDToken> firstMiembroP = firstMiembro;
    public static final HashSet<IDToken> firstExpOrP = new HashSet<IDToken>(){{
        add(IDToken.oOR);
    }};
    public static final HashSet<IDToken> firstExpAndP = new HashSet<IDToken>(){{
        add(IDToken.oAND);
    }};
    public static final HashSet<IDToken> firstExpIgualP = firstOpIgual;
    public static final HashSet<IDToken> firstExpAdP = firstOpAd;
    public static final HashSet<IDToken> firstExpMulP = firstOpMul;
}