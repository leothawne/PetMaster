package com.hm.petmaster.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hm.petmaster.PetMaster;
import com.hm.petmaster.particle.PacketSender;

/**
 * Class in charge of displaying the plugin's help (/petm help).
 * 
 * @author Pyves
 */
public class HelpCommand {

	private PetMaster plugin;

	public HelpCommand(PetMaster plugin) {

		this.plugin = plugin;
	}

	public void getHelp(CommandSender sender) {

		// Header.
		sender.sendMessage(ChatColor.GOLD + "------------------ " + ChatColor.GRAY + ChatColor.GOLD + "\u265E"
				+ ChatColor.translateAlternateColorCodes('&', " &lPet Master ") + ChatColor.GOLD + "\u265E"
				+ ChatColor.GRAY + ChatColor.GOLD + " ------------------");

		sendJsonClickableHoverableMessage(sender,
				plugin.getChatHeader() + ChatColor.GOLD + "/petm info" + ChatColor.GRAY + " > "
						+ plugin.getPluginLang().getString("petmaster-command-info",
								"Display various information about the plugin."),
				"/petm info", plugin.getPluginLang().getString("petmaster-command-info-hover",
						"Some extra info about the plugin and its awesome author!"));

		if (sender.hasPermission("petmaster.admin"))
			sendJsonClickableHoverableMessage(sender,
					plugin.getChatHeader() + ChatColor.GOLD + "/petm reload" + ChatColor.GRAY + " > "
							+ plugin.getPluginLang().getString("petmaster-command-reload",
									"Reload the plugin's configuration."),
					"/petm reload", plugin.getPluginLang().getString("petmaster-command-reload-hover",
							"Reload most settings in config.yml and lang.yml files."));

		if (sender.hasPermission("petmaster.admin"))
			sendJsonClickableHoverableMessage(sender,
					plugin.getChatHeader() + ChatColor.GOLD + "/petm enable" + ChatColor.GRAY + " > "
							+ plugin.getPluginLang().getString("petmaster-command-enable", "Enable plugin."),
					"/petm enable", plugin.getPluginLang().getString("petmaster-command-enable-hover",
							"Plugin enabled by default. Use this if you entered /petm disable before!"));

		if (sender.hasPermission("petmaster.admin"))
			sendJsonClickableHoverableMessage(sender,
					plugin.getChatHeader() + ChatColor.GOLD + "/petm disable" + ChatColor.GRAY + " > "
							+ plugin.getPluginLang().getString("petmaster-command-disable", "Disable plugin."),
					"/petm disable", plugin.getPluginLang().getString("petmaster-command-disable-hover",
							"The plugin will not work until next reload or /petm enable."));

		if (sender.hasPermission("petmaster.setowner"))
			sendJsonClickableHoverableMessage(sender,
					plugin.getChatHeader() + ChatColor.GOLD + "/petm setowner player" + ChatColor.GRAY + " > "
							+ plugin.getPluginLang().getString("petmaster-command-setowner",
									"Change the ownership of a pet."),
					"/petm setowner player", plugin.getPluginLang().getString("petmaster-command-setowner-hover",
							"You can only change the ownership of your own pets, unless you're admin!"));

		// Empty line.
		sender.sendMessage(ChatColor.GOLD + " ");

		// Tip message.
		sender.sendMessage(ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', plugin.getPluginLang()
				.getString("petmaster-tip", "&lHINT&r &8You can &7&n&ohover&r &8or &7&n&oclick&r &8on the commands!")));
	}

	/**
	 * Send a packet message to the server in order to display a clickable and hoverable message. A suggested command is
	 * displayed in the chat when clicked on, and an additional help message appears when a command is hovered.
	 * 
	 * @param sender
	 * @param message
	 * @param command
	 * @param hover
	 */
	public void sendJsonClickableHoverableMessage(CommandSender sender, String message, String command, String hover) {

		// Build the json format string.
		String json = "{\"text\":\"" + message + "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\""
				+ command + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"" + hover
				+ "\",\"color\":\"gold\"}]}}";

		// Send clickable and hoverable message if sender is a player and if no exception is caught.
		if (sender instanceof Player) {
			try {
				PacketSender.sendChatPacket((Player) sender, json);
			} catch (Exception ex) {
				plugin.getLogger().severe(
						"Errors while trying to display clickable and hoverable message in /petm help command. Displaying standard message instead.");
				sender.sendMessage(message);
			}
		} else {
			sender.sendMessage(message);
		}
	}
}
