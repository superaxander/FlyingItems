package alexanders.mods.fi.net;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import static alexanders.mods.fi.tile.FunnelTile.direction;

public class RotationPacket implements IPacket {
    int x;
    int y;
    int dir;

    public RotationPacket() {
    }

    public RotationPacket(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(dir);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        x = buf.readInt();
        y = buf.readInt();
        dir = buf.readInt();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        game.getWorld().setState(x, y, game.getWorld().getState(x,y).prop(direction, dir));
    }
}
