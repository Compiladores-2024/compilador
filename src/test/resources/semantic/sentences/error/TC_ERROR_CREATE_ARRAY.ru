/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 12 | COLUMNA 9 | Se esperaba un tipo de retorno Array Int. Se encontró Array Str |
struct A{

}

impl A{
    .(){
    }
    fn m1(Int num1)->Array Int{
        ret new Str[10];
    }
}

start{
}
