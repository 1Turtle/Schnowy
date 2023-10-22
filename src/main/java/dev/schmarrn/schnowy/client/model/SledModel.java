package dev.schmarrn.schnowy.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.schmarrn.schnowy.Schnowy;
import dev.schmarrn.schnowy.common.entities.Sled;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class SledModel<T extends Sled> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Schnowy.MODID, "sled"), "main");
	private final ModelPart sled;

	public SledModel(ModelPart root) {
		this.sled = root.getChild("sled");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition sled = partdefinition.addOrReplaceChild("sled", CubeListBuilder.create(), PartPose.offset(-0.55F, 16.0F, 3.0F));

		PartDefinition left = sled.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0).addBox(6.0F, -2.0F, -22.0F, 2.0F, 2.0F, 39.0F, new CubeDeformation(0.0F))
			.texOffs(12, 6).addBox(6.0F, -4.0F, -23.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 22).addBox(6.0F, -4.0F, -22.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(43, 0).addBox(6.0F, 7.0F, -18.0F, 2.0F, 1.0F, 35.0F, new CubeDeformation(0.0F))
			.texOffs(6, 6).addBox(6.0F, 0.0F, -7.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 6).addBox(6.0F, 0.0F, 12.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 0.0F));

		/*PartDefinition cube_r1 = */left.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 6).addBox(-0.975F, 1.775F, -3.175F, 1.95F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -0.5F, -22.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition right = sled.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 41).addBox(6.0F, -2.0F, -22.0F, 2.0F, 2.0F, 39.0F, new CubeDeformation(0.0F))
			.texOffs(12, 14).addBox(6.0F, -4.0F, -23.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(6, 22).addBox(6.0F, -4.0F, -22.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(43, 41).addBox(6.0F, 7.0F, -18.0F, 2.0F, 1.0F, 35.0F, new CubeDeformation(0.0F))
			.texOffs(6, 14).addBox(6.0F, 0.0F, -7.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 14).addBox(6.0F, 0.0F, 12.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, 0.0F, 0.0F));

		/*PartDefinition cube_r2 = */right.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(18, 14).addBox(-0.975F, 1.775F, -3.175F, 1.95F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -0.5F, -22.0F, 0.7854F, 0.0F, 0.0F));

		/*PartDefinition center = */sled.addOrReplaceChild("center", CubeListBuilder.create().texOffs(82, 36).addBox(3.0F, -2.0F, -12.0F, 2.0F, 2.0F, 28.0F, new CubeDeformation(0.0F))
			.texOffs(82, 0).addBox(0.0F, -2.0F, -12.0F, 2.0F, 2.0F, 28.0F, new CubeDeformation(0.0F))
			.texOffs(0, 82).addBox(-3.0F, -2.0F, -12.0F, 2.0F, 2.0F, 28.0F, new CubeDeformation(0.0F))
			.texOffs(54, 77).addBox(-6.0F, -2.0F, -12.0F, 2.0F, 2.0F, 28.0F, new CubeDeformation(0.0F))
			.texOffs(0, 3).addBox(-7.0F, -1.0F, -8.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-7.0F, -1.0F, 11.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		sled.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
