.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 
	Fibonacci_vtable: .word Fibonacci_Constructor, Fibonacci_sucesion_fib, Fibonacci_imprimo_numero, Fibonacci_imprimo_sucesion
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
sw $0, -16($sp)					#Local variable fib. Idx: $fp + 16 + (0 * 4)
sw $0, -20($sp)					#Local variable n. Idx: $fp + 16 + (1 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -24				#Update sp
#### MAIN CODE ####
#Assignation code - Left side
addiu $v0, $fp, -16				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
#Create instance code
li $v0, 9						#Reserve memory for the CIR
li $a0, 16
syscall							#$v0 contains address of allocated memory
la $t0, Fibonacci_vtable		#Saves the vtable reference
sw $t0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call constructor
jal Fibonacci_Constructor
lw $v0, 4($sp)
addiu $sp, $sp, 4
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Assignation code - Left side
addiu $v0, $fp, -20				#Assign the memory position of the variable
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
addiu $v0, $fp, -16				#Assign the memory position of the variable
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 4($v0)					#Get the method reference
addiu $v0, $fp, -20				#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal Fibonacci_sucesion_fib
#Return code
j Exit


#### CUSTOM METHODS CODE ####
.data
	Fibonacci_attribute_suma: .word 0 
	Fibonacci_attribute_i: .word 0 
	Fibonacci_attribute_j: .word 0 
.text
Fibonacci_imprimo_sucesion:
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
sw $fp, -4($sp)					#RA caller. Idx: $fp + 4
sw $ra, -8($sp)					#Resume pointer. Idx: $fp + 8
sw $sp, -12($sp)				#Self. Idx: $fp + 12
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -16				#Update sp
#### METHOD CODE ####
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 24($v0)					#Get the method reference
addiu $v0, $fp, 4				#Assign the memory position of the variable
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
lw $ra, -8($fp)
lw $fp, -4($fp)
addiu $sp, $sp, 20
jr $ra


Fibonacci_Constructor:
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
sw $fp, -4($sp)					#RA caller. Idx: $fp + 4
sw $ra, -8($sp)					#Resume pointer. Idx: $fp + 8
sw $sp, -12($sp)				#Self. Idx: $fp + 12
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
lw $ra, -8($fp)
lw $fp, -4($fp)
addiu $sp, $sp, 16
jr $ra


Fibonacci_imprimo_numero:
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
sw $fp, -4($sp)					#RA caller. Idx: $fp + 4
sw $ra, -8($sp)					#Resume pointer. Idx: $fp + 8
sw $sp, -12($sp)				#Self. Idx: $fp + 12
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
addiu $v0, $fp, 4				#Assign the memory position of the variable
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
lw $ra, -8($fp)
lw $fp, -4($fp)
addiu $sp, $sp, 20
jr $ra


Fibonacci_sucesion_fib:
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
sw $fp, -4($sp)					#RA caller. Idx: $fp + 4
sw $ra, -8($sp)					#Resume pointer. Idx: $fp + 8
sw $sp, -12($sp)				#Self. Idx: $fp + 12
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
addiu $v0, $fp, 4				#Assign the memory position of the variable
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
la $v0, Fibonacci_vtable					#Get the VTable reference
lw $t0, 8($v0)					#Get the method reference
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal Fibonacci_imprimo_numero
#Method access code
la $v0, Fibonacci_vtable					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal Fibonacci_imprimo_sucesion
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
la $v0, Fibonacci_vtable					#Get the VTable reference
lw $t0, 8($v0)					#Get the method reference
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal Fibonacci_imprimo_numero
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
la $v0, Fibonacci_vtable					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal Fibonacci_imprimo_sucesion
j endIfElse2
else2:							#Else block
#Method access code
la $v0, Fibonacci_vtable					#Get the VTable reference
lw $t0, 8($v0)					#Get the method reference
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal Fibonacci_imprimo_numero
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
la $v0, Fibonacci_vtable					#Get the VTable reference
lw $t0, 12($v0)					#Get the method reference
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
lw $v0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal Fibonacci_imprimo_sucesion
endIfElse2:						#End if-else
j endIfElse1

endIfElse1:						#End if-else


#Unary expression
la $v0, Fibonacci_attribute_i			#Assign the memory position of the variable
lw $t0, 0($v0)					#Get the expression result
addiu $t0, $t0, 1				# +1
sw $t0, 0($v0)					#Save the new value
j while1						#Jump to init while
endWhile1:
#Return code
la $v0, Fibonacci_attribute_suma			#Assign the memory position of the variable
lw $ra, -8($fp)
lw $fp, -4($fp)
addiu $sp, $sp, 20
jr $ra



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