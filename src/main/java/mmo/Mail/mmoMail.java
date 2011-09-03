package mmo.Mail;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import mmo.Core.MMO;
import mmo.Core.MMOPlugin;

public class mmoMail extends MMOPlugin {
	protected static Server server;
	protected static PluginManager pm;
	protected static PluginDescriptionFile description;
	protected static MMO mmo;
	public mail mail = new mail(this);

	public mmoMail() {
		classes.add(MailDB.class);
	}

	@Override
	public void onEnable() {
		server = getServer();
		pm = server.getPluginManager();
		description = getDescription();
		mail.mmo = mmo = MMO.create(this);
		mmo.setPluginName("Mail");
		mmo.log("loading " + description.getFullName());
		getDatabase().find(MailDB.class);
	}

	@Override
	public void onDisable(){
		mmo.log("Disabled " + description.getFullName());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (command.getName().equalsIgnoreCase("mail")) {
			if (args.length == 0) {
				mmo.sendMessage(player, "/mail help");
			} else if (args[0].equalsIgnoreCase("help")){
				mmo.sendMessage(player, "&cMail commands:");
				mmo.sendMessage(player, "/mail write [Player] [Message]");
				mmo.sendMessage(player, "/mail read");
				mmo.sendMessage(player, "/mail delete [MailID]");
				mmo.sendMessage(player, "/mail delete all");
			} else if (args[0].equalsIgnoreCase("write")){
				if(args.length >= 3){
					String receiver = args[1];
					String message = "";
					Integer count = 3;
					
					while(count <= args.length){
						if(count == 3){
							message = args[count - 1];
						} else {
							message += " " + args[count - 1];
						}
						count++;
					}
					try {
						mail.sendMail(player.getName(), receiver, message);
					} catch (Exception e) {
						e.printStackTrace();
					}
					mmo.sendMessage(player, "Sent message to " + receiver);
				} else {
					mmo.sendMessage(player, "&c/mail write [Player] [Message]");
				}
			}else if (args[0].equalsIgnoreCase("read")){
				if(mail.getUnreadCount(player.getName()) >= 1){
					try {
						mail.getMail(player);
					} catch (Exception e) {
						e.printStackTrace();
					}				
				} else {
					mmo.sendMessage(player, "No mail found");
				}
			}else if (args[0].equalsIgnoreCase("delete")){
				int mailID = Integer.parseInt(args[1]);
				if(mailID != 0){
					try {
						mail.deleteMail(player.getName() , mailID);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(args[1].equalsIgnoreCase("all")){
					try {
						mail.deleteAllMail(player.getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return true;
		}
		return false;
	}
}
