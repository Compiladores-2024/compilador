package src.lib;

import java.io.BufferedReader;
import java.io.File;

/**
 * Esta clase se utilizará para leer línea por línea el código fuente de los
 * archivos .ru
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 06/03/2024
 */
public class FileManager {
    private BufferedReader bufferedReader;

    /**
     * Constructor de la clase. 
     * 
     * <br/>RECORDAR QUE EL PATH COMIENZA CON LA RUTA DONDE NOS ENCONTRAMOS EN LA
     * TERMINAL AL MOMENTO DE EJECUTAR EL PROGRAMA.
     * 
     * <br/>Por ejemplo: C:\\user\\code\\
     * 
     * @since 06/03/2024
     * @param path Path hacia el fichero que se desea importar como código fuente.
     */
    public FileManager(String path){
        File file = new File(path);

        //Valida que el archivo exista y no sea un direcctorio
        if(file.exists() && file.isFile()){
            try {
                //Crea el bufferedReader
                bufferedReader = new BufferedReader(new java.io.FileReader(file));
            }
            catch (Exception e) {
                System.out.println(Const.ERROR_CREATE_FILE_READER);
            }
        }
        else {
            System.out.println(Const.ERROR_READ_FILE + file.getAbsolutePath());
        }
    }

    /**
     * Método que lee y retorna la siguiente línea del documento.
     * 
     * @since 06/03/2024
     * @return Siguiente línea del documento.
     */
    public String getLine () {
        String line = null;
        //Valida que se haya creado el bufferedReader
        if(bufferedReader != null){
            try {
                line = bufferedReader.readLine();
                //Cierra el reader si termina de leer
                if(line == null){
                    bufferedReader.close();
                }
            }
            catch (Exception e) {
                System.out.println(Const.ERROR_READ_NEXT_LINE);
            }
        }
        return line;
    }
}
