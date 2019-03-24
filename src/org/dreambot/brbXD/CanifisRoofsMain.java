package org.dreambot.brbXD;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;

@ScriptManifest(category = Category.AGILITY, name = "CanifisRoofsBRB", author = "BrbXD", version = 1)
public class CanifisRoofsMain extends AbstractScript {
	Area wholeArea = new Area(3472, 3501, 3512, 3469, 0);
	Area wholeRoofArea = new Area(3472, 3501, 3512, 3469, 3);
	Area startArea = new Area(3505, 3488, 3512, 3485, 0);
	Area firstRoof = new Area(3505, 3498, 3509, 3489, 2);
	Tile firstRoofTile = new Tile(3506, 3492, 2);
	Area firstRoofJump = new Area(3504, 3500, 3507, 3497, 2);
	Area secondRoof = new Area(3497, 3506, 3503, 3504, 2);
	Area secondRoofJump = new Area(3495, 3506, 3498, 3504, 2);
	Area thirdRoof = new Area(3487, 3504, 3492, 3499, 2);
	Area thirdRoofJump = new Area(3485, 3500, 3487, 3499, 2);
	Area fourthRoof = new Area(3475, 3499, 3479, 3492, 3);
	Area fourthRoofJump = new Area(3477, 3492, 3479, 3490, 3);
	Area fifthRoof = new Area(3477, 3487, 3483, 3481, 2);
	Area sixthRoof = new Area(3487, 3478, 3503, 3469, 3);
	Area sixthRoofJump = new Area(3501, 3477, 3504, 3474, 3);
	Area sixthRoofPrep = new Area(3497, 3477, 3503, 3473, 3);
	Tile sixthRoofStart = new Tile(3489, 3476, 3);
	Tile sixthRoofGlitch = new Tile(3487, 3476, 3);
	Area finalRoof = new Area(3509, 3482, 3515, 3476, 2);
	Area finalRoofJump = new Area(3509, 3484, 3512, 3482, 2);

	GameObject currentOb;
	GroundItem mark;
	int fixGlitch = 0;

	@Override
	public int onLoop() {
		if (wholeArea.contains(getLocalPlayer()) && !startArea.contains(getLocalPlayer())) {
			getWalking().walk(startArea.getRandomTile());
			sleepUntil(() -> startArea.contains(getLocalPlayer()), Calculations.random(2500, 3000));
		}
		if (startArea.contains(getLocalPlayer())) {
			currentOb = getGameObjects().closest(i -> i != null && i.getName().contains("Tall tree"));
			currentOb.interact("Climb");
			sleepUntil(() -> firstRoofTile.distance(getLocalPlayer()) == 0, Calculations.random(5000, 6000));
			sleep(1000);
		}
		if (firstRoof.contains(getLocalPlayer())) {
			markCheck(firstRoof);
			currentOb = getGameObjects()
					.closest(i -> i != null && i.getName().contains("Gap") && firstRoofJump.contains(i));
			currentOb.interact("Jump");
			sleepUntil(() -> secondRoof.contains(getLocalPlayer()), Calculations.random(5000, 6000));
		}
		if (secondRoof.contains(getLocalPlayer())) {
			markCheck(secondRoof);
			currentOb = getGameObjects()
					.closest(i -> i != null && i.getName().contains("Gap") && secondRoofJump.contains(i));
			currentOb.interact("Jump");
			sleepUntil(() -> thirdRoof.contains(getLocalPlayer()), Calculations.random(5000, 6000));
		}
		if (thirdRoof.contains(getLocalPlayer())) {
			markCheck(thirdRoof);
			currentOb = getGameObjects()
					.closest(i -> i != null && i.getName().contains("Gap") && thirdRoofJump.contains(i));
			currentOb.interact("Jump");
			sleepUntil(() -> fourthRoof.contains(getLocalPlayer()), Calculations.random(5000, 6000));
		}
		if (fourthRoof.contains(getLocalPlayer())) {
			markCheck(fourthRoof);
			currentOb = getGameObjects()
					.closest(i -> i != null && i.getName().contains("Gap") && fourthRoofJump.contains(i));
			currentOb.interact("Jump");
			sleepUntil(() -> fifthRoof.contains(getLocalPlayer()), Calculations.random(5000, 6000));
		}
		if (fifthRoof.contains(getLocalPlayer())) {
			markCheck(fifthRoof);
			currentOb = getGameObjects().closest(i -> i != null && i.getName().contains("Pole-vault"));
			currentOb.interact("Vault");
			sleepUntil(() -> sixthRoofStart.distance(getLocalPlayer()) == 0, Calculations.random(5000, 6000));
		}

		if (sixthRoof.contains(getLocalPlayer())) {
			markCheck(sixthRoof);

			// if (!sixthRoofPrep.contains(getLocalPlayer())) {
			// log(Integer.toString(fixGlitch));
			// if (sixthRoofGlitch.distance() == 0) {
			// fixGlitch++;
			// if (fixGlitch > 3) {
			// log("Fuckin' glitch");
			// getTabs().logout();
			// } else {
			// fixGlitch = 0;
			// }
			// }
			// getWalking().walk(sixthRoofJump.getRandomTile());
			// sleepUntil(() -> sixthRoofPrep.contains(getLocalPlayer()), 5000);
			// }

			// else {
			currentOb = getGameObjects()
					.closest(i -> i != null && i.getName().contains("Gap") && sixthRoofJump.contains(i));
			currentOb.interact("Jump");
			sleepUntil(() -> finalRoof.contains(getLocalPlayer()), Calculations.random(6000, 7000));
			// }
		}
		if (finalRoof.contains(getLocalPlayer())) {
			markCheck(finalRoof);
			currentOb = getGameObjects()
					.closest(i -> i != null && i.getName().contains("Gap") && finalRoofJump.contains(i));
			currentOb.interact("Jump");
			sleepUntil(() -> startArea.contains(getLocalPlayer()), Calculations.random(5000, 6000));
		}
		return 0;
	}

	public void markCheck(Area thisArea) {
		mark = getGroundItems().closest("Mark of grace");
		if (mark != null && thisArea.contains(mark)) {
			int markCount = getInventory().count("Mark of grace");
			mark.interact("Take");
			sleepUntil(() -> getInventory().count("Mark of grace") > markCount, Calculations.random(5000, 7000));
		}
	}

}
