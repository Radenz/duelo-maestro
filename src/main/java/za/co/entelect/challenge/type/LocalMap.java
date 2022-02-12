package za.co.entelect.challenge.type;

import za.co.entelect.challenge.entities.GameState;
import za.co.entelect.challenge.entities.Block;

import java.util.HashMap;

/**
 * {@code LocalMap} represents a map from the game
 * on a specific state.
 */
public class LocalMap {
    private HashMap<Integer, HashMap<Integer, Block>> lanes;

    private LocalMap() {}

    /**
     * Unwraps a map from game state and creates a new
     * {@code LocalMap} representing the map.
     * @param state current game state
     * @return a {@code LocalMap} representing the map
     * in the game state.
     */
    public static LocalMap from(GameState state) {
        LocalMap map = new LocalMap();
        map.lanes = new HashMap<>();

        for (int i = 1; i <= 4; i++) {
            HashMap<Integer, Block> lane = new HashMap<>();
            for (Block block: state.blocks.get(i - 1)) {
                lane.put(block.position.distance(), block);
            }
            map.lanes.put(i, lane);
        }

        return map;
    }

    /**
     * Gets a block from the map at specific lane and distance.
     * @return block at the specified lane and distance
     */
    public Block getBlock(int lane, int distance) {
        HashMap<Integer, Block> l = this.lanes.get(lane);
        return l == null ? null : l.get(distance);
    }

}
