/*
 * Copyright (c) 2022 Keystone Strategic b.v.
 *
 * All rights reserved.
 */

package org.icpc.tools.contest.util.floor;

import org.icpc.tools.contest.Trace;
import org.icpc.tools.contest.model.FloorMap;
import org.icpc.tools.contest.model.IPrinter;
import org.icpc.tools.contest.model.ITeam;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author verwoerd
 * @since 18-10-2022
 */
public class FloorGeneratorNWERC2022 extends FloorGenerator {
  // table width (in meters). ICPC standard is 1.8
  private static final float tw = 1.8f;

  // table depth (in meters). ICPC standard is 0.8
  private static final float td = 0.8f;

  // team area width (in meters). ICPC standard is 3.0
  private static final float taw = 2.0f;

  // team area depth (in meters). ICPC standard is 2.2
  private static final float tad = 2.0f;

  private static final int numRooms = 8;
  private static final int firstRoom = 9;
  private static final int numRows = 30;
  private static final int numCols = 30;
  private static final int numProblems = 13;
  private static final float innerRoomSpace = 1f;
  private static final float magicXDiff = .6f;
  private static final float magicXDiff2 = 1.6f;

  //    private static final boolean showTeams = false;
  private static final boolean showTeams = true;

  // If > 0, use balloons with these numbers
  private static final int useIntegerBalloons = -1;


  private static List<Integer> skippedTeams = new ArrayList<>();
  private static HashMap<Integer, Integer> teamsPerRoom = new HashMap<>();

  private static final FloorMap floor = new FloorMap(taw - .2f, tad - .2f, tw, td);

  private static final String balloon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  protected static void createAdjacentTeam(int teamNumber, int newId, double dx, double dy) {
    ITeam t = floor.getTeam(teamNumber);
    floor.createTeam(newId, t.getX() + dx, t.getY() + dy, t.getRotation());
  }

