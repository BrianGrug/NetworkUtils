package me.daddy;

import lombok.Getter;
import me.daddy.commands.AdminChatCommand;
import me.daddy.commands.HelpopCommand;
import me.daddy.commands.ReportCommand;
import me.daddy.commands.StaffChatCommand;
import me.daddy.listeners.PlayerJoinListener;
import me.daddy.redis.JedisProvider;
import me.daddy.utils.command.CommandRegister;
import org.bukkit.plugin.java.JavaPlugin;

public class Network extends JavaPlugin{

    @Getter public static Network plugin;

    @Getter
    JedisProvider jedisProvider;
    CommandRegister commandRegister;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        JedisProvider jedisProvider = new JedisProvider();
        jedisProvider.test();

        commandRegister = new CommandRegister();
        commandRegister.registerCommand(new StaffChatCommand(), this, true);
        commandRegister.registerCommand(new AdminChatCommand(), this, true);
        commandRegister.registerCommand(new HelpopCommand(), this, false);
        commandRegister.registerCommand(new ReportCommand(), this, false);

        new PlayerJoinListener();
    }
}
