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

sw $0, 4($sp)					#Local variable a. Idx: 4 + (4 * paramSize) + (1 * 4)
sw $0, 0($sp)					#Local variable num23. Idx: 4 + (4 * paramSize) + (0 * 4)
addi $sp, $sp, -8				#Update sp

#Start method code
#Assignation code
addi $t0, $fp, 8				#Saves the memory position of the variable
sw $t0, 0($sp)
addi $sp, $sp, -4

#Create instance code
li $v0, 9						#Reserve memory in the heap for the CIR
li $a0, 8
syscall							#$v0 contains address of allocated memory
la $t0, A_vtable
sw $t0, 0($v0)
sw $0, 4($v0)
sw $v0, 0($sp)					#Saves the pointer in stack
addi $sp, $sp, -4

lw $t0, 8($sp)
lw $t1, 4($sp)
sw $t1, 0($t0)
addi $sp, $sp, 8				#Assignation

j Exit
.text
	A_m1:
.text
	A_Constructor:
#Assignation code
addi $t0, $fp, 12				#Saves the memory position of the variable
sw $t0, 0($sp)
addi $sp, $sp, -4


lw $t0, 8($sp)
lw $t1, 4($sp)
sw $t1, 0($t0)
addi $sp, $sp, 8				#Assignation

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