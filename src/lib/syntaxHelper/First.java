package src.lib.syntaxHelper;

import java.util.ArrayList;

import src.lib.tokenHelper.IDToken;

public class First {
    public static final ArrayList<IDToken> firstProgram = new ArrayList<IDToken>(){{
        add(IDToken.idSTART);
        add(IDToken.pSTRUCT);
        add(IDToken.pIMPL);
    }};
    public static final ArrayList<IDToken> firstStart = new ArrayList<IDToken>(){{
        add(IDToken.idSTART);
        add(IDToken.pSTRUCT);
        add(IDToken.pIMPL);

    }};
    public static final ArrayList<IDToken> firstStruct=  new ArrayList<IDToken>(){{
        add(IDToken.pSTRUCT);
    }};
    public static final ArrayList<IDToken> firstStructP=  new ArrayList<IDToken>(){{
        add(IDToken.sCOLON);
        add(IDToken.sKEY_OPEN);
    }};
    public static final ArrayList<IDToken> firstImpl =  new ArrayList<IDToken>(){{
        add(IDToken.pIMPL);
    }}; 
    public static final ArrayList<IDToken> firstHerencia =  new ArrayList<IDToken>(){{
        add(IDToken.sCOLON);
    }}; 
    public static final ArrayList<IDToken> firstMiembro =  new ArrayList<IDToken>(){{
        add(IDToken.sDOT);
        add(IDToken.pFN);
        add(IDToken.pST);
    }}; 
    public static final ArrayList<IDToken> firstConstructor =  new ArrayList<IDToken>(){{
        add(IDToken.sDOT);
    }}; 
    public static final ArrayList<IDToken> firstAtributo =  new ArrayList<IDToken>(){{
        add(IDToken.pPRI);
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }}; 
    public static final ArrayList<IDToken> firstMetodo =  new ArrayList<IDToken>(){{
        add(IDToken.pFN);
        add(IDToken.pST);
    }}; 
    public static final ArrayList<IDToken> firstVisibilidad = new ArrayList<IDToken>(){{
        add(IDToken.pPRI);
    }}; 

