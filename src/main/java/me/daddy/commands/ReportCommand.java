package me.daddy.commands;

import me.daddy.Network;
import me.daddy.redis.JedisProvider;
import me.daddy.utils.command.ExecutableCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand extends ExecutableCommand {
    public ReportCommand(){
        super("report", "Report a player");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length <= 0){
            sender.sendMessage(ChatColor.RED + "Usage: /report <user> <reason>");
            return true;
        }
        JedisProvider jedisProvider = new JedisProvider();
        StringBuilder stringBuilder = new StringBuilder();

        Player reported = Bukkit.getPlayer(args[0]);

        if(reported == null){
            sender.sendMessage(ChatColor.RED + args[0] + " is not online!");
            return true;
        }

        for(int i = 1; i < args.length; i++){
            stringBuilder.append(args[i]).append(" ");
        }

        String message = Network.getPlugin().getConfig().getString("server") + "///" + sender.getName() + "///" + reported.getName() + "///" + stringBuilder.toString();
        System.out.println(message);
        jedisProvider.publish("Reports", message);

        sender.sendMessage(ChatColor.GREEN + "We've sent your report to all online staff!");
        return true;
    }
}
