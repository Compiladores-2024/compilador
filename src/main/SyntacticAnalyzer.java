package src.main;

import src.lib.exceptionHelper.SyntacticException;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class SyntacticAnalyzer {
    
    LexicalAnalyzer lexA;
    Token currToken;

    public SyntacticAnalyzer(String path){

        lexA = new LexicalAnalyzer(path);
        currToken = lexA.nextToken();
        this.program();
        System.out.println("CORRECTO: ANALISIS SINTACTICO");


    }

    /**
    * funcion macheo
    * se encarga de machear el idToken del token actual 
    * con el idToken pasado por parametro
    * si son iguales retorna pide nextToken y retorna true
     * * @param idToken
     * @return boolean
     */

    private boolean match(IDToken idToken){
        
        if(currToken.getIDToken().equals(idToken)){
            this.currToken = this.lexA.nextToken();
            return true;
        }else return false;
    }



    /**
     * chequea el idToken del token actual con un idToken pasado por parametro. 
     * Sin consumir dicho token.
     * El idToken pasado por parametro pertenece a primeros 
     * de la funcion desde la que se llamo
     * * @param idToken
     * @return boolean
     */
    private boolean check(IDToken idToken){
        return (currToken.getIDToken().equals(idToken));  
    }

    /**
     * Primer no terminal de la gramatica
     * @throws SyntacticException 
     */
    private void program() throws SyntacticException {
        if (check(IDToken.idSTRUCT) || check(IDToken.pIMPL)){
            listaDefiniciones();
            start();
        }
        else{
            if (check(IDToken.idSTART)){
                start();
            }
            else{
                throw new SyntacticException();
            }
        }
    }
    /**
     * metodo start
     * @throws SyntacticException 
     */
    private void start() throws SyntacticException {
        if (match(IDToken.idSTART)){
            bloqueMetodo();
        }else{ 
            throw new SyntacticException(currToken,"se esperaba un token 'start'" + "se encontro: " + currToken.getIDToken().toString());
        }
    }

    private void listaDefiniciones() throws SyntacticException {

        if (check(IDToken.pIMPL)){
            impl();
        }
        else{
            if (check(IDToken.idSTRUCT)){
                struct();
            }
        }
    }

    private void listaDefiniciones() throws SyntacticException {
        if (match(IDToken.pSTRUCT)){
            if (match(IDToken.idSTRUCT)){
                structP();
            }
        }
        else{
            throw new SyntacticException();
        }
    }

    private void structP() throws SyntacticException{
        if (match(IDToken.sKEY_OPEN)){
            atributoP();
        }
    }
}
