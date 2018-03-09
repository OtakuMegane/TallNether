package com.minefit.xerxestireiron.tallnether;

import java.util.List;

public class ServerVersion {

    private final TallNether plugin;
    private final String nmsVersion;
    private final String major;
    private final String minor;
    private final String revision;

    public ServerVersion(TallNether instance) {
        this.plugin = instance;
        String name = this.plugin.getServer().getClass().getPackage().getName();
        this.nmsVersion = name.substring(name.lastIndexOf(".") + 1);
        String[] vn = this.nmsVersion.split("_");
        this.major = vn[0];
        this.minor = vn[1];
        this.revision = vn[2];
    }

    public boolean compatibleVersion(List<String> list) {
        return list.contains(this.nmsVersion);
    }

    public String getMajor() {
        return this.major;
    }

    public String getMinor() {
        return this.minor;
    }

    public String getRevision() {
        return this.revision;
    }

    public String getNMSVersion() {
        return this.nmsVersion;
    }
}
