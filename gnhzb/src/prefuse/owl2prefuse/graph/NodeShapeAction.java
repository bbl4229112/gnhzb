package prefuse.owl2prefuse.graph;

import prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.assignment.ShapeAction;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

/**
 * This class is a specific ColorAction for the nodes in the graph.
 * <p/>
 * Project OWL2Prefuse <br/>
 * NodeColorAction.java created 3 januari 2007, 13:37
 * <p/>
 * Copyright &copy 2006 Jethro Borsje
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje</a>
 * @version $$Revision: 1.1 $$, $$Date: 2009/08/25 08:19:25 $$
 */

public class NodeShapeAction extends ShapeAction
{
    /**
     * Creates a new instance of NodeColorAction.
     * @param p_group The data group for which this ColorAction provides the colors.
     * @param p_vis A reference to the visualization processed by this Action.
     */
    public NodeShapeAction(String p_group, Visualization p_vis)
    {
        super(p_group);
        m_vis = p_vis;
    }

    /**
     * This method returns the color of the given VisualItem.
     * @param p_item The node for which the color needs to be retreived.
     * @return The color of the given node.
     */
    public int getShape(VisualItem p_item)
    {
        int retval = Constants.SHAPE_DIAMOND;
        
        if (m_vis.isInGroup(p_item, Visualization.SEARCH_ITEMS)) retval = Constants.SHAPE_RECTANGLE;
        else if (p_item.isHighlighted()) retval = Constants.SHAPE_RECTANGLE;
        else if (p_item.isFixed()) retval = Constants.SHAPE_RECTANGLE;
        else if (p_item.canGetString("type"))
        {
            if (p_item.getString("type") != null)
            {
                if (p_item.getString("type").equals("class")) retval = Constants.SHAPE_RECTANGLE;
                else if (p_item.getString("type").equals("individual")) retval = Constants.SHAPE_RECTANGLE;
            }
        }
        
        return retval;
    }
}