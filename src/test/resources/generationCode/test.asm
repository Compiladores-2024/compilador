.data
	default_string: .asciiz ""
	division0: .asciiz "ERROR: DIVISION POR CERO" 

#### PREDEFINED METHODS CODE ####
.text
	#Main
	.globl main

main:
#### MAIN DATA ####
la $t0, default_string			#For init strings
#### RA (params are in the stack) ####
######################################
move $fp, $sp					#Set the new $fp.
#### MAIN CODE ####
#Return code
j Exit


#### CUSTOM METHODS CODE ####

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