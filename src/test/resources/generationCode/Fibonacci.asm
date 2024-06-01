
.data
Str_vtable: 
	.word Str_length
	.word Str_concat
ArrayStr_vtable: 
	.word ArrayStr_length
ArrayInt_vtable: 
	.word ArrayInt_length
ArrayChar_vtable: 
	.word ArrayChar_length
Bool_vtable: 
IO_vtable: 
	.word IO_out_array_int
	.word IO_out_array_char
	.word IO_in_str
	.word IO_out_char
	.word IO_out_array_str
	.word IO_in_int
	.word IO_out_int
	.word IO_in_bool
	.word IO_out_str
	.word IO_in_char
	.word IO_out_bool
	.word IO_out_array_bool
Char_vtable: 
Object_vtable: 
Fibonacci_vtable: 
	.word Fibonacci_imprimo_sucesion
	.word Fibonacci_imprimo_numero
	.word Fibonacci_sucesion_fib
Int_vtable: 
ArrayBool_vtable: 
	.word ArrayBool_length

.text #put things into the text segment...
IO_out_str:
move $fp, $sp #mueve el contenido de $sp a $fp
sw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)
addiu $sp, $sp, -4 #mueve el $sp 1 pos arriba
lw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)
li $v0, 4 #carga el valor 4 (print string) en el registro $v0
syscall #syscall
lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)
addiu $sp $sp 12 # mueve el $sp 
lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))
jr $ra          # salta a la dirección almacenada en el registro $ra
IO_out_int:
move $fp, $sp #mueve el contenido de $sp a $fp
sw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)
addiu $sp, $sp, -4 #mueve el $sp 1 pos arriba
lw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)
li $v0, 1 #carga el valor 1 (print int) en el registro $v0
syscall #syscall
lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)
addiu $sp $sp 12 # mueve el $sp 
lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))
jr $ra          # salta a la dirección almacenada en el registro $ra
IO_out_bool:
move $fp, $sp #mueve el contenido de $sp a $fp
sw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)
addiu $sp, $sp, -4 #mueve el $sp 1 pos arriba
lw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)
li $v0, 1 #carga el valor 1 (print int) en el registro $v0
syscall #syscall
lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)
addiu $sp $sp 12 # mueve el $sp 
lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))
jr $ra          # salta a la dirección almacenada en el registro $ra
IO_out_char:
move $fp, $sp #mueve el contenido de $sp a $fp
sw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)
addiu $sp, $sp, -4 #mueve el $sp 1 pos arriba
lw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)
li $v0, 11 #carga el valor 11 (print char) en el registro $v0
syscall #syscall
lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)
addiu $sp $sp 12 # mueve el $sp 
lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))
jr $ra          # salta a la dirección almacenada en el registro $ra
IO_in_str:
move $fp, $sp #mueve el contenido de $sp a $fp
sw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)
addiu $sp, $sp, -4 #mueve el $sp 1 pos arriba
li $v0, 8 #carga el valor 8 (read string) en el registro $v0
syscall #syscall
move $t1,$v0 #copies the value from register $v0 to register $t1
lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)
addiu $sp $sp 8 # mueve el $sp 
lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))
jr $ra          # salta a la dirección almacenada en el registro $ra
IO_in_int:
move $fp, $sp #mueve el contenido de $sp a $fp
sw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)
addiu $sp, $sp, -4 #mueve el $sp 1 pos arriba
li $v0, 5 #carga el valor 5 (read int) en el registro $v0
syscall #syscall
move $t1,$v0 #copies the value from register $v0 to register $t1
lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)
addiu $sp $sp 8 # mueve el $sp 
lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))
jr $ra          # salta a la dirección almacenada en el registro $ra
IO_in_bool:
move $fp, $sp #mueve el contenido de $sp a $fp
sw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)
addiu $sp, $sp, -4 #mueve el $sp 1 pos arriba
li $v0, 5 #carga el valor 5 (read int) en el registro $v0
syscall #syscall
move $t1,$v0 #copies the value from register $v0 to register $t1
lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)
addiu $sp $sp 8 # mueve el $sp 
lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))
jr $ra          # salta a la dirección almacenada en el registro $ra
IO_in_char:
move $fp, $sp #mueve el contenido de $sp a $fp
sw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)
addiu $sp, $sp, -4 #mueve el $sp 1 pos arriba
li $v0, 12 #carga el valor 12 (read char) en el registro $v0
syscall #syscall
move $t1,$v0 #copies the value from register $v0 to register $t1
lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)
addiu $sp $sp 8 # mueve el $sp 
lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))
jr $ra          # salta a la dirección almacenada en el registro $ra
	.globl main
main: 
li $v0, 10 #exitsyscall