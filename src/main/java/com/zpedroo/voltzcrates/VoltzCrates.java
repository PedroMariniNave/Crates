package com.zpedroo.voltzcrates;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.zpedroo.voltzcrates.commands.CratesCmd;
import com.zpedroo.voltzcrates.hooks.ProtocolLibHook;
import com.zpedroo.voltzcrates.listeners.PlayerGeneralListeners;
import com.zpedroo.voltzcrates.listeners.RewardItemListeners;
import com.zpedroo.voltzcrates.managers.CrateManager;
import com.zpedroo.voltzcrates.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;

import static com.zpedroo.voltzcrates.utils.config.Settings.*;

public class VoltzCrates extends JavaPlugin {

    private static VoltzCrates instance;
    public static VoltzCrates get() { return instance; }

    public void onEnable() {
        instance = this;
        new FileUtils(this);
        new CrateManager();

        ProtocolLibrary.getProtocolManager().addPacketListener(new ProtocolLibHook(this, PacketType.Play.Client.LOOK));

        registerCommand(COMMAND, ALIASES, PERMISSION, PERMISSION_MESSAGE, new CratesCmd());
        registerListeners();
    }

    public void onDisable() {
        new HashSet<>(CrateManager.getInstance().getDataCache().getPlacedCrates().values()).forEach(crate -> {
            crate.getShowRewardsTask().getItem().remove();
            crate.getShowRewardsTask().cancel();
            crate.getHologram().remove();
        });
    }

    private void registerCommand(String command, List<String> aliases, String permission, String permissionMessage, CommandExecutor executor) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            PluginCommand pluginCmd = constructor.newInstance(command, this);
            pluginCmd.setAliases(aliases);
            pluginCmd.setExecutor(executor);
            pluginCmd.setPermission(permission);
            pluginCmd.setPermissionMessage(permissionMessage);

            Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            commandMap.register(getName().toLowerCase(), pluginCmd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerGeneralListeners(), this);
        getServer().getPluginManager().registerEvents(new RewardItemListeners(), this);
    }
}