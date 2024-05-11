/? ERROR: SEMANTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 29 | COLUMNA 8 | Struct C no tiene constructor implementado |
/? todo struct en su impl debe tener un constructor

struct A{

}

impl A{
    .(){

    }
    fn m1()->void{

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
    fn m2()->void{

    }
}

start{
    
}