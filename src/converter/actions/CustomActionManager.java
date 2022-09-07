package converter.actions;

import converter.actions.actions.*;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Blocks;
import minecraft.Material;
import minecraft.Property;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerT;

import java.util.Collection;
import java.util.function.Supplier;

public class CustomActionManager extends ActionManager {
  public static final class Materials {
    public static final Material[] IGNORED = new Material[]{
      // Material.ladder,

      // Air
      Material.air,
      Material.cave_air,
      Material.void_air,

      // Interactive
      Material.cobweb,
      Material.fire,
      Material.iron_door,
      Material.oak_door,
      Material.oak_pressure_plate,
      Material.redstone_dust,
      Material.stone_button,
      Material.stone_pressure_plate,

      // Others
      Material.detector_rail,
      Material.detector_rail,
      Material.rail,
      Material.redstone_torch,
      Material.redstone_wall_torch,

      // Plants
      Material.allium,
      Material.azure_bluet,
      Material.blue_orchid,
      Material.brown_mushroom,
      Material.cornflower,
      Material.dandelion,
      Material.fern,
      Material.grass,
      Material.large_fern,
      Material.lever,
      Material.lilac,
      Material.lily_of_the_valley,
      Material.oak_wall_sign,
      Material.orange_tulip,
      Material.oxeye_daisy,
      Material.peony,
      Material.pink_tulip,
      Material.poppy,
      Material.red_mushroom,
      Material.red_tulip,
      Material.rose_bush,
      Material.seagrass,
      Material.sugar_cane,
      Material.sugar_cane,
      Material.sunflower,
      Material.sweet_berry_bush,
      Material.tall_grass,
      Material.tall_grass,
      Material.tall_seagrass,
      Material.wheat,
      Material.white_tulip,
      Material.wither_rose,
    };

    public static final Material[] DETAIL = new Material[]{
      Material._leaves,
      Material.glass,
      Material.ice
    };

    public static final Material[] LIQUID = new Material[]{
      Material.kelp,
      Material.kelp_plant,
      Material.lava,
      Material.seagrass,
      Material.tall_seagrass,
      Material.water,
    };
  }

  public CustomActionManager(Mapper map, Collection<ConvertEntity> converters) {
    super(Solid.INSTANCE);
  }

  public CustomActionManager setDefaults() {
    mapToAction(NoAction.INSTANCE, Blocks._UNSET);
    mapToAction(NoAction.INSTANCE, Materials.IGNORED);
    mapToAction(new DetailBlock(), Materials.DETAIL);
    mapToAction(new Liquid(), Materials.LIQUID);

    mapToAction(new Fire(), Material.fire);
    mapToAction(new Cactus(), Material.cactus);
    mapToAction(Torch.INSTANCE, Material.torch, Material.wall_torch);

    mapToAction(new Fence(), Material._fence);
    mapToAction(new Stairs(), Material._stairs);

    mapToAction(new SlabTop(), Blocks.get(blockTemplate -> blockTemplate
      .setName(Material._slab)
      .addProperty(Property.half, Property.Half.top)
      .addProperty(Property.waterlogged, Property.Waterlogged.false$)
    ));

    mapToAction(new SlabBottom(), Blocks.get(blockTemplate -> blockTemplate
      .setName(Material._slab)
      .addProperty(Property.half, Property.Half.bottom)
      .addProperty(Property.waterlogged, Property.Waterlogged.false$)
    ));

    setTf2Defaults();
    setTttDefaults();
    setCssDefaults();

    return this;
  }

  private void mapToAction(Action action, Supplier<Block>... blockSuppliers) {
    for (Supplier<Block> blockSupplier : blockSuppliers) {
      this.actions.put(blockSupplier, action);
    }
  }

  private void setTf2Defaults() {
    mapToAction(new TallGrassTf2(), Material.grass);
  }

  private void setTttDefaults() {
    mapToAction(new CenteredPointEntity("info_player_start"), Material.zombie_head, Material.zombie_wall_head);
    mapToAction(new CenteredPointEntity("ttt_random_weapon"), Material.skeleton_skull, Material.skeleton_wall_skull);
    mapToAction(new CenteredPointEntity("ttt_random_ammo"), Material.wither_skeleton_skull, Material.wither_skeleton_wall_skull);
  }

  private void setCssDefaults() {
    // this.actions.put(Material.torch, new CssLamp());
    // this.actions.put(Material.wall_torch, new CssLamp());
    mapToAction(new PlayerSpawnCss(new InfoPlayerT().setRotation(180), false), Material.ender_chest);
    mapToAction(new PlayerSpawnCss(new InfoPlayerT().setRotation(0), false), Material.end_portal_frame);
  }
}
