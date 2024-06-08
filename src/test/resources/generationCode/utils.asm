
.data
	IO_false: .asciiz "false"
	IO_true: .asciiz "true"
	IO_newL: .asciiz "\n"
	IO_ingresar_int: .asciiz "Ingresar valor entero: \n"
	IO_ingresar_str: .asciiz "Ingresar valor str: \n"
	IO_ingresar_bool: .asciiz "Ingresar valor bool (0 para false, 1 para true: \n"
	IO_ingresar_char: .asciiz "Ingresar valor char: \n"
	IO_buffer_str: .space 1024	

.text 
	IO_out_int:

		lw $a0, 4($sp) 
		li $v0, 1 #carga el valor 1 (print int) en el registro $v0
		syscall #syscall
		li $v0, 4
		la $a0, IO_newL
 		syscall
		addiu $sp, $sp, 4 # mueve el $sp 
		jr $ra          # salta a la dirección almacenada en el registro $ra

	IO_out_bool:
		lw $a0, 4($sp) 
 		beq $a0, $0,IO_out_false
 		j IO_out_true
 		
	IO_out_true:
		li $v0, 4
		la $a0, IO_true
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4 # mueve el $sp (saca el parametro de la pila)  
 		jr $ra
	IO_out_false:
		li $v0, 4
		la $a0, IO_false
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4 # mueve el $sp (saca el parametro de la pila) 
 		jr $ra
	IO_out_str:
		lw $a0, 4($sp) 
		li $v0, 4
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4  # mueve el $sp (saca el parametro de la pila) 
 		#return
 		jr $ra
 		
 		
 	IO_out_char:
		lw $a0, 4($sp) 
    		li $v0, 11 #carga el valor 11 (print char) en el registro $v0
		syscall #syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4  # mueve el $sp (saca el parametro de la pila) 
 		jr $ra

 	IO_in_int:
 	 	li $v0, 4
		la $a0, IO_ingresar_int
 		syscall
 		li $v0, 5 #carga el valor 5 (read int) en el registro $v0
		syscall #syscall
 		jr $ra

 		
 	 IO_in_bool:
 	  	li $v0, 4
		la $a0, IO_ingresar_bool
 		syscall
 		li $v0, 5 #carga el valor 5 (read int) en el registro $v0
 		li $a1, 4
		syscall #syscall
 		jr $ra
 	 	
 	 	
 	  IO_in_char:
 	 	li $v0, 4
		la $a0, IO_ingresar_char
 		syscall
 		li $v0, 12 #carga el valor 12 (read char) en el registro $v0
		syscall #syscall
 		jr $ra
 		
 	IO_in_str:
 	 	li $v0, 4
		la $a0, IO_ingresar_str
 		syscall
		la $a0, IO_buffer_str
 		li $v0, 8 #carga el valor 8 (read str) en el registro $v0
 		li $a1, 1024  #especificar tamaño del argumento de entrada
		syscall #syscall
		move $v0, $a0
 		jr $ra

 	
