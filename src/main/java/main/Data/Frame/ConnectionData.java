package main.Data.Frame;

import java.io.Serializable;
import java.util.UUID;

public record ConnectionData(UUID frameID, UUID paramID) implements Serializable
{
}
