package dev.kirro.extendedcombat.util;

import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;

import java.util.Set;

public class ScaledItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {
    private static final Set<ModelTransformationMode> renderModes = Set.of(ModelTransformationMode.GUI);
    private final Identifier id;
    private final Identifier itemId;
    private ItemRenderer itemRenderer;
    private BakedModel inventoryModel;
    private BakedModel heldModel;

    public ScaledItemRenderer(Identifier itemId) {
        this.id = new Identifier(itemId.getNamespace(), itemId.getPath() + "_renderer");
        this.itemId = itemId;
    }

    @Override
    public Identifier getFabricId() {
        return this.id;
    }

    @Override
    public void reload(ResourceManager manager) {
        final MinecraftClient client = MinecraftClient.getInstance();
        this.itemRenderer = client.getItemRenderer();
        this.inventoryModel = client.getBakedModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(this.itemId.getNamespace(), this.itemId.getPath())));
        this.heldModel = client.getBakedModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(this.itemId.getNamespace(), this.itemId.getPath() + "_handheld")));
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.pop();
        matrices.push();
        if (stack.isIn(ModItemTags.GREATSWORDS)) {
            if (renderModes.contains(mode)) {
                this.itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, this.inventoryModel);
            } else {
                boolean leftHanded;
                switch (mode) {
                    case FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND -> leftHanded = true;
                    default -> leftHanded = false;
                }
                this.itemRenderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, this.heldModel);
            }
        }
    }
}
