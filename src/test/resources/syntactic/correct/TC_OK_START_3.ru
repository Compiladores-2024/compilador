/? CORRECTO: ANALISIS SINTACTICO
start{
    /? Decl-Var-Locales 
    Str a;
    Bool a;
    Int a;
    Char a;
    A a;
    Array Str a;
    Array Bool a;
    Array Int a;
    Array Char a;


    Str a,b;
    Bool a,b;
    Int a,b;
    Char a,b;
    A a,b;
    Array Str a,b;
    Array Bool a,b;
    Array Int a,b;
    Array Char a,b;

    /? Sentencias
        /? ; 
        ;

        /? Asignacion 
            /?con expresion=literal

                /? con acceso-var-simple
                a=nil;
                a.b=true;
                a[1]=false;
                a[1]=1;
                a="a";
                a='a';
                a=self.a + b.c(1);

                /?con expresiones y literal
                a=1||1;
                a=1&&nil;
                a=1==a;
                a=true!=false;
                a=c<"a";
                b="mundo">=1;
                a=c<=48;
                b="mundo">'\a';
                a=a+1*5;
                a=a+1/5;
                a=a+1%5;
                a=!a;
                a=+a;
                a=-b;
                a=++c;
                a=--c;

                /?con expresiones y primario encadenado?
                    /? ⟨ExpresionParentizada⟩

                        a=(b==5);

                        /? con ⟨Acceso-Variable-Encadenado⟩
                        a=(b==5).a.a.a;
                        a=(b==5).a[1];

                        /? con ⟨Llamada-Metodo-Encadenado⟩ 
                        a=(b==5).a(a);
                        a=(b==5).a(a,b,bool,1,"str",'c',false,true,nil);

                    /? ⟨AccesoSelf ⟩
                        a=self.i;
                        a=self.a.a(11);

                    /? ⟨AccesoVar ⟩ 
                        a=a.a;
                        a=a[1];

                    /? ⟨Llamada-Metodo⟩
                        a= m1(1,true);

                    /? ⟨Llamada-Metodo-Estatico⟩
                        a=M.a(1);
                        a=M.a(1).m2(true);
                        a=M.a(1).a;

                    /? ⟨Llamada-Constructor⟩    
                        /? con idStruct
                        a=new A(b,1);
                        a=new A(b,1).a;

                        /? con tipoPRIMITIVO
                        a=new Bool [1];
                        a=new Str [1];
                        a=new Char [1];
                        a=new Int [1];
                /? con acceso-self-simple
                self.a=nil;
                self.a.b=true;
                self.a=false;
                self.a=1;
                self.a="a";
                self.a='a';
                /?con expresiones y literal
                self.a=1||1;
                self.a=1&&nil;
                self.a=1==a;
                self.a=true!=false;
                self.a=c<"a";
                self.b="mundo">=1;
                self.a=c<=48;
                self.b="mundo">'\a';
                self.a=a+1*5;
                self.a=a+1/5;
                self.a=a+1%5;
                self.a=!a;
                self.a=+a;
                self.a=-b;
                self.a=++c;
                self. a=--c;

                /?con expresiones y primario encadenado?
                    /? ⟨ExpresionParentizada⟩

                    self.a=(b==5);
                        
                        /? con ⟨Acceso-Variable-Encadenado⟩
                        self.a=(b==5).a.a.a;
                        self. a=(b==5).a[1];

                        /? con ⟨Llamada-Metodo-Encadenado⟩ 
                        self.a=(b==5).a(a);
                        self.a=(b==5).a(a,b,bool,1,"str",'c',false,true,nil);

                    /? ⟨AccesoSelf ⟩
                        self.a=self.i;
                        self.a=self.a.a(11);

                    /? ⟨AccesoVar ⟩ 
                        self.a=a.a;
                        self.a=a[1];

                    /? ⟨Llamada-Metodo⟩
                        self.a= m1(1,true);

                    /? ⟨Llamada-Metodo-Estatico⟩
                        self.a=M.a(1);
                        self.a=M.a(1).m2(true);
                        self.a=M.a(1).a;

                    /? ⟨Llamada-Constructor⟩    
                        /? con idStruct
                        self. a=new A(b,1);
                        self.a=new A(b,1).a;

                        /? con tipoPRIMITIVO
                        self.a=new Bool [1];
                        self.a=new Str [1];
                        self.a=new Char [1];
                        self.a=new Int [1];
                    



        /?⟨Sentencia-Simple⟩;
            (1);
            (IO.out("a"));
            (Object.in("a"));
            (A.b(a));
        
        /? if (⟨Expresion⟩) ⟨Sentencia⟩ y if (⟨⟨Expresion) ⟨Sentencia⟩ else ⟨Sentencia⟩
            /? con ;
            if(a==true);
            /? con asignacion
            if(a=='a') a=1;
            if(a=="a") a=1; else b=1;

            /? con sentencia simple
            if(a==false) (IO.out("a"));
            if(a==nil) (IO.out("a")); else (IO.out("pepe"));
            
            /? con while
            if(a==false) while (a||true);
            if(a==false) while (a||false) a=1;
            if(a==false) while (a||false) if(a=="a") a=1; else b=1;
            /? con bloque 
            if(a==1) {;}
            if(a==1) {if(a=='a') a=1;}

            /? con ret
            if(a=='a') ret;
            if(a=='a') ret a==1;

        /? while ( ⟨Expresion⟩ ) ⟨Sentencia⟩
            while (a||true);
            while (a||true){;}
            while (a||true){ while (a==5){;}}
        /? ⟨Bloque⟩
        {;}
        {while (a||true){ while (a==5){;}}}
        {if(a==false) 
            while (a||false) 
                if(a=="a") 
                    a=1; 
                else 
                    b=1;}
        /? ret ⟨Expresion⟩?; 
        ret;
        ret b;

}