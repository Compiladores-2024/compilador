

struct A{}
impl A{
    .(){     
    }
    
    fn m1(Int p1, Int p2)-> Int{
        Int mult;
        mult = p1 * p2;
        ret mult;
    }
}

struct B{  
}
impl B{.(){}}


start{
    A x;
    Int res;
    x = new A();
    res = x.m1(10,20);
    (IO.out_int(res));
}
    