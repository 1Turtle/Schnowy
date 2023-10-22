package dev.schmarrn.schnowy.client;

import dev.schmarrn.schnowy.client.model.SledModel;
import dev.schmarrn.schnowy.client.renderer.entity.SledRenderer;
import dev.schmarrn.schnowy.common.blocks.SchnowyBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

import static dev.schmarrn.schnowy.common.entities.SchnowyEntities.SLED_TYPE;

public class SchnowyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		for (Block block: SchnowyBlocks.FLOWERS.values()) {
			BlockRenderLayerMap.put(RenderType.cutout(), block);
		}
		SchnowyBlocks.SNOWED_GRASS.forEach((key, block) -> {
			BlockRenderLayerMap.put(RenderType.cutout(), block);
			ColorProviderRegistry.BLOCK.register((blockState, level, blockPos, i) -> Minecraft.getInstance().getBlockColors().getColor(key.defaultBlockState(), level, blockPos, i),block);
		});
		BlockRenderLayerMap.put(RenderType.cutout(), SchnowyBlocks.SNOWED_DEAD_BUSH);

		EntityRendererRegistry.register(SLED_TYPE, SledRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SledModel.LAYER_LOCATION, SledModel::createBodyLayer);
	}
}
