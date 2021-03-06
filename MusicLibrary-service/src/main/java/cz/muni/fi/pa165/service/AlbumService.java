package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Album;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by olda on 20.11.2016.
 */
public interface AlbumService {

    Album findAlbumById(Long id);

    List<Album> findAllAlbums();

    Album createAlbum(Album album);

    void updateAlbum(Album album);

    void deleteAlbum(Album album);

    void changeCommentary(Album album, String newCommentary);

    List<Album> findAlbumsByMusicianId(Long musicianId);

    List<Album> findAlbumsByReleaseDates(LocalDate from, LocalDate to);

    List<Album> findAlbumsByPartialTitle(String partialTitle);

    Album createOrUpdateEverything(Album album);

    List<Album> getAlbums(List<Long> musicians, List<Long> genres);
}
