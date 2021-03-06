/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.api.dto;

import cz.muni.fi.pa165.utils.Constants;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Martin Kulisek
 */
public class SongCreateDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    private String title;

    private Long albumId;

    private Long musicianId;

    @NotNull
    @DecimalMin(value = "1")
    private int position;

    private Long genreId;

    @DecimalMin(value = "0")
    private int bitrate;

    @Size(max = Constants.INT_LENGTH_HUGE)
    private String commentary;

    /**
     * Returns unique identifier of song. <strong>Warning:</strong> relevant
     * identifier is provided only when the object is persisted!
     *
     * @return unique identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id of song
     *
     * @param id unique identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns title of song
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title of song Maximal title length is limited to
     * {@link Constants#INT_LENGTH_SMALL}.
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(Long musicianId) {
        this.musicianId = musicianId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    @Override
    public String toString() {
        return "SongCreateDTO{" + "id=" + id + ", title=" + title + ", albumId="
                + albumId + ", musicianId=" + musicianId + ", position=" + position
                + ", genreId=" + genreId + ", bitrate=" + bitrate + ", commentary="
                + commentary + '}';
    }

}
