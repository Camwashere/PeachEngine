package main.Data.Frame;

import java.io.Serializable;

public record StoryFrameData(BaseFrameData baseData, String text) implements Serializable
{
}
