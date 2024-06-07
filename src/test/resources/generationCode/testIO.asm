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

sw $0, 0($sp)					#Local variable bool. Idx: 4 + (4 * paramSize) + (0 * 4)
addiu $sp, $sp, -4				#Update sp

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
li $a0, 0
jal IO_out_bool
li $a0, 1	
jal IO_out_bool
jal IO_in_int
lw $a0, 4($sp)
jal IO_out_int

jal IO_in_bool
lw $a0, 4($sp)
jal IO_out_bool

jal IO_in_char
lw $a0, 4($sp)
jal IO_out_char

jal IO_in_str
la $a0, 1024($sp)
jal IO_out_str



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
