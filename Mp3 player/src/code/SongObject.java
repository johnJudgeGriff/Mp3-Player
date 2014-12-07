package code;

public class SongObject {

	private String name;
	private String artist;
	private String songPath;
	
	public SongObject(String name,
			String artist,
			String songPath) {
		
		this.name = name;
		this.artist = artist;
		this.songPath = songPath;
	}

	public String getName() {
		return name;
	}

	public String getArtist() {
		return artist;
	}

	public String getSongPath() {
		return songPath;
	}

}
