package com.zpedroo.voltzcrates.objects;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Reward {

    private Double chance;
    private ItemStack display;
    private ItemStack itemToGive;
    private List<String> commands;

    public Reward(Double chance, ItemStack display, ItemStack itemToGive, List<String> commands) {
        this.chance = chance;
        this.display = display;
        this.itemToGive = itemToGive;
        this.commands = commands;
    }

    public Double getChance() {
        return chance;
    }

    public ItemStack getDisplay() {
        return display.clone();
    }

    public ItemStack getItemToGive() {
        if (itemToGive == null) return null; // fix null clone

        return itemToGive.clone();
    }

    public List<String> getCommands() {
        return commands;
    }
}