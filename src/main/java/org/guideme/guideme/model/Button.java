package org.guideme.guideme.model;

import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Button
{
  private String IifSet;
  private String IifNotSet;
  private String ISet;
  private String IUnSet;
  private String IText;
  private String Itarget;

  public Button(String target, String text, String ifSet, String ifNotSet, String Set, String UnSet)
  {
    this.Itarget = target;
    this.IText = text;
    this.IifNotSet = ifNotSet;
    this.IifSet = ifSet;
    this.ISet = Set;
    this.IUnSet = UnSet;
  }

  public void setUnSet(ArrayList<String> setList)
  {
    ComonFunctions.SetFlags(this.ISet, setList);
    ComonFunctions.UnsetFlags(this.IUnSet, setList);
  }

  public String getSet() {
    return this.ISet;
  }

  public String getUnSet() {
    return this.IUnSet;
  }

  public boolean canShow(ArrayList<String> setList)
  {
    return ComonFunctions.canShow(setList, this.IifSet, this.IifNotSet);
  }

  public String getText() {
    return this.IText;
  }

  public String getTarget() {
    return this.Itarget;
  }
}