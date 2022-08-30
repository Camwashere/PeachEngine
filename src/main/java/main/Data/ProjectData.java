package main.Data;

import main.Project.ProjectTime;

import java.io.Serializable;

public record ProjectData(String name, ProjectTime time, ModuleManagerData moduleManagerData) implements Serializable
{
}
