package main.Data;

import main.Data.Frame.*;
import main.Module.Story.Scenario.ScenarioType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ScenarioData(UUID id, String name, ScenarioType type, Map<UUID, StoryFrameData> storyFrames, Map<UUID, FunctionFrameData> functionFrames, Map<UUID, ScenarioFrameData> scenarioFrames, Map<UUID, InputFrameData> inputFrames,
                           Map<UUID, BaseFrameData> otherFrames) implements Serializable
{
}
