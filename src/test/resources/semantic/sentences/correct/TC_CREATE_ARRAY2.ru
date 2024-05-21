/? 
struct A{
    Array Str arrayStr;
    Array Bool arrayBool;
    Array Char arrayChar;
}

impl A{
    .(){
    }
    fn m1(Int num1)->Array Int{
        Array Int arrayInt;
        Int size;
        arrayInt = new Int[5];
        size = arrayInt.length();
        arrayBool = new Bool[m2()];
        arrayChar = new Char[m2()+1];
        arrayStr = new Str[++m2()];
        ret arrayInt;
    }

    fn m2()->Int{
        ret 10;
    }
}

start{
}
