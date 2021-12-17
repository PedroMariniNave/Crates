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
        if (args.length == 1) {
            switch (args[0].toUpperCase()) {
                case "REMOVE":
                    if (player == null) return true;

                    Block block = player.getTargetBlock((HashSet<Byte>) null, 5);
                    if (block.getType().equals(Material.AIR)) {
                        player.sendMessage(Messages.NULL_BLOCK);
                        return true;
                    }

                    Location location = block.getLocation();
                    PlacedCrate placedCrate = CrateManager.getInstance().getPlacedCrate(location);
                    if (placedCrate == null) {
                        player.sendMessage(Messages.NULL_CRATE);
                        return true;
                    }

                    CrateManager.getInstance().delete(placedCrate);
                    return true;
            }
        }
        if (args.length == 2) {
            switch (args[0].toUpperCase()) {
                case "SET":
                    if (player == null) return true;

                    Block block = player.getTargetBlock((HashSet<Byte>) null, 5);
                    if (block.getType().equals(Material.AIR)) {
                        player.sendMessage(Messages.NULL_BLOCK);
                        return true;
                    }

                    Location location = block.getLocation();
                    PlacedCrate placedCrate = CrateManager.getInstance().getPlacedCrate(location);
                    if (placedCrate != null) {
                        player.sendMessage(Messages.EXISTING_CRATE);
                        return true;
                    }

                    Crate crate = CrateManager.getInstance().getCrate(args[1]);
                    if (crate == null) {
                        player.sendMessage(Messages.INVALID_CRATE);
                        return true;
                    }

                    CrateManager.getInstance().create(location, crate);
                    return true;
            }
        }

        if (args.length >= 4) {
            switch (args[0].toUpperCase()) {
                case "KEY":
                case "GIVE":
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Messages.OFFLINE_PLAYER);
                        return true;
                    }

                    Crate crate = CrateManager.getInstance().getCrate(args[2]);
                    if (crate == null) {
                        sender.sendMessage(Messages.INVALID_CRATE);
                        return true;
                    }

                    Integer amount = null;
                    try {
                        amount = Integer.parseInt(args[3]);
                    } catch (Exception ex) {
                        // ignore
                    }

                    if (amount == null || amount <= 0) {
                        sender.sendMessage(Messages.INVALID_AMOUNT);
                        return true;
                    }

                    ItemStack key = crate.getKey();
                    key.setAmount(amount);

                    Integer freeSpace = InventoryManager.getFreeSpace(target, key);
                    Integer toDrop = amount - freeSpace;

                    target.getInventory().addItem(key);
                    if (toDrop <= 0) return true;

                    key.setAmount(toDrop);
                    target.getWorld().dropItemNaturally(target.getLocation(), key);
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