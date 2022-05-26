package com.zpedroo.voltzcrates.utils.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
<<<<<<< HEAD
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
=======
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

<<<<<<< HEAD
    private final ItemStack item;

    public ItemBuilder(@NotNull Material material, int amount, short durability) {
=======
    private ItemStack item;

    private Method metaSetProfileMethod;
    private Field metaProfileField;

    public ItemBuilder(Material material, int amount, short durability) {
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98
        if (StringUtils.equals(material.toString(), "SKULL_ITEM")) {
            this.item = new ItemStack(material, amount, (short) 3);
        } else {
            this.item = new ItemStack(material, amount, durability);
        }
    }

<<<<<<< HEAD
    public ItemBuilder(@NotNull ItemStack item) {
        this.item = item;
    }

=======
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98
    public static ItemBuilder build(FileConfiguration file, String where) {
        return build(file, where, null, null);
    }

<<<<<<< HEAD
    public static ItemBuilder build(FileConfiguration file, String where, String[] placeholders, String[] replacers) {
        String type = StringUtils.replaceEach(file.getString(where + ".type"), placeholders, replacers);
        short data = (short) file.getInt(where + ".data", 0);
        int amount = file.getInt(where + ".amount", 1);

        Material material = Material.getMaterial(type);
        Validate.notNull(material, "Material cannot be null! Check your item configs. Invalid material: " + type);
=======
    public static ItemBuilder build(FileConfiguration file, String where, String[] placeholders, String[] replaces) {
        String type = StringUtils.replace(file.getString(where + ".type"), " ", "").toUpperCase();
        short data = (short) (file.contains(where + ".data") ? file.getInt(where + ".data") : 0);
        int amount = file.getInt(where + ".amount", 1);

        Material material = Material.getMaterial(StringUtils.replaceEach(type, placeholders, replaces));
        Validate.notNull(material, "Material cannot be null! Check your item configs.");
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98

        ItemBuilder builder = new ItemBuilder(material, amount, data);

        if (file.contains(where + ".name")) {
            String name = ChatColor.translateAlternateColorCodes('&', file.getString(where + ".name"));
<<<<<<< HEAD
            builder.setName(StringUtils.replaceEach(name, placeholders, replacers));
        }

        if (file.contains(where + ".lore")) {
            builder.setLore(file.getStringList(where + ".lore"), placeholders, replacers);
=======
            builder.setName(StringUtils.replaceEach(name, placeholders, replaces));
        }

        if (file.contains(where + ".lore")) {
            builder.setLore(file.getStringList(where + ".lore"), placeholders, replaces);
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98
        }

        if (file.contains(where + ".owner")) {
            String owner = file.getString(where + ".owner");

            if (owner.length() <= 16) {
<<<<<<< HEAD
                builder.setSkullOwner(StringUtils.replaceEach(owner, placeholders, replacers));
=======
                builder.setSkullOwner(StringUtils.replaceEach(owner, placeholders, replaces));
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98
            } else {
                builder.setCustomTexture(owner);
            }
        }

        if (file.contains(where + ".potions")) {
            for (String potion : file.getConfigurationSection(where + ".potions").getKeys(false)) {
                if (potion == null) continue;

                String potionType = file.getString(where + ".potions." + potion + ".type");
                int duration = file.getInt(where + ".potions." + potion + ".duration") * 20;
                int amplifier = file.getInt(where + ".potions." + potion + ".amplifier") - 1;

                builder.addPotion(potionType, duration, amplifier);
            }
        }

        if (file.contains(where + ".glow") && file.getBoolean(where + ".glow")) {
            builder.setGlow();
        }

        if (file.contains(where + ".enchants")) {
            for (String str : file.getStringList(where + ".enchants")) {
                if (str == null) continue;

                String enchantment = StringUtils.replace(str, " ", "");

                if (StringUtils.contains(enchantment, ",")) {
                    String[] split = enchantment.split(",");
                    builder.addEnchantment(Enchantment.getByName(split[0]), Integer.parseInt(split[1]));
                } else {
                    builder.addEnchantment(Enchantment.getByName(enchantment));
                }
            }
        }

        if (file.contains(where + ".hide-attributes") && file.getBoolean(where + ".hide-attributes")) {
            builder.hideAttributes();
        }

<<<<<<< HEAD
        if (file.contains(where + ".hide-enchants") && file.getBoolean(where + ".hide-enchants")) {
            builder.hideEnchants();
        }

        return builder;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore, String[] placeholders, String[] replacers) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
=======
        return builder;
    }

    private void setName(String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
    }

    private void setLore(List<String> lore, String[] placeholders, String[] replacers) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98

        List<String> newLore = new ArrayList<>(lore.size());

        for (String str : lore) {
            newLore.add(ChatColor.translateAlternateColorCodes('&', StringUtils.replaceEach(str, placeholders, replacers)));
        }

        meta.setLore(newLore);
        item.setItemMeta(meta);
<<<<<<< HEAD
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment) {
        return addEnchantment(enchantment, 1);
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        if (enchantment == null) return this;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;

        if (item.getType().equals(Material.ENCHANTED_BOOK)) {
            ((EnchantmentStorageMeta) meta).addStoredEnchant(enchantment, level, true);
        } else {
            meta.addEnchant(enchantment, level, true);
        }

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addPotion(String type, int duration, int amplifier) {
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        if (meta == null) return this;

        PotionEffectType potionEffectType = PotionEffectType.getByName(type);
        if (potionEffectType == null) return this;
=======
    }

    private void addEnchantment(Enchantment enchantment) {
        addEnchantment(enchantment, 1);
    }

    private void addEnchantment(Enchantment enchantment, int level) {
        if (enchantment == null) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
    }

    private void setGlow() {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    private void hideAttributes() {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
    }

    private void addPotion(String type, int duration, int amplifier) {
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        if (meta == null) return;

        PotionEffectType potionEffectType = PotionEffectType.getByName(type);
        if (potionEffectType == null) return;
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98

        PotionEffect potionEffect = new PotionEffect(potionEffectType, duration, amplifier);

        meta.addCustomEffect(potionEffect, true);

        PotionType potionType = PotionType.getByEffect(potionEffectType);
        if (potionType != null) {
            meta.addCustomEffect(potionEffect, true);
        }
<<<<<<< HEAD

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setGlow() {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;

        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hideAttributes() {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hideEnchants() {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        if (owner == null || owner.isEmpty()) return this;

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (meta == null) return this;

        meta.setOwner(owner);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setCustomTexture(String base64) {
        if (base64 == null || base64.isEmpty()) return this;
=======
        
        item.setItemMeta(meta);
    }

    private void setSkullOwner(String owner) {
        if (!StringUtils.contains(item.getType().toString(), "SKULL_ITEM")) return;
        if (owner == null || owner.isEmpty()) return;

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (meta == null) return;

        meta.setOwner(owner);
        item.setItemMeta(meta);
    }

    private void setCustomTexture(String base64) {
        if (!StringUtils.contains(item.getType().toString(), "SKULL_ITEM")) return;
        if (base64 == null || base64.isEmpty()) return;
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        setCustomTexture(meta, base64);
        item.setItemMeta(meta);
<<<<<<< HEAD
        return this;
    }

    public ItemBuilder setCustomTexture(SkullMeta meta, String base64) {
        try {
            Method metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            metaSetProfileMethod.setAccessible(true);
            metaSetProfileMethod.invoke(meta, createProfile(base64));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            try {
                Field metaProfileField = meta.getClass().getDeclaredField("profile");
                metaProfileField.setAccessible(true);
                metaProfileField.set(meta, createProfile(base64));
=======
    }

    private void setCustomTexture(SkullMeta meta, String base64) {
        try {
            if (metaSetProfileMethod == null) {
                metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                metaSetProfileMethod.setAccessible(true);
            }
            metaSetProfileMethod.invoke(meta, createProfile(base64));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            try {
                if (metaProfileField == null) {
                    metaProfileField = meta.getClass().getDeclaredField("profile");
                    metaProfileField.setAccessible(true);
                }
                metaProfileField.set(meta, createProfile(base64));

>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98
            } catch (NoSuchFieldException | IllegalAccessException ex2) {
                ex2.printStackTrace();
            }
        }
<<<<<<< HEAD

        return this;
    }

    private GameProfile createProfile(String base64) {
        UUID uuid = new UUID(base64.substring(base64.length() - 20).hashCode(), base64.substring(base64.length() - 10).hashCode());
=======
    }

    private GameProfile createProfile(String base64) {
        UUID uuid = new UUID(
                base64.substring(base64.length() - 20).hashCode(),
                base64.substring(base64.length() - 10).hashCode()
        );
>>>>>>> 3b71325d45999be59b70484b7c31cb0745c4ba98
        GameProfile profile = new GameProfile(uuid, "Player");
        profile.getProperties().put("textures", new Property("textures", base64));

        return profile;
    }

    public ItemStack build() {
        return item.clone();
    }
}