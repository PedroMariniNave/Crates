package com.zpedroo.voltzcrates.utils.config;

import com.zpedroo.voltzcrates.utils.FileUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    public static final List<String> COMMAND_USAGE = getColored(FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Messages.command-usage"));

    public static final String NULL_BLOCK = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.null-block"));

    public static final String NULL_CRATE = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.null-crate"));

    public static final String INVALID_CRATE = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.invalid-crate"));

    public static final String INVALID_AMOUNT = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.invalid-amount"));

    public static final String EXISTING_CRATE = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.existing-crate"));

    public static final String OFFLINE_PLAYER = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.offline-player"));

    public static final String NEED_KEY = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.need-key"));

    public static final String NEED_SPACE = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.need-space"));

    public static final String GIVE_ALL = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.give-all"));

    private static String getColored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private static List<String> getColored(List<String> list) {
        List<String> colored = new ArrayList<>();
        for (String str : list) {
            colored.add(getColored(str));
        }

        return colored;
    }
}