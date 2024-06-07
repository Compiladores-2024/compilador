.data 	
	IO_false: .asciiz "false"
	IO_true: .asciiz "true"
	IO_newL: .asciiz "\n"
.text 
	.globl _main	
.text
	_main: 		


.text 
	IO_out_int:
		li $v0, 1
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		#return
 		jr $ra
 		
	IO_out_str:
		li $v0, 4
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		#return
 		jr $ra
 		
 	IO_out_bool:
 		beq $a0, $0,IO_out_false
 		j IO_out_true
	IO_out_true:
		li $v0, 4
		la $a0, IO_true
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		jr $ra
	IO_out_false:
		li $v0, 4
		la $a0, IO_false
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		jr $ra
 		
	IO_out_char:
    	li $v0, 11 #carga el valor 11 (print char) en el registro $v0
		syscall #syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		jr $ra