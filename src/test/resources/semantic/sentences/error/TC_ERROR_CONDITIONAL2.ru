/? ERROR: SEMANTICO - DECLARACIONES 
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 18 | COLUMNA 15 | Metodo: m5 no declarado en struct: Int |
struct A{
    Int a;
    Bool boolean;
    B b;
}

impl A{
    .(){
        (nil);
        ("false");
        a=1;

    }
    fn m1(Int c)->void{
        if (c.m5()){                /? metodo m5 no definido en c tipo Int
            b.check=true;
            boolean = ++1 >=3;
        }else{
            if(true){

            }else{
                if(b.m2()){

                }
            }
        }
    }
}

struct B{
    Bool check;
}

impl B{
    .(){
        
    }
    fn m1()->Bool{
        ret true;
    }

    fn m2()->Bool{
        ret false;
    }

}
start{
}
