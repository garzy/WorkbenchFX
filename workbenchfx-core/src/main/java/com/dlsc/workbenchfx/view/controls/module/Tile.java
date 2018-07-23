package com.dlsc.workbenchfx.view.controls.module;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the standard control used to display {@link WorkbenchModule}s as tiles in the
 * home screen.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
public class Tile extends Control {
  private static final Logger LOGGER = LogManager.getLogger(Tile.class.getName());

  private final Workbench workbench;
  private final ObjectProperty<WorkbenchModule> module;

  private final StringProperty name;
  private final ObjectProperty<Node> icon;

  /**
   * Constructs a new {@link Tile}.
   *
   * @param workbench which created this {@link Tile}
   */
  public Tile(Workbench workbench) {
    this.workbench = workbench;
    module = new SimpleObjectProperty<>();
    name = new SimpleStringProperty();
    icon = new SimpleObjectProperty<>();
    setupModuleListeners();
  }

  private void setupModuleListeners() {
    module.addListener(observable -> {
      WorkbenchModule current = getModule();
      name.setValue(current.getName());
      icon.setValue(current.getIcon());
    });
  }

  /**
   * Opens the {@link WorkbenchModule} belonging to this {@link Tile}.
   */
  public void open() {
    workbench.openModule(getModule());
  }

  public WorkbenchModule getModule() {
    return module.get();
  }

  /**
   * Defines the {@code module} which is being represented by this {@link Tile}.
   *
   * @param module to be represented by this {@link Tile}
   */
  public final void setModule(WorkbenchModule module) {
    LOGGER.trace("Setting reference to module");
    this.module.set(module);
  }

  public ReadOnlyObjectProperty<WorkbenchModule> moduleProperty() {
    return module;
  }

  public String getName() {
    return name.get();
  }

  public ReadOnlyStringProperty nameProperty() {
    return name;
  }

  public Node getIcon() {
    return icon.get();
  }

  public ReadOnlyObjectProperty<Node> iconProperty() {
    return icon;
  }

  @Override
  protected Skin<?> createDefaultSkin() {
    return new TileSkin(this);
  }
}