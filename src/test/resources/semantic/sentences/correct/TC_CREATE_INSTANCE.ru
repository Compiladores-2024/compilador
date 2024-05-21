/? 
struct A{

}

impl A{
    .(){
    }
    fn m1(Int num1)->B{
        B structB;
        structB = new B(1,true);
        ret structB;
    }
}

struct B{
    Int num;
    Bool condition;
}

impl B{
    .(Int a, Bool b){
        num=a;
        condition=b;
    }
}

start{
}
