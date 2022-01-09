package com.galakrond.sentry.events;

import com.galakrond.sentry.Sentry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import org.bukkit.Server;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import static org.bukkit.Bukkit.getServer;


public class launchArrow {

    public static void shootToEntity(LivingEntity shooter, LivingEntity target) {

        if(shooter.isValid() == true) {

            Vector shooterv = shooter.getEyeLocation().toVector();
            shooterv.setY(shooterv.getY() + 1);
            Vector targetv = target.getEyeLocation().toVector();
            targetv.setY(targetv.getY() + 1.3);
            Vector normalize = targetv.subtract(shooterv).normalize();
            Vector vector = normalize.clone().multiply(2);

            Arrow arrow = shooter.launchProjectile(Arrow.class, vector);
            arrow.setDamage(5);
            arrow.setFallDistance(0.05f);
            arrow.setShooter(shooter);

        }

        else{
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Sentry] - Invalid Sentry Task Detected!( is Server OverLoaded or Lagging? )");
        }
    }

}
