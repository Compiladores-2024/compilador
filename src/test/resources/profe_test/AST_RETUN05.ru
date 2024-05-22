/? Prueba el retorno de algo de tipo clase con conformidad

struct A {Int a1;}
impl A{
    fn m1(Int p1)->A
    {
        Int v1;
        ret nil;
    }
    
    fn m2()->void
    {}
         
    .(){ }
}


struct B : A{}
impl B{
    .(){
        
    }
}

start{}