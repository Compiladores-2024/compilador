.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 
	A_vtable: .word A_Constructor, A_m1, A_m2

#### PREDEFINED METHODS CODE ####
.text
	#Main
	.globl main

main:
#### MAIN DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Local variable i. Idx: $fp + 16 + (0 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addi $sp, $sp, -4				#Update sp
#### MAIN CODE ####
#Assignation code - Left side
addi $v0, $fp, 4				#Assign the memory position of the variable
sw $v0, 0($sp)
addi $sp, $sp, -4
#Assignation code - Right side
#Binary expression code - Left side
li $v0, 1						#Assign constant
sw $v0, 0($sp)
addi $sp, $sp, -4
#Binary expression - Right side
#Unary expression
addi $v0, $fp, 4				#Assign the memory position of the variable
lw $t0, 0($v0)					#Get the expression result
addi $t0, $t0, 1				# +1
sw $t0, 0($v0)					#Save the new value
#Binary expression - Result
lw $v0, 0($v0)					#Get the right value
lw $t0, 4($sp)
addu $v0, $t0, $v0				# $v0 = $t0 + $v0
addi $sp, $sp, 4				#End Binary expression
lw $t0, 4($sp)
sw $v0, 0($t0)
addi $sp, $sp, 4				#End Assignation
#Return code
j Exit


#### CUSTOM METHODS CODE ####
A_m1:
.data
	A_attribute_a: .word 0 
.text
#### METHOD DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
sw $0, 0($sp)					#Return. Idx: $fp
lw $fp, 4($sp)					#RA caller. Idx: $fp + 4
lw $ra, 8($sp)					#Resume pointer. Idx: $fp + 8
lw $sp, 12($sp)					#Self. Idx: $fp + 12
sw $0, 0($sp)					#Local variable b. Idx: $fp + 16 + (0 * 4)
sw $0, 8($sp)					#Local variable i. Idx: $fp + 16 + (2 * 4)
sw $0, 4($sp)					#Local variable j. Idx: $fp + 16 + (1 * 4)
######################################
move $fp, $sp					#Set the new $fp.
addi $sp, $sp, -28				#Update sp
#### METHOD CODE ####
#Return code
li $v0, 0						#Assign constant
lw $ra, 8($fp)
lw $fp, 4($fp)
addi $sp, $sp, 28

A_m2:
.data
	A_attribute_a: .word 0 
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
addi $sp, $sp, -16				#Update sp
#### METHOD CODE ####
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addi $sp, $sp, 16

A_Constructor:
.data
	A_attribute_a: .word 0 
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
addi $sp, $sp, -16				#Update sp
#### METHOD CODE ####
#Return code
lw $ra, 8($fp)
lw $fp, 4($fp)
addi $sp, $sp, 16


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