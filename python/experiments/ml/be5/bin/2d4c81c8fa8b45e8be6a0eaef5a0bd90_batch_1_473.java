/* Generated By:JJTree: Do not edit this line. AstBePlaceHolder.java Version 6.1 *//* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
packagecom
.developmentontheedge .sql.model;importjava.util.Objects;publicclassAstBePlaceHolderextends

SimpleNode{ publicAstBePlaceHolder (intid){super

( id ) ; setPlaceHolder
(
    "" );} StringplaceHolder
    ;
        publicvoidsetRawPlaceHolder(String
        ph){if(
    !

    ph .startsWith

    ( "@@" )&&! ph.
    endsWith
        ( "@@"))thrownewIllegalArgumentException("Invalid placeholder: " + ph);setPlaceHolder(ph.substring
            ( 2 ,ph. length ()-
        2));}publicvoidsetPlaceHolder (StringplaceHolder){ this .placeHolder=Objects
    .

    requireNonNull ( placeHolder); this.
    nodeContent
        ="@@"+ placeHolder +"@@";}publicStringgetPlaceHolder
        (){ return placeHolder ; } } /* JavaCC - OriginalChecksum=2743c10e0250bde1f7221a1be78ea500 (do not edit this line) */