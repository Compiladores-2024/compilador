
struct A{
}

impl A{
    .(){
        
    }
    fn m1() -> B{
        ret new B();
    }
}

struct B{

}
impl B{
    .(){}
    fn m2()->Int{
        ret 1;
    }
}

start{
    Int num;
    num = new A().m1().m2();
}