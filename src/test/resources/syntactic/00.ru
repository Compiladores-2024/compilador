struct Mundo {
    pri Int a;
    Str b;
}
impl Mundo{
    .(){ a = 42;}
    fn get_a()-> Int { ret a; }
    st fn imprimo_algo()-> void {
        IO.out_str("hola mundo");
    }
}

struct Prueba{
    Mundo c;
}

impl Prueba{
    .(){
        c = new Mundo();
        y = c.b; // Acceso correcto al atributo de la clase
        z = c.a; // Acceso incorrecto al atributo de la clase
        c.imprimo_algo();
    }
}

start{
}