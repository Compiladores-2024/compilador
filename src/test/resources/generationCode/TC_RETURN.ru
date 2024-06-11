/? Prueba el return

struct A {Int a1;}
impl A{
    fn m1()->Int
    {
        Int v1;
        v1=10;
        ret v1;
    }
    
    fn m2()->void
    {}
         
    .(){ }
}

start{
    A a;
    Int num;
    a = new A();
    num = a.m1();
    (IO.out_int(num));
}