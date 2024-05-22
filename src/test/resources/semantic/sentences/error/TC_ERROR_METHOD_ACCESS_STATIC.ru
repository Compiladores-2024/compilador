/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 18 | COLUMNA 13 | Identificador m1 no válido. Método no existe en estructura A. |
struct A{
}

impl A{
    .(){
        
    }
    fn m1() -> Int{
        ret 0;
    }
}

start{
    Int num;
    num = A.m1();
    ret nil;
}