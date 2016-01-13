/*
GanttProject is an opensource project management tool.
Copyright (C) 2002-2010 Alexandre Thomas, Dmitry Barashev

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 3
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package net.sourceforge.ganttproject.task;

import java.awt.Color;
import java.util.List;

import biz.ganttproject.core.chart.render.ShapePaint;
import biz.ganttproject.core.time.GanttCalendar;
import biz.ganttproject.core.time.TimeDuration;

import net.sourceforge.ganttproject.document.Document;
import net.sourceforge.ganttproject.task.dependency.TaskDependencySlice;

/**
 * Project task definition
 *
 * @author bard
 */
public interface Task extends MutableTask {
  /** Available task priorities */
  public enum Priority {
    LOWEST("3"), LOW("0"), NORMAL("1"), HIGH("2"), HIGHEST("4");

    private final String myPersistentValue;

    private Priority(String persistentValue) {
      myPersistentValue = persistentValue;
    }


    /**
     * @return the Priority value for the given integer value, or
     *         DEFAULT_PRIORITY if unknown
     */
    public static Priority getPriority(int value) {
      for (Task.Priority p : Task.Priority.values()) {
        if (p.ordinal() == value) {
          return p;
        }
      }
      return DEFAULT_PRIORITY;
    }

    public String getPersistentValue() {
      return myPersistentValue;
    }

    /** @return the priority as a lower-case String */
    public String getLowerString() {
      return this.toString().toLowerCase();
    }

    /** @return the key to get the I18n value for the priority */
    public String getI18nKey() {
      return "priority." + getLowerString();
    }

    /**
     * @return the path to the icon representing the priority
     */
    public String getIconPath() {
      return "/icons/task_" + getLowerString() + ".gif";
    }

    public static Priority fromPersistentValue(String priority) {
      for (Priority p : values()) {
        if (p.getPersistentValue().equals(priority)) {
          return p;
        }
      }
      return Priority.NORMAL;
    }
  }


  public enum Role {
	  UNDEFINED("0"), PROJECT_MANAGER("1"), DEVELOPER("2"), DOC_WRITER("3"), TESTER("4"),
	  GRAPHIC_DESIGNER("5"), DOC_TRANSLATOR("6"), PACKAGER("7"), ANALYST("8"),
	  WEB_DESIGNER("9"), NO_SPECIFIC_ROLE("10");
      private final String myPersistentValue;

      private Role(String persistentValue) {
        myPersistentValue = persistentValue;
     }

      public static Role getRole(int value) {
          for (Task.Role r : Task.Role.values()) {
            if (r.ordinal() == value) {
              return r;
            }
          }
          return DEFAULT_ROLE;
        }

      public String getPersistentValue() {
          return myPersistentValue;
        }

      public String getLowerString() {
          return this.toString().toLowerCase();
        }

      public String getI18rKey() {
        return "role." + getLowerString();
      }

      public static Role fromPersistentValue(String role) {
          for (Role r : values()) {
            if (r.getPersistentValue().equals(role)) {
              return r;
            }
          }
          return Role.UNDEFINED;
        }
  }

  /** Default priority (for new tasks) */
  public static final Priority DEFAULT_PRIORITY = Priority.NORMAL;
  public static final Role DEFAULT_ROLE = Role.UNDEFINED;

  TaskMutator createMutator();

  TaskMutator createMutatorFixingDuration();

  // main properties
  int getTaskID();

  String getName();

  boolean isMilestone();

  Priority getPriority();

  Role getRole();

  List<TaskActivity> getActivities();

  GanttCalendar getStart();

  GanttCalendar getDisplayEnd();
  GanttCalendar getEnd();

  TimeDuration getDuration();

  TimeDuration translateDuration(TimeDuration duration);

  int getCompletionPercentage();

  ShapePaint getShape();

  /**
   * @return a color representing this Task (could be a custom color, milestone
   *         color, super task color or default color)
   */
  Color getColor();

  String getNotes();

  boolean getExpand();

  // HumanResource[] getAssignedHumanResources();
  ResourceAssignment[] getAssignments();

  TaskDependencySlice getDependencies();

  TaskDependencySlice getDependenciesAsDependant();

  TaskDependencySlice getDependenciesAsDependee();

  ResourceAssignmentCollection getAssignmentCollection();

  //
  Task getSupertask();

  Task[] getNestedTasks();

  void move(Task targetSupertask);

  void delete();

  TaskManager getManager();

  Task unpluggedClone();

  // Color DEFAULT_COLOR = new Color( 140, 182, 206); not used

  CustomColumnsValues getCustomValues();

  boolean isCritical();

  GanttCalendar getThird();

  void applyThirdDateConstraint();

  int getThirdDateConstraint();

  void setThirdDate(GanttCalendar thirdDate);

  void setThirdDateConstraint(int dateConstraint);

  TaskInfo getTaskInfo();

  boolean isProjectTask();

  boolean isSupertask();

  List<Document> getAttachments();

  void setCriticalTimes(long[] critTimes);

  long[] getCriticalTimes();

}
