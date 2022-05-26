package com.zpedroo.voltzcrates.objects;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class Crate {

    private File file;
    private String name;
    private ItemStack key;
    private String[] hologramLines;
    private List<Reward> rewards;

    public Crate(File file, String name, ItemStack key, String[] hologramLines, List<Reward> rewards) {
        this.file = file;
        this.name = name;
        this.key = key;
        this.hologramLines = hologramLines;
        this.rewards = rewards;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public ItemStack getKey() {
        NBTItem nbt = new NBTItem(key.clone());
        nbt.setString("CrateKey", name);

        return nbt.getItem();
    }

    public String[] getHologramLines() {
        return hologramLines;
    }

    public List<Reward> getRewards() {
        return rewards;
    }
}