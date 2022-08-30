package main.Module.Relationship.Game;

import main.Maths.Vec.Vec2;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Game.Variables.CharacterVariableBase;
import main.Module.Relationship.RelationshipGrid;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class RelationshipManager
{
    private final RelationshipGrid grid;
    private final StoryCharacter parent;
    private final LinkedMapProperty<UUID, Relationship> relationships = new LinkedMapProperty<>();

    public RelationshipManager(final StoryCharacter parentChar)
    {
        parent = parentChar;
        grid = parent.GetManager().GetParentModule().GetParent().GetRelationshipModule().GetCenter().GetGrid();
    }

    public final Relationship GetRelationship(final UUID id)
    {
        if (relationships.containsKey(id))
        {
            return relationships.get(id);
        }
        CreateRelationship(id);
        return GetRelationship(id);
    }
    private void CreateRelationship(final UUID id)
    {
        CreateRelationship(parent.GetManager().GetCharacter(id));
    }
    private void CreateRelationship(final StoryCharacter other)
    {
        Vec2 start = new Vec2(grid.GetStartPoint().GetOffsetProp().GetX(),grid.GetStartPoint().GetOffsetProp().GetY());
        for (final Disposition d : parent.GetDispositions().values())
        {
            for (final CharacterVariableBase v : other.GetVariables().values())
            {
                if (d.GetVariable().equals(v))
                {
                    d.Apply(start);
                    break;
                }
            }
        }
        Relationship r = new Relationship(parent.GetID(), other.GetID());
        r.SetPos(start);
        relationships.put(r.GetOtherCharacter(), r);
    }

    public final boolean HasRelationship(final UUID id){return relationships.containsKey(id);}


    public static void Meet(final StoryCharacter left, final StoryCharacter right)
    {
        left.GetRelationshipManager().CreateRelationship(right);
        right.GetRelationshipManager().CreateRelationship(left);
    }


}
