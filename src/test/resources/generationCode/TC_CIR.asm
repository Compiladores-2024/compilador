.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 
	A_vtable: .word A_Constructor, A_m1
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
sw $0, -20($sp)					#Local variable a. Idx: $fp + 16 + (1 * 4)
sw $0, -16($sp)					#Local variable num23. Idx: $fp + 16 + (0 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -24				#Update sp
#### MAIN CODE ####
#Assignation code - Left side
addiu $v0, $fp, -20				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
#Create instance code
li $v0, 9						#Reserve memory for the CIR
li $a0, 8
syscall							#$v0 contains address of allocated memory
la $t0, A_vtable		#Saves the vtable reference
sw $t0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call constructor
li $v0, 1						#Assign constant int
sw $v0, 0($sp)
addiu $sp, $sp, -4
li $v0, 2						#Assign constant int
sw $v0, 0($sp)
addiu $sp, $sp, -4
jal A_Constructor
lw $v0, 4($sp)
addiu $sp, $sp, 4
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
#Return code
j Exit


#### CUSTOM METHODS CODE ####
.data
	A_attribute_num: .word 0 
.text
A_m1:
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
#Return code
lw $ra, -8($fp)
lw $fp, -4($fp)
addiu $sp, $sp, 16
jr $ra


A_Constructor:
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
sw $fp, -4($sp)					#RA caller. Idx: $fp + 4
sw $ra, -8($sp)					#Resume pointer. Idx: $fp + 8
sw $sp, -12($sp)				#Self. Idx: $fp + 12
sw $0, -16($sp)					#Local variable j. Idx: $fp + 16 + (0 * 4)
sw $0, -20($sp)					#Local variable num2. Idx: $fp + 16 + (1 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -24				#Update sp
#### METHOD CODE ####
#Return code
lw $ra, -8($fp)
lw $fp, -4($fp)
addiu $sp, $sp, 32
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