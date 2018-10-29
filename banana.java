package main;

import java.awt.geom.Area;
import java.util.List;

import org.rspeer.runetek.adapter.Interactable;
import org.rspeer.runetek.adapter.Positionable;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.component.DepositBox;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.ScriptCategory;

@SuppressWarnings("unused")
@ScriptMeta(name = "Bananagrab",  desc = "Grabs bananas", developer = "Dark798", category = ScriptCategory.MONEY_MAKING)
public class banana extends Script {

    @Override
    public void onStart() {
        //When the script is first started the segment of code in this method will be ran once.
    }

    @SuppressWarnings("unused")
	@Override
    

    public int loop() {
        Player local = Players.getLocal();
        org.rspeer.runetek.api.movement.position.Area maindocks = org.rspeer.runetek.api.movement.position.Area.rectangular(3026, 3215, 3029, 3223);
    	org.rspeer.runetek.api.movement.position.Area karamdocks = org.rspeer.runetek.api.movement.position.Area.rectangular(2951, 3146, 2957, 3147);
    	org.rspeer.runetek.api.movement.position.Area plantation = org.rspeer.runetek.api.movement.position.Area.rectangular(2909, 3156, 2928, 3169);
    	org.rspeer.runetek.api.movement.position.Area karamja = org.rspeer.runetek.api.movement.position.Area.rectangular(2883, 3129, 2963, 3188);
    	org.rspeer.runetek.api.movement.position.Area depobox = org.rspeer.runetek.api.movement.position.Area.rectangular(3044, 3230, 3053, 3239);
    	    	
    	//////////////////////////////////////////////////
    	if (karamja.contains(local) && !Inventory.isFull()) {
    		// on karamja and have inventory space
    		if (plantation.contains(local)){
    			SceneObject bananatree = SceneObjects.getNearest("Banana Tree");
					if (bananatree != null && !bananatree.containsAction("Search")) {
    					((Interactable) bananatree).interact("Pick");
    				}
					else {bananatree = null;
    			}
    		}
    		
    		else {Movement.walkTo(plantation.getCenter());
    		}
    	}
					
    	
    	////////////////////////////////////////////////////
    	
    	
    	//////////////////////////////////////////////////////
    	if (!karamja.contains(local) && !Inventory.isFull()) {
    		// not on karamja and has inventory space
    		if (maindocks.contains(local));{
    			if (Dialog.isOpen()) {
    				if (Dialog.isViewingChatOptions()) {
    					Dialog.process(0);
    	                }
    				 else Dialog.getContinue();}
    			else { Npc captain = Npcs.getNearest("Seaman Lorris");
    			}
					Object captain = null;
					if (captain != null){
    					((Interactable) captain).interact("Pay-fare");}
    			}
    				//////////////finish dialog here
    		}
    		else {Movement.walkTo(maindocks.getCenter());
    		}
    	
    		//////////////////////////////////////////////////////
    		
    		
    		//////////////////////////////////////////////
    		if (karamja.contains(local) && Inventory.isFull()) {
			// on karamja and inventory is full
    		if (karamdocks.contains(local)){
    			if (Dialog.isOpen()) {
    				if (Dialog.isViewingChatOptions()) {
    					Dialog.process(0);
    				}
    			else {Dialog.getContinue();
    			}
    		}
    			else {Npc officer = Npcs.getNearest("Customs officer");}    				}
    				}
				Object officer = null;
				if (officer != null)
				{ ((Interactable) officer).interact("Pay-Fare");
				}
    		
    		else {Movement.walkTo(karamdocks.getCenter());
    		}
    	
    	
    	///////////////////////////////////////////
    	
    	
    	
    		////////////////////////////////////////////
    	if (!karamja.contains(local) && Inventory.isFull()) 
    		// not on karamja and inventory is full
    		if (depobox.contains(local)){
    			if (DepositBox.isOpen());{
    				DepositBox.depositAllExcept("coins");
    				}
    			}
    	   	else {DepositBox.open();
    	} else {Movement.walkTo(depobox.getCenter());
    }
    /////////////////////////////////////////////////////
    
    	
        return 400; // The rate of repeat is defined by the returning int, this number represents milliseconds. 1000ms = 1 second.
    	}
	
    
    

    @Override
    public void onStop() {
        //When the script is stopped the segment of code in this method will be ran once.   
    }
}





//////////////////////////////////////////////////
//if (karamja.contains(local) && !Inventory.isFull()) {
	// on karamja and have inventory space
	//if (plantation.contains(local)){
		//SceneObject bananatree = SceneObjects.getNearest("Banana Tree");
			//if (bananatree != null) {
				//((Interactable) bananatree).interact("Pick");
			//}
		//}
	
	//else {Movement.walkTo((Positionable) plantation);
	//}
//}
			

////////////////////////////////////////////////////