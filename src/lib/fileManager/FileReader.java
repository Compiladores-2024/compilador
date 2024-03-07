package src.lib.fileManager;

import java.io.BufferedReader;
import java.io.File;

public class FileReader {
    private BufferedReader bufferedReader;

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
                System.out.println("ERROR: No se ha podido leer la siguiente linea del archivo.");
            }
        }
        return line;
    }
}
