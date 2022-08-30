package main.Module.Relationship;

import javafx.scene.control.ScrollPane;

public class RelationshipCenter extends ScrollPane
{
    private final RelationshipModule parent;
    private final RelationshipGrid grid;
    public RelationshipCenter(final RelationshipModule parentMod)
    {
        parent = parentMod;
        grid = new RelationshipGrid(this);
        setContent(grid);
        setFitToWidth(true);
        setFitToHeight(true);
    }

    public final RelationshipGrid GetGrid(){return grid;}
}
