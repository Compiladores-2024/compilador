struct Mundo {
    pri Int a;
    Str b;
}
impl Mundo{
    .(){}
    fn get_a(Bool param1) -> Int { ret a; }
    st fn imprimo_algo(Int param1) -> void {}
}

struct Prueba{
    Mundo c;
}

impl Prueba{
    .(Str param1, Char param2){}
}

struct PruebaH : Prueba {
}

impl PruebaH {
    .(Array Str param2){}
}

start{
}