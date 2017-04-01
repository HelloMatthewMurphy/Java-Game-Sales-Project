public class GameSales
{
  private String saleDate;
  private int    saleUnits;
  private int    gameID;
  
  GameSales(String aSaleDate, int aSaleUnits, int aGameID)
  {
    saleDate  = aSaleDate;
    saleUnits =	aSaleUnits;
    gameID    = aGameID;
  }	  
  
  public String getSaleDate()
  {
	return saleDate;
  }
  public int getSaleUnits()
  {
	return saleUnits;
  }
  public int getGameID()
  {
	return gameID;
  }
}