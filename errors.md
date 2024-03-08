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