/* Generated By:JJTree: Do not edit this line. AstBeDictionary.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstBeDictionary extends AstBeNode
{
    public AstBeDictionary(int id)
    {
        super(id);
        this.tagName = "";
        this.allowedParameters = null;
    }

    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDictionary(String dict)
    {
        this.tagName = dict.substring(1, dict.length() -1)
    ;

    } public StringgetTagName(
    )
        { returntagName
    ;

    }@
    Override public StringgetParametersString(
    )
        { return ":" +getName( ) +super.getParametersString()
    ;

}
}