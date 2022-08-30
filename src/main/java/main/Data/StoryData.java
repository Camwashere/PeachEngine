package main.Data;

import java.io.Serializable;
import java.util.List;

public record StoryData(ScenarioData mainData, List<ScenarioData> scenarioData) implements Serializable
{
}
