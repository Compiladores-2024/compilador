/? ERROR: SEMANTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | LINEA 10 | COLUMNA 9 | Atributo 'a' ya declarado en un ancestro |
/? un atributo no puede tener el mismo nombre que un atributo heredado, sin importar visibilidad
struct A{
    Int a;
}

struct B:A{
    Str a;
}

start{
    
}