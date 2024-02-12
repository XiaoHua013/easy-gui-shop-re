package pers.zhangyang.easyguishop.easylibrary.util;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyguishop.easylibrary.exception.FailureCreateWorldException;

public class WorldUtil {
   public static World getVoidWorld(@NotNull String worldName) throws FailureCreateWorldException {
      WorldCreator worldCreator = new WorldCreator(worldName);
      if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {
         worldCreator.type(WorldType.FLAT);
         worldCreator.generatorSettings("2;0;1;");
         worldCreator.createWorld();
      } else if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 18) {
         worldCreator.generator(new ChunkGenerator() {
            @NotNull
            public ChunkGenerator.@NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.@NotNull BiomeGrid biome) {
               return Bukkit.createChunkData(world);
            }
         });
      } else {
         worldCreator.generator(new ChunkGenerator() {
         });
      }

      World world = worldCreator.createWorld();
      if (world == null) {
         throw new FailureCreateWorldException();
      } else {
         return world;
      }
   }
}
