package main.Module.Relationship;

import main.Module.ModuleBase.BaseModule;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;

public class RelationshipModule extends BaseModule
{
    private final RelationshipLeftBar left;
    private final RelationshipCenter center;

    public RelationshipModule(final ModuleManager managerParent)
    {
        super(ModuleID.RELATIONSHIP, managerParent);
        center = new RelationshipCenter(this);
        left = new RelationshipLeftBar(this);
        setLeft(left);
        setCenter(center);

    }
    public final RelationshipCenter GetCenter(){return center;}
}
