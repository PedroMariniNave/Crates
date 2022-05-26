package com.zpedroo.voltzcrates.commands;

import com.zpedroo.voltzcrates.managers.CrateManager;
import com.zpedroo.voltzcrates.managers.InventoryManager;
import com.zpedroo.voltzcrates.objects.Crate;
import com.zpedroo.voltzcrates.objects.PlacedCrate;
import com.zpedroo.voltzcrates.utils.config.Messages;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class CratesCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length > 0) {
            Block block = null;
            Location location = null;
            Crate crate = null;
            PlacedCrate placedCrate = null;
            ItemStack keyItem = null;
            int amount = 0;
            switch (args[0].toUpperCase()) {
                case "REMOVE":
                    if (player == null) break;

                    block = player.getTargetBlock((HashSet<Byte>) null, 5);
                    if (block.getType().equals(Material.AIR)) {
                        player.sendMessage(Messages.NULL_BLOCK);
                        return true;
                    }

                    location = block.getLocation();
                    placedCrate = CrateManager.getInstance().getPlacedCrate(location);
                    if (placedCrate == null) {
                        player.sendMessage(Messages.NULL_CRATE);
                        return true;
                    }

                    CrateManager.getInstance().delete(placedCrate);
                    return true;
                case "SET":
                    if (player == null || args.length < 2) break;

                    block = player.getTargetBlock((HashSet<Byte>) null, 5);
                    if (block.getType().equals(Material.AIR)) {
                        player.sendMessage(Messages.NULL_BLOCK);
                        return true;
                    }

                    location = block.getLocation();
                    placedCrate = CrateManager.getInstance().getPlacedCrate(location);
                    if (placedCrate != null) {
                        player.sendMessage(Messages.EXISTING_CRATE);
                        return true;
                    }

                    crate = CrateManager.getInstance().getCrate(args[1]);
                    if (crate == null) {
                        player.sendMessage(Messages.INVALID_CRATE);
                        return true;
                    }

                    CrateManager.getInstance().create(location, crate);
                    return true;
                case "KEY":
                case "GIVE":
                    if (args.length < 4) break;

                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Messages.OFFLINE_PLAYER);
                        return true;
                    }

                    crate = CrateManager.getInstance().getCrate(args[2]);
                    if (crate == null) {
                        sender.sendMessage(Messages.INVALID_CRATE);
                        return true;
                    }

                    try {
                        amount = Integer.parseInt(args[3]);
                    } catch (Exception ex) {
                        // ignore
                    }

                    if (amount <= 0) {
                        sender.sendMessage(Messages.INVALID_AMOUNT);
                        return true;
                    }

                    keyItem = crate.getKey();
                    keyItem.setAmount(amount);

                    int freeSpace = InventoryManager.getFreeSpace(target, keyItem);
                    int toDrop = amount - freeSpace;

                    target.getInventory().addItem(keyItem);
                    if (toDrop <= 0) return true;

                    keyItem.setAmount(toDrop);
                    target.getWorld().dropItemNaturally(target.getLocation(), keyItem);
                    return true;
                case "GIVEALL":
                    if (args.length < 3) break;

                    crate = CrateManager.getInstance().getCrate(args[1]);
                    if (crate == null) {
                        sender.sendMessage(Messages.INVALID_CRATE);
                        return true;
                    }

                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (Exception ex) {
                        // ignore
                    }

                    if (amount <= 0) {
                        sender.sendMessage(Messages.INVALID_AMOUNT);
                        return true;
                    }

                    keyItem = crate.getKey();
                    keyItem.setAmount(amount);

                    final ItemStack finalKeyItem = keyItem;
                    final int finalAmount = amount;
                    final Crate finalCrate = crate;
                    Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                        onlinePlayer.sendMessage(StringUtils.replaceEach(Messages.GIVE_ALL, new String[]{
                                "{amount}",
                                "{key}"
                        }, new String[]{
                                String.valueOf(finalAmount),
                                finalKeyItem.hasItemMeta() ?
                                        finalKeyItem.getItemMeta().hasDisplayName() ?
                                                finalKeyItem.getItemMeta().getDisplayName() : finalCrate.getName() : finalCrate.getName()
                        }));

                        int space = InventoryManager.getFreeSpace(onlinePlayer, finalKeyItem);
                        int amountToDrop = finalAmount - space;

                        onlinePlayer.getInventory().addItem(finalKeyItem);
                        if (amountToDrop <= 0) return;

                        finalKeyItem.setAmount(amountToDrop);
                        onlinePlayer.getWorld().dropItemNaturally(onlinePlayer.getLocation(), finalKeyItem);
                    });
                    return true;
            }
        }

        for (String msg : Messages.COMMAND_USAGE) {
            sender.sendMessage(StringUtils.replaceEach(msg, new String[]{
                    "{cmd}"
            }, new String[]{
                    label
            }));
        }
        return false;
    }
}