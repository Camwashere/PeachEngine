package main.Data.Frame;

import java.io.Serializable;
import java.util.UUID;

public record ScenarioFrameData(UUID ref, BaseFrameData baseData) implements Serializable
{
}
