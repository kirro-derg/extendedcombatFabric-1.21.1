package dev.kirro.extendedcombat.entity.custom;

import dev.kirro.ExtendedCombat;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class ModEntityModelLayers {
    public static final EntityModelLayer STATUE = create("statue");
    public static final EntityModelLayer STATUE_INNER_ARMOR = create("statue", "inner_armor");
    public static final EntityModelLayer STATUE_OUTER_ARMOR = create("statue", "outer_armor");

    public static EntityModelLayer create(String id) {
        return create(id, "main");
    }

    public static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(Identifier.of(ExtendedCombat.MOD_ID, id), layer);
    }
}
