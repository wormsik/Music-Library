package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.entity.Genre;
import static cz.muni.fi.pa165.util.EntityUtils.getValidGenre;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 * Unit tests for Genre DAO.
 *
 * @author David Pribula
 * @see GenreDao
 */
@ContextConfiguration(classes = AppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class GenreDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GenreDao genreDao;

    @Test
    public void createTest() {
        Genre genre = getValidGenre();
        genreDao.create(genre);

        Genre genre2 = genreDao.findById(genre.getId());
        assertEquals(genre, genre2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createWithNullParametersShouldRaiseException() {
        genreDao.create(null);
    }

    @Test
    public void findByIdTest() {
        Genre genre = getValidGenre();
        genreDao.create(genre);

        Genre genre2 = genreDao.findById(genre.getId());
        assertEquals(genre, genre2);
    }

    @Test
    public void findByIdOnNonExistingIdShouldReturnNull() {
        assertNull(genreDao.findById(100L));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdWithNullParameterShouldRaiseException() {
        genreDao.findById(null);
    }

    @Test
    public void findAllOnEmptyDbShouldReturnEmptyList() {
        assertTrue(genreDao.findAll().isEmpty());
    }

    @Test
    public void findAllWith2GenresReturnListOfSize2() {
        Genre genre = getValidGenre();
        genreDao.create(genre);

        Genre genre2 = getValidGenre();
        genre2.setName("Jazz");
        genreDao.create(genre2);

        assertEquals(2, genreDao.findAll().size());
    }

    @Test
    public void findAllReturnsCorrectList() {
        Genre genre = getValidGenre();
        genreDao.create(genre);

        Genre genre2 = getValidGenre();
        genre2.setName("Jazz");
        genreDao.create(genre2);

        List<Genre> genres = genreDao.findAll();
        assertTrue(genres.contains(genre) && genres.contains(genre2));
    }

    @Test
    public void afterDeletingGenreFindByIdShouldReturnNull() {
        Genre genre = getValidGenre();
        genreDao.create(genre);

        genreDao.delete(genre);
        assertNull(genreDao.findById(genre.getId()));
    }

    @Test
    public void deletingNonExistingGenreWillNotRaiseException() {
        Genre genre = getValidGenre();
        genreDao.delete(genre);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteWithNullParameterShouldRaiseException() {
        genreDao.delete(null);
    }

    @Test
    public void updateTest() {
        Genre genre = getValidGenre();
        genreDao.create(genre);
        String jazz = "Jazz";
        genre.setName(jazz);
        genreDao.update(genre);

        assertEquals(genreDao.findById(genre.getId()).getName(), jazz);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateWithNullParameterShouldRaiseException() {
        genreDao.update(null);
    }

}
