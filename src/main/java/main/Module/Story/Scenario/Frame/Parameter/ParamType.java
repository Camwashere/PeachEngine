package main.Module.Story.Scenario.Frame.Parameter;

public enum ParamType
{
    GENERIC,
    FLOW,
    NUMBER,
    TEXT,
    BOOL,
    CHARACTER,
    ;
    public static String ToCode(final ParamType type)
    {
        switch (type)
        {
            case BOOL:
                return "Boolean";
            case NUMBER:
                return "Number";
            case TEXT:
                return "String";
            case FLOW:
                return "BaseFrame";
            case CHARACTER:
                return "StoryCharacter";
            default:
                return "Object";
        }
    }
}
