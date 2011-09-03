package mmo.Mail;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import mmo.Core.MMO;

public class mail {
	public mmoMail plugin;
	public Server server;
	public MMO mmo;
	public ArrayList<MailDB> maillist = new ArrayList<MailDB>();
	public MailDB mail;
	public Iterator<MailDB> iterator = null;
	public int messageid = 0;
	
	public mail(mmoMail plugin){
		this.plugin = plugin;
	}
	
	public int getUnreadCount(String sender){
		int count = 0;
		for (MailDB mlDB : maillist){
			if(sender.equals(mlDB.getReceiver())){
				if(!mlDB.getRead()){
					count++;
				}
			}
		}
		return count;
	}
	
	public boolean sendMail(String sender, String receiver, String message) throws Exception{
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
	public void getMail(Player player)throws Exception{
		for(Iterator<MailDB> it = maillist.iterator(); it.hasNext();){
			MailDB mdb = it.next();
			if (mdb.getReceiver().equals(player.getName())) {
				mmo.sendMessage(player, "%s | &aFrom: &c%s &a- &c%s", Integer.toString(mdb.getMessageID()), mdb.getSender(), mdb.getMessage());
				//mdb.setRead(true);
				synchronized (maillist) {
					//it.remove();
				}
			}
		}
	}
	public void deleteMail(String player, int mailID) throws Exception{
		for(Iterator<MailDB> it = maillist.iterator(); it.hasNext();){
			MailDB mdb = it.next();
			if (mdb.getMessageID() == mailID && mdb.getReceiver().equals(player)) {
				synchronized (maillist) {
					it.remove();
					mmo.sendMessage(player, "Successfully removed mail with id: %s", mdb.getMessageID());
				}
			}
		}
	}

	public void deleteAllMail(String player) throws Exception {
		for(Iterator<MailDB> it = maillist.iterator(); it.hasNext();){
			MailDB mdb = it.next();
			if (mdb.getReceiver().equals(player)) {
				synchronized (maillist) {
					it.remove();
					mmo.sendMessage(player, "Successfully removed all your mails");
				}
			}
		}
	}
}
