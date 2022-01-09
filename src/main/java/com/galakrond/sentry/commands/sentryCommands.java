package com.galakrond.sentry.commands;


import com.galakrond.sentry.Sentry;
import com.galakrond.sentry.events.launchArrow;
import com.galakrond.sentry.items.spawner;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class sentryCommands implements CommandExecutor {
    static FileConfiguration config;
    private Sentry sentry = Sentry.getPlugin(Sentry.class);

    public static void init(FileConfiguration con){
        config = con;

    }



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) { // 요청자가 Player 상속이 아니야? = 콘솔이나 커맨드 블록, 플레이어 아닌 대상이 명령어 실행 시
            sender.sendMessage("only players can use That Command!");
            return true;
        }

        Player player = (Player) sender; // player = 요청자


        if (cmd.getName().equalsIgnoreCase("getspawner")) {
            // ItemManager 가 아닌 여기서도 아이템을 생성할 수 는 있다.
            // 하지만, 커맨드를 돌릴 때마다 계속해서 새로운 아이템을 생성하기 때문에, 서버 자원의 낭비!

            player.getInventory().addItem(spawner.spawner); // 인벤토리 소매넣기


        }

        if (cmd.getName().equalsIgnoreCase("addplayer")) {

            if (args.length >= 1) {

                List<String> strings = new ArrayList<>();
                String name = ((Player) sender).getDisplayName();
                for(int x = 0; x < args.length; x++) {
                    try {
                        Player target = Bukkit.getPlayer(args[x]);
                        if (target != null) {
                            strings.add("_p" + args[x]); // _p 기억하기
                            player.sendMessage("추가중....");
                        }



                    }

                    catch (NullPointerException e) { // try | catch 문을 통해서 에러 잡아내고 리턴하기
                        e.printStackTrace();
                        player.sendMessage(ChatColor.RED + args[x] + "플레이어를 찾을 수 없습니다.");
                        player.sendMessage(ChatColor.RED + "언더바( _ )가 잘못되었거나, 오타가 있는지, 현재 서버 내에 있는지 확인해주세요.");
                    }


                }

                try {

                    try {
                        List<String> previous = config.getStringList(name);
                        player.sendMessage(" 이전에 작성한 내용과 합치고 있습니다... ");
                        strings.addAll(previous);

                    }
                    catch (NullPointerException e) {

                        player.sendMessage(" 첫 내용을 작성하고 있습니다.... ");
                        // not working

                    }

                    // sorting data

                    List<String> sorted = new ArrayList<>() ;
                    for(int x = 0; x < strings.toArray().length; x++) {

                        if(sorted.contains(strings.get(x))) {
                            player.sendMessage(ChatColor.RED + strings.get(x));
                            player.sendMessage(ChatColor.AQUA + "는 중복된 값입니다....");

                        }
                        else {
                            sorted.add(strings.get(x));
                        }

                    }


                    config.set(name, sorted);
                    sentry.saveConfig();
                    player.sendMessage(ChatColor.GOLD + "Done!");
                    player.sendMessage(ChatColor.AQUA + sorted.toString());

                }

                catch (IllegalArgumentException e) {
                    try {

                        List<String> isexist = config.getStringList(name);

                    }
                    catch (NullPointerException n)
                    {
                        player.sendMessage(ChatColor.RED + "UnExpected error - NullPointerException ");
                        n.printStackTrace();;
                    }

                    player.sendMessage(ChatColor.RED + "UnExpected error - IllegalArgumentException ");
                    e.printStackTrace();
                }

            }

            else {

                player.sendMessage(ChatColor.RED + "/addplayer player Moreplayer ");

            }

        }

        if (cmd.getName().equalsIgnoreCase("addtarget")) {

            if (args.length >= 1) {

                List<String> strings = new ArrayList<>();
                String name = ((Player) sender).getDisplayName();
                for(int x = 0; x < args.length; x++) {
                    try {
                        EntityType entity = EntityType.valueOf(args[x].toUpperCase()); // x 번째 인자에서 엔티티 이름 받아오기
                        if (entity != null) {
                            strings.add(args[x].toUpperCase());
                            player.sendMessage("추가중....");
                        }



                    }

                    catch (IllegalArgumentException e) { // try | catch 문을 통해서 에러 잡아내고 리턴하기
                        e.printStackTrace();
                        player.sendMessage(ChatColor.RED + args[x].toUpperCase() + "는 정상적인 엔티티가 아닙니다.");
                        player.sendMessage(ChatColor.RED + "언더바( _ )가 잘못되었거나, 오타가 있는지 확인해주세요.");
                    }


                }

                try {

                    try {
                        List<String> previous = config.getStringList(name);
                        player.sendMessage(" 이전에 작성한 내용과 합치고 있습니다... ");
                        strings.addAll(previous);

                    }
                    catch (NullPointerException e) {

                    player.sendMessage(" 첫 내용을 작성하고 있습니다.... ");
                    // not working

                    }

                    // sorting data

                    List<String> sorted = new ArrayList<>() ;
                    for(int x = 0; x < strings.toArray().length; x++) {

                        if(sorted.contains(strings.get(x))) {
                            player.sendMessage(ChatColor.RED + strings.get(x));
                            player.sendMessage(ChatColor.AQUA + "는 중복된 값입니다....");

                        }
                        else {
                            sorted.add(strings.get(x));
                        }

                    }


                    config.set(name, sorted);
                    sentry.saveConfig();
                    player.sendMessage(ChatColor.GOLD + "Done!");
                    player.sendMessage(ChatColor.AQUA + sorted.toString());

                }

                catch (IllegalArgumentException e) {
                    try {

                        List<String> isexist = config.getStringList(name);

                    }
                    catch (NullPointerException n)
                    {
                        player.sendMessage(ChatColor.RED + "UnExpected error - NullPointerException ");
                        n.printStackTrace();;
                    }

                    player.sendMessage(ChatColor.RED + "UnExpected error - IllegalArgumentException ");
                    e.printStackTrace();
                }

            }

            else {

                player.sendMessage(ChatColor.RED + "/addtarget Entity moreEntity ");

            }
        }

        if (cmd.getName().equalsIgnoreCase("removetarget")) {

            if (args.length == 1) {
                List<String> data = config.getStringList(player.getDisplayName());
                String name = ((Player) sender).getDisplayName();
                String target = args[0].toUpperCase();

                if(data.contains(target)){
                    data.remove(target);
                    config.set(name, data);
                    player.sendMessage(ChatColor.RED + target.toUpperCase());
                    player.sendMessage(ChatColor.AQUA + "가 삭제되었습니다.");
                    player.sendMessage(ChatColor.UNDERLINE + "현재 저장되어있는 엔티티 :");
                    player.sendMessage(ChatColor.AQUA + config.getStringList(name).toString());

                }

                else {

                    player.sendMessage(ChatColor.RED + target.toUpperCase());
                    player.sendMessage(ChatColor.RED + "가 저장된 데이터에 존재하지 않습니다.");
                    player.sendMessage(ChatColor.UNDERLINE + "현재 저장되어있는 엔티티 :");
                    player.sendMessage(ChatColor.AQUA + config.getStringList(name).toString());

                }



            }

            else {

                player.sendMessage(ChatColor.RED + "/removetarget Entity ");

            }

        }

        if (cmd.getName().equalsIgnoreCase("removeplayer")) {

            if (args.length == 1) {
                List<String> data = config.getStringList(player.getDisplayName());
                String name = ((Player) sender).getDisplayName();
                String target = "_p" + args[0];

                if(data.contains(target)){
                    data.remove(target);
                    config.set(name, data);
                    player.sendMessage(ChatColor.RED + target);
                    player.sendMessage(ChatColor.AQUA + "이(가) 삭제되었습니다.");
                    player.sendMessage(ChatColor.UNDERLINE + "현재 저장되어있는 내용:");
                    player.sendMessage(ChatColor.AQUA + config.getStringList(name).toString());

                }

                else {

                    player.sendMessage(ChatColor.RED + target);
                    player.sendMessage(ChatColor.RED + "이(가) 저장된 데이터에 존재하지 않습니다.");
                    player.sendMessage(ChatColor.UNDERLINE + "현재 저장되어있는 내용");
                    player.sendMessage(ChatColor.AQUA + config.getStringList(name).toString());

                }



            }

            else {

                player.sendMessage(ChatColor.RED + "/removeplayer PlayerName ");

            }

        }


        return true;
    }

}
