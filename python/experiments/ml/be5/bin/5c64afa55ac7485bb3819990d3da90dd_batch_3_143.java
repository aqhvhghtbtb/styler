/* Generated By:JJTree: Do not edit this line. AstBeDictionary.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstBeDictionary extends AstBeNode
{
    public AstBeDictionary(intid){super
    (id
        );this.tagName="";this.
        allowedParameters=null;}private Stringname ;publicStringgetName
        (){returnname; }public voidsetName(
   String

   name ) {this

   . name =name;
   }
       public voidsetDictionary
   (

   String dict ){this .tagName
   =
       dict.substring ( 1,
   dict

   . length ()- 1)
   ;
       }publicString getTagName (){returntagName; }@OverridepublicString getParametersString (){
   return

   ":" + getName()
   +
       super .getParametersString
   (

   );
   } } /* JavaCC - OriginalChecksum=e053a321771b5b90a9d756dedb2e0a0a (do not edit this line) */