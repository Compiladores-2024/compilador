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

sw $t0, 4($sp)					#Local variable str. Idx: 4 + (4 * paramSize) + (1 * 4)
sw $0, 0($sp)					#Local variable bool. Idx: 4 + (4 * paramSize) + (0 * 4)
sw $t0, 8($sp)					#Local variable chr. Idx: 4 + (4 * paramSize) + (2 * 4)
addi $sp, $sp, -12				#Update sp

#Start method code
la $a0, 12345
jal IO_out_int
li $a0, 'a'
jal IO_out_char
.data
literal_str_1: .asciiz "Hola mundo"
.text
la $a0, literal_str_1
jal IO_out_str
li $a0, 1
jal IO_out_bool
li $a0, 0
jal IO_out_bool
#Assignation code - Left side
addi $v0, $fp, 8				#Assign the memory position of the variable
sw $v0, 0($sp)
addi $sp, $sp, -4
#Assignation code - Right side
.data
literal_str_2: .asciiz "a"
.text
la $v0, literal_str_2
lw $t0, 4($sp)
sw $v0, 0($t0)
addi $sp, $sp, 4				#End Assignation
#Assignation code - Left side
addi $v0, $fp, 12				#Assign the memory position of the variable
sw $v0, 0($sp)
addi $sp, $sp, -4
#Assignation code - Right side
li $v0, 'b'					#Assign constant char
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