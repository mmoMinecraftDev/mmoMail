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
		mmo.notify(receiver, "You've got mail!", Material.PAPER);
		return true;
	}

	public void getMail(Player player) {
		for (MailDB it : new ArrayList<MailDB>(maillist)) {
			if (it.getReceiver().equalsIgnoreCase(player.getName())) {
				mmo.sendMessage(player, "%d | &aFrom: &c%s &a- &c%s", it.getMessageID(), it.getSender(), it.getMessage());
				//it.setRead(true);
				//deleteMail(player.getName(), it.getMessageID());
			}
		}
	}

	public void deleteMail(String player, int mailID) {
		for (MailDB it : new ArrayList<MailDB>(maillist)) {
			if (it.getMessageID() == mailID && it.getReceiver().equalsIgnoreCase(player)) {
				maillist.remove(it);
				mmo.sendMessage(player, "Successfully removed mail with id: %d", mailID);
			}
		}
	}

	public void deleteAllMail(String player) {
		for (MailDB it : new ArrayList<MailDB>(maillist)) {
			if (it.getReceiver().equalsIgnoreCase(player)) {
				maillist.remove(it);
				mmo.sendMessage(player, "Successfully removed all your mails");
			}
		}
	}
}
