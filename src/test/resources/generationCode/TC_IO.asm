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
.include "utils.asm"