  public static void main(String[] args) {
    Trace.init("ICPC Floor Map Generator", "floorMap", args);
//
//    skippedTeams.add(18);
//    skippedTeams.add(52);
//    skippedTeams.add(68);
//    skippedTeams.add(80);
//    skippedTeams.add(94);
//    skippedTeams.add(105);
//    skippedTeams.add(113);
//    skippedTeams.add(126);

    try {
      float lastX = numRows * taw;
      float lastY = numCols * tad;

      for (int i = 0; i < numProblems; i++) {
        float y = lastY + 1;
        float x = lastX + (i + 1) * 20f / 12f; //roomHeight + 20f * (i + 1) / 12f;
        if (useIntegerBalloons > 0) {
          floor.createBalloon((i + useIntegerBalloons) + "", x, y);
        } else {
          floor.createBalloon(balloon.charAt(i) + "", x, y);
        }
      }
      IPrinter p = floor.createPrinter(lastX + 1, lastY + 1);

      // Foyer bounding box
      float foyerStart = lastX - 7 * taw;
      float foyerHeight = lastY - 10 * tad;
      floor.createAisle(foyerStart - 1, lastY, lastX, lastY);
      floor.createAisle(foyerStart - 1, lastY, foyerStart - 1, foyerHeight);
      floor.createAisle(lastX, foyerHeight, foyerStart - 1, foyerHeight);
      floor.createAisle(lastX, foyerHeight, lastX, lastY);
      int teamId = 1;
      for (int team = 0; team < 5; team++) {
        float y = lastY - (2 * tad) * team;
        floor.createAisle(foyerStart - 1, y, lastX, y);
        for (int times = 0; times < 7; times++) {
          floor.createTeam(teamId++, foyerStart + 1 + taw * times, y - .5 * tad, FloorMap.E);
          floor.createTeam(teamId++, foyerStart + 1 + taw * times, y - 1.5 * tad, FloorMap.E);
        }
      }

      // stairs 2 second floor
      float videXStart = taw * 5;
      float videYEnd = lastY - 6 * tad;
      float videYStart = 6 * tad;
      floor.createAisle(foyerStart, lastY - tad, taw, lastY - tad);
      floor.createAisle(taw, lastY - tad, taw, videYEnd - 5 * tad);
      floor.createAisle(taw, videYEnd - 5 * tad, videXStart, videYEnd - 5 * tad);

      // hallway before senaatszaal
      floor.createAisle(videXStart, videYStart, videXStart, videYEnd);


      // commissie kamer south
      floor.createAisle(videXStart, videYEnd, videXStart + 2 * taw, videYEnd);
      floor.createAisle(videXStart + 2 * taw, videYEnd, videXStart + 2 * taw, videYEnd + 4 * tad);
      floor.createAisle(videXStart + 2 * taw, videYEnd, videXStart + 3 * taw, videYEnd);
      floor.createAisle(videXStart + 3 * taw, videYEnd, videXStart + 3 * taw, videYEnd + 4 * tad);
      // left column
      floor.createTeam(teamId++, videXStart + 1.5 * taw, videYEnd + .5 * tad, FloorMap.N);
      floor.createTeam(teamId++, videXStart + 1.5 * taw, videYEnd + 1.5 * tad, FloorMap.N);
      floor.createTeam(teamId++, videXStart + 1.5 * taw, videYEnd + 2.5 * tad, FloorMap.N);
      // middle column
      floor.createTeam(teamId++, videXStart + 2.5 * taw, videYEnd + .5 * tad, FloorMap.N);
      floor.createTeam(teamId++, videXStart + 2.5 * taw, videYEnd + 1.5 * tad, FloorMap.N);
      floor.createTeam(teamId++, videXStart + 2.5 * taw, videYEnd + 2.5 * tad, FloorMap.N);
      floor.createTeam(teamId++, videXStart + 2.5 * taw, videYEnd + 3.5 * tad, FloorMap.N);
      // right column
      floor.createTeam(teamId++, videXStart + 3.5 * taw, videYEnd + .5 * tad, FloorMap.N);
      floor.createTeam(teamId++, videXStart + 3.5 * taw, videYEnd + 1.5 * tad, FloorMap.N);
      floor.createTeam(teamId++, videXStart + 3.5 * taw, videYEnd + 2.5 * tad, FloorMap.N);
      floor.createTeam(teamId++, videXStart + 3.5 * taw, videYEnd + 3.5 * tad, FloorMap.N);


      // commissie kamers North
      floor.createAisle(videXStart, videYStart, videXStart + 4 * taw, videYStart);
      floor.createAisle(videXStart + 2 * taw, videYStart, videXStart + 2 * taw, videYStart - 4 * tad);
      floor.createAisle(videXStart + 2 * taw, videYStart, videXStart + 3 * taw, videYStart);
      floor.createAisle(videXStart + 4 * taw, videYStart, videXStart + 4 * taw, videYStart - 4 * tad);
      // left column left room
      for (int i = 0; i < 4; i++) {
        floor.createTeam(teamId++, videXStart + (i + 1.5) * taw, videYStart - .5 * tad, FloorMap.S);
        floor.createTeam(teamId++, videXStart + (i + 1.5) * taw, videYStart - 1.5 * tad, FloorMap.S);
        floor.createTeam(teamId++, videXStart + (i + 1.5) * taw, videYStart - 2.5 * tad, FloorMap.S);
        floor.createTeam(teamId++, videXStart + (i + 1.5) * taw, videYStart - 3.5 * tad, FloorMap.S);
      }

      // Senaatszaal

      floor.createAisle(videXStart, videYStart + 4 * taw, videXStart + 12 * tad, videYStart + 4 * taw);
      floor.createAisle(videXStart, videYEnd - 4 * taw, videXStart + 12 * tad, videYEnd - 4 * taw);
      floor.createAisle(videXStart + 10 * tad, videYStart + 4 * taw, videXStart + 10 * tad, videYEnd - 4 * taw);
      floor.createAisle(videXStart + 12 * tad, videYStart + 4 * taw, videXStart + 12 * tad, videYEnd - 4 * taw);
      // aisles to east wall
      floor.createAisle(videXStart + 10 * tad, videYStart + 6 * taw, videXStart + 7 * tad, videYStart + 6 * taw);
      floor.createAisle(videXStart + 10 * tad, videYStart + 8 * taw, videXStart + 5 * tad, videYStart + 8 * taw);
      floor.createAisle(videXStart + 10 * tad, videYStart + 10 * taw, videXStart + 5 * tad, videYStart + 10 * taw);
      floor.createAisle(videXStart + 10 * tad, videYStart + 12 * taw, videXStart + 5 * tad, videYStart + 12 * taw);
      // aisles to west wall
      floor.createAisle(videXStart + 12 * tad, videYStart + 7 * taw, videXStart + 14 * tad, videYStart + 7 * taw);
      floor.createAisle(videXStart + 12 * tad, videYStart + 11 * taw, videXStart + 14 * tad, videYStart + 11 * taw);

      // north corner teams
      floor.createTeam(teamId++, videXStart + 12 * tad, videYStart + 3.5 * taw, FloorMap.E);
      floor.createTeam(teamId++, videXStart + 11 * tad, videYStart + 3.5 * taw, FloorMap.E);
      floor.createTeam(teamId++, videXStart + 10 * tad, videYStart + 3.5 * taw, FloorMap.E);

      // south corner teams
      floor.createTeam(teamId++, videXStart + 12 * tad, videYEnd - 3.5 * taw, FloorMap.E);
      floor.createTeam(teamId++, videXStart + 11 * tad, videYEnd - 3.5 * taw, FloorMap.E);
      floor.createTeam(teamId++, videXStart + 10 * tad, videYEnd - 3.5 * taw, FloorMap.E);

      // west wall teams
      floor.createTeam(teamId++, videXStart + 14 * tad, videYStart + 7 * taw, FloorMap.E);
      floor.createTeam(teamId++, videXStart + 14 * tad, videYStart + 11 * taw, FloorMap.E);
      floor.createTeam(teamId++, videXStart + 13 * tad, videYStart + 8 * taw, FloorMap.S);
      floor.createTeam(teamId++, videXStart + 13 * tad, videYStart + 10 * taw, FloorMap.N);

      // east wall teams
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          floor.createTeam(teamId++, videXStart + (9.5 - i) * tad, videYStart + (5 + 2 * j) * taw, FloorMap.E);
        }
      }

