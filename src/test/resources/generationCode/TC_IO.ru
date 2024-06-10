/? Prueba basica de ejecucion, imprime 12345 por pantalla


start{

    Bool bool;
    Str str;
    Char chr;
    Int num;
    (IO.out_int(12345));
    (IO.out_char('a'));
    (IO.out_str("Hola mundo"));
    str = IO.in_str();
    (IO.out_str(str));
    (IO.out_bool(true));
    (IO.out_bool(false));
    (IO.out_str(str));
    chr = 'b';
    (IO.out_char(chr));
    bool = IO.in_bool();
    (IO.out_bool(bool));
    num = (IO.in_int());
    (IO.out_int(num));
    chr = (IO.in_char());
    (IO.out_char(chr));


}