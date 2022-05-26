package com.zpedroo.voltzcrates.managers.cache;

import com.zpedroo.voltzcrates.objects.Crate;
import com.zpedroo.voltzcrates.objects.PlacedCrate;
import org.bukkit.Location;

import java.util.*;

public class DataCache {

    private Map<String, Crate> crates;
    private Map<Location, PlacedCrate> placedCrates;

    public DataCache() {
        this.crates = new HashMap<>(8);
        this.placedCrates = new HashMap<>(8);
    }

    public Map<String, Crate> getCrates() {
        return crates;
    }

    public Map<Location, PlacedCrate> getPlacedCrates() {
        return placedCrates;
    }
}