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
	private int freeDeletes;	
	private int miniGame;
	private boolean freeDeleteUsed = false;
	
	/**
	 * Constructor for User
	 */
	public User() {
		this.collectibles = new HashSet<>();
		this.gold = 0;
		this.freeDeletes = 0;
		this.miniGame = 0;
		getUserInfo();
	}
	
	/**
	 * Returns total gold count of the user
	 * @return    user's total gold
	 */
	public int getMoney() {
		return gold;
	}
	
	/**
	 * Indicates whether a free delete has been used
	 * User will get no penalty
	 * @param tf    indicates whether user has used a delete
	 */
	public void setFreeDeleteUsed(boolean tf) {
		this.freeDeleteUsed = tf;
	}
	
	/**
	 * Checks if a free delete has been used
	 * @return    true if a delete has been used
	 * 			  false otherwise
	 */
	public boolean checkFreeDeleteUsed() {
		return this.freeDeleteUsed;
	}
	
	/**
	 * Give user gold
	 * @param value    gold given to the user
	 */
	public void addMoney(int value) {
		this.gold += value;
		updateUserInfo();
	}
	
	/**
	 * @precondition    user has enough gold to buy
	 * Withdraw money from user gold bank
	 * @param value     amount of gold to be withdrawn
	 */
	public void withdrawMoney(int value) {
		this.gold -= value;
		updateUserInfo();
	}
	
	/**
	 * Update amount of free deletes a user has
	 * @param i    amount of deletes used or given to the user
	 */
	public void setFreeDelete(int i) {
		this.freeDeletes += i;
		updateUserInfo();
	}
	
	/**
	 * Returns number of free deletes the user has
	 * @return    number of free deletes
	 */
	public int getFreeDeletes() {
		return this.freeDeletes;
	}
	
	/**
	 * Check if user has collected all animal rewards (excluding money_pig and rubber_duck)
	 * @return    true if user has collected all animal rewards
	 *     	      false otherwise
	 */
	public boolean checkAllCollectibles() {
		return collectibles.size() == 18;
	}
	
	/**
	 * Indicates if user has access to the mini game
	 * @param tf    indicates whether user has access to the mini game
	 */
	public void setMiniGame(int tf) {
		this.miniGame = tf;
	}
	
	/**
	 * Checks if user has access to the mini game
	 * @return    true if user has access to mini game
	 *  	      false otherwise
	 */
	public int checkMiniGameAccess() {
		return this.miniGame;
	}
	
	/**
	 * Remove all collectibles 
	 */
	public void resetCollectibles() {
		this.collectibles.clear();
		updateUserInfo();
	}
	
	/**
	 * Add a collectible to the user's collectibles (exlcuding money_pig and rubber_duck)
	 * Money pig will allow access to mini game
	 * Rubber duck will give user free deletes
	 * @param animalReward    collectible received 
	 */
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
	
	/**
	 * Returns all of user's collectibles
	 * @return    set of user's collectibles
	 */
	public Set<String> getCollectibles() {
		return collectibles;
	}
	
	/**
	 * Write to file the updated user information
	 */
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
	
	/**
	 * Gets user information from file
	 */
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
