/? Se asigna un valor a una vars de inst mediante un metodo y luego se muestra con 
/? m1. Imprime 1234

struct A{
    Int a1;}
impl A{
    .(){ 
        
    }
    
    fn seta1(Int p1)->void{
        a1 = p1;
    }
    
    fn m1()->void{
        (IO.out_int(a1));
    }
}


start
    { 
        A x;
        x = new A();
        (x.seta1(1234));
        (x.m1());
    }