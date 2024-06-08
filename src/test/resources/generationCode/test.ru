struct A {
}
impl A {
    .(){}
    st fn m1 (Int a, Int b, Str c) -> void {}
    fn m2 () -> void {}
}

start {
    (A.m1(1, 2, "hola"));
}