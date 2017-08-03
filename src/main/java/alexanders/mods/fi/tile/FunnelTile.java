package alexanders.mods.fi.tile;

import alexanders.mods.fi.net.RotationPacket;
import alexanders.mods.fi.render.FunnelTileRenderer;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class FunnelTile extends TileBasic {
    static final BoundBox BB = new BoundBox(1/12f, 0, 11/12f, 10/12f);
    public static final IntProp direction = new IntProp("direction", 0, 3);

    public FunnelTile(IResourceName name) {
        super(name);
        this.setForceDrop();
        addProps(direction);
    }

    @Override
    public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer) {
        super.doPlace(world, x, y, layer, instance, placer);
        if(getNet().isClient() || !getNet().isActive()) {
            int dir = facingToDir(placer.facing);
            if (getNet().isClient())
                getNet().sendToServer(new RotationPacket(x, y, dir));
            else if(!getNet().isActive()) {
                world.setState(layer, x, y, getDefState().prop(direction, dir));
            }
        }
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        world.setState(layer, x, y, world.getState(layer, x, y).cycleProp(direction));
        return true;
    }

    private int facingToDir(Direction facing) {
        switch (facing){
            case NONE:
            case DOWN:
            case UP:
                return 0;
            case LEFT_DOWN:
            case LEFT_UP:
            case LEFT:
                return 2;
            case RIGHT_DOWN:
            case RIGHT:
            case RIGHT_UP:
                return 1;
        }
        return 0;
    }

    @Override
    protected ITileRenderer createRenderer(IResourceName name) {
        return new FunnelTileRenderer(name);
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y) {
        return new FunnelTileEntity(world, x, y);
    }

    @Override
    public BoundBox getBoundBox(IWorld world, int x, int y) {
        return BB;
    }
}
