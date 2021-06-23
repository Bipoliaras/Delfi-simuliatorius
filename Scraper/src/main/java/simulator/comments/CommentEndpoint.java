package simulator.comments;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import simulator.Constants.CommentTypes;
import simulator.domain.entity.Comment;

@Service
public class CommentEndpoint {

  private static final String COMMENT_URL = "https://api.delfi.lt/comment/v1/graphql";

  private final RestTemplate restTemplate;

  private final ObjectMapper objectMapper;

  private final Logger logger = LoggerFactory.getLogger(CommentEndpoint.class);


  public CommentEndpoint(RestTemplate restTemplate,
      ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }

  public List<Comment> getComments(Long articleId, CommentTypes commentType) {

    try {

      var httpHeaders = new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);

      var jsonObject = new JSONObject();

      jsonObject.put("operationName", "cfe_getComments");
      jsonObject.put("variables", Map.of(
          "articleId", articleId,
          "modeType", commentType.name(),
          "orderBy", "DATE_DESC",
          "limit", 200,
          "offset", 0,
          "limitReplies", 3,
          "orderByReplies", "DATE_DESC"
      ));

      jsonObject.put("query",
          "fragment CommentBody on Comment {\n  id\n  subject\n  content\n  created_time\n  created_time_unix\n  article_entity {\n    article_id\n    count_total\n    count_anonymous\n    __typename\n  }\n  vote {\n    up\n    down\n    sum\n    __typename\n  }\n  author {\n    id\n    customer_id\n    idp_id\n    __typename\n  }\n  parent_comment {\n    id\n    subject\n    __typename\n  }\n  quote_to_comment {\n    id\n    subject\n    __typename\n  }\n  reaction {\n    comment_id\n    name\n    reaction\n    count\n    __typename\n  }\n  count_replies\n  count_registered_replies\n  status\n  __typename\n}\n\nquery cfe_getComments($articleId: Int!, $modeType: ModeType!, $offset: Int, $limit: Int, $orderBy: OrderBy, $limitReplies: Int, $orderByReplies: OrderBy, $lastCommentId: Int, $commentsBefore: Boolean) {\n  getCommentsByArticleId(article_id: $articleId) {\n    article_id\n    count_total\n    count_total_main_posts\n    count_registered\n    count_registered_main_posts\n    count_anonymous_main_posts\n    count_anonymous\n    comments(mode_type: $modeType, offset: $offset, limit: $limit, orderBy: $orderBy) {\n      ...CommentBody\n      replies(lastCommentId: $lastCommentId, commentsBefore: $commentsBefore, limit: $limitReplies, orderBy: $orderByReplies) {\n        ...CommentBody\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n}\n");

      HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), httpHeaders);

      String responseJson = restTemplate.postForObject(COMMENT_URL, request, String.class);

      return StreamSupport.stream(objectMapper
          .readTree(responseJson)
          .get("data")
          .get("getCommentsByArticleId")
          .get("comments")
          .spliterator(), false)
          .filter(this::isValidComment)
          .map(comment -> new Comment(
              comment.get("subject").asText(),
              comment.get("content").asText()))
          .collect(Collectors.toList());

    } catch (Exception ex) {
      logger.error(ex.toString());
    }

    return Collections.emptyList();
  }

  private boolean isValidComment(JsonNode comment) {
    return !comment.get("subject").asText().equals("null") || !comment.get("content").asText()
        .equals("null");
  }


}
