
##Imprime un entero guardado en la posicion dememoria $t0
lw $t1, 0($t0)
move $a0, $t1   # Mover el valor de $t0 a $a0 (el argumento para imprimir)
li $v0, 1       # Cargar el código de servicio 1 (imprimir entero) en $v0
syscall         # Llamar al sistema para imprimir el valor