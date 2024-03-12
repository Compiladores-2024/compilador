import os
import subprocess

# Define las rutas al archivo .jar, la carpeta 1 y la carpeta 2
jar_file_path = "compilador-2024.jar"
folder1_path = "resources/lexicalTests"
folder2_path = "resources/lexicalResults"

# Obtiene una lista de todos los archivos en la carpeta 1
folder1_files = [os.path.join(folder1_path, filename) for filename in os.listdir(folder1_path)]

# Crea la carpeta 2 si no existe
if not os.path.exists(folder2_path):
    os.makedirs(folder2_path)

# Itera sobre cada archivo en la carpeta 1
for file_path in folder1_files:
    # Obtiene el nombre base del archivo (sin extensión)
    base_filename = os.path.splitext(os.path.basename(file_path))[0]
    
    # Crea la ruta del archivo de salida .txt en la carpeta 2
    output_file_path = os.path.join(folder2_path, f"{base_filename}.txt")
    
    # Ejecuta el archivo .jar con la ruta del archivo de entrada y la ruta del archivo de salida como parámetros
    subprocess.run(["java", "-jar", "--enable-preview", jar_file_path, file_path, output_file_path])