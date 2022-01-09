package com.galakrond.sentry.items;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class spawner {

    public static ItemStack spawner;


    public static void init() {
        create_spawner(); // 이런 식으로 init 에서 호출되도록 하면, 아이템을 초기화하고 Main Class 에서 부를 때 로드될 것임.

    }

    private static void create_spawner() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);


        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§b Sentry Spawner");


        List<String> lore = new ArrayList<>();

        lore.add("§6우클릭으로 아이템을 사용해 센트리를 소환합니다");
        lore.add("§e센트리가 경사로나 벽에 끼일 경우, 아이템을 다시 드롭하고 죽습니다.");

        lore.add("§4물,비에 취약하니 설치할 때 주의를 요합니다.");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.LUCK, 1, false);


        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);


        item.setItemMeta(meta);


        spawner = item;


    }



}

