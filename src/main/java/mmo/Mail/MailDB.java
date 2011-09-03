package mmo.Mail;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "mmo_Mail")
public class MailDB {

	@Id
	@NotNull
	@NotEmpty
	private String sender;

	@NotNull
	private String receiver;

	@NotNull
	private String message;
	
	@NotNull
	private int messageID = 0;
	
	private boolean read = false;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public String getMessage() {
	return message;
	}

	public void setMessage(String message) {
	this.message = message;
	}
	
	public boolean getRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
}
