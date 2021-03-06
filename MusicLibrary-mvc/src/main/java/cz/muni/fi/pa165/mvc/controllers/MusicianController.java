/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.mvc.Alert;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author David Pribula
 */
@Controller
@RequestMapping("/musicians")
public class MusicianController {

    final static Logger log = LoggerFactory.getLogger(MusicianController.class);

    public static final String REDIRECT_MUSICIANS = "redirect:/musicians/";

    @Autowired
    private MusicianFacade musicianFacade;

    @Autowired
    private AlbumFacade albumFacade;

    @Autowired
    private SongFacade songFacade;

    @RequestMapping("/")
    public String list(Model model) {
        model.addAttribute("musicians", musicianFacade.getAllMusicians());

        return "musicians/list";
    }

    @RequestMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        MusicianDTO musician = musicianFacade.getMusicianById(id);
        List<AlbumDTO> albums = albumFacade.getAlbumByMusician(musician);

        model.addAttribute("albums", albums);
        model.addAttribute("musician", musician);
        return "musicians/details";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String add(Model model) {
        model.addAttribute("musicianForm", new MusicianDTO());
        return "musicians/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doAdd(@ModelAttribute("musicianForm") MusicianDTO musician, RedirectAttributes redir) {
        if (!(musician.getName().trim().length() > 0)) {
            log.error("Name was not set in the form");
            redir.addFlashAttribute(Alert.ERROR, "You have to fill the name");
            return REDIRECT_MUSICIANS + "add";
        } else {
            musicianFacade.createMusician(musician);
            redir.addFlashAttribute(Alert.SUCCESS, "Successfuly created");
            return REDIRECT_MUSICIANS;
        }
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redir) {
        MusicianDTO musician = musicianFacade.getMusicianById(id);

        if (musician == null) {
            redir.addFlashAttribute(Alert.ERROR, "Musician with id '" + id + "' not found in the database!");
            return REDIRECT_MUSICIANS;
        }

        model.addAttribute("form", musician);
        return "musicians/edit";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doEdit(@PathVariable Long id, @ModelAttribute("form") MusicianDTO musicianForm, RedirectAttributes redir, HttpServletRequest request) {
        MusicianDTO musician = musicianFacade.getMusicianById(id);

        if (musician == null) {
            redir.addFlashAttribute(Alert.ERROR, "Musician with id '" + id + "' not found in the database!");
            return REDIRECT_MUSICIANS;
        } else if (!(musicianForm.getName().trim().length() > 0)) {
            log.error("Name was not set in the form");
            redir.addFlashAttribute(Alert.ERROR, "You have to fill the name");
            return REDIRECT_MUSICIANS + "{id}/edit";
        } else {

            // Update only relevant attributes
            musician.setName(musicianForm.getName());

            try {
                musicianFacade.updateMusician(musician);
                redir.addFlashAttribute(Alert.SUCCESS, "Successfuly updated");
            } catch (Exception ex) {
                ex.printStackTrace();
                redir.addFlashAttribute(Alert.ERROR, "Unable to update album (reason: " + ex.getMessage() + ")");
            }
        }
        return "redirect:/musicians/" + musician.getId();

    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String delete(@PathVariable Long id, RedirectAttributes redir) {
        MusicianDTO musician = musicianFacade.getMusicianById(id);

        if (musician == null) {
            redir.addFlashAttribute(Alert.ERROR, "Musician with id '" + id + "' does not exist!");
        } else {

            try {
                if (albumFacade.getAlbumByMusician(musician).size() > 0) {
                    redir.addFlashAttribute(Alert.ERROR, "You need to delete all albums connected to musician first.");
                } else if (songFacade.getSongsForMusician(musician).size() > 0) {

                    redir.addFlashAttribute(Alert.ERROR, "You need to delete all songs connected to musician first.");

                } else {
                    musicianFacade.deleteMusician(musician);
                    redir.addFlashAttribute(Alert.SUCCESS, "Successfuly deleted");
                }
            } catch (Exception ex) {
                //TODO: Logging
                ex.printStackTrace();
                redir.addFlashAttribute(Alert.ERROR, "Unable to delete musician (reason: " + ex.getMessage() + ")");
            }
        }
        return "redirect:/musicians/";
    }
}
