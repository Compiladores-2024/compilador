package src.lib.syntaxHelper;

import java.util.HashSet;

import src.lib.tokenHelper.IDToken;

/**
 * Clase First que contiene un set de tokens
 * por cada no terminal de la gramatica. 
 * Donde cada HashSet almacena los PRIMEROS de los mismos. 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 08/04/2024
 */
public class First {
    private First () {}
    /**
     * Primeros de Program 
     */
    public static final HashSet<IDToken> firstProgram = new HashSet<IDToken>(){{
        add(IDToken.idSTART);
        add(IDToken.pSTRUCT);
        add(IDToken.pIMPL);
    }};
    /**
     * Primeros de Start 
     */
    public static final HashSet<IDToken> firstStart = new HashSet<IDToken>(){{
        add(IDToken.idSTART);
        add(IDToken.pSTRUCT);
        add(IDToken.pIMPL);

    }};
    /**
     * Primeros de Struct 
     */
    public static final HashSet<IDToken> firstStruct=  new HashSet<IDToken>(){{
        add(IDToken.pSTRUCT);
    }};
    /**
     * Primeros de StructP 
     */
    public static final HashSet<IDToken> firstStructP=  new HashSet<IDToken>(){{
        add(IDToken.sCOLON);
        add(IDToken.sKEY_OPEN);
    }};
    /**
     * Primeros de Impl 
     */
    public static final HashSet<IDToken> firstImpl =  new HashSet<IDToken>(){{
        add(IDToken.pIMPL);
    }}; 
    /**
     * Primeros de Herencia 
     */
    public static final HashSet<IDToken> firstHerencia =  new HashSet<IDToken>(){{
        add(IDToken.sCOLON);
    }}; 
    /**
     * Primeros de Miembro 
     */
    public static final HashSet<IDToken> firstMiembro =  new HashSet<IDToken>(){{
        add(IDToken.sDOT);
        add(IDToken.pFN);
        add(IDToken.pST);
    }}; 
    /**
     * Primeros de Constructor 
     */
    public static final HashSet<IDToken> firstConstructor =  new HashSet<IDToken>(){{
        add(IDToken.sDOT);
    }}; 
    /**
     * Primeros de Atributo 
     */
    public static final HashSet<IDToken> firstAtributo =  new HashSet<IDToken>(){{
        add(IDToken.pPRI);
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }}; 
    /**
     * Primeros de Metodo 
     */
    public static final HashSet<IDToken> firstMetodo =  new HashSet<IDToken>(){{
        add(IDToken.pFN);
        add(IDToken.pST);
    }}; 
    /**
     * Primeros de Visibilidad 
     */
    public static final HashSet<IDToken> firstVisibilidad = new HashSet<IDToken>(){{
        add(IDToken.pPRI);
    }}; 

