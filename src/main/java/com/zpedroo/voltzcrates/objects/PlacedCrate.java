package com.zpedroo.voltzcrates.objects;

import com.zpedroo.voltzcrates.tasks.ShowRewardsTask;
import org.bukkit.Location;

public class PlacedCrate {

    private Location location;
    private Crate crate;
    private CrateHologram hologram;
    private ShowRewardsTask showRewardsTask;

    public PlacedCrate(Location location, Crate crate) {
        this.location = location;
        this.crate = crate;
        this.hologram = new CrateHologram(this);
        this.showRewardsTask = new ShowRewardsTask(this);
    }

    public Location getLocation() {
        return location;
    }

    public Crate getCrate() {
        return crate;
    }

    public CrateHologram getHologram() {
        return hologram;
    }

    public ShowRewardsTask getShowRewardsTask() {
        return showRewardsTask;
    }

    public void delete() {
        hologram.remove();
        showRewardsTask.getItem().remove();
        showRewardsTask.cancel();
    }
}