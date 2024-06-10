.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 
	A_vtable: .word A_Constructor, A_m1
	B_vtable: .word B_Constructor
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
sw $0, -16($sp)					#Local variable x. Idx: $fp + 16 + (0 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -20				#Update sp
#### MAIN CODE ####
#Assignation code - Left side
addiu $v0, $fp, -16				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
#Create instance code
li $v0, 9						#Reserve memory for the CIR
li $a0, 4
syscall							#$v0 contains address of allocated memory
la $t0, A_vtable		#Saves the vtable reference
sw $t0, 0($v0)
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call constructor
jal A_Constructor
lw $v0, 4($sp)
addiu $sp, $sp, 4
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
addiu $v0, $fp, -16				#Assign the memory position of the variable
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 4($v0)					#Get the method reference
li $v0, 10						#Assign constant int
sw $v0, 0($sp)
addiu $sp, $sp, -4
li $v0, 20						#Assign constant int
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal A_m1
#Return code
j Exit


#### CUSTOM METHODS CODE ####
.data
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
la $v0, IO_struct_static		#Assign the memory position of the label
#Method access code
lw $v0, 0($v0)					#Get the VTable reference
lw $t0, 24($v0)					#Get the method reference
#Binary expression code - Left side
addiu $v0, $fp, 8				#Assign the memory position of the variable
lw $v0, 0($v0)					#Get the left value
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Binary expression - Right side
addiu $v0, $fp, 4				#Assign the memory position of the variable
#Binary expression - Result
lw $v0, 0($v0)					#Get the right value
lw $t0, 4($sp)
addu $v0, $t0, $v0				# $v0 = $t0 + $v0
addiu $sp, $sp, 4				#End Binary expression
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Call method
jal IO_out_int
#Return code
lw $ra, -8($fp)
lw $fp, -4($fp)
addiu $sp, $sp, 24
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
######################################
move $fp, $sp					#Set the new $fp.
addiu $sp, $sp, -16				#Update sp
#### METHOD CODE ####
#Return code
lw $ra, -8($fp)
lw $fp, -4($fp)
addiu $sp, $sp, 16
jr $ra


B_Constructor:
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