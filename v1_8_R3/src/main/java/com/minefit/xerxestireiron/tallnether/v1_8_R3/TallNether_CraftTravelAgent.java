package com.minefit.xerxestireiron.tallnether.v1_8_R3;

import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.BlockPortal;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.LongHashMap;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PortalTravelAgent;
import net.minecraft.server.v1_8_R3.WorldServer;

import org.bukkit.Location;
import org.bukkit.TravelAgent;
import org.bukkit.craftbukkit.v1_8_R3.CraftTravelAgent;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import com.google.common.collect.Lists;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TallNether_CraftTravelAgent extends CraftTravelAgent implements TravelAgent {

    WorldServer a;
    private final Random b;
    private final LongHashMap<PortalTravelAgent.ChunkCoordinatesPortal> c = new LongHashMap();
    private final List<Long> d = Lists.newArrayList();

    public TallNether_CraftTravelAgent(WorldServer worldserver) {
        super(worldserver);
        this.a = worldserver;
        this.b = new Random(this.a.getSeed());
    }

    @Override
    public Location findOrCreate(Location target) {
        WorldServer worldServer = ((CraftWorld) target.getWorld()).getHandle();
        boolean before = worldServer.chunkProviderServer.forceChunkLoad;
        worldServer.chunkProviderServer.forceChunkLoad = true;

        Location found = this.findPortal(target);
        if (found == null) {
            if (this.getCanCreatePortal() && this.createPortal(target)) {
                found = this.findPortal(target);
            } else {
                found = target; // fallback to original if unable to find or
                                // create
            }
        }

        worldServer.chunkProviderServer.forceChunkLoad = before;
        return found;
    }

    @Override
    public Location findPortal(Location location) {
        PortalTravelAgent pta = this;
        BlockPosition found = pta.findPortal(location.getX(), location.getY(), location.getZ(), this.getSearchRadius());
        return found != null ? new Location(location.getWorld(), found.getX(), found.getY(), found.getZ(),
                location.getYaw(), location.getPitch()) : null;
    }

    @Override
    public boolean createPortal(Location location) {
        PortalTravelAgent pta = this;
        return pta.createPortal(location.getX(), location.getY(), location.getZ(), this.getCreationRadius());
    }

    @Override
    public BlockPosition findPortal(double x, double y, double z, int short1) {
        // CraftBukkit end
        double d0 = -1.0D;
        // CraftBukkit start
        int i = MathHelper.floor(x);
        int j = MathHelper.floor(z);
        // CraftBukkit end
        boolean flag1 = true;
        Object object = BlockPosition.ZERO;
        long k = ChunkCoordIntPair.a(i, j);

        if (this.c.contains(k) && !purgeCache(k)) {
            PortalTravelAgent.ChunkCoordinatesPortal portaltravelagent_chunkcoordinatesportal = (PortalTravelAgent.ChunkCoordinatesPortal) this.c.getEntry(k);

            d0 = 0.0D;
            object = portaltravelagent_chunkcoordinatesportal;
            portaltravelagent_chunkcoordinatesportal.c = this.a.getTime();
            flag1 = false;
        } else {
            BlockPosition blockposition = new BlockPosition(x, y, z);

            for (int l = short1 * -1; l <= short1; ++l) {
                BlockPosition blockposition1;

                for (int i1 = short1 * -1; i1 <= short1; ++i1) {
                    for (BlockPosition blockposition2 = blockposition.a(l, 256 - 1 - blockposition.getY(),
                            i1); blockposition2.getY() >= 0; blockposition2 = blockposition1) {
                        blockposition1 = blockposition2.down();
                        if (this.a.getType(blockposition2).getBlock() == Blocks.PORTAL) {
                            while (this.a.getType(blockposition1 = blockposition2.down()).getBlock() == Blocks.PORTAL) {
                                blockposition2 = blockposition1;
                            }

                            double d1 = blockposition2.i(blockposition);

                            if (d0 < 0.0D || d1 < d0) {
                                d0 = d1;
                                object = blockposition2;
                            }
                        }
                    }
                }
            }
        }

        if (d0 >= 0.0D) {
            if (flag1) {
                this.c.put(k, new PortalTravelAgent.ChunkCoordinatesPortal((BlockPosition) object, this.a.getTime()));
                this.d.add(Long.valueOf(k));
            }
            // CraftBukkit start - Move entity teleportation logic into exit
            return (BlockPosition) object;
        } else {
            return null;
        }
    }

    public boolean createPortal(double x, double y, double z, int b0) {
        // CraftBukkit end
        double d0 = -1.0D;
        // CraftBukkit start
        int i = MathHelper.floor(x);
        int j = MathHelper.floor(y);
        int k = MathHelper.floor(z);
        // CraftBukkit end
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = this.b.nextInt(4);
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

        int i2;
        double d1;
        int j2;
        double d2;
        int k2;
        int l2;
        int i3;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        double d3;
        double d4;

        for (i2 = i - b0; i2 <= i + b0; ++i2) {
            d1 = (double) i2 + 0.5D - x; // CraftBukkit

            for (j2 = k - b0; j2 <= k + b0; ++j2) {
                d2 = (double) j2 + 0.5D - z; // CraftBukkit

                label271: for (k2 = 256 - 1; k2 >= 0; --k2) {
                    if (this.a.isEmpty(blockposition_mutableblockposition.c(i2, k2, j2))) {
                        while (k2 > 0 && this.a.isEmpty(blockposition_mutableblockposition.c(i2, k2 - 1, j2))) {
                            --k2;
                        }

                        for (l2 = l1; l2 < l1 + 4; ++l2) {
                            i3 = l2 % 2;
                            j3 = 1 - i3;
                            if (l2 % 4 >= 2) {
                                i3 = -i3;
                                j3 = -j3;
                            }

                            for (k3 = 0; k3 < 3; ++k3) {
                                for (l3 = 0; l3 < 4; ++l3) {
                                    for (i4 = -1; i4 < 4; ++i4) {
                                        j4 = i2 + (l3 - 1) * i3 + k3 * j3;
                                        k4 = k2 + i4;
                                        int l4 = j2 + (l3 - 1) * j3 - k3 * i3;

                                        blockposition_mutableblockposition.c(j4, k4, l4);
                                        if (i4 < 0
                                                && !this.a.getType(blockposition_mutableblockposition).getBlock()
                                                        .getMaterial().isBuildable()
                                                || i4 >= 0 && !this.a.isEmpty(blockposition_mutableblockposition)) {
                                            continue label271;
                                        }
                                    }
                                }
                            }

                            d3 = (double) k2 + 0.5D - y; // CraftBukkit
                            d4 = d1 * d1 + d3 * d3 + d2 * d2;
                            if (d0 < 0.0D || d4 < d0) {
                                d0 = d4;
                                l = i2;
                                i1 = k2;
                                j1 = j2;
                                k1 = l2 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D) {
            for (i2 = i - b0; i2 <= i + b0; ++i2) {
                d1 = (double) i2 + 0.5D - x; // CraftBukkit

                for (j2 = k - b0; j2 <= k + b0; ++j2) {
                    d2 = (double) j2 + 0.5D - z; // CraftBukkit

                    label219: for (k2 = 256 - 1; k2 >= 0; --k2) {
                        if (this.a.isEmpty(blockposition_mutableblockposition.c(i2, k2, j2))) {
                            while (k2 > 0 && this.a.isEmpty(blockposition_mutableblockposition.c(i2, k2 - 1, j2))) {
                                --k2;
                            }

                            for (l2 = l1; l2 < l1 + 2; ++l2) {
                                i3 = l2 % 2;
                                j3 = 1 - i3;

                                for (k3 = 0; k3 < 4; ++k3) {
                                    for (l3 = -1; l3 < 4; ++l3) {
                                        i4 = i2 + (k3 - 1) * i3;
                                        j4 = k2 + l3;
                                        k4 = j2 + (k3 - 1) * j3;
                                        blockposition_mutableblockposition.c(i4, j4, k4);
                                        if (l3 < 0
                                                && !this.a.getType(blockposition_mutableblockposition).getBlock()
                                                        .getMaterial().isBuildable()
                                                || l3 >= 0 && !this.a.isEmpty(blockposition_mutableblockposition)) {
                                            continue label219;
                                        }
                                    }
                                }

                                d3 = (double) k2 + 0.5D - y; // CraftBukkit
                                d4 = d1 * d1 + d3 * d3 + d2 * d2;
                                if (d0 < 0.0D || d4 < d0) {
                                    d0 = d4;
                                    l = i2;
                                    i1 = k2;
                                    j1 = j2;
                                    k1 = l2 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i5 = l;
        int j5 = i1;

        j2 = j1;
        int k5 = k1 % 2;
        int l5 = 1 - k5;

        if (k1 % 4 >= 2) {
            k5 = -k5;
            l5 = -l5;
        }

        if (d0 < 0.0D) {
            i1 = MathHelper.clamp(i1, 70, 256 - 10);
            j5 = i1;

            for (k2 = -1; k2 <= 1; ++k2) {
                for (l2 = 1; l2 < 3; ++l2) {
                    for (i3 = -1; i3 < 3; ++i3) {
                        j3 = i5 + (l2 - 1) * k5 + k2 * l5;
                        k3 = j5 + i3;
                        l3 = j2 + (l2 - 1) * l5 - k2 * k5;
                        boolean flag = i3 < 0;

                        this.a.setTypeUpdate(new BlockPosition(j3, k3, l3),
                                flag ? Blocks.OBSIDIAN.getBlockData() : Blocks.AIR.getBlockData());
                    }
                }
            }
        }

        IBlockData iblockdata = Blocks.PORTAL.getBlockData().set(BlockPortal.AXIS,
                k5 != 0 ? EnumDirection.EnumAxis.X : EnumDirection.EnumAxis.Z);

        for (l2 = 0; l2 < 4; ++l2) {
            for (i3 = 0; i3 < 4; ++i3) {
                for (j3 = -1; j3 < 4; ++j3) {
                    k3 = i5 + (i3 - 1) * k5;
                    l3 = j5 + j3;
                    i4 = j2 + (i3 - 1) * l5;
                    boolean flag1 = i3 == 0 || i3 == 3 || j3 == -1 || j3 == 3;

                    this.a.setTypeAndData(new BlockPosition(k3, l3, i4),
                            flag1 ? Blocks.OBSIDIAN.getBlockData() : iblockdata, 2);
                }
            }

            for (i3 = 0; i3 < 4; ++i3) {
                for (j3 = -1; j3 < 4; ++j3) {
                    k3 = i5 + (i3 - 1) * k5;
                    l3 = j5 + j3;
                    i4 = j2 + (i3 - 1) * l5;
                    BlockPosition blockposition = new BlockPosition(k3, l3, i4);

                    this.a.applyPhysics(blockposition, this.a.getType(blockposition).getBlock());
                }
            }
        }

        return true;
    }

    public boolean purgeCache(long k) {
        PortalTravelAgent.ChunkCoordinatesPortal portaltravelagent_chunkcoordinatesportal = (PortalTravelAgent.ChunkCoordinatesPortal) this.c.getEntry(k);
        long j = this.a.getTime() - 300L;

        if (portaltravelagent_chunkcoordinatesportal == null || portaltravelagent_chunkcoordinatesportal.c < j) {
            this.d.remove(k);
            this.c.remove(Long.valueOf(k));
            return true;
        }

        return false;
    }
}
