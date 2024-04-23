/? ERROR: SEMANTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 24 | COLUMNA 8 | METODO MAL REDEFINIDO. NO COINCIDEN LAS SIGNATURE |
/? si un metodo es sobreescrito toda su signature debe coincidir
struct A{

}
impl A{
    .(){

    }
    fn m1(Str a)->void{

    }
}

struct B:A{

}
impl B{
    .(){
        
    }
    fn m1(Int a)->void{

    }
}

start{

}