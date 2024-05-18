struct A{
    Str str;
    Char chr;
    Bool booleano;
    B b;
}

impl A{
    .(){
    }
    fn m1()->Int{
        ret 1;
    }
    fn m2()->Int{
        Int num;
        ret num;
    }
    fn m3()->Str{
        ret "a";
    }
    fn m4()->Str{
        ret str;
    }

    fn m5()->Char{
        ret chr;
    }

    fn m6()->Char{
        ret 'C';
    }

    fn m7()->Bool{
        ret booleano;
    }
    fn m8()->Bool{
        ret true;
    }
    fn m9()->Bool{
        ret false;
    }
    
    fn m10()->B{
        ret b;
    }
    fn m11()->Bool{
        ret b.check;
    }
    fn m12()->void{
        ret;
    }

}

struct B{
    Bool check;
}

impl B{
    .(){
        
    }

}
start{
}
