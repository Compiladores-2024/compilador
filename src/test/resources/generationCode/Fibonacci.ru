struct Fibonacci {
    Int suma;
    Int i,j;
}
impl Fibonacci {
    fn sucesion_fib(Int n)-> Int{
        Int count;
        i=0; j=1; suma=0; count = 0;
        while (count <= n){
            (imprimo_numero(count));
            (imprimo_sucesion(i));
            suma = i + j;
            i = j;
            j = suma;
            count = count + 1;
        }
        ret suma;
    }
    .(){
        i=0; /? inicializo i
        j=0; /? inicializo j
        suma=0; /? inicializo suma
    }
    fn imprimo_numero(Int num) -> void{
        (IO.out_str("f_"));
        (IO.out_int(num));
        (IO.out_str("="));
    }
    fn imprimo_sucesion(Int s) -> void{
        /?"el valor es: ";
        (IO.out_int(s));
        (IO.out_str("\n"));
    }
}
start{
    Fibonacci fib;
    Int n;
    fib = new Fibonacci();
    n = IO.in_int();
    (fib.sucesion_fib(n));
}
