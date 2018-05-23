import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Manage user information
 */

public class User {
	private Set<String> collectibles;
	private int gold;
	private int freeDeletes;	// prob remove free freeDelete -> put duck gives 3 free deletes
	private int miniGame;
	private boolean freeDeleteUsed = false;
	
	public User() {
		this.collectibles = new HashSet<>();
		this.gold = 0;
		this.freeDeletes = 0;
		this.miniGame = 0;
		getUserInfo();
	}
	
	public int getMoney() {
		return gold;
	}
	
	public void setFreeDeleteUsed(boolean tf) {
		this.freeDeleteUsed = tf;
	}
	
	public boolean checkFreeDeleteUsed() {
		return this.freeDeleteUsed;
	}
	
	// call addMoney after any method that you want to save info -> only addmoney will updateuserinfo
	public void addMoney(int value) {
		this.gold += value;
		updateUserInfo();
	}
	
	// only called if enough money
	public void withdrawMoney(int value) {
		this.gold -= value;
		updateUserInfo();
	}
	
	public void setFreeDelete(int i) {
		this.freeDeletes += i;
		updateUserInfo();
	}
	
	public int getFreeDeletes() {
		return this.freeDeletes;
	}
	
	public boolean checkAllCollectibles() {
		return collectibles.size() == 18;
	}
	
	public void setMiniGame(int tf) {
		this.miniGame = tf;
	}
	
	public int checkMiniGameAccess() {
		return this.miniGame;
	}
	
	public void resetCollectibles() {
		this.collectibles.clear();
		updateUserInfo();
	}
	
	public void saveCollectible(String animalReward) {
		if (animalReward.equals("money_pig")) {
			this.miniGame = 1;
			return;
		}
		if (animalReward.equals("rubber_duck")) {
			this.freeDeletes += 3;
			return;
		}
		this.collectibles.add(animalReward);
		updateUserInfo();
	}
	
	// maybe page for user info -> colletible progress, etc
	public Set<String> getCollectibles() {
		return collectibles;
	}
	
	private void updateUserInfo() {

		PrintWriter writer = null;
		try {
			writer = new PrintWriter("user_info.txt", "UTF-8");
			for (String collectible : collectibles) {
				writer.println("- " + collectible);
			}
			writer.println("Gold: " + gold);
			writer.println("FreeDelete: " + freeDeletes);
			writer.println("MiniGameAccess: " + miniGame);
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} finally {
			if (writer != null) writer.close();
		}
	}
	
	private void getUserInfo() {
		File input = new File("user_info.txt");
		try {
			input.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scanner sc = null;
        try {
            sc = new Scanner(input);  
            while (sc.hasNext()) {
            	String[] line = sc.nextLine().split(" ");
            	switch (line[0]) {
            		case "-":
            			collectibles.add(line[1]);
            			break;
            		case "Gold:":
            			gold = Integer.parseInt(line[1]);
            			break;
            		case "FreeDelete:":
            			freeDeletes = Integer.parseInt(line[1]);
            			break;
            		case "MiniGameAccess:":
            			miniGame = Integer.parseInt(line[1]); 
            			break;
            	}
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (sc != null) sc.close();
        }
	}
	
	
	
	
	
	
	
}
