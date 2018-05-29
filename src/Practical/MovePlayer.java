package Practical;

import Visual.Board;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class MovePlayer {
	private Board board;
	private Player player;
	
	public MovePlayer(Board board, Player player) {
		this.board = board;
		this.player = player;
	}
	public boolean canMove(int diceTotal, Location loc) {
		if(loc.isPassageway()) {
			if(canUsePassageway(loc)) {
				//usePassage(loc);
				return true;
			}
			return false;
		}
		else {
			leaveRoom();
			int newRow = board.getBoard().getRowIndex(loc.getTile());
			int newCol = board.getBoard().getColumnIndex(loc.getTile());
			int currentRow = player.getRow();
			int currentCol = player.getCol();
			int distance=-1;
			//is currentCol greater that newCol
			if(currentCol>newCol) {
				distance=distance+(currentCol-newCol);
			}
			//else if currentCol is less than newCol
			else {//if(currentCol<newCol) {
				distance=distance+(newCol-currentCol);
			}
			//else currentCol and newCol are the same.
			//is currentRow greater than newRow
			if(currentRow>newRow) {
				distance=distance+(currentRow-newRow);
			}
			//else if currentRow is less that newRow
			else if(currentRow<newRow) {
				distance=distance+(newRow-currentRow);
			}
			//else they are the same
			//if the total distance is less than or equal to the dice total
			//movement is allowed
			if(diceTotal>distance) {
				player.setLocation(newCol, newRow);
				return true;
			}
			else {
				return false;
			}
		}
	}
	public void usePassage(Location start) {
		Location end;
		if(start.isPassageway()) {
			end = start.travelToLocation();
			
			player.setLocation(end.getCol(), end.getRow());
		}
	}
	private boolean canUsePassageway(Location passage) {
		for(Location loc : board.getTiles()) {
			if(player.getRow()==loc.getRow() && player.getCol()==loc.getCol()) {
				String passageName = passage.getName().toLowerCase();
				String roomName = loc.getName().toLowerCase();
				if(passageName.contains(roomName)) {
					return true;
				}
			}
		}
		return false;
	}
	private void usePassageWay(Location passage) {
		player.setLocation(passage.travelToLocation().getCol(), passage.travelToLocation().getRow());
	}
	private void leaveRoom() {
		Location room;
		for(Location loc : board.getTiles()) {
			if(player.getRow()==loc.getRow() && player.getCol()==loc.getCol()) {
				room=loc;
				if(room.getIsRoom()) {
					String roomName = room.getName().toLowerCase();
					for(Location newLoc : board.getTiles()) {
						String locName = newLoc.getName().toLowerCase();
						if(locName.contains(roomName) && locName.contains("door")) {
							int row = newLoc.getRow();
							int col = newLoc.getCol();
							player.setLocation(row, col);
						}
					}
				}
			}
		}
	}
}
