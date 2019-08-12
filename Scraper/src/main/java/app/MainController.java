package scraper;

import entities.Comment;
import markov.Markov;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/comments/")
public class MainController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private HeadlineRepository headlineRepository;

    @Autowired
    private LinkRepository linkRepository;


    @RequestMapping(method = RequestMethod.GET)
    public Story getRandomStory() {


       Markov commentMarkov = new Markov();

       commentRepository.findAll().forEach(comment -> commentMarkov.parseText(comment.getComment()));

       Markov headlineMarkov = new Markov();

       headlineRepository.findAll().forEach(headline -> headlineMarkov.parseText(headline.getHeadline()));

       Story story = new Story();

       story.setHeadline(headlineMarkov.generate());

//       story.setLink(linkRepository.find();


       List<Comment> randomComments = new ArrayList<>();



       story.setComments(randomComments);


       return story;






    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus addComments() {
        Scraper scraper = new Scraper(commentRepository, headlineRepository, linkRepository);
        scraper.scrape();
        return HttpStatus.OK;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class BadRequestException extends RuntimeException {
        //
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        //
    }



}
