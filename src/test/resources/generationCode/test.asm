.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 

.text #Predefined methods code
	#Main
	.globl main

main:
#Start method data
move $fp, $sp
la $t0, default_string

sw $0, 0($sp)					#Local variable i. Idx: 4 + (4 * paramSize) + (0 * 4)
addi $sp, $sp, -4				#Update sp

#Start method code
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
j Exit
.text
	ErrorDiv0:
	li $v0, 4
	la $a0, division0
	syscall
	li $v0, 10
	syscall
.text
	Exit:
	li $v0, 10
	syscall
.include "utils.asm"