public class GameDevelopers
{
  private int    developerID;
  private String developerName;
  
  GameDevelopers(int aDeveloperID, String aDeveloperName)
  {
    developerID   = aDeveloperID;
    developerName = aDeveloperName;
  }
  
  public int getDevID()
  {
    return developerID;
  }
  
  public String getDevTitle()
  {
    return developerName;
  }
}