package src.lib.lexicalHelper;

import java.io.BufferedReader;
import java.io.File;

import src.lib.Const;

/**
 * Clase auxiliar de analizador léxico.<br/>
 * 
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
        String extension=getFileExtension(path);
        System.out.println(extension);
        if (extension.equals("ru")){
            
            File file = new File(path);
    
            //Valida que el archivo exista y no sea un direcctorio
            if(file.exists() && file.isFile()){
                try {
                    //Crea el bufferedReader
                    bufferedReader = new BufferedReader(new java.io.FileReader(file));
                }
                catch (Exception e) {
                    System.out.println(Const.ERROR_CREATE_FILE_READER);
                    System.exit(0);
                }
            }
            else {
                System.out.println(Const.ERROR_READ_FILE + file.getAbsolutePath());
                System.exit(0);
            }
        }
        else{
            System.out.println("ERROR: El archivo fuente es invalido, no tiene extension .ru");
            System.exit(0);
        }
    }

    /**
     * Método que lee y retorna la siguiente línea del documento.
     * 
     * @since 06/03/2024
     * @return Siguiente línea del documento.
     */
    public char[] getLine () {
        char[] r = null;
        String result = null;

        //Valida que se haya creado el bufferedReader
        if(bufferedReader != null){
            try {
                result = bufferedReader.readLine();
                //Cierra el reader si termina de leer
                if(result == null){
                    bufferedReader.close();
                }
            }
            catch (Exception e) {
                System.out.println(Const.ERROR_READ_NEXT_LINE);
            }
        }
        if (result != null) {
            r = result.toCharArray();
        }
        return r;
    }

    // Function to get the extension from a file path 
    public static String getFileExtension(String filePath) 
    { 
        int lastIndexOfDot = filePath.lastIndexOf('.'); 
        if (lastIndexOfDot == -1) { 
            return "No extension"; 
        } 
        
        return filePath.substring(lastIndexOfDot + 1); 
    } 

}
