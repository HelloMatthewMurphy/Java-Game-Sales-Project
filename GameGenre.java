public class GameGenre
{
  private int    genreID;
  private String genreName;
  
  GameGenre(int aGenreID, String aGenreName)
  {
    genreID   = aGenreID;
    genreName = aGenreName;
  }
  
  public int getGenreID()
  {
    return genreID;
  }
  
  public String getGenreTitle()
  {
    return genreName;
  }
}