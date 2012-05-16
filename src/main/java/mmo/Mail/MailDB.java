/*
 * This file is part of mmoMail <http://github.com/mmoMinecraftDev/mmoMail>.
 *
 * mmoMail is free software: you can redistribute it and/or modify
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
package mmo.Mail;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

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
