package core;

import java.awt.image.BufferedImage;
import java.util.List;

public class TileAnimated extends Tile {
	
    private List<BufferedImage> frames;
    private long frameDuration;

    public TileAnimated(int id, String name, List<BufferedImage> frames, long frameDuration, String path) {
        super(id, name, frames.get(0), path);
        this.frames = frames;
        this.frameDuration = frameDuration;
    }

    @Override
    public BufferedImage getImage() {
        //use the system time to determine which frame to show
        int index = (int) ((System.currentTimeMillis() / frameDuration) % frames.size());
        return frames.get(index);
    }
}
