package com.galakrond.sentry.events;

import com.galakrond.sentry.Sentry;
import com.galakrond.sentry.items.spawner;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class sentryTargetEvent implements Listener {

    private EntityType entityType;
    static FileConfiguration config;
    static int onEnableSentryLoad;

    public static void init(FileConfiguration con){
        config = con;
        onEnableSentryLoad = 0 ;

    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                if (event.getItem().getItemMeta().equals(spawner.spawner.getItemMeta())) {
                    Player player = event.getPlayer();

                    Location loca = player.getTargetBlock(null,15).getLocation();

                    loca.setY(loca.getY()+1);
                    spawnSentry(loca, player);

                }
            }

        }
    }

    @EventHandler
    public void sentryHarm(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Snowman || event.getEntity() instanceof Snowman) {

            if (event.getDamager() instanceof Projectile) {
                Projectile projectile;
                LivingEntity attacker = (LivingEntity) ((Projectile) event.getDamager()).getShooter();
                LivingEntity defender = (LivingEntity) event.getEntity();

                if ((defender.getEquipment().getChestplate() == new ItemStack(Material.VILLAGER_SPAWN_EGG))) {
                    // On Sentry Got Hit By Projectile Shooter.
                } else if ((attacker.getEquipment().getChestplate() == new ItemStack(Material.VILLAGER_SPAWN_EGG) && defender.getHealth() - event.getDamage() <= 0)) {
                    // On Sentry Killed Some Entity.
                }
            } else if (event.getDamager() instanceof LivingEntity) {

                LivingEntity defender = (LivingEntity) event.getEntity();
                LivingEntity attacker = (LivingEntity) event.getDamager();

                //

                if ((defender.getEquipment().getChestplate() == new ItemStack(Material.VILLAGER_SPAWN_EGG))) {
                    // On Sentry Got Hit By Projectile Shooter.
                } else if ((attacker.getEquipment().getChestplate() == new ItemStack(Material.VILLAGER_SPAWN_EGG) && defender.getHealth() - event.getDamage() <= 0)) {
                    // On Sentry Killed Some Entity.
                }
            }
        }
    }

    @EventHandler
    public void removesnowball(ProjectileLaunchEvent event) {

        Projectile projectile = (Projectile) event.getEntity();
        LivingEntity shooter = (LivingEntity) projectile.getShooter();
        if (shooter.getType() == EntityType.SNOWMAN){
            if(event.getEntityType() == EntityType.SNOWBALL) { // 눈덩이 맞음?


                if(shooter.getEquipment().getChestplate().getType() == Material.VILLAGER_SPAWN_EGG ) {
                    event.getEntity().remove();
                }

            }
        }
    }

    @EventHandler
    public void runSentryWorld(WorldLoadEvent event){
        for(Chunk chunk : event.getWorld().getLoadedChunks()) {
            for(Entity entity : chunk.getEntities()){
                if (entity.getType() == EntityType.SNOWMAN){
                    LivingEntity Livingentity = (LivingEntity)entity;
                    if(Livingentity.getEquipment().getChestplate().getType() == Material.VILLAGER_SPAWN_EGG) {
                        Bukkit.getServer().getConsoleSender().sendMessage(" - Sentry Loaded by World Load Event - "); }
                    new SentryTask(Livingentity).runTaskTimer(JavaPlugin.getPlugin(Sentry.class), 0, 30);
                }
            }
        }
    }

    @EventHandler
    public void disableSentryWorld(WorldUnloadEvent event) {
        NamespacedKey key = new NamespacedKey(JavaPlugin.getPlugin(Sentry.class), "taskNo");
        for (Chunk chunk : event.getWorld().getLoadedChunks()) {
            for(Entity entity : chunk.getEntities()) {

                if (entity.getType() == EntityType.SNOWMAN) {

                    LivingEntity livingEntity = (LivingEntity) entity;

                    if (livingEntity.getEquipment().getChestplate().getType() == Material.VILLAGER_SPAWN_EGG) {

                    PersistentDataContainer container = entity.getPersistentDataContainer();
                    Bukkit.getScheduler().cancelTask(container.get(key, PersistentDataType.INTEGER));

                    }
                }
            }
        }

    }

    @EventHandler
    public void disableSentryChunk(ChunkUnloadEvent event) {
        NamespacedKey key = new NamespacedKey(JavaPlugin.getPlugin(Sentry.class), "taskNo");
        for(Entity entity : event.getChunk().getEntities()) {

            if (entity.getType() == EntityType.SNOWMAN) {

                LivingEntity livingEntity = (LivingEntity) entity;

                if (livingEntity.getEquipment().getChestplate().getType() == Material.VILLAGER_SPAWN_EGG) {

                    PersistentDataContainer container = entity.getPersistentDataContainer();
                    Bukkit.getScheduler().cancelTask(container.get(key, PersistentDataType.INTEGER));

                }
            }
        }

    }

    @EventHandler
    public void runSentryChunk(ChunkLoadEvent event){
        for(Entity entity : event.getChunk().getEntities()){
            if (entity.getType() == EntityType.SNOWMAN){
                LivingEntity Livingentity = (LivingEntity)entity;
                if(Livingentity.getEquipment().getChestplate().getType() == Material.VILLAGER_SPAWN_EGG) {
                Bukkit.getServer().getConsoleSender().sendMessage(" - Sentry Loaded by Chunk Load - "); }
                new SentryTask(Livingentity).runTaskTimer(JavaPlugin.getPlugin(Sentry.class), 0, 30);
            }
        }
    }

    public void spawnSentry(Location location, Player player) {

        PotionEffect sentryFireResistance = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, false, false);
        PotionEffect sentrySlowness = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 7, false, false);
        PotionEffect sentryRegen = new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2, false, false);
        PotionEffect sentryDisableJump = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 249, false, false); // 오버플로
        // MAX_VALUE 를 사용해야지만 영구히 지속되는 버프를 얻을 수 있음!

        Snowman sentry = (Snowman) player.getWorld().spawnEntity(location, EntityType.SNOWMAN);

        ((LivingEntity) sentry).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50);
        ((LivingEntity) sentry).setHealth(50);
        ((LivingEntity) sentry).getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);


        EntityEquipment sentryitems = sentry.getEquipment();
        ItemStack sentryitem = new ItemStack(Material.VILLAGER_SPAWN_EGG);
        sentryitems.setChestplate(sentryitem, true);



        ((LivingEntity) sentry).setCustomName(player.getDisplayName());
        ((LivingEntity) sentry).setSilent(true);
        ((LivingEntity) sentry).setCustomNameVisible(true);
        ((LivingEntity) sentry).addPotionEffect(sentryFireResistance); // 화염 저항 = 따뜻한 바이옴에서도 피해를 받지 않음.
        ((LivingEntity) sentry).addPotionEffect(sentrySlowness); // 둔화 7+ = 이동 불가를 걸어줌.
        ((LivingEntity) sentry).addPotionEffect(sentryDisableJump);
        ((LivingEntity) sentry).addPotionEffect(sentryRegen);
        ((LivingEntity) sentry).getEquipment().setChestplateDropChance(0);
        ((LivingEntity) sentry).getEquipment().setBootsDropChance(0);
        sentry.setDerp(true);

        SentryTask sentryTask = new sentryTargetEvent.SentryTask(sentry);
        sentryTask.runTaskTimer(JavaPlugin.getPlugin(Sentry.class), 0, 30);


        player.sendMessage("§6Spawn completed");
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
        List<String> data = config.getStringList(sentry.getCustomName());

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
