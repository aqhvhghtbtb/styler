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
    private final ProjectProvider projectProvider;
    private final UserInfoProvider userInfoProvider;

    @ Injectpublic
    UserAwareMetaImpl
        (Metameta , ProjectProviderprojectProvider
        ,UserInfoProvideruserInfoProvider ) {this
        .meta= meta ;this

        .projectProvider=projectProvider;this.userInfoProvider=
        userInfoProvider;projectProvider.
    addToReload

(
this
::
compileLocalizations
)

    ;compileLocalizations
    ( ) ;}//    @Override
    //    public void configure(String config)
        //    { //        compileLocalizations(); //    }@OverridepublicvoidcompileLocalizations(){localizations=
    CompiledLocalizations

    .
    from(
    projectProvider . get() );
    }
        //todo localize entity, query, operation names @OverridepublicString
                getLocalizedBe5ErrorMessage(Be5Exceptione){returnErrorTitles.formatTitle(getLocalizedExceptionMessage(ErrorTitles
                .getTitle(e.
        getCode(
    )

    ))
    , e .getParameters( ))
    ;
        }@Overridepublic String getLocalizedEntityTitle (Entityentity){Optional<String >localization=localizations.getEntityTitle(

        getLanguage (),entity.getName()
        )
            ; if(!localization.isPresent()){if(!
            Strings
                . isNullOrEmpty(entity.getDisplayName(
            )
            ) ){returnentity.getDisplayName
        (

        ) ;}returnentity.getName
    (

    );
    } return localization.get ()
    ;
        } @OverridepublicStringgetLocalizedEntityTitle(Stringentity){
    return

    getLocalizedEntityTitle(
    meta . getEntity(entity )) ; }@
    Override
        public StringgetLocalizedQueryTitle(Stringentity,Stringquery ){ returnlocalizations.
    getQueryTitle

    (getLanguage
    ( ) ,entity, query)
    ;
        } @OverridepublicStringgetLocalizedOperationTitle(Operationoperation
                ){returnlocalizations.getOperationTitle(getLanguage() ,operation.getEntity().
    getName

    ()
    , operation .getName( )) ; }@
    Override
        public StringgetLocalizedOperationTitle(Stringentity,Stringname ){ returnlocalizations.
    getOperationTitle

    ( getLanguage (), entity, name ); } publicString
    getLocalizedOperationField
        ( StringentityName,StringoperationName,Stringname ){ returnlocalizations .getFieldTitle
                (getLanguage(), entityName ,operationName,name ).orElseGet(
    (

    )->
    getColumnTitle ( entityName,name )) ; }@ Override publicString
    getLocalizedCell
        ( String content ,Stringentity,Stringquery ){ Stringlocalized= MoreStrings
                .substituteVariables(content,MESSAGE_PATTERN,( message) ->localizations .get(getLanguage(),
        entity,

        query ,message).orElse(message ) );if(localized.startsWith
        (
            "{{{" ) && localized.endsWith("}}}") ){StringclearContent= localized .substring(
            3 ,localized.length()-3 ); returnlocalizations .get
                    (getLanguage(),entity
        ,

        query ,clearContent
    )

    .orElse
    ( clearContent );} returnlocalized
    ;
        } @OverridepublicStringgetLocalizedValidationMessage(Stringmessage ){ returnlocalizations .get(getLanguage(),"messages.l10n"
    ,

    "validation",
    message ) .orElse( message)
    ;
        } @OverridepublicStringgetLocalizedExceptionMessage(Stringmessage ){ returnlocalizations .get(getLanguage(),"messages.l10n"
    ,

    "exception",
    message ) .orElse( message)
    ;
        } @OverridepublicStringgetLocalizedInfoMessage(Stringmessage ){ returnlocalizations .get(getLanguage(),"messages.l10n"
    ,

    "info",
    message ) .orElse( message)
    ;
        }@Overridepublic QuerySettings getQuerySettings (Queryquery){List<String>currentRoles
        = userInfoProvider. get ( ).getCurrentRoles();
        for
            (QuerySettingssettings: query . getQuerySettings()){Set<String>roles
            = settings. getRoles ( ).
            getFinalRoles
                ( );for(Stringrole:currentRoles
                )
                    { if(
                roles
            .
        contains
        ( role )){returnsettings
    ;

    }}
    } return newQuerySettings( query) ; }@
    Override
        public Operation getOperation (StringentityName,Stringname ){Operation
        operation =meta.getOperation(entityName,name);if( !meta.hasAccess(operation.getRoles(),
            userInfoProvider .get().getCurrentRoles ())

        ) throwBe5Exception
    .

    accessDeniedToOperation(
    entityName , name); returnoperation ; }@ Override publicboolean
    hasAccessToOperation
        ( String entityName ,StringqueryName,Stringname ){ Operationoperation=
        meta .getOperation(entityName,queryName,name); returnmeta.hasAccess(operation.getRoles(),
    userInfoProvider

    .get
    ( ) .getCurrentRoles( )) ; }@ Override publicOperation
    getOperation
        ( String entityName ,StringqueryName,Stringname ){ Operationoperation=
        meta .getOperation(entityName,queryName,name);if( !meta.hasAccess(operation.getRoles(),
            userInfoProvider .get().getCurrentRoles ())

        ) throwBe5Exception
    .

    accessDeniedToOperation(
    entityName , name); returnoperation ; }@
    Override
        public Query getQuery (StringentityName,StringqueryName ){Query
        query =meta.getQuery(entityName,queryName);if( !meta.hasAccess(query.getRoles(),
            userInfoProvider .get().getCurrentRoles ())
        ) throwBe5Exception
    .

    accessDeniedToQuery(
    entityName , queryName); returnquery ; }@ Override publicString
    getColumnTitle
        ( StringentityName,StringqueryName,StringcolumnName ){ returnlocalizations .get(getLanguage(),entityName
    ,

    queryName , columnName). orElse( columnName );
    }
        publicStringgetColumnTitle( String entityName ,StringcolumnName){ImmutableList<
        String >defaultQueries = ImmutableList .of
        (
            "All records");for ( String queryName:defaultQueries){Optional<String >columnTitle =localizations .get(
            getLanguage (),entityName,queryName, columnName );if(columnTitle.
        isPresent
        ( ))
    return

    columnTitle.
    get ( );} returncolumnName ; }@ Override publicString getFieldTitle (String
    entityName
        , StringoperationName,StringqueryName,Stringname ){ returnlocalizations .getFieldTitle (getLanguage(),entityName,operationName
    ,

    queryName , name).
    orElse
        ( name)
    ;

    } public CompiledLocalizationsgetLocalizations(
    )
        { returnlocalizations;}privateStringgetLanguage(){returnmeta.getLocale(userInfoProvider.get(
    )

    .getLocale
    ( ) ).getLanguage ()
    ;
        } @ Override publicStringgetStaticPageContent(Stringname){StringpageContent=projectProvider .get(
        ) .getStaticPageContent ( getLanguage(
            ) ,name);if ( pageContent==null

        ) throwBe5Exception
    .
notFound