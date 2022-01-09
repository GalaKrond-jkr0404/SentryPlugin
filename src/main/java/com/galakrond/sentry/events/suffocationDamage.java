package com.galakrond.sentry.events;

import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


public class suffocationDamage implements Listener {

        @EventHandler
        public void SuffocationDamage(EntityDamageEvent event) {

            EntityDamageEvent.DamageCause damage = event.getCause();
            Entity entity = event.getEntity();
            EntityDamageEvent.DamageCause suff = EntityDamageEvent.DamageCause.SUFFOCATION;

            if(entity.getType() == EntityType.SNOWMAN && damage == suff) {
                Snowman sentry = (Snowman) event.getEntity();
                Player player = Bukkit.getPlayer(sentry.getCustomName());

                player.sendMessage(ChatColor.DARK_PURPLE + "센트리가 벽에 끼이거나, 경사로에 설치되었습니다.");

                sentry.damage(99999);

            }

        }
}
