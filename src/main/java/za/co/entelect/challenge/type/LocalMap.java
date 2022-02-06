package za.co.entelect.challenge.type;

import za.co.entelect.challenge.entities.GameState;
import za.co.entelect.challenge.entities.Block;

import java.util.HashMap;

public class LocalMap {
    private HashMap<Integer, HashMap<Integer, Block>> lanes;

    private LocalMap() {}

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

    public Block getBlock(int lane, int distance) {
        HashMap<Integer, Block> l = this.lanes.get(lane);
        return l == null ? null : l.get(distance);
    }

}
