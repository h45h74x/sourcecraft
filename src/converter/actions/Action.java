package converter.actions;

import basic.Loggger;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import vmfWriter.Orientation;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

import java.util.LinkedList;
import java.util.List;

public abstract class Action {

  protected int[] materialUsedFor = {};

  public Action() {
  }

  public Iterable<Action> getInstances() {
    List<Action> list = new LinkedList<>();
    try {
      Action a = (Action) this.getClass()
        .getConstructors()[0].newInstance();
      list.add(a);
    } catch (Exception ex) {
      Loggger.warn("Addable " + this.getClass()
        .getSimpleName() + " does not have a suitable constructor (InvocationTargetException)");
    }
    return list;
  }

  public String getName() {
    return this.getClass()
      .getSimpleName();
  }

  /**
   * Returns whether the added blocks are air.
   */
  public boolean isAirBlock() {
    return true;
  }

  public boolean hasWall(Orientation orientation) {
    return false;
  }

  @Deprecated
  public void setMaterialUsedFor(int[] material) {
    this.materialUsedFor = material;
  }

  public void setMaterialUsedFor(int material) {
    this.materialUsedFor = new int[1];
    this.materialUsedFor[0] = material;
  }

  /**
   * For a given position with given material, this method adds solids and
   * entities to the resulting Source map.
   */
  public void add(Mapper context, Position position, Block material) {

  }

  protected void addDebugMarker(Mapper context, Position position, Block material) {
    context.setPointToGrid(position);
    context.movePointInGridDimension(0.5, 0, 0.5);
    int verticalAngle = (int) (Math.random() * 360);
    context.addPointEntity(new RotateablePointEntity().setName(material.getName() + " at " + position.toString())
      .setRotation(verticalAngle));
    context.markAsConverted(position);
  }
}
