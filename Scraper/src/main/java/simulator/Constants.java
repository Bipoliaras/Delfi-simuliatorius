package simulator;

public class Constants {

  private Constants() {
  }

  public enum WebsiteLinks {
    NEWS("https://www.delfi.lt/news/daily/lithuania/"),
    CRIME("https://www.delfi.lt/news/daily/crime/"),
    WORLD("https://www.delfi.lt/news/daily/world/"),
    LIFE("https://www.delfi.lt/gyvenimas/"),
    SCIENCE("https://www.delfi.lt/mokslas/");

    private String url;

    WebsiteLinks(String url) {
      this.url = url;
    }

    public String getUrl() {
      return url;
    }

  }

  public enum CommentTypes {
    ANONYMOUS_MAIN,
    REGISTERED_MAIN
  }

  public enum InterestType {
    HOT,
    COLD
  }

}
