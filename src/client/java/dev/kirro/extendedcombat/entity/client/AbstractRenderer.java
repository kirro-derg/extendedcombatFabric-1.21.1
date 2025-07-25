package dev.kirro.extendedcombat.entity.client;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

public abstract class AbstractRenderer<T extends StatueEntity> extends LivingEntityRenderer<T, BasicStatueModel<T>> {


    public AbstractRenderer(EntityRendererFactory.Context context, BasicStatueModel<T> model, BasicStatueModel<T> innerArmorModel, BasicStatueModel<T> outerArmorModel) {
        super(context, model, 0.0f);
        this.addFeature(new ArmorFeatureRenderer<>(this, innerArmorModel, outerArmorModel, context.getModelManager()));
        this.addFeature(new ElytraFeatureRenderer<>(this, context.getModelLoader()));
        this.addFeature(new HeadFeatureRenderer<>(this, context.getModelLoader(), context.getHeldItemRenderer()));
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(T entity) {
        return null;
    }
}
