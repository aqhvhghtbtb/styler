/* Generated By:JJTree: Do not edit this line. AstIf.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstBeIf extends AstBeCondition
{
    public AstBeIf(int id)
    {
        super(id);
        this.tagName = "if";
    } public boolean hasElse(
    )
        { returnchildren().anyMatch(AstBeElse.class::isInstance)
    ;
}
}