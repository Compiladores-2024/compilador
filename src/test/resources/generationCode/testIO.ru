/? Prueba basica de ejecucion, imprime 12345 por pantalla


start{

    Bool bool;
    Str str;
    Char chr;
    Int num;
    (IO.out_int(12345));
    (IO.out_char('a'));
    (IO.out_str("Hola mundo"));
    (IO.out_bool(true));
    (IO.out_bool(false));
    str = "a";
    (IO.out_str(str));
    chr = 'b';
    (IO.out_char(chr));
    bool = true;
    (IO.out_bool(bool));
    num = 44;
    (IO.out_int(num));
    (IO.out_str(str));

}