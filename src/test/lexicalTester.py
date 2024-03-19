import os
import subprocess

# Define las rutas al archivo .jar, fuentes y resultados
jar_file_path = "etapa1.jar"
sources_path = "resources/lexical/tests"
results_path = "resources/lexical/results"

# Avisa si desea mostrar los resultados por consola o no
show = False

# Obtiene una lista de todos los archivos fuente
sources_folder = [os.path.join(sources_path, filename) for filename in os.listdir(sources_path)]

# Crea la carpeta de resultados si no existe
if not os.path.exists(results_path):
    os.makedirs(results_path)

print ("Comenzando testing...")
i = 1

# Itera sobre cada archivo fuente
for file_path in sources_folder:
    if not show:
        print ("Test", i, "/", len(sources_folder), "completado.")
        i += 1

    # Obtiene el nombre base del archivo (sin extensión)
    base_filename = os.path.splitext(os.path.basename(file_path))[0]
    
    # Crea la ruta del archivo de salida .txt en la resultado
    output_file_path = os.path.join(results_path, f"{base_filename}.txt")
    
    # Ejecuta el archivo .jar para que escriba en el archivo
    subprocess.run(["java", "-jar", "--enable-preview", jar_file_path, file_path, output_file_path])
    # Ejecuta el archivo .jar para que muestre por consola
    if show:
        subprocess.run(["java", "-jar", "--enable-preview", jar_file_path, file_path])

print ("Testing completado.")