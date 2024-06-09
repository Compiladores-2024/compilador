start{
    Bool bool;
    bool = (IO.in_bool());
    if (bool==true){
        (IO.out_int(100));
    }
    else{
        (IO.out_int(55));
    }
    (IO.out_bool(bool));

}