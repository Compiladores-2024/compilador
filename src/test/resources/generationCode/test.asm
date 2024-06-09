.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 
	Str_vtable: .word Str_Constructor, Str_length, Str_concat
	ArrayStr_vtable: .word ArrayStr_Constructor, ArrayStr_length
	ArrayInt_vtable: .word ArrayInt_Constructor, ArrayInt_length
	ArrayChar_vtable: .word ArrayChar_Constructor, ArrayChar_length
	Bool_vtable: .word Bool_Constructor
	IO_vtable: .word IO_Constructor
	IO_vtable_static: .word IO_out_array_int, IO_out_array_char, IO_in_str, IO_out_char, IO_out_array_str, IO_in_int, IO_out_int, IO_in_bool, IO_out_str, IO_in_char, IO_out_bool, IO_out_array_bool
	IO_struct_static: .word IO_vtable_static
	Char_vtable: .word Char_Constructor
	Object_vtable: .word Object_Constructor
	Fibonacci_vtable: .word Fibonacci_Constructor, Fibonacci_imprimo_sucesion, Fibonacci_start, Fibonacci_imprimo_numero, Fibonacci_sucesion_fib
	Int_vtable: .word Int_Constructor
	ArrayBool_vtable: .word ArrayBool_Constructor, ArrayBool_length

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
sw $0, 16($sp)					#Local variable fib. Idx: $fp + 16 + (0 * 4)
sw $0, 20($sp)					#Local variable n. Idx: $fp + 16 + (1 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -8				#Update sp
#### MAIN CODE ####
#Assignation code - Left side
addiu $v0, $fp, 16				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
#Create instance code
li $v0, 9						#Reserve memory for the CIR
li $a0, 16
syscall							#$v0 contains address of allocated memory
la $t0, Fibonacci_vtable
sw $t0, 0($v0)					#Saves the vtable reference
#Call constructor
jal Fibonacci_Constructor
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Assignation code - Left side
addiu $v0, $fp, 20				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 20($v0)					#Get the method reference
#Call method
jal IO_in_int
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
addiu $v0, $fp, 16				#Assign the memory position of the variable
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 4($v0)					#Get the method reference
addiu $v0, $fp, 20				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal Fibonacci_sucesion_fib
#Return code
j Exit


#### CUSTOM METHODS CODE ####
Fibonacci_imprimo_sucesion:
.data
	Fibonacci_attribute_suma: .word 0 
	Fibonacci_attribute_i: .word 0 
	Fibonacci_attribute_j: .word 0 
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
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 24($v0)					#Get the method reference
addiu $v0, $fp, -4				#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_int
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 32($v0)					#Get the method reference
.data							#Assign constant string
	literal_str_1: .asciiz "\n"
.text
la $v0, literal_str_1
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_str
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addiu $sp, $sp, 20

Fibonacci_Constructor:
.data
	Fibonacci_attribute_suma: .word 0 
	Fibonacci_attribute_i: .word 0 
	Fibonacci_attribute_j: .word 0 
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
#Assignation code - Left side
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
li $v0, 0						#Assign constant int
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Assignation code - Left side
la $v0, Fibonacci_attribute_j			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
li $v0, 0						#Assign constant int
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Assignation code - Left side
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
li $v0, 0						#Assign constant int
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addiu $sp, $sp, 16

Fibonacci_start:
.data
	Fibonacci_attribute_suma: .word 0 
	Fibonacci_attribute_i: .word 0 
	Fibonacci_attribute_j: .word 0 
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
######################################
move $fp, $sp					#Set the new $fp.
#### METHOD CODE ####
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addiu $sp, $sp, 0

Fibonacci_imprimo_numero:
.data
	Fibonacci_attribute_suma: .word 0 
	Fibonacci_attribute_i: .word 0 
	Fibonacci_attribute_j: .word 0 
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
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 32($v0)					#Get the method reference
.data							#Assign constant string
	literal_str_2: .asciiz "f_"
