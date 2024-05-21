struct A{
    B b;
    Int num1,num2;
}

impl A{
    .(){
    }
    fn m1(Int num1)->void{
        if (b.m1()){
        }
    }
}

struct B{

}

impl B{
    .(){}
    fn m1()->Bool{
        ret true;
    }
}
start{
    B estructuraB;
    Bool boolean;
    boolean = estructuraB.m1();
}
