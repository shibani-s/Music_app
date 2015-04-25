package com.example.shibanis.mymusic;

/**
 * Created by shibanis on 3/29/2015.
 */
public class Song {
    private long id;
    private String title;
    private String artist;
    private String year;
    public Song(long songID, String songTitle, String songArtist, String songYear) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        year=songYear;
    }
    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getYear(){return year;}
}
