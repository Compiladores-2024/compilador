# Errores lexicos

***Reporta identificadores mal formados, caracteres tabulares, símbolos inválidos, comentarios no cerrados***


### Casos de prueba
@ en cualquier parte (Excepto dentro de string)
Caracter con más de una letra
Abrir string y nunca cerrarlo
Salto de linea en string
Archivos grandes
Símbolos extraños (lambda, <, >, ñ, acentos, otros alfabetos)
Testear en varios SO (Terminaciones de linea)

Errores:
Operador mayor o menor y despues uno opuesto: <> o >< 
Identificador mal formado: 84hola
símbolos que no pertenecen al alfabeto de entrada (@, #, entre otros),

Operador mal formado ( &a, &6, |3, |m)

a@()

"hola"+"chau"

Numero invalido:  
6a
1000q
50a65

Caracter sin cerrar:  
‘aaaaa84  
 ‘aaa’ 
Caracter sin cerrar: ‘ 

Caracter invalido (no se permite '\n' o '\t' o '\0')

String o Cadena sin cerrar: 
“aaaaa
String invalido: “\0”   y cualquier String que contenga EOF 


TinyRust(Hello-pub} &&||


# Errores de sintaxis

Archivo vacío
Abrir y cerrar [] en vez de {}


Metodos heredados (Deben tener la misma firma)



LexicalAnalyzer () {
    Llamar al CheckToken con cada nuevo caracter y su sucesor

    Si es error, termina ejecucion
    Sino
        Si es fin de linea, retorna el Token
        Sino sigue leyendo
}

CheckToken ( lecturaActual = '', nextChar = '' ) {
    lecturaActual y lecturaActual + nextChar es invalido

    Validar lecturaActual y lecturaActual + nextChar
        
    Si lecturaActual + nextChar es un id válido: isEnd = false
    Sino
        Si lecturaActual es valida: Retorna true y datos
        Sino
            Sino: Retornar error y lo muestra por pantalla o escribe
}


Validador(string = 'struct', next = ' ') {

    //Si string esta compuesto de un solo caracter, se restablecen los datos

    //Si se espera string y next es null, nada es valido

    //Comparar string
    //Comparar string + next (Solo si no es null)

    //Si se espera string y next es ", deja de esperar string
    //Si se espera string, asigna el token y avisa que espera " en next

    //Retorna si string y string + next son validos
}

Comparador (string) {

    Retorna si es un identificador valido o no
}