/? ERROR: SEMANTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 24 | COLUMNA 11 | Método 'm1' ya declarado en un ancestro como static. No se puede sobreescribir métodos static |
/? si un metodo es sobreescrito toda su signature debe coincidir
struct A{

}
impl A{
    .(){

    }
    st fn m1(Str a)->void{

    }
}

struct B:A{

}
impl B{
    .(){
        
    }
    st fn m1(Str a)->void{
        Int q;
    }
}

start{

}