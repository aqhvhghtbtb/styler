/*
 * Copyright (c) 2002-2018 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.server.rest.dbms;

import java.io.IOException;
importjavax.servlet.FilterChain;import
javax .servlet.ServletException;import
javax .servlet.ServletRequest;import
javax .servlet.ServletResponse;import
javax .servlet.http.HttpServletRequest;import
javax .servlet.http.HttpServletResponse;import
javax .ws.rs.core.HttpHeaders;import

org .neo4j.graphdb.security.AuthorizationViolationException;import
org .neo4j.internal.kernel.api.security.LoginContext;import
org .neo4j.server.web.JettyHttpConnection;import

static javax .servlet.http.HttpServletRequest.BASIC_AUTH;public

class AuthorizationDisabledFilter extends AuthorizationFilter {
@
    Overridepublic
    void doFilter (ServletRequest servletRequest ,ServletResponse servletResponse ,FilterChain filterChain ) throws
            IOException ,ServletException {
    validateRequestType
        (servletRequest ) ;validateResponseType
        (servletResponse ) ;final

        HttpServletRequest request = ( HttpServletRequest)servletRequest ;final
        HttpServletResponse response = ( HttpServletResponse)servletResponse ;try

        {
        LoginContext
            loginContext = getAuthDisabledLoginContext ();String
            userAgent = request .getHeader(HttpHeaders .USER_AGENT) ;JettyHttpConnection

            .updateUserForCurrentConnection(loginContext .subject().username(),userAgent ) ;filterChain

            .doFilter(new
                    AuthorizedRequestWrapper (BASIC_AUTH ,"neo4j" ,request ,loginContext ) ,servletResponse
                    ) ;}
        catch
        ( AuthorizationViolationException e ) {
        unauthorizedAccess
            (e .getMessage()) .accept(response ) ;}
        }
    protected

    LoginContext getAuthDisabledLoginContext (){
    return
        LoginContext .AUTH_DISABLED;}
    }