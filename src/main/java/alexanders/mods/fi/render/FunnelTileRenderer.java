package alexanders.mods.fi.render;

import alexanders.mods.fi.tile.FunnelTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.tex.Texture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class FunnelTileRenderer extends DefaultTileRenderer<FunnelTile> {
    public FunnelTileRenderer(IResourceName name) {
        super(name);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, FunnelTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light) {
        switch (state.get(FunnelTile.direction)) {
            case 0: // Down
                manager.getTexture(texture.addSuffix("_down")).drawWithLight(renderX, renderY, scale, scale, light);
                break;
            case 1: // Right
                manager.getTexture(texture.addSuffix("_right")).drawWithLight(renderX, renderY, scale, scale, light);
                break;
            case 2: // Left
                Texture t = manager.getTexture(texture.addSuffix("_right"));
                t.drawWithLight(renderX + scale, renderY, renderX, renderY + scale, 0, 0, t.getWidth(), t.getHeight(), light, null); // Draw flipped
                break;
            default:
                throw new IllegalStateException("Direction not supported");
        }
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, Graphics g, FunnelTile tile, ItemInstance instance, float x, float y, float scale, Color filter) {
        manager.getTexture(texture.addSuffix("_down")).draw(x, y, scale, scale, filter);
    }

    @Override
    public Image getParticleTexture(IGameInstance game, IAssetManager manager, Graphics g, FunnelTile tile, TileState state) {
        return manager.getTexture(texture.addSuffix("_down"));
    }
}
