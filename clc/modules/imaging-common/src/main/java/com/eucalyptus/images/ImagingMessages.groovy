/*************************************************************************
 * Copyright 2009-2013 Eucalyptus Systems, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Please contact Eucalyptus Systems, Inc., 6755 Hollister Ave., Goleta
 * CA 93117, USA or visit http://www.eucalyptus.com/licenses/ if you need
 * additional information or have any questions.
 ************************************************************************/
@GroovyAddClassUUID
package com.eucalyptus.images;

import java.io.Serializable;

import com.eucalyptus.binding.HttpParameterMapping
import com.eucalyptus.component.annotation.ComponentMessage

import edu.ucsb.eucalyptus.msgs.BaseMessage;
import edu.ucsb.eucalyptus.msgs.EucalyptusData;
import edu.ucsb.eucalyptus.msgs.EucalyptusMessage
import edu.ucsb.eucalyptus.msgs.GroovyAddClassUUID

@ComponentMessage(Imaging.class)
public class ImagingMessage extends BaseMessage implements Cloneable, Serializable {
  
  public ImagingMessage( ) {
    super( );
  }
  
  public ImagingMessage( BaseMessage msg ) {
    super( msg );
  }
  
  public ImagingMessage( String userId ) {
    super( userId );
  }
}
/** *******************************************************************************/
/*
public class ImportImageType extends ImagingMessage {
  String manifestUrl;

  public ImportImageType( ) {
    super( );
  }

  public ImportImageType( EucalyptusMessage msg ) {
    super( msg );
  }

  public ImportImageType( String manifestUrl ) {
    this.manifestUrl = manifestUrl;
  }
}
*/
public class PutInstanceImportTaskStatusResponseType extends ImagingMessage {
}

public class PutInstanceImportTaskStatusType extends ImagingMessage {
	String importTaskId
	String status
	String message
	long bytesConverted
}

public class GetInstanceImportTaskResponseType extends ImagingMessage {
	String importTaskId
}

public class GetInstanceImportTaskType extends ImagingMessage {
}

public class Error extends EucalyptusData {
  String type
  String code
  String message
  public Error() {  }
  ErrorDetail detail = new ErrorDetail()
}

public class ErrorResponse extends ImagingMessage {
  String requestId
  public ErrorResponse() {
  }
  ArrayList<Error> error = new ArrayList<Error>()
}
public class ErrorDetail extends EucalyptusData {
  public ErrorDetail() {  }
}
