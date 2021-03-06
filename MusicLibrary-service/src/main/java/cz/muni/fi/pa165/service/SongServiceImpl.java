/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.SongDao;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.exceptions.DataAccessException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Implementation of SongService
 *
 * @author Martin Kulisek
 */
@Service
public class SongServiceImpl implements SongService {

    @Inject
    private SongDao songDao;

    @Override
    public Song findById(Long id) {
        try {
            return songDao.findById(id);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<Song> findAll() {
        try {
            return songDao.findAll();
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void assignSongToAlbum(Long song_id, Album album) {
        try {
            Song song = songDao.findById(song_id);
            if (song != null) {
                song.setAlbum(album);
                songDao.update(song);

            }
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Song create(Song s) {
        try {
            songDao.create(s);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
        return s;
    }

    @Override
    public void delete(Song s) {
        try {
            songDao.delete(s);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<Song> getSongsForMusician(Musician musician) {
        try {
            return songDao.findByMusician(musician);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void updateSongPosition(Song song, int newPosition) {
        try {
            if (song.getAlbum() == null) {
            song.setPosition(newPosition);
            songDao.update(song);
            return;
            }

            Set<Song> songs = song.getAlbum().getSongs();

            if (newPosition <= 0 || newPosition > songs.size()) {
                throw new IllegalArgumentException("New position must be between zero and song count");
            }

            int origPosition = song.getPosition();
            for (Song s : songs) {
                int pos = s.getPosition();
                if (s.equals(song)) {
                    s.setPosition(newPosition);
                } else if (pos > origPosition && pos <= newPosition) {
                    s.setPosition(s.getPosition() - 1);
                } else {
                    continue;
                }

                songDao.update(s);
            }
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void updateSong(Song song) {
        try {
            songDao.update(song);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<Song> getSongsForGenre(Genre genre) {
        try {
            return songDao.findAll().stream().filter(s -> s.getGenre().equals(genre)).collect(Collectors.toList());
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

}
