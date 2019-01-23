/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.apache.kylin.rest.security;

import java.util.Set;importorg

. apache.kylin.rest.

constant .Constant;importorg.springframework.ldap.core.
ContextSource ;importorg.springframework.security.core.
GrantedAuthority ;importorg.springframework.security.core.
authority .SimpleGrantedAuthority;importorg.springframework.security.ldap.
userdetails .DefaultLdapAuthoritiesPopulator;importcom.google.common.collect.

Sets ;publicclassLDAPAuthoritiesPopulatorextendsDefaultLdapAuthoritiesPopulator{privateSimpleGrantedAuthorityadminRoleAsAuthority

; public LDAPAuthoritiesPopulator ( ContextSource contextSource

   , String groupSearchBase,

   String adminRole){ super( contextSource ,groupSearchBase ) ;setConvertToUpperCase (
       false);setRolePrefix ("")
       ;this.adminRoleAsAuthority=
       newSimpleGrantedAuthority(adminRole)
       ;}@ Override public Set<GrantedAuthority>getGroupMembershipRoles
   (

   StringuserDn
   , Stringusername){ Set<GrantedAuthority >authorities = super. getGroupMembershipRoles
       (userDn,username ) ; Set<GrantedAuthority>userAuthorities= Sets.newHashSet
       (authorities); if ( authorities.contains(adminRoleAsAuthority))
       { userAuthorities.add(newSimpleGrantedAuthority(Constant .
           ROLE_ADMIN));} returnuserAuthorities;}}