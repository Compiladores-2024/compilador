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

#Conditional code
#Binary expression - Left side
addi $t0, $fp, 4				#Assign the memory position of the variable
sw $t0, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($sp)					#Get the variable value
lw $t0, 0($t0)
sw $t0, 4($sp)
#Binary expression - Right side
#Unary expression
addi $t0, $fp, 4				#Assign the memory position of the variable
sw $t0, 0($sp)
addi $sp, $sp, -4
lw $t1, 4($sp)					#Get the expression result
lw $t0, 0($t1)
addi $t0, $t0, 1				# +1
sw $t0, 0($t1)					#Save the new value
#Binary expression - Result
lw $t0, 8($sp)
lw $t1, 4($sp)
lw $t1, 4($sp)					#Get the variable value
lw $t1, 0($t1)
seq $t0, $t0, $t1				# ==
sw $t0, 8($sp)
addi $sp, $sp, 4
lw $t0, 4($sp)
addi $sp, $sp, 4

bne $t0, 1, else				#Then block. If $t0 != 1, jumps to else
#Assignation code
addi $t0, $fp, 4				#Assign the memory position of the variable
sw $t0, 0($sp)
addi $sp, $sp, -4
li $t0, 5						#Assign constant
sw $t0, 0($sp)
addi $sp, $sp, -4
lw $t0, 8($sp)
lw $t1, 4($sp)
sw $t1, 0($t0)
addi $sp, $sp, 8				#Assignation

j endIfElse
else:							#Else block
#Assignation code
addi $t0, $fp, 4				#Assign the memory position of the variable
sw $t0, 0($sp)
addi $sp, $sp, -4
li $t0, 6						#Assign constant
sw $t0, 0($sp)
addi $sp, $sp, -4
lw $t0, 8($sp)
lw $t1, 4($sp)
sw $t1, 0($t0)
addi $sp, $sp, 8				#Assignation

endIfElse:						#End if-else

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