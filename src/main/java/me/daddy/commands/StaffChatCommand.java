package me.daddy.commands;

import me.daddy.Network;
import me.daddy.redis.JedisProvider;
import me.daddy.utils.command.CommandArgument;
import me.daddy.utils.command.ExecutableCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import redis.clients.jedis.Jedis;

public class StaffChatCommand extends ExecutableCommand {
    public StaffChatCommand(){
        super("staffchat", "Send a message to fellow staff", "sc");
    }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length <= 0){
            sender.sendMessage(ChatColor.RED + "Usage: /sc <message>");
            return true;
        }
        JedisProvider jedisProvider = new JedisProvider();
        StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args) {
            stringBuilder.append(arg).append(" ");
        }

        String message = Network.getPlugin().getConfig().getString("server") + "///" + sender.getName() + "///" + stringBuilder.toString();

        jedisProvider.publish("StaffChat", message);
        return true;
    }
}
