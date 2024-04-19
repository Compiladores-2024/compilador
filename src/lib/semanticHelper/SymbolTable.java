package src.lib.semanticHelper;

import java.util.HashMap;

import src.lib.semanticHelper.symbolTableHelper.*;

/**
 * Esta clase se encarga de contener la estructura del código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class SymbolTable {
    Struct currentStruct;
    Method currentMethod;
    HashMap<String, Struct> structs;

}
