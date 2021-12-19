package wintersteve25.oniutils.common.contents.base;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import wintersteve25.oniutils.api.functional.IPlacementCondition;
import wintersteve25.oniutils.api.functional.IToolTipCondition;
import wintersteve25.oniutils.common.contents.base.ONIBaseBlock;
import wintersteve25.oniutils.common.contents.base.ONIIItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ONIBaseBlockItem extends BlockItem implements ONIIItem {

    private final String regName;

    // item builder properties
    private boolean doModelGen = true;
    private boolean doLangGen = false;
    private Supplier<TextFormatting> colorName;
    private Supplier<List<ITextComponent>> tooltips;
    private Supplier<IToolTipCondition> tooltipCondition = IToolTipCondition.DEFAULT;
    private IPlacementCondition placementCondition;

    public ONIBaseBlockItem(ONIBaseBlock blockIn, Properties builder) {
        super(blockIn, builder);
        this.regName = blockIn.getRegName();
    }

    @Override
    public boolean doModelGen() {
        return doModelGen;
    }

    @Override
    public boolean doStateGen() {
        return false;
    }

    @Override
    public boolean doLangGen() {
        return doLangGen;
    }

    @Override
    public boolean doLootTableGen() {
        return false;
    }

    @Override
    public Item get() {
        return this;
    }

    @Override
    public String getRegName() {
        return regName;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (getColorName() != null && getColorName().get() != null) {
            return super.getDisplayName(stack).copyRaw().mergeStyle(getColorName().get());
        }
        return super.getDisplayName(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip(stack, worldIn, tooltip, flagIn);
    }

    @Override
    protected boolean canPlace(BlockItemUseContext context, BlockState state) {
        if (placementCondition != null) {
            return super.canPlace(context, state) && placementCondition.test(context, state);
        }
        return super.canPlace(context, state);
    }

    @Override
    public Supplier<TextFormatting> getColorName() {
        return colorName;
    }

    @Override
    public void setColorName(Supplier<TextFormatting> colorName) {
        this.colorName = colorName;
    }

    @Override
    public Supplier<List<ITextComponent>> getTooltips() {
        return tooltips;
    }

    @Override
    public void setTooltips(Supplier<List<ITextComponent>> tooltips) {
        this.tooltips = tooltips;
    }

    @Override
    public Supplier<IToolTipCondition> getTooltipCondition() {
        return tooltipCondition;
    }

    @Override
    public void setTooltipCondition(Supplier<IToolTipCondition> condition) {
        this.tooltipCondition = condition;
    }

    @Override
    public IPlacementCondition getPlacementCondition() {
        return placementCondition;
    }

    @Override
    public void setPlacementCondition(IPlacementCondition placementCondition) {
        this.placementCondition = placementCondition;
    }

    @Override
    public void setDoModelGen(boolean doModelGen) {
        this.doModelGen = doModelGen;
    }

    @Override
    public void setDoLangGen(boolean doLangGen) {
        this.doLangGen = doLangGen;
    }
}
