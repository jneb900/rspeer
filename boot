package climbingboots;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.Trade;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.EquipmentSlot;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;
import org.rspeer.script.ScriptCategory;

//@ScriptMeta(name = "Bootsbuyer", desc = "Buys climbing boots from Tenzing", developer = "Dark798", category = ScriptCategory.MONEY_MAKING)
@ScriptMeta(developer = "Dark798", category = ScriptCategory.MONEY_MAKING, desc = "Buys climbing boots", name = "Boots",version = 0.01)

public class bootsbuyer extends Script {

	@Override
	public void onStart() {
		// When the script is first started the segment of code in this method will be
		// ran once.
	}
	int counter = 0;
	@Override
    public int loop() {
    	
    	Area cw = Area.rectangular(2432, 3078, 2449, 3100);
    	Area tenzinghouse = Area.rectangular(2818, 3557, 2822, 3553);
    	Area burthorpe = Area.rectangular(2789, 3500, 2948, 3620);
    	Player mule = Players.getNearest("OBDSN");
    	
    	if (counter == 0) {
    		if (!Trade.isOpen() && !Inventory.contains("Coins", "Games necklace(8)", "Ring of dueling(8)")) {
    			if (mule != null) {
    				mule.interact("Trade-with");
    			}
    		}
    		if (Trade.isOpen()) {
    			if (Trade.isWaitingForMe()) {
    				Trade.accept();
    			}
    		}
    		if (Bank.isOpen() && Inventory.contains("Coins", "Games necklace(8)", "Ring of dueling(8)")){
    			Bank.depositAll("Games necklace(8)", "Ring of dueling(8)");
    			counter++;
    		}
    		else {Bank.open();
    		}
    	}
    	
    	
    	
if (counter == 1) {
    if (!Inventory.isFull()) {
    	if (Movement.isRunEnabled()) {
    	if (!Equipment.isOccupied(EquipmentSlot.NECK)) {
    		if (Inventory.contains("Games necklace(8)")){
    			Inventory.getFirst("Games necklace(8)").interact("Wear");
    		}
    		else if (Bank.isOpen() && !Inventory.contains("Games necklace(8)")) {
    			Bank.withdraw("Games necklace(8)", 1);
    			Time.sleep(3000);
    			Log.info("gameneck");
    		}
    		else if (Bank.isOpen() && Inventory.contains("Games necklace(8)")){
    			Bank.close();
    			Time.sleep(1000);
    		}
    		else if (SceneObjects.getNearest("Bank chest") != null) {
    			SceneObjects.getNearest("Bank chest").interact("Use");
    	}
    		else {Bank.open();
    		}
    	}
    	if (!Equipment.isOccupied(EquipmentSlot.RING)) {
    		if (Inventory.contains("Ring of dueling(8)")){
    			Inventory.getFirst("Ring of dueling(8)").interact("Wear");
    		}
    		else if (Bank.isOpen()) {
    			Bank.withdraw("Ring of dueling(8)", 1);
    		}
    		else if (Bank.isOpen() && Inventory.contains("Ring of dueling(8)")){
    			Bank.close();
    		}
    		else if (SceneObjects.getNearest("Bank chest") != null) {
    			SceneObjects.getNearest("Bank chest").interact("Use");
    	}
    		else {Bank.open();
    		}
    	}	
    	if (Equipment.isOccupied(EquipmentSlot.RING) && Equipment.isOccupied(EquipmentSlot.NECK) && !burthorpe.contains(Players.getLocal())) {
    		if (Tab.EQUIPMENT.isOpen()){
                EquipmentSlot.NECK.interact("Burthorpe");
                Time.sleep(3000);
                }
    		else {Tabs.open(Tab.EQUIPMENT);
    		}
    	}
    		
    	if (burthorpe.contains(Players.getLocal())) {
        	if (tenzinghouse.contains(Players.getLocal())) {
        		if (Dialog.isOpen()) {
        			if (Dialog.isViewingChatOptions()) {
        				Dialog.process(0);
        				return 500;
        			}
        			else {Dialog.processContinue();
        				return 500;}
        		}
        		Npcs.getNearest("Tenzing").interact("Talk-to");
        	}
        	else Movement.walkTo(new Position(2819, 3558));
    	}
    
        }
    	else {Movement.toggleRun(true);}
        	if (Inventory.isFull()) {
        		if (cw.contains(Players.getLocal())){
        			if (Bank.isOpen()){
        				Bank.depositAll("Climbing boots");
        			}
        			else {SceneObjects.getNearest("Bank chest").interact("Use");
        			}
        		}
        		else if (Tab.EQUIPMENT.isOpen()){
                EquipmentSlot.RING.interact("Castle Wars");
                Time.sleep(3000);
                }
                else {Tabs.open(Tab.EQUIPMENT);
                }	
        	}
    	}
	}
        return 1000;
        
	}

	@Override
	public void onStop() {
		// When the script is stopped the segment of code in this method will be ran
		// once.
	}
}
