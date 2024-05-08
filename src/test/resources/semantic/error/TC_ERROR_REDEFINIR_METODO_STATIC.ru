/? ERROR: SEMANTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 24 | COLUMNA 8 | Método 'm1' ya declarado en un ancestro. Verifique la signature. |
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
    fn m1(Int a)->void{

    }
}

start{

}