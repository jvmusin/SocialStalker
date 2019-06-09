package musin.seeker.vkseeker.web;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.SimpleVkUser;
import musin.seeker.vkseeker.VkApi;
import musin.seeker.vkseeker.db.RelationChange;
import musin.seeker.vkseeker.db.RelationChangeService;
import musin.seeker.vkseeker.db.Seeker;
import musin.seeker.vkseeker.db.SeekerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@AllArgsConstructor
public class MainController {

    private final SeekerService seekerService;
    private final RelationChangeService relationChangeService;
    private final VkApi vkApi;

    @RequestMapping("/")
    public String index() {
        return "redirect:/seekers";
    }

    @RequestMapping("/seekers")
    public String seekers(Model model) {
        List<SeekerDto> seekers = seekerService.findAll().stream()
                .map(Seeker::getOwner)
                .map(this::seekerFromDb)
                .collect(toList());
        model.addAttribute("model", new SeekerListDto(seekers));
        model.addAttribute("newSeeker", new NewSeekerDto());
        return "seekers";
    }

    @RequestMapping("/seekers/{owner}/changes")
    public ModelAndView changes(@PathVariable("owner") int owner) {
        List<RelationChange> changes = relationChangeService.findAllByOwner(owner).stream()
                .filter(rc -> !rc.isHidden())
                .collect(toList());
        List<Integer> userIds = changes.stream()
                .map(RelationChange::getTarget)
                .collect(toList());
        vkApi.loadUsers(userIds);
        List<RelationChangeDto> changeDtos = changes.stream()
                .map(this::relationChangeDtoFromDb)
                .sorted(comparingInt(RelationChangeDto::getId).reversed())
                .collect(toList());
        return new ModelAndView("changes", "model", new FullSeekerDto(
                seekerFromDb(owner),
                changeDtos
        ));
    }

    @RequestMapping(value = "/seekers", method = POST)
    public String registerSeeker(@ModelAttribute NewSeekerDto newSeeker) {
        final List<RelationChange> changes = vkApi.buildChangesForNewUser(newSeeker.userId);
        seekerService.create(newSeeker.getUserId(), changes);
        return "redirect:/seekers";
    }

    private RelationChangeDto relationChangeDtoFromDb(RelationChange rc) {
        return new RelationChangeDto(
                rc.getId(),
                rc.getTime(),
                seekerFromDb(rc.getOwner()),
                seekerFromDb(rc.getTarget()),
                rc.getPrevType(),
                rc.getCurType()
        );
    }

    private SeekerDto seekerFromDb(int owner) {
        SimpleVkUser user = vkApi.loadUser(owner);
        String name = String.format("%s %s", user.getFirstName(), user.getLastName());
        return new SeekerDto(owner, name);
    }
}