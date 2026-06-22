package data;

import java.util.ArrayDeque;
import java.util.Deque;

public class HistoryData {

	private static final int MAX_UNDO_STATES = 20;
	
	//history undo / redo actions inside tool
	private Deque<MapData> undoStack = new ArrayDeque<>();
	private Deque<MapData> redoStack = new ArrayDeque<>();
	
	protected MapState mapState;
	
	public HistoryData(MapState mapState) {
		this.mapState = mapState;
	}
	
	public void saveState() {
		//clear redo stack on any new action
		redoStack.clear();

		//deep copy the current state
		MapData state = new MapData(mapState.getData());

		//if max states are reached then always remove the oldest ones
		if (undoStack.size() >= MAX_UNDO_STATES) {
			undoStack.removeLast();
		}

		undoStack.push(state);
	}

	public void resetSaveState() {
		undoStack.clear();
		redoStack.clear();
	}

	public void undo() {
		if (undoStack.size() > 1) {
			MapData currentState = undoStack.pop();
			redoStack.push(currentState);

			MapData previousState = undoStack.peek();
			mapState.getData().applyState(previousState);
		}
	}

	public void redo() {
		if (!redoStack.isEmpty()) {
			MapData nextState = redoStack.pop();
			undoStack.push(nextState);
			mapState.getData().applyState(nextState);
		}
	}
}
