package com.developmentontheedge.be5.server.services.impl;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;importjava.
io. ByteArrayOutputStream;importjava.io.InputStream;importjava.
io. OutputStream;importjava.io.PrintStream;publicclassMimeMessage2
extendsMimeMessage {publicstaticclassPrintStream2extendsPrintStream{publicPrintStream2(OutputStreamoutputStream
, booleanautoFlush){super(


outputStream , autoFlush ) ;
}
    public ByteArrayOutputStream getOutput ( ) {
    return
        ( ByteArrayOutputStream)out ;} } publicMimeMessage2
        (
            Sessionsession){ super(session
        )

        ; } publicMimeMessage2(
        Session
            session ,InputStreamis )throws
        MessagingException
    {

    super (session, is)
    ;
        }publicSessiongetSession(
    )

    { returnsession; }} 