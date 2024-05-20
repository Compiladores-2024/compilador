struct A{
    B b;
    Int a;
    Str s;
    Array Int arrInt;
    Array Int arr;
    Bool boolean;
}

struct Start {}
impl Start {
    .(){}
}

impl A{
    .(){
        
    }
    fn m1() -> Int{
        ret 0;
    }
}
struct B{
    Int num;
}

impl B{
    .(Int a){}
    st fn m3(Bool boolean, Int num)->Str{
        ret "";
    }
    st fn m5(Bool boolean, Int num)->Bool{
        ret false;
    }
}
start{
    Int num1, a, s;
    B b;
    Array Int arr, arrInt;
    Bool boolean;


    (nil);
    ("false");
    b=new B((++num1));
    b.num=1;
    a=++1;
    arr=new Int[5];
    arr=new Int[b.num];
    arrInt[0]=0;
    a=1+ ++A.m1();
    s=B.m3(true,1).length();
    a=arrInt[0];
    a=a+num1;
    boolean = !(a+num1 ==10);

    if (B.m5(false, num1)) {
        a = 1;
    } else {

    }
    while (b == nil){

    }
    ret nil;
}