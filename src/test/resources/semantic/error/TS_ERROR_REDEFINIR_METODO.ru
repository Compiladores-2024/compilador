/?
/?
/?
/? si un metodo es sobreescrito toda su signature debe coincidir
struct A{

}
impl A{
    fn m1(Str a)->void{

    }
}

struct B:A{

}
impl B{
    fn m1(Int a)->void{

    }
}

start{

}