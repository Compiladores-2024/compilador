struct A {
}
impl A {
    .(Int a){}
    fn m1 () -> void {}
    fn m2 () -> void {}
    fn m3 () -> void {}
    fn m4 (Int a, Str b) -> void {}
}

start {
    A a;
    a = new A(1);
    (a.m4(1, ""));
}