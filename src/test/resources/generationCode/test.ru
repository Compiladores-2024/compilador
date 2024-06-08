struct A {
    Int a;
    Str b;
}
impl A {
    .(Int a, Int b){}
}

start {
    A a;
    a = new A(1, 2);
}