    public static final ArrayList<IDToken> firstFormaMetodo = new ArrayList<IDToken>(){{
        add(IDToken.pST);
    }}; 
    public static final ArrayList<IDToken> firstBloqueMetodo = new ArrayList<IDToken>(){{
        add(IDToken.sKEY_OPEN);
    }};
    public static final ArrayList<IDToken> firstDeclVarLocales = new ArrayList<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    public static final ArrayList<IDToken> firstListaDeclaracionVariables = new ArrayList<IDToken>(){{
        add(IDToken.idOBJECT);
    }};
    public static final ArrayList<IDToken> firstArgumentosFormales = new ArrayList<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    public static final ArrayList<IDToken> firstListaArgumentosFormales = new ArrayList<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    public static final ArrayList<IDToken> firstArgumentoFormal = new ArrayList<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    public static final ArrayList<IDToken> firstTipoMétodo = new ArrayList<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
        add(IDToken.typeVOID);
    }};
    public static final ArrayList<IDToken> firstTipo = new ArrayList<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    public static final ArrayList<IDToken> firstTipoPrimitivo = new ArrayList<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
    }};
    public static final ArrayList<IDToken> firstTipoReferencia = new ArrayList<IDToken>(){{
        add(IDToken.idSTRUCT);
    }};
    public static final ArrayList<IDToken> firstTipoArreglo = new ArrayList<IDToken>(){{
        add(IDToken.typeARRAY);
    }};
    public static final ArrayList<IDToken> firstSentencia = new ArrayList<IDToken>(){{
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
    public static final ArrayList<IDToken> firstBloque = new ArrayList<IDToken>(){{
        add(IDToken.sKEY_OPEN);
    }};
    public static final ArrayList<IDToken>  firstAsignacion = new ArrayList<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.pSELF);
    }};
    public static final ArrayList<IDToken>  firstAccesoVarSimple = new ArrayList<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final ArrayList<IDToken> firstAccesoSelfSimple = new ArrayList<IDToken>(){{
        add(IDToken.pSELF);
    }};
    public static final ArrayList<IDToken> firstEncadenadoSimple = new ArrayList<IDToken>(){{
        add(IDToken.sDOT);
    }};
    public static final ArrayList<IDToken> firstSentenciaSimple = new ArrayList<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    public static final ArrayList<IDToken> firstExpresion = new ArrayList<IDToken>(){{
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
    public static final ArrayList<IDToken> firstExpOr = firstExpresion;
    public static final ArrayList<IDToken> firstExpAnd = firstExpresion;
    public static final ArrayList<IDToken> firstExpIgual = firstExpresion;
    public static final ArrayList<IDToken> firstExpCompuesta = firstExpresion;
    public static final ArrayList<IDToken> firstExpAd = firstExpresion;
    public static final ArrayList<IDToken> firstExpMul = firstExpresion;
    public static final ArrayList<IDToken> firstExpUn = firstExpresion;
    public static final ArrayList<IDToken> firstOpIgual = new ArrayList<IDToken>(){{
        add(IDToken.oEQUAL);
        add(IDToken.oNOT_EQ);
    }};
    public static final ArrayList<IDToken> firstOpCompuesto = new ArrayList<IDToken>(){{
        add(IDToken.oMIN);
        add(IDToken.oMAX);
        add(IDToken.oMIN_EQ);
        add(IDToken.oMAX_EQ);
    }};
    public static final ArrayList<IDToken> firstOpAd = new ArrayList<IDToken>(){{
        add(IDToken.oSUM);
        add(IDToken.oSUB);
    }};
    public static final ArrayList<IDToken> firstOpUnario = new ArrayList<IDToken>(){{
        add(IDToken.oSUM);
        add(IDToken.oSUB);            
        add(IDToken.oNOT);            
        add(IDToken.oSUM_SUM);
        add(IDToken.oSUB_SUB);
    }};
    public static final ArrayList<IDToken> firstOpMul = new ArrayList<IDToken>(){{
        add(IDToken.oMULT);
        add(IDToken.oDIV);
        add(IDToken.oMOD);
    }};
    public static final ArrayList<IDToken> firstOperando = new ArrayList<IDToken>(){{
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
    public static final ArrayList<IDToken> firstLiteral = new ArrayList<IDToken>(){{
        add(IDToken.pNIL);
        add(IDToken.pTRUE);
        add(IDToken.pFALSE);
        add(IDToken.constINT);
        add(IDToken.constSTR);
        add(IDToken.constCHAR);
    }};
    public static final ArrayList<IDToken> firstPrimario = new ArrayList<IDToken>(){{
        add(IDToken.sPAR_OPEN);
        add(IDToken.pSELF);
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.pNEW);
    }};
    public static final ArrayList<IDToken> firstExpresionParentizada = new ArrayList<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    public static final ArrayList<IDToken> firstAccesoSelf = new ArrayList<IDToken>(){{
        add(IDToken.pSELF);
    }};
    public static final ArrayList<IDToken> firstAccesoVar = new ArrayList<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final ArrayList<IDToken> firstLlamadaMetodo = new ArrayList<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final ArrayList<IDToken> firstLlamadaMetodoEstatico = new ArrayList<IDToken>(){{
        add(IDToken.idSTRUCT);
    }};
    public static final ArrayList<IDToken> firstLlamadaConstructor = new ArrayList<IDToken>(){{
        add(IDToken.pNEW);
    }};
    public static final ArrayList<IDToken> firstArgumentosActuales = new ArrayList<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    public static final ArrayList<IDToken> firstListaExpresiones = firstExpresion;
    public static final ArrayList<IDToken> firstEncadenado = new ArrayList<IDToken>(){{
        add(IDToken.sDOT);
    }};
    public static final ArrayList<IDToken> firstLlamadaMetodoEncadenado = new ArrayList<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final ArrayList<IDToken> firstAccesoVariableEncadenado = new ArrayList<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
    }};
    public static final ArrayList<IDToken> firstListaDefiniciones = new ArrayList<IDToken>(){{
        add(IDToken.pIMPL);
        add(IDToken.pSTRUCT);
    }};
    public static final ArrayList<IDToken> firstAtributoP = firstAtributo;
    public static final ArrayList<IDToken> firstDeclVarLocalesP = firstDeclVarLocales;
    public static final ArrayList<IDToken> firstSentenciaP = firstSentencia;
    public static final ArrayList<IDToken> firstEncadenadoSimpleP = firstEncadenadoSimple;
    public static final ArrayList<IDToken> firstHerenciaP = firstHerencia;
    public static final ArrayList<IDToken> firstVisibilidadP = firstVisibilidad;
    public static final ArrayList<IDToken> firstFormaMetodoP = firstFormaMetodo;
    public static final ArrayList<IDToken> firstExpresionP = firstExpresion;
    public static final ArrayList<IDToken> firstEncadenadoP = firstEncadenado;
    public static final ArrayList<IDToken> firstListaExpresionesP = firstListaExpresiones;
    public static final ArrayList<IDToken> firstListaArgumentosFormalesP = firstListaArgumentosFormales;
    public static final ArrayList<IDToken> firstMiembroP = firstMiembro;
    public static final ArrayList<IDToken> firstExpOrP = new ArrayList<IDToken>(){{
        add(IDToken.oOR);
    }};
    public static final ArrayList<IDToken> firstExpAndP = new ArrayList<IDToken>(){{
        add(IDToken.oAND);
    }};
    public static final ArrayList<IDToken> firstExpIgualP = firstOpIgual;
    public static final ArrayList<IDToken> firstExpAdP = firstOpAd;
    public static final ArrayList<IDToken> firstExpMulP = firstOpMul;
    

    /**
     * chequea si un idToken pasado como parametro pertenece a
     * un ArrayList de primeros de un no terminal firsts
     * @param firsts ArrayList de IDToken
     * @param idToken IDToken a comprobar
     * @return boolean
     */
    public static boolean check(ArrayList<IDToken> firsts, IDToken idToken){
        
        return firsts.contains(idToken);
    }
}
