package dev.schmarrn.schnowy.common.entities;

import dev.schmarrn.schnowy.Schnowy;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;


public class SchnowyEntities {
	public static final EntityType<Sled> SLED_TYPE = QuiltEntityTypeBuilder.create(MobCategory.MISC, Sled::new)
		.setDimensions(EntityDimensions.fixed(1.3F,  0.6F))
		.build();

	public static void init() {
		Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(Schnowy.MODID, "sled"),
			SLED_TYPE
		);
	}
}
