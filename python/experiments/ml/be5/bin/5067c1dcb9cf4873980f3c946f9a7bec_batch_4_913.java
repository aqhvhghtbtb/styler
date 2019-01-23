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

    private static final Pattern MESSAGE_PATTERN = MoreStrings.variablePattern(LOC_MSG_PREFIX,

    LOC_MSG_POSTFIX ) ;private

    CompiledLocalizations localizations ; privatefinal
    Meta meta ; privatefinal
    ProjectProvider projectProvider ; privatefinal

    UserInfoProvideruserInfoProvider
    ; @Injectpublic UserAwareMetaImpl( Meta meta, ProjectProvider projectProvider,
    UserInfoProvider
        userInfoProvider){ this .meta
        =meta; this .projectProvider
        =projectProvider; this .userInfoProvider

        =userInfoProvider;projectProvider.addToReload(this::
        compileLocalizations);compileLocalizations
    (

)
;
}
//    @Override
//    public void configure(String config)

    //    {//        compileLocalizations();
    //    } @ Overridepublicvoid
    compileLocalizations
        ( ) {localizations=CompiledLocalizations.from(projectProvider.get(
    )

    )
    ;}
    //todo localize entity, query, operation names @ OverridepublicString getLocalizedBe5ErrorMessage(
    Be5Exception
        e ){returnErrorTitles
                .formatTitle(getLocalizedExceptionMessage(ErrorTitles.getTitle(e.getCode()
                )),e.
        getParameters(
    )

    );
    } @ OverridepublicString getLocalizedEntityTitle(
    Entity
        entity){Optional < String >localization=localizations.getEntityTitle(getLanguage (),entity.getName(

        ) );if(!localization.isPresent
        (
            ) ){if(!Strings.isNullOrEmpty(entity.getDisplayName(
            )
                ) ){returnentity.getDisplayName
            (
            ) ;}returnentity.getName
        (

        ) ;}returnlocalization.get
    (

    );
    } @ OverridepublicString getLocalizedEntityTitle(
    String
        entity ){returngetLocalizedEntityTitle(meta.getEntity(entity
    )

    );
    } @ OverridepublicString getLocalizedQueryTitle( String entity,
    String
        query ){returnlocalizations.getQueryTitle(getLanguage () ,entity,
    query

    );
    } @ OverridepublicString getLocalizedOperationTitle(
    Operation
        operation ){returnlocalizations.getOperationTitle(getLanguage
                (),operation.getEntity().getName (),operation.getName(
    )

    );
    } @ OverridepublicString getLocalizedOperationTitle( String entity,
    String
        name ){returnlocalizations.getOperationTitle(getLanguage () ,entity,
    name

    ) ; }publicString getLocalizedOperationField( String entityName, String operationName,
    String
        name ){returnlocalizations.getFieldTitle(getLanguage () ,entityName ,operationName
                ,name).orElseGet ( ()->getColumnTitle (entityName,name
    )

    );
    } @ OverridepublicString getLocalizedCell( String content, String entity,
    String
        query ) { Stringlocalized=MoreStrings.substituteVariables (content ,MESSAGE_PATTERN, (
                message)->localizations.get(getLanguage () ,entity ,query,message).orElse
        (message

        ) );if(localized.startsWith ( "{{{")&&localized.endsWith(
        "}}}"
            ) ) { StringclearContent=localized.substring (3,localized. length ()-
            3 );returnlocalizations.get(getLanguage () ,entity ,query
                    ,clearContent).orElse(
        clearContent

        ) ;}
    return

    localized;
    } @ OverridepublicString getLocalizedValidationMessage(
    String
        message ){returnlocalizations.get(getLanguage () ,"messages.l10n" ,"validation",message).orElse(
    message

    );
    } @ OverridepublicString getLocalizedExceptionMessage(
    String
        message ){returnlocalizations.get(getLanguage () ,"messages.l10n" ,"exception",message).orElse(
    message

    );
    } @ OverridepublicString getLocalizedInfoMessage(
    String
        message ){returnlocalizations.get(getLanguage () ,"messages.l10n" ,"info",message).orElse(
    message

    );
    } @ OverridepublicQuerySettings getQuerySettings(
    Query
        query){List < String >currentRoles=userInfoProvider.get().getCurrentRoles
        ( ); for ( QuerySettingssettings:query.getQuerySettings
        (
            )){Set < String >roles=settings.getRoles().getFinalRoles
            ( ); for ( Stringrole
            :
                currentRoles ){if(roles.contains(
                role
                    ) ){
                return
            settings
        ;
        } } }returnnewQuerySettings(
    query

    );
    } @ OverridepublicOperation getOperation( String entityName,
    String
        name ) { Operationoperation=meta.getOperation (entityName,
        name );if(!meta.hasAccess(operation.getRoles (),userInfoProvider.get().getCurrentRoles(
            ) ))throwBe5Exception.accessDeniedToOperation (entityName,

        name );
    return

    operation;
    } @ Overridepublicboolean hasAccessToOperation( String entityName, String queryName,
    String
        name ) { Operationoperation=meta.getOperation (entityName ,queryName,
        name );returnmeta.hasAccess(operation.getRoles (),userInfoProvider.get().getCurrentRoles(
    )

    );
    } @ OverridepublicOperation getOperation( String entityName, String queryName,
    String
        name ) { Operationoperation=meta.getOperation (entityName ,queryName,
        name );if(!meta.hasAccess(operation.getRoles (),userInfoProvider.get().getCurrentRoles(
            ) ))throwBe5Exception.accessDeniedToOperation (entityName,

        name );
    return

    operation;
    } @ OverridepublicQuery getQuery( String entityName,
    String
        queryName ) { Queryquery=meta.getQuery (entityName,
        queryName );if(!meta.hasAccess(query.getRoles (),userInfoProvider.get().getCurrentRoles(
            ) ))throwBe5Exception.accessDeniedToQuery (entityName,
        queryName );
    return

    query;
    } @ OverridepublicString getColumnTitle( String entityName, String queryName,
    String
        columnName ){returnlocalizations.get(getLanguage () ,entityName ,queryName,columnName).orElse(
    columnName

    ) ; }publicString getColumnTitle( String entityName,
    String
        columnName){ImmutableList < String >defaultQueries=ImmutableList.of(
        "All records" ); for ( StringqueryName
        :
            defaultQueries){Optional < String >columnTitle=localizations.get(getLanguage () ,entityName ,queryName,
            columnName );if(columnTitle.isPresent ( ))returncolumnTitle.get
        (
        ) ;}
    return

    columnName;
    } @ OverridepublicString getFieldTitle( String entityName, String operationName, String queryName,
    String
        name ){returnlocalizations.getFieldTitle(getLanguage () ,entityName ,operationName ,queryName,name).orElse(
    name

    ) ; }publicCompiledLocalizations
    getLocalizations
        ( ){
    return

    localizations ; }privateString
    getLanguage
        ( ){returnmeta.getLocale(userInfoProvider.get().getLocale()).getLanguage
    (

    );
    } @ OverridepublicString getStaticPageContent(
    String
        name ) { StringpageContent=projectProvider.get().getStaticPageContent(getLanguage (),
        name ); if (pageContent
            == null)throwBe5Exception. notFound ("static/"+

        name );
    return
pageContent