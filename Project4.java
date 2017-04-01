import java.io.*;
import java.util.*;
import javax.swing.*;
public class Project4 {
	public static ArrayList <GameGenre> gameGenresList = new ArrayList <GameGenre>();
	public static ArrayList <GameDevelopers> gameDevelopersList = new ArrayList <GameDevelopers>();
	public static ArrayList <GameDetails> gameDetailsList = new ArrayList <GameDetails>();
	public static ArrayList <GameSales> gameSalesList = new ArrayList <GameSales>();
	// This checks if loadArrays is true and will not run the program if one of the files don't exist
	public static void main(String [] args) {
		
		if (loadArrays()) {
			String instruction ="";
			instruction = displayMenu(instruction);
		}
	}
	
	//Displays the first drop down menu and gives the user the 5 different options every method returns to here until the press cancel or quit
	public static String displayMenu(String instruction)
	{
		String game = "Game sales",genre = "Genre sales",developer = "Developer sales",tsGame = "Total sales for a game",tsGenre = "Total sales for a genre",quit = "Quit";
		Object menuSelect[] ={"Game sales","Genre sales","Developer sales","Total sales for a game","Total sales for a genre", "Quit"};
		String displayMenu;
		boolean done = false;
		while(!done){
			displayMenu = (String)JOptionPane.showInputDialog(null,"Choose one", "Select Option", 1, null, menuSelect, menuSelect[0]);
			if(displayMenu == null || displayMenu == quit)
				done = true;
			else if(displayMenu == game)
				monthlySales(0);
			else if(displayMenu == genre)
				monthlySales(1);
			else if(displayMenu == developer)
				monthlySales(2);
			else if(displayMenu == tsGame)
				 totalMonthSalesGame();
			else if(displayMenu == tsGenre)
				 totalMonthSalesGenre();
		}
		return instruction;
	}
	
	//Menu of names depending on if the user wants genre names dev names or game names
	public static int menuList(int selection){
		int count = 0;
		System.out.print(gameGenresList.get(1).getGenreTitle());
		if (selection == 0){
			Object menuSelect[] = new Object[gameDetailsList.size()];
			for(int i = 0; i < gameDetailsList.size(); i++){
				menuSelect[i] = gameDetailsList.get(i).getGameTitle();
			}
			count = findPos(menuSelect);
		}
		else if (selection == 1){
			Object menuSelect[] = new Object[gameGenresList.size()];
			for(int i = 0; i < gameGenresList.size(); i++){
				menuSelect[i] = gameGenresList.get(i).getGenreTitle();
			}
			count = findPos(menuSelect);
		}
		else if(selection ==2){
			Object menuSelect[] = new Object[gameDevelopersList.size()];
			for(int i = 0; i < gameDevelopersList.size(); i++){
				menuSelect[i] = gameDevelopersList.get(i).getDevTitle();
			}
			count = findPos(menuSelect);
		}
		return count;
	}
	
	public static int findPos(Object [] menuSelect){
		int count = 0;
		String option = "";
		boolean done = false;
		boolean optionSelected = false;
		option = (String)JOptionPane.showInputDialog(null,"Choose one", "Select Option", 1, null, menuSelect, menuSelect[0]);
		if(option != null)
			optionSelected = true;
		if(option != null){
			for( ; count < menuSelect.length && !done; count++){
				String temp = (String)menuSelect[count];
				if(option.matches(temp))
					done = true;
			}
		}
		return count;
	}
	
