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
import java.util.List;
import mmo.Core.MMOPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class MMOMail extends MMOPlugin {

	public Mail mail;

	@Override
	public void onEnable() {
		super.onEnable();
		mail = new Mail(this);
//		getDatabase().find(MailDB.class);
	}

	@Override
	public void loadConfiguration(Configuration cfg) {	
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (command.getName().equalsIgnoreCase("mail")) {
			if (args.length == 0) {
				sendMessage(player, "/mail help");
			} else if (args[0].equalsIgnoreCase("help")) {
				sendMessage(player, "&cMail commands:");
				sendMessage(player, "/mail write [Player] [Message]");
				sendMessage(player, "/mail read");
				sendMessage(player, "/mail delete [MailID]");
				sendMessage(player, "/mail delete all");
			} else if (args[0].equalsIgnoreCase("write")) {
				if (args.length >= 3) {
					String receiver = args[1];
					String message = "";
					Integer count = 3;

					while (count <= args.length) {
						if (count == 3) {
							message = args[count - 1];
						} else {
							message += " " + args[count - 1];
						}
						count++;
					}
					mail.sendMail(player.getName(), receiver, message);
					sendMessage(player, "Sent message to " + receiver);
				} else {
					sendMessage(player, "&c/mail write [Player] [Message]");
				}
			} else if (args[0].equalsIgnoreCase("read")) {
				if (mail.getUnreadCount(player.getName()) >= 1) {
					mail.getMail(player);
				} else {
					sendMessage(player, "No mail found");
				}
			} else if (args[0].equalsIgnoreCase("delete")) {
				if ("all".equalsIgnoreCase(args[1])) {
					mail.deleteAllMail(player.getName());
				} else {
					try {
						int mailID = Integer.parseInt(args[1]);
						if (mailID != 0) {
							mail.deleteMail(player.getName(), mailID);
						}
					} catch (Exception e) {
						sendMessage(player, "Bad mail Id");
					}
				}
			} else {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(MailDB.class);
		return list;
	}
}
