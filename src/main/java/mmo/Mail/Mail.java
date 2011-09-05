/*
 * This file is part of mmoMinecraft (https://github.com/mmoMinecraftDev).
 *
 * mmoMinecraft is free software: you can redistribute it and/or modify
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

import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import mmo.Core.MMO;

public class Mail {

	public MMOMail plugin;
	public Server server;
	public MMO mmo;
	public ArrayList<MailDB> maillist = new ArrayList<MailDB>();
	public MailDB mail;
	public Iterator<MailDB> iterator = null;
	public int messageid = 0;

	public Mail(MMOMail plugin) {
		this.plugin = plugin;
	}

	public int getUnreadCount(String sender) {
		int count = 0;
		for (MailDB mlDB : maillist) {
			if (sender.equalsIgnoreCase(mlDB.getReceiver())) {
				if (!mlDB.getRead()) {
					count++;
				}
			}
		}
		return count;
	}

	public boolean sendMail(String sender, String receiver, String message) {
		messageid++;
		mail = new MailDB();
		mail.setSender(sender);
		mail.setReceiver(receiver);
		mail.setMessage(message);
		mail.setMessageID(messageid);
		maillist.add(mail);
		plugin.notify(receiver, "You've got mail!", Material.PAPER);
		return true;
	}

	public void getMail(Player player) {
		for (MailDB it : new ArrayList<MailDB>(maillist)) {
			if (it.getReceiver().equalsIgnoreCase(player.getName())) {
				plugin.sendMessage(player, "%d | &aFrom: &c%s &a- &c%s", it.getMessageID(), it.getSender(), it.getMessage());
				//it.setRead(true);
				//deleteMail(player.getName(), it.getMessageID());
			}
		}
	}

	public void deleteMail(String player, int mailID) {
		for (MailDB it : new ArrayList<MailDB>(maillist)) {
			if (it.getMessageID() == mailID && it.getReceiver().equalsIgnoreCase(player)) {
				maillist.remove(it);
				plugin.sendMessage(player, "Successfully removed mail with id: %d", mailID);
			}
		}
	}

	public void deleteAllMail(String player) {
		for (MailDB it : new ArrayList<MailDB>(maillist)) {
			if (it.getReceiver().equalsIgnoreCase(player)) {
				maillist.remove(it);
				plugin.sendMessage(player, "Successfully removed all your mails");
			}
		}
	}
}
