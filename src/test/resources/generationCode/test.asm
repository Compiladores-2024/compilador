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
#Assignation code

#Simple sentence code
li $t0, 1						#Assign the value
addu $t1, $t0, $t1				#Sum $t0 + $t1
addi $t0, $sp, 4				#Save the memory position
sw $t1, 0($t0)					#Assignation


#Exit
li $v0, 10
syscall