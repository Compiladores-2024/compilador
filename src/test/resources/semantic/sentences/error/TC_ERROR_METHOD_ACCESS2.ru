/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 13 | COLUMNA 15 | Se esperaba una variable de tipo Str y se encontro una de tipo Int. |
struct A{
    B b;
    Int num1,num2;
}

impl A{
    .(){
    }
    fn m1(Int num1)->void{
        if (b.m1(num1)){
        }
    }
}

struct B{

}

impl B{
    .(){}
    fn m1(Str a)->Str{
        ret a;
    }
}
start{
}
