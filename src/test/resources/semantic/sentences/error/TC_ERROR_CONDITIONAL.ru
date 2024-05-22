/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 13 | COLUMNA 9 | El tipo de la condicion if no es booleano |
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
    fn m1()->Int{
        ret 1;
    }
}
start{
}
