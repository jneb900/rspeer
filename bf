package blastfurnacesteel;

import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.EnterAmount;
import org.rspeer.runetek.api.component.EnterInput;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.ScriptCategory;

@SuppressWarnings({ "unused", "deprecation" })
@ScriptMeta(name = "Blastoff", desc = "Makes steel bars", developer = "Dark798", category = ScriptCategory.MONEY_MAKING)
public class blast extends Script {

	@Override
	public void onStart() {
		
	}

	
	
	@Override
    public int loop() {
		
		int count = 4;
    	Area scaf = Area.rectangular(1943, 4968, 1937, 4966);
    	Area coffer = Area.rectangular(1947, 4957, 1947, 4957);
		int Coins = Inventory.getCount(true, "Coins");
		int Result = (int) (Coins * 0.75);
		
    		

		if (Movement.isStaminaEnhancementActive()) {
			
			    
			    	if (count == 0) { // putting iron ore on conveyor belt
			    			if (Dialog.isOpen() && scaf.contains(Players.getLocal())) {
			    				Dialog.process(1);
			    			if (Inventory.contains("Iron Ore")) {
			    				SceneObjects.getNearest("Conveyor Belt").interact("Put-ore-on");
			    				}
			    			if (scaf.contains(Players.getLocal()) && !Inventory.contains("Iron Ore")) {
			    				count = 1;
			    			}
			    			else {
			    				if (Bank.isOpen()) {
			    					Bank.withdraw("Iron Ore", 25); }
			    				else {Bank.open();
			    				}
			    			}
			    		}
			    	}
			    	if (count == 1) { // putting coal in conveyor belt
			    			if (Inventory.contains("Coal")) {
			    				SceneObjects.getNearest("Conveyor Belt").interact("Put-ore-on");
			    			}
			    			if (scaf.contains(Players.getLocal()) && !Inventory.contains("Coal")) {
			    					count = 2; }
			    			else {
			    				if (Bank.isOpen()) {
			    					Bank.withdraw("Coal", 25); }
			    				else {Bank.open();
			    				}
			    			}
			    		}
			    	if (count == 2) { // cooling bars and banking
			    		if (!Inventory.contains("Steel Bars")) {
			    			if (SceneObjects.getNearest("Bar dispenser").containsAction("Take") == true) {
			    				if (Inventory.contains("Bucket of water")) {
			    					SceneObject dispenser = SceneObjects.getNearest("Bar dispenser");
			    					Inventory.use(a -> a.getName().equals("Bucket of water"), dispenser);
			    					}
			    				else {SceneObjects.getNearest("Sink").interact("Fill-bucket"); }
			    				}
	    				if (Interfaces.isDialogOpen()) {
	    					Interfaces.processContinue();
	    				}
			    			else {SceneObjects.getNearest("Bar Dispenser").interact("Take"); }
			    			}
			    		else if (Bank.isOpen()) {
			    			Bank.depositAll("Steel Bars"); 
			    			count = 0; }
			    		else { SceneObjects.getNearest("Bank Chest").interact("Open");
			    		}
			    	}
			    	
			    	if (count == 3) { // paying foreman
						if (Dialog.isViewingChatOptions()) {
							Dialog.process(0);
							count = 0;
						}
						else{Npcs.getNearest("Blast Furnace Foreman").interact("Pay");
						}
					}
			    	
					if (count == 4) {  
						if (Dialog.isOpen() && !Players.getLocal().isMoving()) {
							if (Dialog.isViewingChatOptions()) {
								Dialog.process(0);
							}
							else if (EnterInput.isOpen()) {
								EnterInput.initiate(Result);
								count = 0;
							}
						}
						else if (coffer.contains(Players.getLocal())) {
							{SceneObjects.getNearest("Coffer").interact("Use");
						}
					}
				}
		    	
					if (Dialog.isOpen() && scaf.contains(Players.getLocal())) {
						count = 3;
					}
			    			
			    	

    		
			}
    	
	    else 
	    	if (Inventory.contains("Stamina Potion(1)", "Stamina Potion(2)", "Stamina Potion(3)", "Stamina Potion(4)")) {
	    		Inventory.getFirst("Stamina Potion(1)", "Stamina Potion(2)", "Stamina Potion(3)", "Stamina Potion(4)").interact("Drink"); }	
	    	else if (Bank.isOpen()) {
	    		Bank.withdraw("Stamina Potion(4)", 1); }
	    	else {Bank.open(); 
	    	
	    	}
		
		
        return 300; 
    }

	@Override
	public void onStop() {

	}
}
