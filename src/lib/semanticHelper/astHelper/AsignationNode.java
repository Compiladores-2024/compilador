package src.lib.semanticHelper.astHelper;

import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de generar nodos de asignación
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 04/05/2024
 */
public class AsignationNode extends SentenceNode{
    private ExpressionNode leftSide;    
    private ExpressionNode rightSide;


    public AsignationNode(Token token, int position, ExpressionNode left, ExpressionNode right){
        super(token, position);
        this.leftSide=left;
        this.rightSide=right;
    }

    /**
     * Reescritura del método, convierte los datos en JSON.
     * 
     * @since 04/05/2024
     * @return Estructura de datos en formato JSON
     */
    @Override
    public String toJSON(String tabs) {
        return "";
    }
}
