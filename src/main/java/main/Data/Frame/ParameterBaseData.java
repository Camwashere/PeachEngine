package main.Data.Frame;

import main.Module.Story.Scenario.Frame.Parameter.ParamType;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record ParameterBaseData<T>(UUID id, ParamType type, String name, T value, boolean isArray, boolean isInput, List<ConnectionData> connectedParams) implements Serializable
{
}
