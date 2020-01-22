package me.daddy.commands;

import me.daddy.Network;
import me.daddy.redis.JedisProvider;
import me.daddy.utils.command.ExecutableCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpopCommand extends ExecutableCommand {
    public HelpopCommand(){
        super("helpop", "Request help command");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length <= 0){
            sender.sendMessage(ChatColor.RED + "Usage: /helpop <message>");
            return true;
        }
        JedisProvider jedisProvider = new JedisProvider();
        StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args) {
            stringBuilder.append(arg).append(" ");
        }

        String message = Network.getPlugin().getConfig().getString("server") + "///" + sender.getName() + "///" + stringBuilder.toString();

        jedisProvider.publish("Helpop", message);

        sender.sendMessage(ChatColor.GREEN + "We've sent your request to all online staff!");
        return true;
    }
}
