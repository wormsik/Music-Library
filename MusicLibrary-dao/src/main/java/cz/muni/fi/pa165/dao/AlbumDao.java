package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Data access object layer interface for Album entity.
 *
 * @author Oldrich Konecny
 */
public interface AlbumDao {

	/**
	 * Returns album with given id or null.
	 *
	 * @param id album id
	 * @return album with given id
	 */
	public Album findById(Long id);

	/**
	 * Stores given album in database.
	 *
	 * @param album album to store
	 */
	public void create(Album album);

	/**
	 * Updates given album in database.
	 *
	 * @param album album to update
	 * @return updated instance
	 */
	public Album update(Album album);

	/**
	 * Deletes given album from database.
	 *
	 * @param album album to delete
	 */
	public void delete(Album album);

	/**
	 * Returns all albums from database.
	 *
	 * @return list containing all albums
	 */
	public List<Album> findAll();

	public List<Album> findAlbumByMusicianId(Long id);

	List<Album> findAlbumsByReleaseDates(LocalDate from, LocalDate to);

	List<Album> findAlbumsByPartialTitle(String partialTitle);

	List<Album> findBestRated(int limit);

	List<Album> findBestRated(int limit, Date upTo);
}
