struct Master {}
impl Master {
    .() {}
    fn m1 () -> void {}
}

struct Prueba : Master {}
impl Prueba {
    .(){}
    fn m1 () -> void {}
    st fn m1 () -> Array Int {}
}

start{
}