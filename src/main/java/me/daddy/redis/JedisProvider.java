package me.daddy.redis;

import lombok.Getter;
import me.daddy.Network;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.*;

public class JedisProvider {
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);
    
    Jedis jedis = new Jedis(Network.getPlugin().getConfig().getString("redis.host"),
            Network.getPlugin().getConfig().getInt("redis.port"));

    public void publish(String channel, String message) {
        this.threadPool.execute(() -> {
            jedis.publish(channel, message);
            jedis.quit();
        });
    }

    public void start() {
        if(!Network.getPlugin().getConfig().getString("redis.password").isEmpty()){
            jedis.auth(Network.getPlugin().getConfig().getString("redis.password"));
        }

        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (channel.equals("StaffChat")) {
                        if (player.hasPermission("network.command.staffchat")) {
                            String[] input = message.split("///");
                            String server = input[0];
                            String user = input[1];
                            String msg = input[2];

                            String config = ChatColor.translateAlternateColorCodes('&', Network.getPlugin().getConfig().getString("Messages.staffchat"))
                                    .replace("%server%", server)
                                    .replace("%player%", user)
                                    .replace("%message%", msg);

                            player.sendMessage(config);
                        }
                    }
                    if(channel.equals("StaffJoin")){
                        if(player.hasPermission("network.staffjoin")){
                            String[] input = message.split("///");
                            String server = input[0];
                            String user = input[1];

                            String config = ChatColor.translateAlternateColorCodes('&', Network.getPlugin().getConfig().getString("Messages.staffjoin"))
                                    .replace("%server%", server)
                                    .replace("%player%", user);

                            player.sendMessage(config);
                        }
                    }
                    if(channel.equals("AdminChat")){
                        if(player.hasPermission("network.command.adminchat")){
                            String[] input = message.split("///");
                            String server = input[0];
                            String user = input[1];
                            String msg = input[2];

                            String config = ChatColor.translateAlternateColorCodes('&', Network.getPlugin().getConfig().getString("Messages.adminchat"))
                                    .replace("%server%", server)
                                    .replace("%player%", user)
                                    .replace("%message%", msg);

                            player.sendMessage(config);
                        }
                    }
                    if(channel.equals("Helpop")){
                        if(player.hasPermission("network.helpop.receive")){
                            String[] input = message.split("///");
                            String server = input[0];
                            String user = input[1];
                            String msg = input[2];

                            String config = ChatColor.translateAlternateColorCodes('&', Network.getPlugin().getConfig().getString("Messages.helpop"))
                                    .replace("%server%", server)
                                    .replace("%player%", user)
                                    .replace("%message%", msg);

                            player.sendMessage(config);
                        }
                    }
                    if(channel.equals("Reports")) {
                        if (player.hasPermission("network.helpop.receive")) {
                            String[] input = message.split("///");
                            String server = input[0];
                            String user = input[1];
                            String reported = input[2];
                            String reason = input[3];

                            System.out.println(reason);

                            String config = ChatColor.translateAlternateColorCodes('&', Network.getPlugin().getConfig().getString("Messages.report"))
                                    .replace("%server%", server)
                                    .replace("%player%", user)
                                    .replace("%reported%", reported)
                                    .replace("%reason%", reason);

                            player.sendMessage(config);
                        }
                    }
                });
            }
        };
        this.threadPool.execute(() -> {
            try{
                jedis.subscribe(jedisPubSub, "StaffJoin", "StaffChat", "AdminChat", "Helpop", "Reports");
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
