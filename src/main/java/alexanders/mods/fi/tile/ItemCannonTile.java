package alexanders.mods.fi.tile;

import alexanders.mods.fi.FlyingItems;
import alexanders.mods.fi.net.OpenGUIPacket;
import alexanders.mods.fi.render.ItemCannonGui;
import alexanders.mods.fi.render.ItemCannonRenderer;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getGame;
import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class ItemCannonTile extends TileBasic {
    public static final IntProp rotation = new IntProp("rotation", 0, 36); // Angle between 0-180 multiply the value by 5

    public ItemCannonTile(IResourceName name) {
        super(name);
        addProps(rotation);
        this.setForceDrop();
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        if (!getNet().isActive()) {
            if (FlyingItems.instance.KEY_ROTATE.isDown()) {
                TileState state = world.getState(layer, x, y);
                world.setState(layer, x, y, state.prop(rotation, (state.get(rotation) + 1) % 36)); // Make direction reverse
                return true;
            } else {
                //Open gui
                ItemCannonTileEntity te = (ItemCannonTileEntity) world.getTileEntity(x, y);
                player.openGuiContainer(new ItemCannonGui(player, te), new ItemCannonContainer(player, te));
                return true;
            }
        } else if (getNet().isClient()) {
            if (FlyingItems.instance.KEY_ROTATE.isDown()) {
                return true;
            } else {
                //Open gui
                ItemCannonTileEntity te = (ItemCannonTileEntity) world.getTileEntity(x, y);
                player.openGuiContainer(new ItemCannonGui(player, te), new ItemCannonContainer(player, te));
                getNet().sendToServer(new OpenGUIPacket(player.getUniqueId(), x, y));
                return false; // This is probably not a good idea
            }
        } else { //getNet().isServer() == true
            if (getGame().isDedicatedServer() || !getNet().isThePlayer(player)) {
                TileState state = world.getState(layer, x, y);
                world.setState(layer, x, y, state.prop(rotation, (state.get(rotation) + 1) % 36)); // Make direction reverse
                return true;
            } else if (FlyingItems.instance.KEY_ROTATE.isDown()) {
                TileState state = world.getState(layer, x, y);
                world.setState(layer, x, y, state.prop(rotation, (state.get(rotation) + 1) % 36)); // Make direction reverse
                return true;
            } else {
                ItemCannonTileEntity te = (ItemCannonTileEntity) world.getTileEntity(x, y);
                player.openGuiContainer(new ItemCannonGui(player, te), new ItemCannonContainer(player, te));
            }
        }
        return false;
    }

    @Override
    public boolean canPlace(IWorld world, int x, int y, TileLayer layer) {
        return world.getState(layer, x, y - 1).getTile().isFullTile() && super.canPlace(world, x, y, layer);
    }

    @Override
    protected ITileRenderer createRenderer(IResourceName name) {
        return new ItemCannonRenderer(name);
    }

    @Override
    public BoundBox getBoundBox(IWorld world, int x, int y) {
        return null;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new ItemCannonTileEntity(world, x, y, layer);
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }

    @Override
    public boolean isFullTile() {
        return false;
    }
}
