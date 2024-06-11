.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 
	#Main
	.text
	.globl main

main:
#### MAIN DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
sw $fp, -4($sp)					#RA caller. Idx: $fp + 4
sw $ra, -8($sp)					#Resume pointer. Idx: $fp + 8
sw $sp, -12($sp)				#Self. Idx: $fp + 12
sw $t0, -20($sp)					#Local variable str. Idx: $fp + 16 + (1 * 4)
sw $0, -16($sp)					#Local variable bool. Idx: $fp + 16 + (0 * 4)
sw $0, -28($sp)					#Local variable num. Idx: $fp + 16 + (3 * 4)
sw $t0, -24($sp)					#Local variable chr. Idx: $fp + 16 + (2 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -32				#Update sp
#### MAIN CODE ####
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 24($v0)					#Get the method reference
li $v0, 12345						#Assign constant int
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_int
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
li $v0, 'a'					#Assign constant char
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_char
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 32($v0)					#Get the method reference
.data							#Assign constant string
	literal_str_1: .asciiz "Hola mundo"
.text
la $v0, literal_str_1
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_str
#Assignation code - Left side
addiu $v0, $fp, -20				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 8($v0)					#Get the method reference
#Call method
jal IO_in_str
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 32($v0)					#Get the method reference
addiu $v0, $fp, -20				#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_str
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 40($v0)					#Get the method reference
li $v0, 1						#Assign True (1)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_bool
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 40($v0)					#Get the method reference
li $v0, 0						#Assign False or Nil (0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_bool
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 32($v0)					#Get the method reference
addiu $v0, $fp, -20				#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_str
#Assignation code - Left side
addiu $v0, $fp, -24				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
li $v0, 'b'					#Assign constant char
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
addiu $v0, $fp, -24				#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_char
#Assignation code - Left side
addiu $v0, $fp, -16				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 28($v0)					#Get the method reference
#Call method
jal IO_in_bool
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 40($v0)					#Get the method reference
addiu $v0, $fp, -16				#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_bool
#Assignation code - Left side
addiu $v0, $fp, -28				#Assign the memory position of the variable
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
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 24($v0)					#Get the method reference
addiu $v0, $fp, -28				#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_int
#Assignation code - Left side
addiu $v0, $fp, -24				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 36($v0)					#Get the method reference
#Call method
jal IO_in_char
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
addiu $v0, $fp, -24				#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_char
#Return code
j Exit


#### CUSTOM METHODS CODE ####

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
.data
	IO_false: .asciiz "false"
	IO_true: .asciiz "true"
	IO_newL: .asciiz ""
	IO_ingresar_int: .asciiz "Ingresar valor entero: "
	IO_ingresar_str: .asciiz "Ingresar valor str: "
	IO_ingresar_bool: .asciiz "Ingresar valor bool (0 para false, 1 para true: "
	IO_ingresar_char: .asciiz "Ingresar valor char: "
	IO_buffer_str: .space 1024
	Str_vtable: .word Str_Constructor, length, Str_concat
	ArrayStr_vtable: .word ArrayStr_Constructor, length
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

.text
	IO_out_int:

		lw $a0, 4($sp)
		li $v0, 1 #carga el valor 1 (print int) en el registro $v0
		syscall #syscall
		li $v0, 4
		la $a0, IO_newL
 		syscall
		addiu $sp, $sp, 4 # mueve el $sp
		jr $ra          # salta a la dirección almacenada en el registro $ra

	IO_out_bool:
		lw $a0, 4($sp)
 		beq $a0, $0,IO_out_false
 		j IO_out_true

	IO_out_true:
		li $v0, 4
		la $a0, IO_true
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4 # mueve el $sp (saca el parametro de la pila)
 		jr $ra
	IO_out_false:
		li $v0, 4
		la $a0, IO_false
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4 # mueve el $sp (saca el parametro de la pila)
 		jr $ra
	IO_out_str:
		lw $a0, 4($sp)
		li $v0, 4
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4  # mueve el $sp (saca el parametro de la pila)
 		#return
 		jr $ra


 	IO_out_char:
		lw $a0, 4($sp)
    		li $v0, 11 #carga el valor 11 (print char) en el registro $v0
		syscall #syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4  # mueve el $sp (saca el parametro de la pila)
 		jr $ra

 	IO_in_int:
 	 	li $v0, 4
		la $a0, IO_ingresar_int
 		syscall
 		li $v0, 5 #carga el valor 5 (read int) en el registro $v0
		syscall #syscall
 		jr $ra


 	 IO_in_bool:
 	  	li $v0, 4
		la $a0, IO_ingresar_bool
 		syscall
 		li $v0, 5 #carga el valor 5 (read int) en el registro $v0
 		li $a1, 4
		syscall #syscall
 		jr $ra


 	  IO_in_char:
 	 	li $v0, 4
		la $a0, IO_ingresar_char
 		syscall
 		li $v0, 12 #carga el valor 12 (read char) en el registro $v0
		syscall #syscall
 		jr $ra

 	IO_in_str:
 	 	li $v0, 4
		la $a0, IO_ingresar_str
 		syscall
		la $a0, IO_buffer_str
 		li $v0, 8 #carga el valor 8 (read str) en el registro $v0
 		li $a1, 1024  #especificar tamaño del argumento de entrada
		syscall #syscall
		move $v0, $a0
 		jr $ra

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
	Str_Constructor:
	IO_Constructor:
	Bool_Constructor:
	Char_Constructor:
	length:
	Object_Constructor:
	Int_Constructor:

