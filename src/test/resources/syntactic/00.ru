struct Master {
    pri Int a;
    Str b;
    Char c;
    Bool d;
}
impl Master {
    .() {}
    fn m1 () -> void {}
}

struct Prueba : Master {}
impl Prueba {
    .(){}
    fn m1 (Int a, Str b) -> void {}
    
    st fn m2 () -> Array Int {
        Int a, i, j;
        Str b;
        Char c;
        Bool d;
    }
}

start{
}