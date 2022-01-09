package com.galakrond.sentry.detect;

import com.galakrond.sentry.events.launchArrow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class detectTarget {

    // Not for Using, Most of Code Replaced in sentryTargetEvent.

//    static FileConfiguration config;
//    static Plugin plugin;
//
//
//    public static void init(FileConfiguration con, Plugin plug) {
//        config = con;
//        plugin = plug;
//
//    }

//    public void detectNearestEntity(LivingEntity sentry) {
//        Player player = Bukkit.getPlayer("_Chogath_");
//            player.sendMessage("run()");
//
//
//            Location location = sentry.getLocation();
//            BoundingBox box = new BoundingBox(location.getX() - 16, location.getY() - 16, location.getZ() - 16,
//                    location.getX() + 16, location.getY() + 16, location.getZ() + 16);
//
//            Collection<Entity> entities = sentry.getWorld().getNearbyEntities(box);
//            ArrayList<Entity> nearby = new ArrayList(entities);
//            List<String> data = config.getStringList(sentry.getCustomName());
//
//            for (Entity entity : entities) {
//
//                for (int y = 0; y < data.size(); y++) {
//
//
//                    if (data.contains(entity.getType().toString())) {
//
//                        launchArrow.shootToEntity(sentry, ((LivingEntity) entity));
//                        break;
//                    }
//                }
//            }
//    }

//    public static void startRepeatedTask(LivingEntity sentry1) {
//        long initialDelay = 0L;
//        long repeatedDelay = 30L;
//
//
//
//        BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
//        bukkitScheduler.runTaskTimer(plugin, task -> new BroadcastRunnable(task, sentry1), initialDelay, repeatedDelay);
//
//
//
//        new BukkitRunnable() {
//
//            LivingEntity entity = sentry1;
//
//            @Override
//            public void run() {
//                entityman(entity); // 만약 이런식으로 간다면 밑에 바운딩박스 코드 다 launcharrow로 옮기고, 그냥 sentry 하나만 계속 유지시키면 될거같음
//
//
//            }
//
//        }.runTaskTimer(plugin, initialDelay, repeatedDelay);
//    }
//
//    public static void entityman(LivingEntity e){
//        if (e.getCustomName() == "T H E") {
//            e.setCustomName("M E T H O D   M A N");
//        }
//        e.setCustomName("T H E");
//    }



//    public static class BroadcastRunnable implements Runnable {
//
//        private LivingEntity sentry;
//        private final BukkitTask task;
//
//
//        public BroadcastRunnable(BukkitTask Task, LivingEntity entity) {
//            this.sentry = entity;
//            this.task = Task;
//        }
//
//        @Override
//        public void run() {
//            Player player = Bukkit.getPlayer("_Chogath_");
//            player.sendMessage("run()");
//
//            if (sentry.isDead() == true) {
//                player.sendMessage("Task Loop Canceled Cus Snowman Died!");
//                this.task.cancel();
//            }
//
//            Location location = sentry.getLocation();
//            BoundingBox box = new BoundingBox(location.getX() - 16, location.getY() - 16, location.getZ() - 16,
//                    location.getX() + 16, location.getY() + 16, location.getZ() + 16);
//
//            Collection<Entity> entities = sentry.getWorld().getNearbyEntities(box);
//            ArrayList<Entity> nearby = new ArrayList(entities);
//            List<String> data = config.getStringList(sentry.getCustomName());
//
//            for (Entity entity : entities) {
//
//                for (int y = 0; y < data.size(); y++) {
//
//
//                    if (data.contains(entity.getType().toString())) {
//
//                        launchArrow.shootToEntity(sentry, ((LivingEntity) entity));
//                        break;
//                    }
//                }
//            }
//
//
//        }
//    }
}

