package com.eucalyptus.records;

import org.apache.log4j.Logger;
import org.mule.RequestContext;
import org.mule.api.MuleEvent;
import com.eucalyptus.auth.principal.FakePrincipals;
import com.eucalyptus.auth.principal.UserFullName;
import com.eucalyptus.context.Context;
import com.eucalyptus.context.Contexts;
import com.eucalyptus.context.NoSuchContextException;
import edu.ucsb.eucalyptus.msgs.EucalyptusMessage;

public class EventRecord extends EucalyptusMessage {
  private static Logger            LOG   = Logger.getLogger( EventRecord.class );
  
  private static Record create( final Class component, final EventClass eventClass, final EventType eventName, final String other, int dist ) {
    EucalyptusMessage msg = tryForMessage( );
    StackTraceElement[] stack = Thread.currentThread( ).getStackTrace( );
    StackTraceElement ste = stack[dist+3<stack.length?dist+3:stack.length-1];
    UserFullName userFn = FakePrincipals.NOBODY_USER_ERN;
    try {
      Context ctx = Contexts.lookup( msg.getCorrelationId( ) );
      userFn = ctx.getUserFullName( );
    } catch ( Exception ex ) {
    }
    return new LogFileRecord( eventClass, eventName, component, ste, msg == BOGUS ? "nobody" : userFn.toString( ), msg.getCorrelationId( ), other );
  }

  public static Record here( final Class component, final EventClass eventClass, final EventType eventName, final String... other ) {
    return create( component, eventClass, eventName, getMessageString( other ), 1 );
  }
    
  public static Record caller( final Class component, final EventClass eventClass, final EventType eventName, final Object... other ) {
    return create( component, eventClass, eventName, getMessageString( other ), 2 );
  }

  public static Record here( final Class component, final EventType eventName, final String... other ) {
    return create( component, EventClass.ORPHAN, eventName, getMessageString( other ), 1 );
  }
    
  public static Record caller( final Class component, final EventType eventName, final Object... other ) {
    return create( component, EventClass.ORPHAN, eventName, getMessageString( other ), 2 );
  }

  private static String getMessageString( final Object[] other ) {
    StringBuffer last = new StringBuffer( );
    if( other != null ) {
      for ( Object x : other ) {
        last.append( ":" ).append( x );
      }
    }
    return last.length( ) > 1 ? last.substring( 1 ) : last.toString( );
  }

  private static EucalyptusMessage BOGUS  = getBogusMessage( );
  private static EucalyptusMessage getBogusMessage( ) {
    EucalyptusMessage hi = new EucalyptusMessage( );
    hi.setCorrelationId( FakePrincipals.NOBODY_ID );
    hi.setUserId( FakePrincipals.NOBODY_USER_ERN.getUserName( ) );
    return hi;
  }
  private static EucalyptusMessage tryForMessage( ) {
    EucalyptusMessage msg = null;
    MuleEvent event = RequestContext.getEvent( );
    if ( event != null ) {
      if ( event.getMessage( ) != null && event.getMessage( ).getPayload( ) != null && event.getMessage( ).getPayload( ) instanceof EucalyptusMessage ) {
        msg = ( ( EucalyptusMessage ) event.getMessage( ).getPayload( ) );
      }
    }
    return msg == null ? BOGUS : msg;
  }

}
