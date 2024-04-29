/? ERROR: SEMANTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 16 | COLUMNA 9 | Atributo 'a' ya declarado en un ancestro |
/? un atributo no puede tener el mismo nombre que un atributo heredado, sin importar visibilidad
struct A{
    pri Int a;
}

impl A{
    .(){

    }
}

struct B:A{
    Int a;
}


impl B{
    .(){
        
    }
}

start{
    
}