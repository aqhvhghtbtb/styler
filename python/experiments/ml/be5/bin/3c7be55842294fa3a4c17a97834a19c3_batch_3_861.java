package com.developmentontheedge.be5.database.impl;

import com.developmentontheedge.be5.database.ConnectionService;
import org.aopalliance.intercept.MethodInterceptor;importorg.aopalliance.
intercept. MethodInvocation;importjavax.inject.Inject;publicclassTransactionInterceptorimplementsMethodInterceptor{@

Injectprivate ConnectionServiceconnectionService;@
{
    @Inject
    private ConnectionService connectionService;

    @Override
    public Object invoke(MethodInvocation invocation)
    {
        return connectionService.transactionWithResult(conn -> invocation.proceed());
    }
}
