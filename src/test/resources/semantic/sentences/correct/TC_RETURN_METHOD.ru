struct A{

}

impl A{
    .(){
    }
    fn m1(Int num1)->B{
        ret nil;
    }
    fn m2()->B{
        ret new B();
    }
    fn m3(Int num1)->Int{
        ret num1+5;
    }
    fn m4(Str str)->Str{
        ret str;
    }
    fn m5(Bool boolean)->Bool{
        ret boolean;
    }
    fn m6(Char char)->Char{
        ret char;
    }

}

struct B{

}

impl B{
    .(){}
}
start{
}
