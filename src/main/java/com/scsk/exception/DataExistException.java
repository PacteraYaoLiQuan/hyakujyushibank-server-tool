package com.scsk.exception;

import com.scsk.util.ResultMessage;
import com.scsk.util.ResultMessages;

public class DataExistException extends ResultMessagesNotificationException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7435024549632712808L;

	public DataExistException(ResultMessages messages) {
        super(messages);
    }

    public DataExistException(String message) {
        super(ResultMessages.error().add(ResultMessage.fromText(message)));
    }

    public DataExistException(ResultMessages messages, Throwable cause) {
        super(messages, cause);
    }
	
}
