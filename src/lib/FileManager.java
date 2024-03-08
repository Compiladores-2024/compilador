package src.lib;

import java.io.BufferedReader;
import java.io.File;

public class FileManager {
    private BufferedReader bufferedReader;

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
