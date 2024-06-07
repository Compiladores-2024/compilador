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
#Assignation code
addi $t0, $fp, 8				#Assign the memory position of the variable
sw $t0, 0($sp)
addi $sp, $sp, -4
.data
literal_str_2: .asciiz "a"
.text
la $t0, literal_str_2
sw $t0, 0($sp)
addi $sp, $sp, -4
lw $t0, 8($sp)
lw $t1, 4($sp)
sw $t1, 0($t0)					#Assignation
addi $sp, $sp, 8

#Assignation code
addi $t0, $fp, 12				#Assign the memory position of the variable
sw $t0, 0($sp)
addi $sp, $sp, -4
li $t0, 'b'					#Assign constant char
sw $t0, 0($sp)
addi $sp, $sp, -4
lw $t0, 8($sp)
lw $t1, 4($sp)
sw $t1, 0($t0)					#Assignation
addi $sp, $sp, 8

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