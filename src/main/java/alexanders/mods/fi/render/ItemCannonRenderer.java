package alexanders.mods.fi.render;

import alexanders.mods.fi.tile.ItemCannonTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.tex.Texture;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ItemCannonRenderer extends DefaultTileRenderer<ItemCannonTile> {
    public ItemCannonRenderer(IResourceName name) {
        super(name);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, ItemCannonTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light) {
        super.render(game, manager, g, world, tile, state, x, y, layer, renderX, renderY, scale, light);
        Texture t = manager.getTexture(texture.addSuffix(".head"));
        t.setCenterOfRotation(scale * 0.5f, scale / 2);
        t.setRotation(360 - state.get(ItemCannonTile.rotation) * 5);
        t.drawWithLight(renderX, renderY - scale / 4, scale * 2, scale, light);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, Graphics g, ItemCannonTile tile, int meta, float x, float y, float scale, Color filter) {
        manager.getTexture(texture.addSuffix(".item")).draw(x, y, scale, scale, filter);
    }
}