    /**
     * Primeros de FormaMetodo 
     */
    public static final HashSet<IDToken> firstFormaMetodo = new HashSet<IDToken>(){{
        add(IDToken.pST);
    }}; 
    /**
     * Primeros de BloqueMetodo 
     */
    public static final HashSet<IDToken> firstBloqueMetodo = new HashSet<IDToken>(){{
        add(IDToken.sKEY_OPEN);
    }};
    /**
     * Primeros de DeclVarLocales 
     */
    public static final HashSet<IDToken> firstDeclVarLocales = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    /**
     * Primeros de ListaDeclaracionVariables 
     */
    public static final HashSet<IDToken> firstListaDeclaracionVariables = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
    }};
    /**
     * Primeros de ArgumentosFormales 
     */
    public static final HashSet<IDToken> firstArgumentosFormales = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    /**
     * Primeros de ListaArgumentosFormales 
     */
    public static final HashSet<IDToken> firstListaArgumentosFormales = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    /**
     * Primeros de ArgumentoFormal 
     */
    public static final HashSet<IDToken> firstArgumentoFormal = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    /**
     * Primeros de TipoMetodo 
     */
    public static final HashSet<IDToken> firstTipoMetodo = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
        add(IDToken.typeVOID);
    }};
    /**
     * Primeros de Tipo 
     */
    public static final HashSet<IDToken> firstTipo = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
        add(IDToken.idSTRUCT);
        add(IDToken.typeARRAY);
    }};
    /**
     * Primeros de TipoPrimitivo 
     */
    public static final HashSet<IDToken> firstTipoPrimitivo = new HashSet<IDToken>(){{
        add(IDToken.typeSTR);
        add(IDToken.typeBOOL);
        add(IDToken.typeINT);
        add(IDToken.typeCHAR);
    }};
    /**
     * Primeros de TipoReferencia 
     */
    public static final HashSet<IDToken> firstTipoReferencia = new HashSet<IDToken>(){{
        add(IDToken.idSTRUCT);
    }};
    /**
     * Primeros de TipoArreglo 
     */
    public static final HashSet<IDToken> firstTipoArreglo = new HashSet<IDToken>(){{
        add(IDToken.typeARRAY);
    }};
    /**
     * Primeros de Sentencia 
     */
    public static final HashSet<IDToken> firstSentencia = new HashSet<IDToken>(){{
        add(IDToken.sSEMICOLON);
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
        add(IDToken.pSELF);
        add(IDToken.sPAR_OPEN);
        add(IDToken.pIF);
        add(IDToken.pWHILE);
        add(IDToken.sKEY_OPEN);
        add(IDToken.pRET);
    }};
    /**
     * Primeros de MoreIF 
     */
    public static final HashSet<IDToken> firstMoreIF = new HashSet<IDToken>(){{
        add(IDToken.pELSE);
    }};
    /**
     * Primeros de Bloque 
     */
    public static final HashSet<IDToken> firstBloque = new HashSet<IDToken>(){{
        add(IDToken.sKEY_OPEN);
    }};
    /**
     * Primeros de  Asignacion 
     */
    public static final HashSet<IDToken>  firstAsignacion = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
        add(IDToken.pSELF);
    }};
    /**
     * Primeros de  AccesoVarSimple 
     */
    public static final HashSet<IDToken>  firstAccesoVarSimple = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
    }};
    /**
     * Primeros de AccesoSelfSimple 
     */
    public static final HashSet<IDToken> firstAccesoSelfSimple = new HashSet<IDToken>(){{
        add(IDToken.pSELF);
    }};
    /**
     * Primeros de EncadenadoSimple 
     */
    public static final HashSet<IDToken> firstEncadenadoSimple = new HashSet<IDToken>(){{
        add(IDToken.sDOT);
    }};
    /**
     * Primeros de SentenciaSimple 
     */
    public static final HashSet<IDToken> firstSentenciaSimple = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    /**
     * Primeros de Expresion 
     */
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
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
        add(IDToken.pNEW);
    }};
    /**
     * Primeros de ExpOr 
     */
    public static final HashSet<IDToken> firstExpOr = firstExpresion;
    /**
     * Primeros de ExpAnd 
     */
    public static final HashSet<IDToken> firstExpAnd = firstExpresion;
    /**
     * Primeros de ExpIgual 
     */
    public static final HashSet<IDToken> firstExpIgual = firstExpresion;
    /**
     * Primeros de ExpCompuesta 
     */
    public static final HashSet<IDToken> firstExpCompuesta = firstExpresion;
    /**
     * Primeros de ExpAd 
     */
    public static final HashSet<IDToken> firstExpAd = firstExpresion;
    /**
     * Primeros de ExpMul 
     */
    public static final HashSet<IDToken> firstExpMul = firstExpresion;
    /**
     * Primeros de ExpUn 
     */
    public static final HashSet<IDToken> firstExpUn = firstExpresion;
    /**
     * Primeros de OpIgual 
     */
    public static final HashSet<IDToken> firstOpIgual = new HashSet<IDToken>(){{
        add(IDToken.oEQUAL);
        add(IDToken.oNOT_EQ);
    }};
    /**
     * Primeros de OpCompuesto 
     */
    public static final HashSet<IDToken> firstOpCompuesto = new HashSet<IDToken>(){{
        add(IDToken.oMIN);
        add(IDToken.oMAX);
        add(IDToken.oMIN_EQ);
        add(IDToken.oMAX_EQ);
    }};
    /**
     * Primeros de OpAd 
     */
    public static final HashSet<IDToken> firstOpAd = new HashSet<IDToken>(){{
        add(IDToken.oSUM);
        add(IDToken.oSUB);
    }};
    /**
     * Primeros de OpUnario 
     */
    public static final HashSet<IDToken> firstOpUnario = new HashSet<IDToken>(){{
        add(IDToken.oSUM);
        add(IDToken.oSUB);            
        add(IDToken.oNOT);            
        add(IDToken.oSUM_SUM);
        add(IDToken.oSUB_SUB);
    }};
    /**
     * Primeros de OpMul 
     */
    public static final HashSet<IDToken> firstOpMul = new HashSet<IDToken>(){{
        add(IDToken.oMULT);
        add(IDToken.oDIV);
        add(IDToken.oMOD);
    }};
    /**
     * Primeros de Operando 
     */
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
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
        add(IDToken.pNEW);
    }};
    /**
     * Primeros de Literal 
     */
    public static final HashSet<IDToken> firstLiteral = new HashSet<IDToken>(){{
        add(IDToken.pNIL);
        add(IDToken.pTRUE);
        add(IDToken.pFALSE);
        add(IDToken.constINT);
        add(IDToken.constSTR);
        add(IDToken.constCHAR);
    }};
    /**
     * Primeros de Primario 
     */
    public static final HashSet<IDToken> firstPrimario = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
        add(IDToken.pSELF);
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
        add(IDToken.pNEW);
    }};
    /**
     * Primeros de ExpresionParentizada 
     */
    public static final HashSet<IDToken> firstExpresionParentizada = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    /**
     * Primeros de AccesoSelf 
     */
    public static final HashSet<IDToken> firstAccesoSelf = new HashSet<IDToken>(){{
        add(IDToken.pSELF);
    }};
    /**
     * Primeros de AccesoVar 
     */
    public static final HashSet<IDToken> firstAccesoVar = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
    }};
    /**
     * Primeros de LlamadaMetodo 
     */
    public static final HashSet<IDToken> firstLlamadaMetodo = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
    }};
    /**
     * Primeros de LlamadaMetodoEstatico 
     */
    public static final HashSet<IDToken> firstLlamadaMetodoEstatico = new HashSet<IDToken>(){{
        add(IDToken.idSTRUCT);
    }};
    /**
     * Primeros de LlamadaConstructor 
     */
    public static final HashSet<IDToken> firstLlamadaConstructor = new HashSet<IDToken>(){{
        add(IDToken.pNEW);
    }};
    /**
     * Primeros de ArgumentosActuales 
     */
    public static final HashSet<IDToken> firstArgumentosActuales = new HashSet<IDToken>(){{
        add(IDToken.sPAR_OPEN);
    }};
    /**
     * Primeros de ListaExpresiones 
     */
    public static final HashSet<IDToken> firstListaExpresiones = firstExpresion;
    /**
     * Primeros de Encadenado 
     */
    public static final HashSet<IDToken> firstEncadenado = new HashSet<IDToken>(){{
        add(IDToken.sDOT);
    }};
    /**
     * Primeros de LlamadaMetodoEncadenado 
     */
    public static final HashSet<IDToken> firstLlamadaMetodoEncadenado = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
    }};
    /**
     * Primeros de AccesoVariableEncadenado 
     */
    public static final HashSet<IDToken> firstAccesoVariableEncadenado = new HashSet<IDToken>(){{
        add(IDToken.idOBJECT);
        add(IDToken.idSTRUCT);
        add(IDToken.spIO);
        add(IDToken.spOBJECT);
    }};
    /**
     * Primeros de ListaDefiniciones 
     */
    public static final HashSet<IDToken> firstListaDefiniciones = new HashSet<IDToken>(){{
        add(IDToken.pIMPL);
        add(IDToken.pSTRUCT);
    }};
    /**
     * Primeros de AtributoP 
     */
    public static final HashSet<IDToken> firstAtributoP = firstAtributo;
    /**
     * Primeros de DeclVarLocalesP 
     */
    public static final HashSet<IDToken> firstDeclVarLocalesP = firstDeclVarLocales;
    /**
     * Primeros de SentenciaP 
     */
    public static final HashSet<IDToken> firstSentenciaP = firstSentencia;
    /**
     * Primeros de EncadenadoSimpleP 
     */
    public static final HashSet<IDToken> firstEncadenadoSimpleP = firstEncadenadoSimple;
    /**
     * Primeros de HerenciaP 
     */
    public static final HashSet<IDToken> firstHerenciaP = firstHerencia;
    /**
     * Primeros de VisibilidadP 
     */
    public static final HashSet<IDToken> firstVisibilidadP = firstVisibilidad;
    /**
     * Primeros de FormaMetodoP 
     */
    public static final HashSet<IDToken> firstFormaMetodoP = firstFormaMetodo;
    /**
     * Primeros de ExpresionP 
     */
    public static final HashSet<IDToken> firstExpresionP = firstExpresion;
    /**
     * Primeros de EncadenadoP 
     */
    public static final HashSet<IDToken> firstEncadenadoP = firstEncadenado;
    /**
     * Primeros de ListaExpresionesP 
     */
    public static final HashSet<IDToken> firstListaExpresionesP = firstListaExpresiones;
    /**
     * Primeros de ListaArgumentosFormalesP 
     */
    public static final HashSet<IDToken> firstListaArgumentosFormalesP = firstListaArgumentosFormales;
    /**
     * Primeros de MiembroP 
     */
    public static final HashSet<IDToken> firstMiembroP = firstMiembro;
    /**
     * Primeros de ExpOrP 
     */
    public static final HashSet<IDToken> firstExpOrP = new HashSet<IDToken>(){{
        add(IDToken.oOR);
    }};
    /**
     * Primeros de ExpAndP 
     */
    public static final HashSet<IDToken> firstExpAndP = new HashSet<IDToken>(){{
        add(IDToken.oAND);
    }};
    /**
     * Primeros de ExpIgualP 
     */
    public static final HashSet<IDToken> firstExpIgualP = firstOpIgual;
    /**
     * Primeros de ExpAdP 
     */
    public static final HashSet<IDToken> firstExpAdP = firstOpAd;
    /**
     * Primeros de ExpMulP 
     */
    public static final HashSet<IDToken> firstExpMulP = firstOpMul;
}