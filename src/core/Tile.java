package core;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Tile {
	
    private final int id; //new field to store the unique ID extracted from the filename
    final String name;
    protected final BufferedImage image; //fully loaded image
    private String path;
    
    private List<Autotile> autotiles = null;
    
    public Tile(int id, String name, BufferedImage image, String path) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void addAutotiles(Autotile e) {
    	if(autotiles == null) autotiles = new ArrayList<>();
    	autotiles.add(e);
    }
    
	public List<Autotile> getAutotiles() {
		return autotiles;
	}

	public String getPath() {
		return path;
	}

	@Override
    public String toString() {
        return "Tile [id=" + id + ", name=" + name + "]";
    }
}
