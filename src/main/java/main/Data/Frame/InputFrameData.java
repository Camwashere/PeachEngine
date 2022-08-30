package main.Data.Frame;

import main.Module.Story.Scenario.Frame.Parameter.ParamType;

import java.io.Serializable;

public record InputFrameData(ParamType inputType, BaseFrameData baseData) implements Serializable
{
}
