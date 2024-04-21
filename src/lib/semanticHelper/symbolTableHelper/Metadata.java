package src.lib.semanticHelper.symbolTableHelper;

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
     */
    public String getName () {
        return metadata.getLexema();
    }

    /**
     * Obtiene la posición del objeto correspondiente.
     * 
     * @since 19/04/2024
     */
    public int getPosition () {
        return position;
    }

    /**
     * @return Token con la metadata del struct
     */
    public Token getMetadata() {
        return metadata;
    }

    /**
     * Método a definir en cada subclase.
     * 
     * @since 19/04/2024
     */
    public abstract String toJSON (String tabs);
}
