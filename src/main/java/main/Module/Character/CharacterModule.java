package main.Module.Character;

import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Layout.LeftBar.CharacterLeftBar;
import main.Module.ModuleBase.BaseModule;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;


public class CharacterModule extends BaseModule
{
    private final CharacterLeftBar left;


    public CharacterModule(final ModuleManager managerParent)
    {
        super(ModuleID.CHARACTER, managerParent);
        left = new CharacterLeftBar(this);
        setLeft(left);
        left.GetManager().TestFill(left.GetTree(), false);
    }
    public final CharacterLeftBar GetLeft(){return left;}
    public final CharacterTemplate GetTemplate(){return left.GetTree().GetTemplate();}


}
