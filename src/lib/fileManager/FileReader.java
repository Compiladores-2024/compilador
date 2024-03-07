package src.lib.fileManager;

import java.io.BufferedReader;
import java.io.File;

/**
 * Esta clase se utilizará para leer línea por línea el código fuente de los
 * archivos .ru
 * 
 * @author Cristian Serrano
 * @since 06/03/2024
 */
public class FileReader {
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
    public FileReader(String path){
        File file = new File(path);

        //Valida que el archivo exista y no sea un direcctorio
        if(file.exists() && file.isFile()){
            try {
                //Crea el bufferedReader
                bufferedReader = new BufferedReader(new java.io.FileReader(file));
            }
            catch (Exception e) {
                System.out.println("ERROR: No se ha podido crear el lector de archivo.");
            }
        }
        else {
            System.out.println("ERROR: El archivo no existe o es un directorio. Se busca en: " + file.getAbsolutePath());
        }
    }

    /**
     * Método que lee y retorna la siguiente línea del documento.
     * 
     * @since 06/03/2024
     * @return Siguiente línea del documento.
     */
    public String getLine() {
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
                System.out.println("ERROR: No se ha podido leer la siguiente linea del archivo.");
            }
        }
        return line;
    }
}
