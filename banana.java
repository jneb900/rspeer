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
@ScriptMeta(name = "usethis one", desc = "Grabs bananas", developer = "Dark798", category = ScriptCategory.MONEY_MAKING)
public class banana extends Script {

	@Override
	public void onStart() {
		// When the script is first started the segment of code in this method will be
		// ran once.
	}

	@SuppressWarnings("unused")
	@Override

	public int loop() {
		org.rspeer.runetek.api.movement.position.Area maindocks = org.rspeer.runetek.api.movement.position.Area
				.rectangular(3018, 3193, 3062, 3246);
		org.rspeer.runetek.api.movement.position.Area karamdocks = org.rspeer.runetek.api.movement.position.Area
				.rectangular(2947, 3152, 2964, 3140);
		org.rspeer.runetek.api.movement.position.Area plantation = org.rspeer.runetek.api.movement.position.Area
				.rectangular(2909, 3156, 2928, 3169);
		org.rspeer.runetek.api.movement.position.Area karamja = org.rspeer.runetek.api.movement.position.Area
				.rectangular(2883, 3129, 2963, 3188);
		org.rspeer.runetek.api.movement.position.Area depobox = org.rspeer.runetek.api.movement.position.Area
				.rectangular(3044, 3230, 3053, 3239);
		org.rspeer.runetek.api.movement.position.Area sarimboat = org.rspeer.runetek.api.movement.position.Area
				.rectangular(3028, 3234, 3039, 3208, 1);
		org.rspeer.runetek.api.movement.position.Area karamboat = org.rspeer.runetek.api.movement.position.Area
				.rectangular(2946, 3146, 2973, 3135, 1);
		org.rspeer.runetek.api.movement.position.Area place = org.rspeer.runetek.api.movement.position.Area
				.rectangular(2953, 3146, 2955, 3148);

		Npc officer = Npcs.getNearest("Customs officer");
		SceneObject bananatree = SceneObjects
				.getNearest(x -> x.getName().contains("Banana Tree") && x.containsAction("Pick"));
		Npc captain = Npcs.getNearest("Seaman Lorris");

		if (Inventory.isFull()) {// inventory is full so we need to bank
			if (karamja.contains(Players.getLocal())) {
				if (karamdocks.contains(Players.getLocal())) {
					if (Dialog.isOpen() || Dialog.isProcessing()) {
						if (Dialog.isViewingChatOptions() && Dialog.process(0))
							;
						if (Dialog.canContinue() && Dialog.processContinue())
							;
					} else {
						if (officer != null && officer.isPositionInteractable()) {
							officer.interact("Pay-fare");
						}
					}
				} else {
					Movement.walkTo(place.getCenter());
				}
			} else {
				if (sarimboat.contains(Players.getLocal())) {
					SceneObjects.getNearest("Gangplank").interact("Cross");
				}
			}
			if (DepositBox.isOpen()) {
				DepositBox.depositAll("Banana");
			} else {
				DepositBox.open();
			}
		} else {// inventory has space so we need to pick bananas
			if (karamboat.contains(Players.getLocal())) {
				SceneObjects.getNearest("Gangplank").interact("Cross");
			} else if (karamja.contains(Players.getLocal())) {
				if (bananatree != null && bananatree.isPositionInteractable()) {
					bananatree.interact("Pick");
				} else {
					Movement.walkTo(plantation.getCenter());
				}
			} else {// just banked, need to go back to karamja
				if (maindocks.contains(Players.getLocal())) {
					if (Dialog.isOpen() || Dialog.isProcessing()) {
						if (Dialog.isViewingChatOptions()) {
							Dialog.process(0);
						}
						if (Dialog.canContinue()) {
							Dialog.processContinue();
						}
					} else {
						Npcs.getNearest("Captain Tobias").interact("Pay-fare");
					}
				} else {
					Movement.walkTo(maindocks.getCenter());
				}
			}
		}

		return 400; // The rate of repeat is defined by the returning int, this number represents
					// milliseconds. 1000ms = 1 second.
	}

	@Override
	public void onStop() {
		// When the script is stopped the segment of code in this method will be ran
		// once.
	}
}

//////////////////////////////////////////////////
//if (karamja.contains(local) && !Inventory.isFull()) {
// on karamja and have inventory space
// if (plantation.contains(local)){
// SceneObject bananatree = SceneObjects.getNearest("Banana Tree");
// if (bananatree != null) {
// ((Interactable) bananatree).interact("Pick");
// }
// }

// else {Movement.walkTo((Positionable) plantation);
// }
//}

////////////////////////////////////////////////////