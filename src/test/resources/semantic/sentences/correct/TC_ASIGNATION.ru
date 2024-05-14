struct A{
    B b;
    Int a;
    Str s;
    Array Int arrInt;
    Bool boolean;
}

impl A{
    .(){
        Int num1;
        (nil);
        ("false");
        b=new B((++num1));
        b.num=1;
        a=++1;
        arr=new Int[5];
        arrInt[0]=0;
        a=m1();
        s=B.m3(true,1);
        a=arrInt[0];
        a=a+num;
        boolean = a+num ==10;

    }
    fn m1()->Int{
        ret 10;
    }
}
struct B{
    Int num;
}

impl B{
    .(Int a){}
    fn m3(Bool boolean, Int num)->Str{
        ret "";
    }
}
start{
}
