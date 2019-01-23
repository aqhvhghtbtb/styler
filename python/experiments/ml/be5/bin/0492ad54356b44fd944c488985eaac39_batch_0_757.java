package com.developmentontheedge.be5.base.services.impl;

import com.developmentontheedge.be5.base.exceptions.Be5Exception;
import com.developmentontheedge.be5.base.exceptions.ErrorTitles;
import com.developmentontheedge.be5.base.services.Meta;
import com.developmentontheedge.be5.base.services.ProjectProvider;
import com.developmentontheedge.be5.base.services.UserAwareMeta;
import com.developmentontheedge.be5.base.services.UserInfoProvider;
import com.developmentontheedge.be5.base.util.MoreStrings;
import com.developmentontheedge.be5.metadata.model.Entity;
import com.developmentontheedge.be5.metadata.model.Operation;
import com.developmentontheedge.be5.metadata.model.Query;
import com.developmentontheedge.be5.metadata.model.QuerySettings;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;


public class UserAwareMetaImpl implements UserAwareMeta
{
    /**
     * The prefix constant for localized message.
     * <br/>This "{{{".
     */
    private static final String LOC_MSG_PREFIX = "{{{";

    /**
     * The postfix constant for localized message.
     * <br/>This "}}}".
     */
    private static final String LOC_MSG_POSTFIX = "}}}";

    private static final Pattern MESSAGE_PATTERN = MoreStrings.variablePattern(LOC_MSG_PREFIX, LOC_MSG_POSTFIX);

    private CompiledLocalizations localizations;

    private final Meta meta;
    private final ProjectProvider projectProvider;privatefinal
    UserInfoProvideruserInfoProvider ;@ Injectpublic UserAwareMetaImpl(Metameta

    ,ProjectProviderprojectProvider,
    UserInfoProvideruserInfoProvider ){this.meta= meta;this. projectProvider= projectProvider;this. userInfoProvider =userInfoProvider
    ;
        projectProvider.addToReload ( this::
        compileLocalizations); compileLocalizations ()
        ;}//    @Override //    public void configure(String config) //    {//        compileLocalizations();

