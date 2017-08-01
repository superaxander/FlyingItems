package alexanders.mods.fi;

import alexanders.mods.fi.net.OpenGUIPacket;
import alexanders.mods.fi.tile.ItemCannonTile;
import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.data.settings.Keybind;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.Input;

import static de.ellpeck.rockbottom.api.RockBottomAPI.*;
import static de.ellpeck.rockbottom.api.construction.resource.ResourceRegistry.*;

public class FlyingItems implements IMod {
    public static FlyingItems instance;
    public Keybind KEY_ROTATE;

    public FlyingItems() {
        instance = this;
        IResourceName name = createRes(this, "rotate");
        KEY_ROTATE = new Keybind(name, Input.KEY_LCONTROL, false);
        KEYBIND_REGISTRY.register(name, KEY_ROTATE);
    }

    @Override
    public String getDisplayName() {
        return "FlyingItems";
    }

    @Override
    public String getId() {
        return "fi";
    }

    @Override
    public String getVersion() {
        return "@VERSION@";
    }

    @Override
    public String getResourceLocation() {
        return "/assets/" + getId();
    }

    @Override
    public String getDescription() {
        return getGame().getAssetManager().localize(createRes(this, "desc.mod"));
    }

    @Override
    public void init(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        Tile tile = new ItemCannonTile(createRes(this, "cannon")).register();
        CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(tile), new ResUseInfo(PROCESSED_STONE, 4), new ResUseInfo(WOOD_BOARDS, 4), new ResUseInfo(PARTLY_PROCESSED_COPPER, 8)));
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), OpenGUIPacket.class);
    }
}
