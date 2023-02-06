@complete @posts
Feature: Make posts
  As a developer
  I want to use the posts endpoint to create, read, update and delete posts
  So that I can create new posts, get existing posts, update posts, replace entire posts and delete posts
  
  @posts1
  Scenario: Successfully create a post
    Given I create a post
      | userId | title              | body                   |
     	|      1 | CREATED POST TITLE | CREATED POST BODY TEXT |
    When I fetch the post with id "1"
    Then the post title should be "CREATED POST TITLE"
    And the post body should be "CREATED POST BODY TEXT"
    