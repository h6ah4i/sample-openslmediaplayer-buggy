package soundifya.underground_architects.com.testsound;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HuNny on 28 Dec 2015.
 */
public class AudioModel implements Parcelable {
    private String title;
    private String album;
    private String data;
    private long duration;
    private String year;
    private String displayname;
    private String composer;
    private String tracknum;
    private String artist;
    private Uri albumarturi;
    private String disknum;
    private long albumId;
    private long trackId;
    private long artistId;
    private int trackIndex;


    public AudioModel()
    {

    }

    public AudioModel(AudioModel other) {
        this.title = other.title;
        this.album = other.album;
        this.data = other.data;
        this.duration = other.duration;
        this.year = other.year;
        this.displayname = other.displayname;
        this.composer = other.composer;
        this.tracknum = other.tracknum;
        this.artist = other.artist;
        this.albumarturi = other.albumarturi;
        this.disknum = other.disknum;
        this.albumId = other.albumId;
        this.trackId = other.trackId;
        this.artistId = other.artistId;
        this.trackIndex = other.trackIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getTracknum() {
        return tracknum;
    }

    public void setTracknum(String tracknum) {
        this.tracknum = tracknum;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Uri getAlbumarturi() {
        return albumarturi;
    }

    public void setAlbumarturi(Uri albumarturi) {
        this.albumarturi = albumarturi;
    }

    public String getDisknum() {
        return disknum;
    }

    public void setDisknum(String disknum) {
        this.disknum = disknum;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public int getTrackIndex() {
        return trackIndex;
    }

    public void setTrackIndex(int trackIndex) {
        this.trackIndex = trackIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.album);
        dest.writeString(this.data);
        dest.writeLong(this.duration);
        dest.writeString(this.year);
        dest.writeString(this.displayname);
        dest.writeString(this.composer);
        dest.writeString(this.tracknum);
        dest.writeString(this.artist);
        dest.writeParcelable(this.albumarturi, flags);
        dest.writeString(this.disknum);
        dest.writeLong(this.albumId);
        dest.writeLong(this.trackId);
        dest.writeLong(this.artistId);
        dest.writeInt(this.trackIndex);
    }

    protected AudioModel(Parcel in) {
        this.title = in.readString();
        this.album = in.readString();
        this.data = in.readString();
        this.duration = in.readLong();
        this.year = in.readString();
        this.displayname = in.readString();
        this.composer = in.readString();
        this.tracknum = in.readString();
        this.artist = in.readString();
        this.albumarturi = in.readParcelable(Uri.class.getClassLoader());
        this.disknum = in.readString();
        this.albumId = in.readLong();
        this.trackId = in.readLong();
        this.artistId = in.readLong();
        this.trackIndex = in.readInt();
    }

    public static final Creator<AudioModel> CREATOR = new Creator<AudioModel>() {
        @Override
        public AudioModel createFromParcel(Parcel source) {
            return new AudioModel(source);
        }

        @Override
        public AudioModel[] newArray(int size) {
            return new AudioModel[size];
        }
    };
}
