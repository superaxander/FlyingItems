package alexanders.mods.fi.render;

import alexanders.mods.fi.FlyingItems;
import alexanders.mods.fi.tile.ItemCannonTileEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static de.ellpeck.rockbottom.api.RockBottomAPI.createRes;

public class ItemCannonGui extends GuiContainer {
    private final ItemCannonTileEntity te;

    public ItemCannonGui(AbstractEntityPlayer player, ItemCannonTileEntity te) {
        super(player, 200, 120);
        this.te = te;
    }

    @Override
    public void initGui(IGameInstance game) {
        super.initGui(game);
        this.components.add(new ComponentProgressBar(this, guiLeft + 78, guiTop, 10, 18, PROGRESS_COLOR, true, () -> te.cooldown/200f));
    }

    @Override
    public IResourceName getName() {
        return createRes(FlyingItems.instance, "item_cannon_gui");
    }
}
