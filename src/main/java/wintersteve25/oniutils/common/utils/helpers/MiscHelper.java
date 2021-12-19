package wintersteve25.oniutils.common.utils.helpers;

import mekanism.common.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.bounding.ONIBoundingBlock;
import wintersteve25.oniutils.common.contents.base.bounding.ONIBoundingTE;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class MiscHelper {

    public static final int INT_MAX = 2147483647;
    public static final double ONEPIXEL = 1D / 16;
    public static final Item.Properties DEFAULT_ITEM_PROPERTY = new Item.Properties().group(ONIUtils.creativeTab);

    public static String langToReg(String lang) {
        return lang.toLowerCase().replace(' ', '_').replace('-', '_');
    }

    public static double randomInRange(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    public static float randomInRange(float min, float max) {
        return (float) ((Math.random() * (max - min)) + min);
    }

    public static int randomInRange(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    /**
     * Method modified from https://github.com/mekanism/Mekanism/blob/1.16.x/src/main/java/mekanism/common/util/WorldUtils.java#L537
     */
    public static void makeBoundingBlock(@Nullable IWorld world, BlockPos boundingLocation, BlockPos orig) {
        if (world != null) {
            ONIBoundingBlock boundingBlock = (ONIBoundingBlock) ONIBlocks.Misc.BOUNDING_BLOCK;
            BlockState newState = boundingBlock.getDefaultState();
            world.setBlockState(boundingLocation, newState, 3);
            if (!world.isRemote()) {
                ONIBoundingTE tile = (ONIBoundingTE) WorldUtils.getTileEntity((Class) ONIBoundingTE.class, (IBlockReader) world, boundingLocation);
                if (tile != null) {
                    tile.setMainLocation(orig);
                } else {
                    ONIUtils.LOGGER.warn("Unable to find Bounding Block Tile at: {}", boundingLocation);
                }
            }
        }
    }

    public static <K, V> HashMap<K, V> newHashmap(List<K> keys, List<V> values) {
        HashMap<K, V> map = new HashMap<>();

        for (int i = 0; i < keys.size(); i++) {
            try {
                map.put(keys.get(i), values.get(i));
            } catch (ArrayIndexOutOfBoundsException e) {
                return map;
            }
        }

        return map;
    }

    /**
     * @return returns a new list that contains all the object in the first list that isn't in the second list
     */
    public static <E> List<E> filterList(Collection<E> list, Collection<E> other) {
        return list.stream().filter(obj -> !other.contains(obj)).collect(Collectors.toList());
    }

    public static <T, E> List<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}