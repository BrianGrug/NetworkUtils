package me.daddy.commands;

import me.daddy.Network;
import me.daddy.redis.JedisProvider;
import me.daddy.utils.command.ExecutableCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AdminChatCommand extends ExecutableCommand {
    public AdminChatCommand() {
        super("adminchat", "Send a message to fellow admins", "ac");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length <= 0){
            sender.sendMessage(ChatColor.RED + "Usage: /ac <message>");
            return true;
        }
        JedisProvider jedisProvider = new JedisProvider();
        StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args) {
            stringBuilder.append(arg).append(" ");
        }

        String message = Network.getPlugin().getConfig().getString("server") + "///" + sender.getName() + "///" + stringBuilder.toString();

        jedisProvider.publish("AdminChat", message);
        return true;
    }
}
