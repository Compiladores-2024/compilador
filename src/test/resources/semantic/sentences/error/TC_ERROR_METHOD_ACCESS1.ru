/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 13 | COLUMNA 15 | El numero de argumentos actuales del metodo m1 no coincide con el numero de argumentos formales |
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
    fn m1()->Int{
        ret 1;
    }
}
start{
}