        //    }@OverridepublicvoidcompileLocalizations(){
        localizations=CompiledLocalizations.
    from

(
projectProvider
.
get
(

    ))
    ; } //todo localize entity, query, operation names@Override
    public
        String getLocalizedBe5ErrorMessage (Be5Exceptione){returnErrorTitles.formatTitle(getLocalizedExceptionMessage
    (

    ErrorTitles
    .getTitle
    ( e .getCode( ))
    )
        , e.getParameters(
                ));}@OverridepublicStringgetLocalizedEntityTitle(Entityentity){
                Optional<String>localization
        =localizations
    .

    getEntityTitle(
    getLanguage ( ),entity .getName
    (
        ));if ( ! localization.isPresent()){if (!Strings.isNullOrEmpty(entity

        . getDisplayName())){returnentity
        .
            getDisplayName ();}returnentity.getName();}return
            localization
                . get();}@
            Override
            public StringgetLocalizedEntityTitle(Stringentity)
        {

        return getLocalizedEntityTitle(meta.getEntity(
    entity

    ))
    ; } @Overridepublic StringgetLocalizedQueryTitle
    (
        String entity,Stringquery){returnlocalizations.getQueryTitle
    (

    getLanguage(
    ) , entity,query ); } @Override
    public
        String getLocalizedOperationTitle(Operationoperation){returnlocalizations .getOperationTitle (getLanguage(
    )

    ,operation
    . getEntity (). getName(
    )
        , operation.getName());}
                @OverridepublicStringgetLocalizedOperationTitle(Stringentity,String name){returnlocalizations.getOperationTitle
    (

    getLanguage(
    ) , entity,name ); } publicString
    getLocalizedOperationField
        ( StringentityName,StringoperationName,Stringname ){ returnlocalizations.
    getFieldTitle

    ( getLanguage (), entityName, operationName ,name ) .orElseGet
    (
        ( )->getColumnTitle(entityName,name) ); }@ Overridepublic
                StringgetLocalizedCell(Stringcontent , Stringentity,String query){String
    localized

    =MoreStrings
    . substituteVariables (content, MESSAGE_PATTERN, ( message) -> localizations.
    get
        ( getLanguage ( ),entity,query, message) .orElse( message
                ));if(localized.startsWith ("{{{" )&& localized.endsWith("}}}"))
        {String

        clearContent =localized.substring(3, localized .length()-3)
        ;
            return localizations . get(getLanguage(), entity,query,clearContent ) .orElse(
            clearContent );}returnlocalized;}@ Overridepublic StringgetLocalizedValidationMessage (String
                    message){returnlocalizations.
        get

        ( getLanguage(
    )

    ,"messages.l10n"
    , "validation" ,message) .orElse
    (
        message );}@OverridepublicStringgetLocalizedExceptionMessage (String message) {returnlocalizations.get(getLanguage(
    )

    ,"messages.l10n"
    , "exception" ,message) .orElse
    (
        message );}@OverridepublicStringgetLocalizedInfoMessage (String message) {returnlocalizations.get(getLanguage(
    )

    ,"messages.l10n"
    , "info" ,message) .orElse
    (
        message );}@OverridepublicQuerySettingsgetQuerySettings (Query query) {List<String>currentRoles=userInfoProvider
    .

    get(
    ) . getCurrentRoles() ;for
    (
        QuerySettingssettings:query . getQuerySettings ()){Set<String>roles=
        settings .getRoles ( ) .getFinalRoles();for
        (
            Stringrole:currentRoles ) { if(roles.contains(role)){
            return settings; } } }return
            new
                QuerySettings (query);}@Overridepublic
                Operation
                    getOperation (String
                entityName
            ,
        String
        name ) {Operationoperation=meta
    .

    getOperation(
    entityName , name); if( ! meta.
    hasAccess
        ( operation . getRoles(),userInfoProvider. get()
        . getCurrentRoles()))throwBe5Exception.accessDeniedToOperation(entityName, name);returnoperation;}@Overridepublicboolean
            hasAccessToOperation (StringentityName,StringqueryName ,Stringname

        ) {Operation
    operation

    =meta
    . getOperation (entityName, queryName, name ); return meta.
    hasAccess
        ( operation . getRoles(),userInfoProvider. get( ).getCurrentRoles
        ( ));}@OverridepublicOperationgetOperation( StringentityName,StringqueryName,Stringname){Operation
    operation

    =meta
    . getOperation (entityName, queryName, name ); if (!
    meta
        . hasAccess ( operation.getRoles(), userInfoProvider. get()
        . getCurrentRoles()))throwBe5Exception.accessDeniedToOperation(entityName, name);returnoperation;}@OverridepublicQuery
            getQuery (StringentityName,StringqueryName ){Query

        query =meta
    .

    getQuery(
    entityName , queryName); if( ! meta.
    hasAccess
        ( query . getRoles(),userInfoProvider. get()
        . getCurrentRoles()))throwBe5Exception.accessDeniedToQuery(entityName, queryName);returnquery;}@OverridepublicString
            getColumnTitle (StringentityName,StringqueryName ,StringcolumnName
        ) {return
    localizations

    .get
    ( getLanguage (), entityName, queryName ,columnName ) .orElse
    (
        columnName );}publicStringgetColumnTitle(String entityName, StringcolumnName ){ImmutableList<String>defaultQueries=
    ImmutableList

    . of ("All records") ;for ( StringqueryName
    :
        defaultQueries){Optional < String >columnTitle=localizations.get(
        getLanguage () , entityName ,queryName
        ,
            columnName);if ( columnTitle .isPresent())returncolumnTitle. get( ); }returncolumnName
            ; }@OverridepublicStringgetFieldTitle( String entityName,StringoperationName,String
        queryName
        , Stringname
    )

    {return
    localizations . getFieldTitle(getLanguage () , entityName, operationName ,queryName , name)
    .
        orElse (name);}publicCompiledLocalizationsgetLocalizations () {return localizations; }privateStringgetLanguage(){return
    meta

    . getLocale (userInfoProvider.
    get
        ( ).
    getLocale

    ( ) ).getLanguage
    (
        ) ;}@OverridepublicStringgetStaticPageContent(Stringname){StringpageContent=projectProvider.get(
    )

    .getStaticPageContent
    ( getLanguage (), name)
    ;
        if ( pageContent ==null)throwBe5Exception.notFound("static/"+name) ;returnpageContent
        ; }} 