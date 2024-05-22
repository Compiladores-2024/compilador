/? ERROR: SEMANTICO - SENTENCIAS 
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 13 | COLUMNA 23 | Se esperaba una variable de tipo Int y se encontro una de tipo Bool. |
struct A{

}

impl A{
    .(){
    }
    fn m1(Int num1)->B{
        B structB;
        structB = new B(true, 10);
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
