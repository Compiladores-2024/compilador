.data
	default_string: .asciiz ""

.text #Predefined methods code
	#Main
	.globl main

main:
#Start method data
la $t0, default_string

sw $0, 0($sp)					#Return. Idx: 0
sw $0, 4($sp)					#Local variable i. Idx: 4 + (4 * paramSize) + (0 * 4)
addi $sp, $sp, -8				#Update sp

#Start method code
addi $t0, $sp, 4				#Save the memory position
	addi $a0, $a0, 1  # Add immediate value +1 to $a0 (effectively increment )
#Exit
li $v0, 10
syscall