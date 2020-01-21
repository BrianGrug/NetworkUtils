package me.daddy.listeners;

import me.daddy.Network;
import me.daddy.redis.JedisProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener(){
        Bukkit.getPluginManager().registerEvents(this, Network.getPlugin());
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        JedisProvider jedisProvider = new JedisProvider();

        if(event.getPlayer().hasPermission("network.staffjoin")) {
            String message = Network.getPlugin().getConfig().getString("server") + "///" + event.getPlayer().getName();
            jedisProvider.publish("StaffJoin", message);
        }
    }
}
