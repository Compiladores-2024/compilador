struct A{
    Int a;
    Bool b;
    Char c;
}

impl A{
    .(){

    }

}

struct B:A{
    Str s;
    Array Int arr;
}

impl B{
    .(){

    }
}

struct C:B{
    Array Int listaInt;
}
impl C{
    .(){
        
    }
}
start{

}