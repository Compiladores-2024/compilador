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
sw $0, 16($sp)					#Local variable bool. Idx: $fp + 16 + (0 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -4				#Update sp
#### MAIN CODE ####
#Assignation code - Left side
addiu $v0, $fp, 4				#Assign the memory position of the variable
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

#Conditional code
#Binary expression code - Left side
addiu $v0, $fp, 4				#Assign the memory position of the variable
lw $v0, 0($v0)					#Get the left value
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Binary expression - Right side
li $v0, 1						#Assign True (1)
#Binary expression - Result
lw $t0, 4($sp)
seq $v0, $t0, $v0				# $v0 = $t0 == $v0
addiu $sp, $sp, 4				#End Binary expression
bne $v0, 1, else1				#Conditional. $v0 != 1, jumps to else
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 24($v0)					#Get the method reference
li $v0, 100						#Assign constant int
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_int
j endIfElse1
else1:							#Else block
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 24($v0)					#Get the method reference
li $v0, 55						#Assign constant int
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_int
endIfElse1:						#End if-else


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