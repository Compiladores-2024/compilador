struct A {}

impl A {
    .(){}

    fn m1 (Int p1) -> void {
        (IO.out_int(p1));
    }

    fn m2 () -> Int {
        ret 33;
    }

    fn m3 () -> Int {
        ret 1;
    }

    fn m4 (Int p1) -> Int {
        ret p1 * 2;
    }
}

start {
    A x;
    x = new A();
    (IO.out_int(x.m2() + (new A()).m3() + x.m4(1)));
}