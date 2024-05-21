/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 13 | COLUMNA 23 | Se esperaba una variable de tipo Int y se encontro una de tipo Str. |
struct A{

}

impl A{
    .(){
    }
    fn m1(Int num1)->B{
        B structB;
        structB = new B("1",true);
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
