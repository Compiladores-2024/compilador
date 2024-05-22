/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 10 | COLUMNA 20 | Se esperaba una variable de tipo B y se encontro una de tipo Int. |
struct A{
    B estructuraB;
}

impl A{
    .(){
        estructuraB= 10;
    }
}

struct B{

}

impl B{
    .(){}
}

start{
}
