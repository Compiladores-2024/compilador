/? ERROR: SEMANTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 19 | COLUMNA 9 | El retorno del metodo m2: Str, no coincide con su tipo de retorno declarado: Int |
struct A{
    Str str;
    Char chr;
    Bool booleano;
    B b;
}

impl A{
    .(){
    }
    fn m1()->Int{
        ret 1;
    }
    fn m2()->Int{
        Str num;
        ret num;
    }

}

struct B{
    Bool check;
}

impl B{
    .(){
        
    }

}
start{
}