.text
la $v0, literal_str_2
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_str
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 24($v0)					#Get the method reference
addiu $v0, $fp, -4				#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_int
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 32($v0)					#Get the method reference
.data							#Assign constant string
	literal_str_3: .asciiz "="
.text
la $v0, literal_str_3
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_str
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addiu $sp, $sp, 20

Fibonacci_sucesion_fib:
.data
	Fibonacci_attribute_suma: .word 0 
	Fibonacci_attribute_i: .word 0 
	Fibonacci_attribute_j: .word 0 
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
#Assignation code - Left side
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
li $v0, 0						#Assign constant int
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Assignation code - Left side
la $v0, Fibonacci_attribute_j			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
li $v0, 0						#Assign constant int
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Assignation code - Left side
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
li $v0, 0						#Assign constant int
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Loop code
while1:
#Binary expression code - Left side
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
lw $v0, 0($v0)					#Get the left value
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Binary expression - Right side
addiu $v0, $fp, -4				#Assign the memory position of the variable
#Binary expression - Result
lw $v0, 0($v0)					#Get the right value
lw $t0, 4($sp)
sle $v0, $t0, $v0				# $v0 = $t0 <= $v0
addiu $sp, $sp, 4				#End Binary expression
bne $v0, 1, endWhile1				#Conditional: $v0 != 1, jumps to endWhile

#Conditional code
#Binary expression code - Left side
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
lw $v0, 0($v0)					#Get the left value
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Binary expression - Right side
li $v0, 0						#Assign constant int
#Binary expression - Result
lw $t0, 4($sp)
seq $v0, $t0, $v0				# $v0 = $t0 == $v0
addiu $sp, $sp, 4				#End Binary expression
bne $v0, 1, else1				#Conditional. $v0 != 1, jumps to else
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 8($v0)					#Get the method reference
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal _imprimo_numero
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal _imprimo_sucesion
j endIfElse1
else1:							#Else block

#Conditional code
#Binary expression code - Left side
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
lw $v0, 0($v0)					#Get the left value
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Binary expression - Right side
li $v0, 1						#Assign constant int
#Binary expression - Result
lw $t0, 4($sp)
seq $v0, $t0, $v0				# $v0 = $t0 == $v0
addiu $sp, $sp, 4				#End Binary expression
bne $v0, 1, else2				#Conditional. $v0 != 1, jumps to else
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 8($v0)					#Get the method reference
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal _imprimo_numero
#Assignation code - Left side
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
#Binary expression code - Left side
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
lw $v0, 0($v0)					#Get the left value
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Binary expression - Right side
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
#Binary expression - Result
lw $v0, 0($v0)					#Get the right value
lw $t0, 4($sp)
addu $v0, $t0, $v0				# $v0 = $t0 + $v0
addiu $sp, $sp, 4				#End Binary expression
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal _imprimo_sucesion
j endIfElse2
else2:							#Else block
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 8($v0)					#Get the method reference
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal _imprimo_numero
#Assignation code - Left side
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
#Binary expression code - Left side
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
lw $v0, 0($v0)					#Get the left value
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Binary expression - Right side
la $v0, Fibonacci_attribute_j			#Assign the memory position of the variable
#Binary expression - Result
lw $v0, 0($v0)					#Get the right value
lw $t0, 4($sp)
addu $v0, $t0, $v0				# $v0 = $t0 + $v0
addiu $sp, $sp, 4				#End Binary expression
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Assignation code - Left side
la $v0, Fibonacci_attribute_j			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
lw $v0, 0($v0)					#Get the right value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal _imprimo_sucesion
endIfElse2:						#End if-else
j endIfElse1

endIfElse1:						#End if-else


j while1						#Jump to init while
endWhile1:
#Unary expression
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
lw $t0, 0($v0)					#Get the expression result
addiu $t0, $t0, 1				# +1
sw $t0, 0($v0)					#Save the new value
#Return code
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
lw $ra, 8($fp)
lw $fp, 4($fp)
addiu $sp, $sp, 20


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