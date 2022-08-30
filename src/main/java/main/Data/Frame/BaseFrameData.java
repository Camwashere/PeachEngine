package main.Data.Frame;

import main.Maths.Vec.Vec2;
import main.Module.Story.Scenario.Frame.FrameType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record BaseFrameData(UUID id, String name, FrameType type, Vec2 pos, Map<UUID, ParameterBaseData<?>> parameters) implements Serializable
{
}
