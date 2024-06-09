.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 
	Str_vtable: .word Str_Constructor, length, Str_concat
	ArrayStr_vtable: .word ArrayStr_Constructor, length
	A_vtable: .word A_Constructor
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
	ArrayInt_Constructor:
		li $v0, 9			#Syscall para reservar memoria en el heap
		addi $a0, $a0, 8			#Add space para vtable y length
		syscall
		move $t1, $a0			#Moves dimention a $t1
		la $t0, ArrayInt_vtable
		sw $t0, 0($v0)			#Saves the vtable reference
		sw $t1, 4($v0)			#Saves the dimention en el cir
		jr $ra			# salta a la dirección almacenada en el registro $ra
	ArrayStr_Constructor:
		li $v0, 9			#Syscall para reservar memoria en el heap
		addi $a0, $a0, 8			#Add space para vtable y length
		syscall
		la $t0, ArrayStr_vtable
		sw $t0, 0($v0)			#Saves the vtable reference
		sw $t1, 4($v0)			#Saves the dimention en el cir
		jr $ra          # salta a la dirección almacenada en el registro $ra
	ArrayChar_Constructor:
		li $v0, 9			#Syscall para reservar memoria en el heap
		addi $a0, $a0, 8			#Add space para vtable y length
		syscall
		la $t0, ArrayChar_vtable
		sw $t0, 0($v0)			#Saves the vtable reference
		sw $t1, 4($v0)			#Saves the dimention en el cir
		jr $ra          # salta a la dirección almacenada en el registro $ra
	ArrayBool_Constructor:
		li $v0, 9			#Syscall para reservar memoria en el heap
		addi $a0, $a0, 8			#Add space para vtable y length
		syscall
		la $t0, ArrayBool_vtable
		sw $t0, 0($v0)			#Saves the vtable reference
		sw $t1, 4($v0)			#Saves the dimention en el cir
		jr $ra          # salta a la dirección almacenada en el registro $ra
	Array_length:
		
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
sw $0, 16($sp)					#Local variable a. Idx: $fp + 16 + (0 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -4				#Update sp
#### MAIN CODE ####
#Assignation code - Left side
addiu $v0, $fp, 16				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
#Create instance code
li $v0, 9						#Reserve memory for the CIR
li $a0, 12
syscall							#$v0 contains address of allocated memory
la $t0, A_vtable
sw $t0, 0($v0)					#Saves the vtable reference
#Call constructor
li $v0, 1						#Assign constant int
sw $v0, 0($sp)
addiu $sp, $sp, -4
li $v0, 2						#Assign constant int
sw $v0, 0($sp)
addiu $sp, $sp, -4
jal A_Constructor
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Return code
j Exit


#### CUSTOM METHODS CODE ####
A_Constructor:
.data
	A_attribute_i: .word 0 
	A_attribute_j: .word 0 
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
lw $fp, 4($sp)					#RA caller. Idx: $fp + 4
lw $ra, 8($sp)					#Resume pointer. Idx: $fp + 8
lw $sp, 12($sp)					#Self. Idx: $fp + 12
sw $0, 16($sp)					#Local variable c. Idx: $fp + 16 + (0 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -20				#Update sp
#### METHOD CODE ####
#Unary expression
la $v0, A_attribute_j			#Assign the memory position of the variable
lw $t0, 0($v0)					#Get the expression result
addiu $t0, $t0, 1				# +1
sw $t0, 0($v0)					#Save the new value
#Unary expression
addiu $v0, $fp, -8				#Assign the memory position of the variable
lw $t0, 0($v0)					#Get the expression result
addiu $t0, $t0, 1				# +1
sw $t0, 0($v0)					#Save the new value
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addiu $sp, $sp, 28


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