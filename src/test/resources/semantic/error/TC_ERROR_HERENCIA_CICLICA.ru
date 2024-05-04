/? ERROR: SEMANTICO - DECLARACIONES 
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 21 | COLUMNA 8 | La estructura posee herencia cíclica. Estructura que genera el ciclo: A |

struct A:C{

}
impl A{
    .(){

    }
}
struct B:A{

}
impl B{
    .(){
        
    }
}
struct C:B{

}
impl C{
    .(){
        
    }
}

start{

}