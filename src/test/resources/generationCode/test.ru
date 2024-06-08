struct A {
}
impl A {
    .(Int a){}
    fn m1 () -> void {}
}

start {
    A a;
    a = new A(1);
    (a.m1());
}