	// Load in the objects to each array from the txt files
	public static boolean loadArrays()
	{
		boolean result = true;
		String fileName = "";
		try {
				for(int i = 0; i < 4; i++)
			{
				if(i == 0)
					fileName = "GameDetails.txt";
				else if(i == 1)
					fileName = "GameGenres.txt";
				else if(i == 2)
					fileName = "GamesDevelopers.txt";
				else
					fileName = "GameSales.txt";
				FileReader fileRead = new FileReader(fileName);
				BufferedReader bufferRead = new BufferedReader(fileRead);
				String line = bufferRead.readLine();
				while(line != null)
				{
					String [] lineTemp = line.split(",");
					if(i == 0){
						int gameID = Integer.parseInt(lineTemp[0]);
						String gameTitle = lineTemp[1];
						int gameGenreID = Integer.parseInt(lineTemp[3]);
						int gameDeveloperID = Integer.parseInt(lineTemp[2]);
						double gamePrice = Double.parseDouble(lineTemp[4]);
						GameDetails objt = new GameDetails(gameID, gameTitle, gameGenreID, gameDeveloperID, gamePrice); 
						gameDetailsList.add(objt); 
					}
					else if(i == 1){
						int    genreID = Integer.parseInt(lineTemp[0]);
						String genreName = lineTemp[1];
						GameGenre objt = new GameGenre(genreID, genreName); 
						gameGenresList.add(objt); 
					}
					else if(i == 2){
						int id = Integer.parseInt(lineTemp[0]);
						String devName = lineTemp[1];
						GameDevelopers objt = new GameDevelopers(id, devName); 
						gameDevelopersList.add(objt);
					}
					else{
						String saleDate = lineTemp[0];
						int saleUnits = Integer.parseInt(lineTemp[1]);
						int gameID = Integer.parseInt(lineTemp[2]);
						GameSales objt = new GameSales(saleDate, saleUnits, gameID); 
						gameSalesList.add(objt); 
					}
					line = bufferRead.readLine();
				}
				bufferRead.close();
			}
		} catch (IOException e) {
			System.out.println(fileName + " does not exist, program now closing.");
			result = false;
		}
		return result;
	}
	
	//Gets the game price
	public static double getGamePrice(int gameID){
		boolean found = false;
		double price = 0.0;
		for(int i=0;i<gameDetailsList.size() && !found;i++){
			if(gameDetailsList.get(i).getGameID() == gameID){
				price = gameDetailsList.get(i).getGamePrice();
				found = true;
			}
		}
		return price;
	}
	
	//Gets the monthly sales for games developers and genres depending on user input
	public static void monthlySales (int selection){
		String months [] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
		int [] salesByMonth = new int [Calendar()];
		double [] priceByMonth = new double[Calendar()];
		String result = "";
		double salesAmount = 0.0;
		int option = menuList(selection);
		double gamePrice =0.0;
		if(option != 0)
		{
			for(int i=0;i<gameSalesList.size();i++){
				String currentDate = gameSalesList.get(i).getSaleDate();
				int currentUnitsSold = gameSalesList.get(i).getSaleUnits();
				int currentGameID = gameSalesList.get(i).getGameID();
				boolean found = false;
				if(selection == 0){
					result = gameDetailsList.get(option-1).getGameTitle() + "\n";
					if(option == currentGameID)
						found = true;
				}
				else if(selection == 1){
					result = gameGenresList.get(option-1).getGenreTitle() + "\n";
					int currentGenreID = gameDetailsList.get(currentGameID-1).getGenreID();
					
					if(option == currentGenreID)
						found = true;
				}
				else if(selection == 2){
					result = gameDevelopersList.get(option-1).getDevTitle() + "\n";
					int currentDeveloperID = gameDetailsList.get(currentGameID-1).getDeveloperID();
					if(option == currentDeveloperID)
						found = true;
				}
				if(found){
					String splitDate [] = currentDate.split("/");
					int month = Integer.parseInt(splitDate[1]);
					salesByMonth [month-1] += currentUnitsSold;
				}
				gamePrice = gameDetailsList.get(currentGameID-1).getGamePrice();
			}
			
			for(int i=0;i< salesByMonth.length;i++)
			{
				priceByMonth[i] = salesByMonth[i] * gamePrice;
			}
			for(int i = 0; i < salesByMonth.length; i++){
				result += months[i] + ": " + salesByMonth[i] + " : \u20AC"+ +priceByMonth[i]+"\n";
			}
			JOptionPane.showMessageDialog(null, result);
		}
	}
	
	//Gets the Month
	public static int Calendar() 
	{
		GregorianCalendar aCalendar = new GregorianCalendar();
		int month = aCalendar.get(Calendar.MONTH) + 1;
		return month;
	}
	
