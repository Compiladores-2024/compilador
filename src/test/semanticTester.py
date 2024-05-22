import os
import subprocess

# Define las rutas al archivo .jar, fuentes y resultados
jar_file_path = "compilador-2024.jar"
sources_path = "resources/semantic/sentences/correct"

# Obtiene una lista de todos los archivos fuente
sources_folder = [os.path.join(sources_path, filename) for filename in os.listdir(sources_path)]

print ("Comenzando testing...")
i = 1

# Itera sobre cada archivo fuente
for file_path in sources_folder:
    print(file_path)
    print ("Test", i, "/", len(sources_folder), "completado.")
    i += 1
    
    # Ejecuta el archivo .jar
    subprocess.run(["java", "-jar", "--enable-preview", jar_file_path, file_path])
    print("\n")

print ("Testing completado.")