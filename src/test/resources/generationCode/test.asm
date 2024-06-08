.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 
	A_vtable: .word A_Constructor, A_m1, A_m2, A_m3, A_m4

#### PREDEFINED METHODS CODE ####
.text
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
addiu $v0, $fp, 4				#Assign the memory position of the variable
sw $v0, 0($sp)
addiu $sp, $sp, -4
#Assignation code - Right side
#Create instance code
li $v0, 9						#Reserve memory for the CIR
li $a0, 4
syscall							#$v0 contains address of allocated memory
la $t0, A_vtable
sw $t0, 0($v0)					#Saves the vtable reference
#Assignation code - Result
lw $t0, 4($sp)					#Get the left value
sw $v0, 0($t0)
addiu $sp, $sp, 4				#End Assignation
addiu $v0, $fp, 4				#Assign the memory position of the variable
#Method access code
lw $v0, 0($v0)					#Get the CIR reference
lw $t0, 16($v0)					#Get the method reference
li $v0, 1						#Assign constant int
.data							#Assign constant string
	literal_str_1: .asciiz ""
.text
la $v0, literal_str_1
jal A_m4						#Call method
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
addiu $sp, $sp, 20

A_m3:
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

A_m4:
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
addiu $sp, $sp, 24


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