	//Geths the total sales for all Games
	public static void totalMonthSalesGame(){
		String displayGameSales[] = new String [gameSalesList.size()];
		String tempArray[][] = new String [displayGameSales.length][3];
		String result = "";
		double currentDouble;
		
		for (int i=0 ; i<gameDetailsList.size();i++){
			double currentGamePrice = gameDetailsList.get(i).getGamePrice();
			double salesAmount =0.0;
			int currentGenreID = gameDetailsList.get(i).getGenreID();
			int currentGameID = gameDetailsList.get(i).getGameID();
			String currentGameName = gameDetailsList.get(i).getGameTitle();
			for (int k=0 ; k<gameSalesList.size() ; k++){
			    if(currentGenreID == gameSalesList.get(k).getGameID())
                    salesAmount += currentGamePrice * gameSalesList.get(k).getSaleUnits();	
			}
			displayGameSales[i] = currentGameID + "," + currentGameName + "," + salesAmount;
	    }
		double [] sortingArray = new double [displayGameSales.length];
		for (int i=0 ; i < gameDetailsList.size() ; i++){
			String temp [] = displayGameSales[i].split(",");
			currentDouble = Double.parseDouble(temp[2]);
			currentDouble = Math.round(currentDouble * 100);
            currentDouble = currentDouble/100;
			temp[2] = String.valueOf(currentDouble);
			sortingArray[i] = Double.parseDouble(temp[2]);
			displayGameSales[i] = temp[0] + "," + temp[1] + "," + "\u20AC" + temp[2];
		}
		displayGameSales = bubbleSortDescending(displayGameSales, sortingArray);
		for (int i=0; i<gameDetailsList.size() ;i++)
			result += displayGameSales[i] + "\n";
		JOptionPane.showMessageDialog(null, result);
		
	}
	
	// Gets the total sales for all Genres
	public static void totalMonthSalesGenre(){
		
		String displayGenreSales[] = new String [gameGenresList.size()];
		String result = "";
		
		for (int i=0 ; i<gameGenresList.size();i++){
			String currentGameGenre = gameGenresList.get(i).getGenreTitle();
			int salesAmount=0;
			int currentGenreID = gameDetailsList.get(i).getGenreID();
			for (int k=0 ; k<gameDetailsList.size() ; k++){
			    if(currentGenreID == gameDetailsList.get(k).getGenreID())
                    salesAmount += gameSalesList.get(k).getSaleUnits();	
			}
			displayGenreSales[i] = currentGenreID + "," + currentGameGenre + "," + salesAmount;
	    }
		displayGenreSales = bubbleSortAscending(displayGenreSales);
		for (int i=0 ; i < displayGenreSales.length ; i++){
			result += displayGenreSales[i] + "\n";
		}
		JOptionPane.showMessageDialog(null, result);
	}
	
	//Sorts the Games Sales in descending sequence and returns the value
	public static String[] bubbleSortDescending(String[] displayGameSales, double[] sortingArray){
		
		double swap =0.0;
		String swap2="";
		
		for (int i = 0; i < displayGameSales.length ; i++) {
            for (int d = 0; d < displayGameSales.length - i - 1; d++) {
                if (sortingArray[d] < sortingArray[d+1]){
					swap       = sortingArray[d];
					sortingArray[d]   = sortingArray[d+1];
					sortingArray[d+1] = swap;
					swap2       = displayGameSales[d];
					displayGameSales[d]   = displayGameSales[d+1];
					displayGameSales[d+1] = swap2;
            }
        }
      }
	  return displayGameSales;
	}
	
	//Sorts the Game Genres in ascending sequence and return the value
	public static String[] bubbleSortAscending(String[] displayGenreSales){
		
		int swap=0;
		String swap2="";
		int [] sortingArray = new int [displayGenreSales.length];
		
		for (int i=0;i<gameGenresList.size();i++){
			String temp2 [] = displayGenreSales[i].split(",");
			sortingArray[i] = Integer.parseInt(temp2[2]);
		}
		
		for (int i = 0; i < displayGenreSales.length ; i++) {
            for (int d = 0; d < displayGenreSales.length - i - 1; d++) {
                if (sortingArray[d] > sortingArray[d+1]){
					swap       = sortingArray[d];
					sortingArray[d]   = sortingArray[d+1];
					sortingArray[d+1] = swap;
					swap2       = displayGenreSales[d];
					displayGenreSales[d]   = displayGenreSales[d+1];
					displayGenreSales[d+1] = swap2;
            }
        }
      } 
		return displayGenreSales;
   }
}
	
	