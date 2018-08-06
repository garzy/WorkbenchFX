package com.dlsc.workbenchfx.view.controls;

import javafx.scene.control.MenuButton;
import javafx.scene.control.SkinBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the Skin which is made for the {@link ToolbarItem}. It uses a {@link MenuButton} to
 * inherit the main functionality.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
public class ToolbarItemSkin extends SkinBase<ToolbarItem> {

  private static final Logger LOGGER =
      LogManager.getLogger(ToolbarItemSkin.class.getName());

  private final MenuButton menuButton = new MenuButton();
  private static final int FIRST_ELEMENT_INDEX = 0;

  /**
   * Creates a new {@link ToolbarItemSkin} object for a corresponding {@link ToolbarItem}.
   *
   * @param toolbarItem the {@link ToolbarItem} for which this Skin is created
   */
  public ToolbarItemSkin(ToolbarItem toolbarItem) {
    super(toolbarItem);

    menuButton.getItems().setAll(getSkinnable().getItems());
    getChildren().add(menuButton);

    setupBindings();
    setupListeners();
  }

  private void setupBindings() {
    menuButton.textProperty().bind(getSkinnable().textProperty());
    menuButton.graphicProperty().bind(getSkinnable().graphicProperty());
    menuButton.onMouseClickedProperty().bind(getSkinnable().onClickProperty());

    menuButton.minWidthProperty().bind(getSkinnable().minWidthProperty());
    menuButton.prefWidthProperty().bind(getSkinnable().prefWidthProperty());
    menuButton.maxWidthProperty().bind(getSkinnable().maxWidthProperty());
    menuButton.minHeightProperty().bind(getSkinnable().minHeightProperty());
    menuButton.prefHeightProperty().bind(getSkinnable().prefHeightProperty());
    menuButton.maxHeightProperty().bind(getSkinnable().maxHeightProperty());
  }

  private void setupListeners() {
    // When the items are changing update the items from the menuButton
    getSkinnable().itemsProperty()
        .addListener((observable, oldValue, newValue) -> menuButton.getItems().setAll(newValue));

    // Sets the pref-width of the toolbarItems items when the ContextMenu is showing
    menuButton.showingProperty().addListener((observable, wasShowing, isShowing) -> {
      if (!menuButton.getItems().isEmpty() && isShowing) {
        menuButton.getItems().get(FIRST_ELEMENT_INDEX).getParentPopup()
            .setStyle("-fx-min-width: " + menuButton.getWidth());
      }
    });
  }
}
