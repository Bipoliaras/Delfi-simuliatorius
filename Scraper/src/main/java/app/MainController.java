package app;

import entities.Comment;
import entities.Link;
import entities.Story;
import markov.Markov;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scraper.Scraper;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/comments/")
public class MainController {

    public static final int TOTAL_COMMENT_AMOUNT = 10;

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

       story.setLink(linkRepository.findAll().get(0));

       List<Comment> randomComments = new ArrayList<>();

       for(int comment = 0; comment < TOTAL_COMMENT_AMOUNT; comment++) {
           randomComments.add(new Comment(commentMarkov.generate()));
       }

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
