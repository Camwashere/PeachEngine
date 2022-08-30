package main.Data.Frame;

import main.Module.Story.Scenario.Frame.FunctionFrame.FunctionType;

import java.io.Serializable;

public record FunctionFrameData(FunctionType functionType, BaseFrameData baseData) implements Serializable
{
}
