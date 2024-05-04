package src.lib.semanticHelper;

import java.util.Collection;
import java.util.HashMap;

import src.lib.tokenHelper.Token;

/**
 * Esta clase posee datos generales a guardar en todas las entidades de la
 * tabla de símbolos.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public abstract class Metadata {
    private Token metadata;
    private int position;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     * @param metadata Token que posee la metadata correspondiente.
     * @param position Posición del objeto en el código fuente.
     */
    protected Metadata (Token metadata, int position) {
        this.metadata = metadata;
        this.position = position;
    }

    /**
     * Obtiene el lexema del token asignado.
     * 
     * @since 19/04/2024
     * @return Nombre del objeto
     */
    public String getName () {
        return metadata.getLexema();
    }

    /**
     * Obtiene la posición del objeto correspondiente.
     * 
     * @since 19/04/2024
     * @return posición
     */
    public int getPosition () {
        return position;
    }

    /**
     * Setea la posición del objeto
     * @param position Nueva posición dentro de la entidad
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Obtiene la metadata del objeto
     * @return Token con la metadata del struct
     */
    public Token getMetadata() {
        return metadata;
    }

    /**
     * Ordena una estructura dada
     * @param entity Estructura a ordenar
     * @return Arreglo con elementos ordenados
     */
    @SuppressWarnings("unchecked")
    public String [] order (HashMap<String, ?> entity) {
        String[] result = new String[entity.size()];
        for (Metadata object : (Collection<Metadata>)entity.values()) {
            result[object.getPosition()] = object.getName();
        }
        return result;
    }

    
    /** 
     * Método que genera el JSON de una entidad dada.
     * 
     * @param entity Entidad a convertir en JSON
     * @param tabs Cantidad de tabs que se deben aplicar
     * @return Estructura en formato json
     */
    public String toJSONEntity (HashMap<String, ?> entity, String tabs) {
        int count = entity.size();
        String JSON = count > 0 ? "\n" : "";

        //Genera el json de params
        for (String name : order(entity)) {
            JSON += ((Metadata)entity.get(name)).toJSON(tabs + "        ") + (count > 1 ? "," : "") + "\n";
            count--;
        }

        return JSON;
    }

    /**
     * Método a definir en cada subclase.
     * 
     * @since 19/04/2024
     * @param tabs Cantidad de tabs que posee el objeto en formato JSON
     * @return String con estructura en formato JSON
     */
    public abstract String toJSON (String tabs);
}
