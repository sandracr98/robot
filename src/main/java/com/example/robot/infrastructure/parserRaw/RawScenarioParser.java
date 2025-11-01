package com.example.robot.infrastructure.parserRaw;


import com.example.robot.application.port.in.GridSize;
import com.example.robot.application.port.in.RobotProgram;
import com.example.robot.application.port.in.ScenarioCommand;

import java.util.ArrayList;
import java.util.List;

public final class RawScenarioParser {

    private RawScenarioParser() { }

    public static ScenarioCommand parse(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Empty scenario input");
        }
        String[] lines = raw.strip().split("\\R+"); // separa por nuevas líneas
        if (lines.length < 3) throw new IllegalArgumentException("Incomplete scenario");

        // 1) Primera línea: grid
        String[] gridParts = lines[0].trim().split("\\s+");
        if (gridParts.length != 2) throw new IllegalArgumentException("Invalid grid line");
        int maxX = parseInt(gridParts[0], "maxX");
        int maxY = parseInt(gridParts[1], "maxY");
        GridSize grid = new GridSize(maxX, maxY);

        // 2) Pares (posición, instrucciones)
        List<RobotProgram> programs = new ArrayList<>();
        int i = 1;
        while (i < lines.length) {
            if (i + 1 >= lines.length) throw new IllegalArgumentException("Missing instruction line for robot at line " + (i+1));

            String posLine = lines[i].trim();
            String cmdLine = lines[i + 1].trim();
            i += 2;

            String[] pos = posLine.split("\\s+");
            if (pos.length != 3) throw new IllegalArgumentException("Invalid robot position line: " + posLine);

            int x = parseInt(pos[0], "startX");
            int y = parseInt(pos[1], "startY");
            char o = toOrientation(pos[2]);

            String instr = cmdLine.toUpperCase();
            if (!instr.matches("[LRM]+")) throw new IllegalArgumentException("Invalid instruction string: " + instr);

            programs.add(new RobotProgram(x, y, o, instr));
        }

        return new ScenarioCommand(grid, programs);
    }

    private static int parseInt(String s, String name) {
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Invalid integer for " + name + ": " + s); }
    }

    private static char toOrientation(String token) {
        if (token == null || token.isBlank()) throw new IllegalArgumentException("Missing orientation");
        char c = Character.toUpperCase(token.charAt(0));
        if ("NESW".indexOf(c) < 0) throw new IllegalArgumentException("Invalid orientation: " + token);
        return c;
    }
}
