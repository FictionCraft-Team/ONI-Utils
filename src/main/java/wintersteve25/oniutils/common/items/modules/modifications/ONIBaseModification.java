package wintersteve25.oniutils.common.items.modules.modifications;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.init.ONIItems;
import wintersteve25.oniutils.common.items.base.ONIToolTipColorNameItem;
import wintersteve25.oniutils.common.items.base.enums.EnumModifications;
import wintersteve25.oniutils.common.network.ModificationPacket;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.utils.ONIConstants;

public class ONIBaseModification extends ONIToolTipColorNameItem {
    private final int maxBonus;
    private final EnumModifications modType;

    public ONIBaseModification(Properties properties, String regName, boolean doRegularDataGen, TextFormatting color, int maxBonus, EnumModifications modType, ITextComponent... tooltips) {
        super(properties, regName, doRegularDataGen, color, tooltips);
        this.maxBonus = maxBonus;
        this.modType = modType;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote()) {
            ItemStack heldItem = playerIn.getHeldItem(handIn);
            playerIn.swing(handIn, true);
            ONINetworking.sendToClient(new ModificationPacket(heldItem, maxBonus, ONIConstants.PacketType.MODIFICATION_GUI), (ServerPlayerEntity) playerIn);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public EnumModifications getModType() {
        return modType;
    }

    public static ONIBaseModification create(String regName, TextFormatting color, int maxBonus, EnumModifications modType, ITextComponent... tooltips) {
        ONIBaseModification mod = new ONIBaseModification(new Properties().group(ONIUtils.creativeTab).maxStackSize(1).setNoRepair(), regName, true, color, maxBonus, modType, tooltips);
        ONIItems.itemRegistryList.add(mod);
        return mod;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return getBonusDataFromItemStack(stack) != 0;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public static void setBonusDataToItemStack(ServerPlayerEntity modification, int bonusData) {
        CompoundNBT nbt = modification.getHeldItemMainhand().getOrCreateTag();
        nbt.putInt("oniutils_bonus", bonusData);
    }

    public static int getBonusDataFromItemStack(ItemStack modification) {
        CompoundNBT tagCompound = modification.getOrCreateTag();
        return tagCompound.getInt("oniutils_bonus");
    }
}