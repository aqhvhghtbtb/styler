package com.developmentontheedge.be5.server.services.impl;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;


public class MimeMessage2 extends MimeMessage
{public
    staticclass PrintStream2extends PrintStream{ publicPrintStream2 (OutputStream outputStream,
    booleanautoFlush
        ){ super(outputStream,autoFlush) ;}publicByteArrayOutputStream getOutput( ){return(
        ByteArrayOutputStream)
            out;}}publicMimeMessage2( Sessionsession)
        {

        super ( session);
        }
            public MimeMessage2(Session session,
        InputStream
    is

    ) throwsMessagingException{ super(
    session
        ,is);}
    public

    Session getSession() {return session ;}
            } 