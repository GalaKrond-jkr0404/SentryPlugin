package com.galakrond.sentry;

import com.galakrond.sentry.commands.sentryCommands;


import com.galakrond.sentry.events.launchArrow;
import com.galakrond.sentry.events.sentryTargetEvent;
import com.galakrond.sentry.events.suffocationDamage;
import com.galakrond.sentry.items.spawner;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Sentry extends JavaPlugin {


    private int initSentryCount;

    private File file;
    private JSONObject json;
    private JSONParser parser = new JSONParser();
    private HashMap<String, Object> defaults = new HashMap<String, Object>();


    @Override
    public void onEnable() {
        Server server = getServer();
        World world = server.getWorlds().get(0);



        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "SentryPlugin Online.");

        getServer().getPluginManager().registerEvents(new suffocationDamage(), this);
        getServer().getPluginManager().registerEvents(new sentryTargetEvent(), this);

        spawner.init(); // 아이템 로드

        sentryCommands commands = new sentryCommands();
        getCommand("getspawner").setExecutor(commands);
        getCommand("addtarget").setExecutor(commands);
        getCommand("removetarget").setExecutor(commands);
        getCommand("addplayer").setExecutor(commands);
        getCommand("removeplayer").setExecutor(commands);


        try {
            this.saveDefaultConfig();

        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        Plugin plugin = this;
        FileConfiguration config = this.getConfig();
        sentryCommands.init(this.getConfig());
        sentryTargetEvent.init(this.getConfig());
        this.saveConfig();
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Chunk chunk : world.getLoadedChunks()) {
                    for(Entity entity : chunk.getEntities()){
                        if (entity.getType() == EntityType.SNOWMAN){

                            LivingEntity Livingentity = (LivingEntity)entity;

                            if(Livingentity.getEquipment().getChestplate().getType() == Material.VILLAGER_SPAWN_EGG) {

                                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN +" - Sentry Loaded by World Load - "); }

                            new SentryTask(Livingentity).runTaskTimer(JavaPlugin.getPlugin(Sentry.class), 0, 30);
                        }
                    }
                }
            }
        }.runTaskLater(this, 200);
    }



    @Override
    public void onDisable() {
        World world = getServer().getWorlds().get(0);
        NamespacedKey key = new NamespacedKey(JavaPlugin.getPlugin(Sentry.class), "taskNo");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Please wait until Disabling All Sentrys...");

        for(Chunk chunk : world.getLoadedChunks()) {
            for(Entity entity : chunk.getEntities()){
                if (entity.getType() == EntityType.SNOWMAN){
                    LivingEntity Livingentity = (LivingEntity)entity;
                    if(Livingentity.getEquipment().getChestplate().getType() == Material.VILLAGER_SPAWN_EGG) {
                        PersistentDataContainer container = entity.getPersistentDataContainer();
                        Bukkit.getScheduler().cancelTask(container.get(key, PersistentDataType.INTEGER));
                        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + " - Sentry Disabled by World Close - ");

                    }

                }
            }
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Done!");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "SentryPlugin Offline.");
    }

    public void save() {
        this.saveConfig();
    }

    public class SentryTask extends BukkitRunnable
    {
        private LivingEntity sentry;



        public SentryTask(LivingEntity entity)
        {
            sentry = entity;
        }

        @Override
        public void run()
        {
            NamespacedKey key = new NamespacedKey(JavaPlugin.getPlugin(Sentry.class), "taskNo");
            sentry.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, this.getTaskId()); // 넣어두고 이따가 청크 언로드에 Task 번호 없애기
            if(sentry == null || !sentry.isValid()) {
                this.cancel();
            }
            else {
                detectNearestEntity(sentry);
            }
        }
    }

    public void detectNearestEntity(LivingEntity sentry){



        Location location = sentry.getLocation();
        BoundingBox box = new BoundingBox(location.getX() - 16, location.getY() - 16, location.getZ() - 16,
                location.getX() + 16, location.getY() + 16, location.getZ() + 16);
        List<String> data = this.getConfig().getStringList(sentry.getCustomName());

        Collection <Player> players = sentry.getWorld().getNearbyEntities(box).stream().filter((entity) -> entity instanceof Player).map((entity) -> (Player) entity).collect(Collectors.toList());

        if(!players.isEmpty()){


            for(Player tp : players){

                for(int x = 0; x < data.size(); x++){

                    if( data.get(x).substring(0,2).equals("_p")){


                        if (data.get(x).substring(2).equals(tp.getDisplayName())){

                            launchArrow.shootToEntity(sentry, ((LivingEntity)tp));
                            return;
                        }
                    }
                }
            }
        }

        Collection<Entity> entities = sentry.getWorld().getNearbyEntities(box);
        ArrayList<Entity> nearby = new ArrayList(entities);

        for (Entity entity : entities) {

            for (int y = 0; y < data.size(); y++) {

                if (data.contains(entity.getType().toString())) {

                    launchArrow.shootToEntity(sentry, ((LivingEntity) entity));
                    return;
                }
            }

        }
    }
}