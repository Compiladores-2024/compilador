

struct A{}
impl A{
    .(){     
    }
    
    fn m1(Int p1, Int p2)-> void{
        (IO.out_int(p1+p2));
    }
}

struct B{  
}
impl B{.(){}}


start{
    A x;
    x = new A();
    (x.m1(10,20));
}
    