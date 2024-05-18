/? ERROR: SEMANTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 15 | COLUMNA 9 | El retorno del metodo m1: literal Str, no coincide con su tipo de retorno declarado: Int |
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
        ret "s";
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
