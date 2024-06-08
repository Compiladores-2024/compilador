.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 
	Str_vtable: .word Str_Constructor, length, Str_concat
	ArrayStr_vtable: .word ArrayStr_Constructor, length
	A_vtable: .word A_Constructor, m2
	A_vtable_static: .word A_m1
	A_struct_static: .word A_vtable_static
	ArrayInt_vtable: .word ArrayInt_Constructor, length
	ArrayChar_vtable: .word ArrayChar_Constructor, length
	Bool_vtable: .word Bool_Constructor
	IO_vtable: .word IO_Constructor
	IO_vtable_static: .word IO_out_array_int, IO_out_array_char, IO_in_str, IO_out_char, IO_out_array_str, IO_in_int, IO_out_int, IO_in_bool, IO_out_str, IO_in_char, IO_out_bool, IO_out_array_bool
	IO_struct_static: .word IO_vtable_static
	Char_vtable: .word Char_Constructor
	Object_vtable: .word Object_Constructor
	Int_vtable: .word Int_Constructor
	ArrayBool_vtable: .word ArrayBool_Constructor, length

#### PREDEFINED METHODS CODE ####
.text
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
	IO_out_array_int:
	IO_out_array_str:
	IO_out_array_bool:
	IO_out_array_char:
	ArrayStr_length:
	ArrayInt_length:
	ArrayChar_length:
	ArrayBool_length:
	Str_concat:
	Str_length:
	#Main
	.globl main

main:
#### MAIN DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
######################################
move $fp, $sp					#Set the new $fp.
#### MAIN CODE ####
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
#Call method
jal IO_in_str
#Return code
j Exit


#### CUSTOM METHODS CODE ####
A_m1:
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
lw $fp, 4($sp)					#RA caller. Idx: $fp + 4
lw $ra, 8($sp)					#Resume pointer. Idx: $fp + 8
lw $sp, 12($sp)					#Self. Idx: $fp + 12
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -16				#Update sp
#### METHOD CODE ####
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addiu $sp, $sp, 16

A_m2:
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
lw $fp, 4($sp)					#RA caller. Idx: $fp + 4
lw $ra, 8($sp)					#Resume pointer. Idx: $fp + 8
lw $sp, 12($sp)					#Self. Idx: $fp + 12
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -16				#Update sp
#### METHOD CODE ####
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addiu $sp, $sp, 16

A_Constructor:
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
lw $fp, 4($sp)					#RA caller. Idx: $fp + 4
lw $ra, 8($sp)					#Resume pointer. Idx: $fp + 8
lw $sp, 12($sp)					#Self. Idx: $fp + 12
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -16				#Update sp
#### METHOD CODE ####
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addiu $sp, $sp, 16


#### EXCEPTION AND END CODE ####
.text
ErrorDiv0:
	li $v0, 4
	la $a0, division0
	syscall
	li $v0, 10
	syscall
Exit:
	li $v0, 10
	syscall
.include "utils.asm"