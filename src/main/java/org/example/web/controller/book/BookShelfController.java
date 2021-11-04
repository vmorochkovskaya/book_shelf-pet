package org.example.web.controller.book;

import org.apache.log4j.Logger;
import org.example.app.entity.book.Book;
import org.example.app.entity.enums.Rating;
import org.example.app.entity.tag.Tag;
import org.example.app.service.ResourceStorage;
import org.example.app.service.TagService;
import org.example.app.service.book.BookService;
import org.example.app.service.book.BooksRatingAndPopularityService;
import org.example.web.dto.BooksPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;
    private final ResourceStorage resourceStorage;
    private final BooksRatingAndPopularityService booksRatingAndPopularityService;
    private final TagService tagService;

    @Autowired
    public BookShelfController(BookService bookService, BooksRatingAndPopularityService booksRatingAndPopularityService,
                               TagService tagService, ResourceStorage resourceStorage) {
        this.bookService = bookService;
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
        this.tagService = tagService;
        this.resourceStorage = resourceStorage;
    }

    @GetMapping("/")
    public String books() {
        logger.info("got book shelf");
        return "index";
    }

    @ResponseBody
    @PostMapping("/bookReview")
    public void saveBookReview(@RequestBody Map<String, String> bookMap, HttpServletResponse response, Model model) {
        bookService.updateBookWithReview(bookMap.get("bookId"), bookMap.get("text"));
    }

    @PostMapping("/books/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        String resourcePath = resourceStorage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookService.getBookBySlug(slug);
        bookToUpdate.setImage(resourcePath);
        bookService.save(bookToUpdate);
        return "redirect:/books/" + slug;
    }

    @GetMapping("/books/recent-view")
    public String booksRecent() {
        logger.info("got book shelf");
        return "books/recent";
    }

    @GetMapping("/books/author")
    public String booksOfAuthor() {
        return "books/author";
    }

    @GetMapping("/books/popularview")
    public String booksPopular() {
        System.out.println("9858309584305");

        return "books/popular";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksRecommendedPage(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getBooksRecentPage(@RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit,
                                           @RequestParam(value = "from", required = false) String from,
                                           @RequestParam(value = "to", required = false) String to) {
        return new BooksPageDto(bookService.getPageOfRecentBooksSortedByPubBaseDesc(offset, limit, from, to).getContent());
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getBooksPopular2Page(@RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit) {
        return new BooksPageDto(booksRatingAndPopularityService.getPopularBooksSortedByPopularityDesc(offset, limit));
    }

    @GetMapping("/postponed")
    public String postponed() {
        return "postponed";
    }

    @GetMapping("/search")
    public String search() {
        return "search/index";
    }

    @GetMapping("/books/tag-view")
    public String getBooksByTag(@RequestParam("id") Integer id, Model model) {
        Tag tag = tagService.getTagById(id);
        model.addAttribute("tagName", tag.getName());
        model.addAttribute("tagId", id);
        model.addAttribute("tagBooks", tag.getBooks().stream().skip(0).limit(20).collect(Collectors.toList()));
        return "tags/index";
    }

    @GetMapping("/books/tag/{id}")
    @ResponseBody
    public BooksPageDto getNextBooksByTag(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit, @PathVariable(value = "id") Integer id) {
        return new BooksPageDto(bookService.getBooksByTagId(id, offset, limit).getContent());
    }

    @GetMapping("/books/{slug}**")
    public String bookPage(@PathVariable("slug") String slug, Model model) {
        System.out.println("ieoiwoeiqwpei");
        logger.info(String.format("got book by %1$s slug", slug));
        Book book = bookService.getBookBySlug(slug);
        Map<Rating, Long> mapOfRatings = bookService.getCountOfUsersMarkedBookPerRate(slug);
        model.addAttribute("slugBook", book);
        model.addAttribute("countOfOneStar", mapOfRatings.get(Rating.ONE));
        model.addAttribute("countOfTwoStars", mapOfRatings.get(Rating.TWO));
        model.addAttribute("countOfThreeStars", mapOfRatings.get(Rating.THREE));
        model.addAttribute("countOfFourStars", mapOfRatings.get(Rating.FOUR));
        model.addAttribute("countOfFiveStars", mapOfRatings.get(Rating.FIVE));
        model.addAttribute("ratingValue", booksRatingAndPopularityService.countBookRating(slug));
        model.addAttribute("reviewList", bookService.getBookReviews(slug));
        return "/books/slug";
    }

    @PostMapping("/books/changeBookRating")
    @ResponseBody
    public void changeBookRating(@RequestBody Map<String, String> bookMap) {
        booksRatingAndPopularityService.saveBookRate(bookMap.get("bookId"), bookMap.get("value"));
    }

    @ModelAttribute("bookList")
    public List<Book> bookList() {
        return bookService.getAllBooks();
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.getPageOfRecentBooks(0, 6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return booksRatingAndPopularityService.getPopularBooksSortedByPopularityDesc(0, 6);
    }

    @ModelAttribute("popularBooksSeparatePage")
    public List<Book> popularBooksSeparatePage() {
        return booksRatingAndPopularityService.getPopularBooksSortedByPopularityDesc(0, 20);
    }

    @ModelAttribute("recentBooksSeparatePage")
    public List<Book> recentBooksSeparatePage() {
        return bookService.getPageOfRecentBooks(0, 20).getContent();
    }

    @ModelAttribute("tags")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }
}
