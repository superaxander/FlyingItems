package alexanders.mods.fi.net;

import alexanders.mods.fi.render.ItemCannonGui;
import alexanders.mods.fi.tile.ItemCannonContainer;
import alexanders.mods.fi.tile.ItemCannonTileEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.UUID;

public class OpenGUIPacket implements IPacket {
    private UUID uuid;
    private int x;
    private int y;

    public OpenGUIPacket() {
    }

    public OpenGUIPacket(UUID uuid, int x, int y) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
        buf.writeInt(x);
        buf.writeInt(y);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        uuid = new UUID(buf.readLong(), buf.readLong());
        x = buf.readInt();
        y = buf.readInt();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        game.enqueueAction((gameInstance, o) -> {
            System.out.println("Executing");
            IWorld world = gameInstance.getWorld();
            Entity e = world.getEntity(uuid);
            if (e instanceof AbstractEntityPlayer) {
                AbstractEntityPlayer player = (AbstractEntityPlayer) e;
                ItemCannonTileEntity te = (ItemCannonTileEntity) world.getTileEntity(x, y);
                player.openGuiContainer(new ItemCannonGui(player, te), new ItemCannonContainer(player, te));
            }
        }, null);
    }
}
