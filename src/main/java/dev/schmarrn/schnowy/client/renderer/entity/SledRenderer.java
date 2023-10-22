package dev.schmarrn.schnowy.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.schmarrn.schnowy.Schnowy;
import dev.schmarrn.schnowy.client.model.SledModel;
import dev.schmarrn.schnowy.common.entities.Sled;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class SledRenderer extends EntityRenderer<Sled> {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Schnowy.MODID, "textures/entity/sled/body.png");
	private final SledModel<Sled> model;

	public SledRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
		this.model = new SledModel<>(ctx.bakeLayer(SledModel.LAYER_LOCATION));
	}

	@Override
	public void render(Sled entity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
		poseStack.pushPose();
		poseStack.translate(0.0F,  1.5F, 0.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));

		poseStack.scale(-1.0F, -1.0F, 1.0F);

		VertexConsumer vertexConsumer = multiBufferSource.getBuffer(model.renderType(getTextureLocation(entity)));
		model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		poseStack.popPose();
		super.render(entity, yaw, tickDelta, poseStack, multiBufferSource, light);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(Sled entity) {
		return TEXTURE;
	}
}