      // route to van hasselt
      float vanHasseltEntranceX = lastX + 2 * taw;
      floor.createAisle(lastX, lastY, vanHasseltEntranceX, lastY);
      floor.createAisle(vanHasseltEntranceX, lastY, vanHasseltEntranceX, foyerStart - 6 * taw);
      // aisles in van hasselt
      floor.createAisle(vanHasseltEntranceX, foyerStart - 6 * taw, vanHasseltEntranceX - 2 * tad, foyerStart - 8 * taw);
      floor.createAisle(vanHasseltEntranceX - 2 * tad, foyerStart - 8 * taw, vanHasseltEntranceX + 8 * tad,
          foyerStart - 8 * taw);
      floor.createAisle(vanHasseltEntranceX, foyerStart - 6 * taw, vanHasseltEntranceX + 8 * tad, foyerStart - 8 * taw);
      floor.createAisle(vanHasseltEntranceX, foyerStart - 12 * taw, vanHasseltEntranceX + 8 * tad,
          foyerStart - 8 * taw);

      // van hasselt west teams
      floor.createTeam(teamId++, vanHasseltEntranceX - tad, foyerStart - 6 * taw, 135);
      floor.createTeam(teamId++, vanHasseltEntranceX - 2 * tad, foyerStart - 7 * taw, 135);
      floor.createTeam(teamId++, vanHasseltEntranceX - 3 * tad, foyerStart - 8 * taw, 135);

      // van hasselt east bottom teams
      for (int i = 0; i < 8; i++) {
        floor.createTeam(teamId++, vanHasseltEntranceX + (i + 1) * tad, foyerStart - (5.7 + i * .25) * taw, 194);
      }

      // van hasselt east top teams
      for (int i = 0; i < 6; i++) {
        floor.createTeam(teamId++, vanHasseltEntranceX + (i + 1) * tad, foyerStart - (12 - i * .5) * taw, 149);
      }

      // van hasselt center teams
      for (int i = 0; i < 6; i++) {
        floor.createTeam(teamId++, vanHasseltEntranceX - tad + (tad * i), foyerStart - 8.5 * taw, FloorMap.E);
      }

      floor.write(Paths.get("tmp").toFile());

      Trace.trace(Trace.USER, "------------------");

      long time = System.currentTimeMillis();
      FloorMap.Path path1 = floor.getPath(p, floor.getTeam(6));
      FloorMap.Path path2 = floor.getPath(p, floor.getTeam(155));
      FloorMap.Path path3 = floor.getPath(p, floor.getTeam(107));

      Trace.trace(Trace.USER, "Time: " + (System.currentTimeMillis() - time));

      show(floor, -1, true);
      show(floor, 6, true, path1);
      show(floor, 155, true, path2);
      show(floor, 107, true, path3);
    } catch (Exception e) {
      Trace.trace(Trace.ERROR, "Error generating floor map", e);
    }
  }
}
