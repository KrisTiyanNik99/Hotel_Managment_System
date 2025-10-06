package ui.components;

/**
 * Base interface for all UI components.
 * Enforces a unified initialization and identification contract.
 */
public interface UIComponent {
    /**
     * Returns the component's logical type identifier.
     * @return type as string
     */
    String getType();

    /**
     * Initializes and configures all Swing elements within this component.
     */
    void initComponents();
}
