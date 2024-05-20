struct B{
}

impl B{
    .(){}
    st fn m3()->Str{
        ret "";
    }
}
start{
    Str s;
    s=B.m3().length();
}
