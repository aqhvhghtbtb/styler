package com.developmentontheedge.be5.database.impl;

import com.developmentontheedge.be5.database.ConnectionService;
import org.aopalliance.intercept.MethodInterceptor;
import org . aopalliance . intercept
.
    MethodInvocation;
    import javax .inject

    .Inject
    ; public classTransactionInterceptorimplements MethodInterceptor{
    @
        Inject privateConnectionServiceconnectionService;@ Override publicObjectinvoke(MethodInvocationinvocation)
    {
return